package ik.soaptest.db;

import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;

import javax.sql.PooledConnection;
import java.sql.SQLException;

abstract class AbstractDAO {

    private final static String host = "A1QA-TASKS-1";
    private final static String port = "1433";
    private final static String dbName = "ServiceDB";
    private final static String user = "TESTSRV";
    private final static String pwd = "123qweASD";
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
