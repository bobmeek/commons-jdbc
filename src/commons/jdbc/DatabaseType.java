package commons.jdbc;
import java.sql.*;

/**
 * Constants define the different database types
 * @author Jeff S Smith
 * @author Paolo Orru (paolo.orru@gmail.com), added PostgreSQL constants
 */
public class DatabaseType
{
	public final static int UNKNOWN = 0;
	public final static int ORACLE = 1;
	public final static int MYSQL = 2;
	public final static int POSTGRESQL = 3;
	public final static int HSQL = 4;

	public final static String ORACLE_NAME = "ORACLE";
	public final static String MYSQL_NAME = "MYSQL";
	public final static String POSTGRESQL_NAME = "POSTGRESQL";
	public final static String HSQL_NAME = "HSQL";

	/**
	 * Parses the connection info to determine the database type 
	 * @param con Connection
	 * @return int type of database (e.g. ORACLE)
	 */
	static int getDbType(Connection con)
	{
		String dbName = null;
		int dbType = 0;

		try
		{
			dbName = con.getMetaData().getDatabaseProductName();

			if (dbName.toUpperCase().indexOf(ORACLE_NAME) > -1)
				dbType = ORACLE;
			else if (dbName.toUpperCase().indexOf(MYSQL_NAME) > -1)
				dbType = MYSQL;
			else if (dbName.toUpperCase().indexOf(POSTGRESQL_NAME) > -1)
				dbType = POSTGRESQL;
			else if (dbName.toUpperCase().indexOf(HSQL_NAME) > -1)
				dbType = HSQL;
		}
		catch (SQLException e)
		{
			System.out.println("Exception: unknown database");
			e.printStackTrace();                        
		}
		return(dbType);
	}

	/**
	 * Parses the driver name to determine the database type
	 * @param driverName String
	 * @return int type of database (e.g. ORACLE)
	 */
	static int getDbType(String driverName)
	{
		int dbType = 0;
		if (driverName.toUpperCase().indexOf(ORACLE_NAME) > -1)
			dbType = ORACLE;
		else if (driverName.toUpperCase().indexOf(MYSQL_NAME) > -1)
			dbType = MYSQL;
		else if (driverName.toUpperCase().indexOf(POSTGRESQL_NAME) > -1)
			dbType = POSTGRESQL;
		else if (driverName.toUpperCase().indexOf(HSQL_NAME) > -1)
			dbType = HSQL;
		return(dbType);                    
	}
}
