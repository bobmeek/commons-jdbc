/*
    Copyright 2004-2006 Jenkov Development

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/



package commons.jdbc.config;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.PrintWriter;

/**
 * This class is still experimental. Though Mr. Persister will eventually get
 * some connection creation mechanism this may not be it.
 * 
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class SimpleDataSource implements DataSource{

    private String driver   = null;
    private String url      = null;
    private String user     = null;
    private String password = null;

    /**
     *
     *
     * @param driver
     * @param url
     * @param user
     * @param password
     * @throws IllegalArgumentException If the driver cannot be instantiated.
     */
    public SimpleDataSource(String driver, String url, String user, String password) {
        validateDriver(driver);

        this.driver   = driver;
        this.url      = url;
        this.user     = user;
        this.password = password;
    }

    private void validateDriver(String driver) {
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Error instantiating JDBC driver: " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Error instantiating JDBC driver: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Error instantiating JDBC driver: " + e.getMessage());
        }
    }

    /**
     * Not supported.
     * @return 0
     * @throws SQLException Never.
     */
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    /**
     * Not supported.
     * @throws SQLException Never.
     */
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    /**
     * Not supported.
     * @throws SQLException Never.
     */
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    /**
     * Not supported.
     * @throws SQLException Never.
     */
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.user, this.password);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(this.url, username, password);
    }

    
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
