package commons.db.helper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JdbcResultFactories {

    private JdbcResultFactories() {
    }


    public static <T> JdbcResultFactory<T> build(Class<T> objectClass) {
        return build(objectClass, (List<String>)null);
    }

    public static <T> JdbcResultFactory<T> build(Class<T> objectClass, String ... fields) {
        return build(objectClass, Arrays.asList(fields));
    }

    public static <T> JdbcResultFactory<T> build(Class<T> objectClass, List<String> fields) {
        return objectClass.isPrimitive()
                ? new PrimitiveFactory<T>(objectClass, fields)
                : new ClassFactory<T>(objectClass, fields);
    }



    public static <T> JdbcResultFactory<T> buildPrimitive(Class<T> objectClass) {
        return buildPrimitive(objectClass, 0);
    }

    public static <T> JdbcResultFactory<T> buildPrimitive(Class<T> objectClass, int columnIndex) {
        return new PrimitiveFactory<T>(objectClass, columnIndex);
    }

    public static <T> JdbcResultFactory<T> buildPrimitive(Class<T> objectClass, String field) {
        return new PrimitiveFactory<T>(objectClass, field);
    }



    public static <T> JdbcResultFactory<T> buildClass(Class<T> objectClass) {
        return buildClass(objectClass, (List<String>)null);
    }

    public static <T> JdbcResultFactory<T> buildClass(Class<T> objectClass, String ... fields) {
        return buildClass(objectClass, Arrays.asList(fields));
    }

    public static <T> JdbcResultFactory<T> buildClass(Class<T> objectClass, List<String> fields) {
        return new ClassFactory<T>(objectClass, fields);
    }



    public static class PrimitiveFactory<T> implements JdbcResultFactory<T> {

        private final Class<T> objectClass;
        private final String field;
        private int columnIndex;

        public PrimitiveFactory(Class<T> objectClass, int columnIndex) {
            this.objectClass = objectClass;
            this.field = null;
            this.columnIndex = columnIndex;
        }

        public PrimitiveFactory(Class<T> objectClass, String field) {
            this.objectClass = objectClass;
            this.field = field;
            this.columnIndex = 0;
        }

        public PrimitiveFactory(Class<T> objectClass, List<String> fields) {
            this.objectClass = objectClass;
            this.field = fields == null || fields.isEmpty() ? null : fields.get(0);
            this.columnIndex = 0;
        }

        public void init(ResultSet result) throws SQLException {
            if (field != null) {
                ResultSetMetaData meta = result.getMetaData();
                int count = meta.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    String label = meta.getColumnLabel(i);
                    if (label.equals(field)) {
                        columnIndex = i;
                        break;
                    }
                }
            }
        }

        @SuppressWarnings("unchecked")
        public T create(ResultSet result) throws SQLException {
            Object value = result.getObject(columnIndex);
            if (value instanceof BigDecimal) value = new Long(((BigDecimal)value).longValue());
            if (!objectClass.isInstance(value)) throw new IllegalArgumentException();
            return (T) value;
        }

    }

    public static class ClassFactory<T> implements JdbcResultFactory<T> {

        private final Class<T> objectClass;
        private List<String> fields;

        public ClassFactory(Class<T> objectClass, List<String> fields) {
            this.objectClass = objectClass;
            this.fields = fields;
        }

        public void init(ResultSet result) throws SQLException {
            if (fields == null) {
                fields = new ArrayList<String>();
                ResultSetMetaData meta = result.getMetaData();
                int count = meta.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    String label = meta.getColumnLabel(i);
                    if (label.length()>0) fields.add(label);
                }
            }
        }

        public T create(ResultSet result) throws SQLException {
            try {
                T obj = objectClass.newInstance();
                for (String field : fields) {
                    Object value = result.getObject(field);
                    if (value instanceof BigDecimal) value = new Long(((BigDecimal)value).longValue());
                    objectClass.getDeclaredField(field).set(obj, value);
                }
                return obj;
            } catch (InstantiationException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
