package Controllers;

import Entidades.JDBC;

/**
 *
 * @author JoaoAN2
 */
public class Connect {

    public Connect(JDBC jdbc, String hostname, String username, String password, String jdbcDriver, String databaseName, String databasePrefix, String databasePort) {
        jdbc.setHostName(hostname);
        jdbc.setUserName(username);
        jdbc.setPassword(password);
        jdbc.setJdbcDriver(jdbcDriver);
        jdbc.setDataBaseName(databaseName);
        jdbc.setDataBasePrefix(databasePrefix);
        jdbc.setDataBasePort(databasePort);
        
    }

}
