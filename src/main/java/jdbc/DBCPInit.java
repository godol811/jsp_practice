package jdbc;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.DriverManager;


public class DBCPInit extends HttpServlet {

    @Override
    public void init() throws ServletException {
        loadJDBCDriver();
        initConnectionPool();

    }

    private void loadJDBCDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("fail to load JDBC Driver", ex);
        }
    }

    private void initConnectionPool() {
        try {
            String jdbcDriver =  "jdbc:mysql://localhost/Main?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
            String dbUser = "root";
            String dbPass = "qwer1234";

//            String query = "select * from MEMBERS order by MEMBERID";


            ConnectionFactory connFactory =
                    new DriverManagerConnectionFactory(jdbcDriver, dbUser, dbPass);

            PoolableConnectionFactory poolableConnFactory =
                    new PoolableConnectionFactory(connFactory, null);
            poolableConnFactory.setValidationQuery("select 1");

            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
            poolConfig.setTestWhileIdle(true);
            poolConfig.setMinIdle(4);
            poolConfig.setMaxTotal(50);

            GenericObjectPool<PoolableConnection> connectionPool =
                    new GenericObjectPool<>(poolableConnFactory, poolConfig);
            poolableConnFactory.setPool(connectionPool);

            Class.forName("org.apache.commons.dbcp2.PoolingDriver");
            PoolingDriver driver =
                    (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
            driver.registerPool("Main", connectionPool);
            //풀이름 설정
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
