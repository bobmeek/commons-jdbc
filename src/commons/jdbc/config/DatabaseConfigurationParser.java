package commons.jdbc.config;

import java.util.*;

/**
 * This class is still experimental.
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class DatabaseConfigurationParser {

    private String driverParam   = "db.driverClass";
    private String urlParam      = "db.url";
    private String userParam     = "db.user";
    private String passwordParam = "db.password";

    public String getDriverParam() {
        return driverParam;
    }

    public void setDriverParam(String driverParam) {
        this.driverParam = driverParam;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getUserParam() {
        return userParam;
    }

    public void setUserParam(String userParam) {
        this.userParam = userParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }


    /**
     * 
     * @param resources
     * @return
     */
	@SuppressWarnings("unchecked")
	public List parse(ResourceBundle resources){
        List configurations = new ArrayList();
        Enumeration keys = resources.getKeys();
        while(keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            if(key.endsWith(driverParam)){
                configurations.add(parseDatabaseConfiguration(resources, key));
            }
        }
        return configurations;
    }
	
    private DatabaseConfiguration parseDatabaseConfiguration(ResourceBundle resources, String key) {
        String configurationId       = "";
        String configurationIdPrefix = "";
        int index = key.indexOf("." + this.driverParam);
        if(index > -1){
            configurationId = key.substring(0, key.indexOf("."));
            configurationIdPrefix = configurationId + ".";
        }

        DatabaseConfiguration configuration = new DatabaseConfiguration();
        configuration.setDriver  (resources.getString(configurationIdPrefix + driverParam));
        configuration.setUrl     (resources.getString(configurationIdPrefix + urlParam));
        configuration.setUser    (resources.getString(configurationIdPrefix + userParam));
        configuration.setPassword(resources.getString(configurationIdPrefix + passwordParam));
        configuration.setConfigurationId(configurationId);

        return configuration;
    }

    @SuppressWarnings("unchecked")
	public List parse(Properties resources){
        List configurations = new ArrayList();
        Enumeration keys = resources.keys();
        while(keys.hasMoreElements()){
            String key = (String) keys.nextElement();
            if(key.endsWith(driverParam)){
                configurations.add(parseDatabaseConfiguration(resources, key));
            }
        }
        return configurations;
    }

    private DatabaseConfiguration parseDatabaseConfiguration(Properties resources, String key) {
        String configurationId       = "";
        String configurationIdPrefix = "";
        int index = key.indexOf("." + this.driverParam);
        if(index > -1){
            configurationId = key.substring(0, key.indexOf("."));
            configurationIdPrefix = configurationId + ".";
        }

        DatabaseConfiguration configuration = new DatabaseConfiguration();
        configuration.setDriver  (resources.getProperty(configurationIdPrefix + driverParam));
        configuration.setUrl     (resources.getProperty(configurationIdPrefix + urlParam));
        configuration.setUser    (resources.getProperty(configurationIdPrefix + userParam));
        configuration.setPassword(resources.getProperty(configurationIdPrefix + passwordParam));
        configuration.setConfigurationId(configurationId);

        return configuration;
    }





}
