package ik.soaptest.soaptest.db;

import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;

import javax.sql.PooledConnection;
import java.sql.SQLException;

abstract class AbstractDAO {

    private final static String host = "";
    private final static String port = "";
    private final static String dbName = "";
    private final static String user = "";
    private final static String pwd = "";
    protected PooledConnection pooledConn;

    public AbstractDAO() {
        SQLServerXADataSource dataSource = new SQLServerXADataSource();
        dataSource.setURL("jdbc:sqlserver://" + host + ":" + port +
                ";databaseName=" + dbName + ";user=" + user + ";password=" + pwd);
        try {
            this.pooledConn = dataSource.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        if (null != pooledConn) {
            try {
                pooledConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
