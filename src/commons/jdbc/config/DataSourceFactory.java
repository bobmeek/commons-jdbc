package commons.jdbc.config;

import javax.sql.DataSource;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * This class is still experimental.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class DataSourceFactory {

    private Map configurationMap     = new HashMap();
    private Map dataSourceMap        = new HashMap();


    public DataSourceFactory(){
        String propertyFileName = System.getProperty("mrpersister.db.propertyfile");
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(propertyFileName);
            init(resourceBundle);
        } catch (java.util.MissingResourceException e) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(propertyFileName));
                init(properties);
            } catch (IOException e1) {
                throw new IllegalStateException("Cannot find database property file " + propertyFileName +
                        " in neither classpath nor local file system.");
            }
        }

    }

    public DataSourceFactory(ResourceBundle resources){
        init(resources);
    }

    private void init(ResourceBundle resources) {
        synchronized(this){
            DatabaseConfigurationParser parser = new DatabaseConfigurationParser();
            List configurations = parser.parse(resources);
            for (Iterator iterator = configurations.iterator(); iterator.hasNext();) {
                DatabaseConfiguration configuration = (DatabaseConfiguration) iterator.next();
                this.configurationMap.put(configuration.getConfigurationId(), configuration);
                DataSource dataSource = new SimpleDataSource(
                        configuration.getDriver(), configuration.getUrl(),
                        configuration.getUser(),   configuration.getPassword());

                this.dataSourceMap.put(configuration.getConfigurationId(), dataSource);

            }
        }
    }
    private void init(Properties resources) {
        synchronized(this){
            DatabaseConfigurationParser parser = new DatabaseConfigurationParser();
            List configurations = parser.parse(resources);
            for (Iterator iterator = configurations.iterator(); iterator.hasNext();) {
                DatabaseConfiguration configuration = (DatabaseConfiguration) iterator.next();
                this.configurationMap.put(configuration.getConfigurationId(), configuration);
                DataSource dataSource = new SimpleDataSource(
                        configuration.getDriver(), configuration.getUrl(),
                        configuration.getUser(),   configuration.getPassword());

                this.dataSourceMap.put(configuration.getConfigurationId(), dataSource);

            }
        }
    }

    public synchronized DataSource getDataSource(){
        return getDataSource(System.getProperty("commons.jdbc.db.datasourceId"));
    }

    public synchronized DataSource getDataSource(String configurationId){
        if(configurationId == null) configurationId = "";
        return (DataSource) this.dataSourceMap.get(configurationId);
    }


    public static DataSourceFactory createDataSourceFactory(ResourceBundle resources){
        if(resources == null){
            throw new NullPointerException("Error creating DataSourceFactory. The resource bundle supplied was null.");
        }
        return new DataSourceFactory(resources);
    }






}
