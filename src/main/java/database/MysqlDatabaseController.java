package database;

import constant_field.FoldName;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Database Controller.
 *
 * @version 1.0 2017年10月23日
 * @author Alex
 *
 */
public class MysqlDatabaseController {
    /**
     * Database Host.
     */
    private String dbHost;

    /**
     * Database Name.
     */
    private String dbName;

    /**
     * Database User Name.
     */
    private String userName;

    /**
     * Database User Password.
     */
    private String password;

    /**
     * Database Connection.
     */
    private Connection connection;

    /**
     * Constructor.
     */
    public MysqlDatabaseController() {
        initLoadJdbc();
        initConnection();
    }

    /**
     * Database Connection Configuration.
     */
    private void initLoadJdbc() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(
                    FoldName.RESOURCE + FoldName.DATABASE_CONFIGURATION));
            dbHost = properties.getProperty("dbHost");
            dbName = properties.getProperty("dbName");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connect DB.
     */
    private void initConnection() {
        try {
            connection = DriverManager.getConnection(dbHost + dbName
                    + "?useUnicode=true&characterEncoding=utf8"
                    + "&useJDBCCompliantTimezoneShift=true"
                    + "&useLegacyDatetimeCode=false&serverTimezone=UTC", userName, password);
            System.out.println("以連結至:" + dbHost + ":" + dbName + ":" + userName + ":" + password + "資料庫。");
            //statement=connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * INSERT INTO 資料表 (Column Name...) VALUES (Column Values...).
     * @param tableName table name
     * @param obj sql obj
     */
    public void execInsert(String tableName, SqlObject obj) {
        String sql = "INSERT INTO " + tableName + " (" + obj.getColumnNameString() + ")"
                + " VALUES (" + obj.getColumnValueString() + ");";
        System.out.println(sql);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("The data can't loaded into db " + tableName + " table.");
        }
    }

    /**
     * DELETE FROM 哪些資料表 WHERE 限制的條件.
     * @param tableName table name
     * @param condition condition
     */
    public void execDelete(String tableName, String condition) {
        String sql = "DELETE FROM " + tableName + " WHERE " + condition;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("The data can't loaded into db " + tableName + " table.");
        }
    }

    /**
     * UPDATE Table SET Column = [value] WHERE Condition.
     * @param tableName table name
     * @param obj obj
     * @param condition condition
     */
    public void execUpdate(String tableName, SqlObject obj, String condition) {
        String sql = "UPDATE " + tableName + " SET " + obj.getColumnNameValuePairString() + " " + condition + ";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("The data can't loaded into db " + tableName + " table.");
        }
    }

    /**
     * SELECT 所需求之欄位 FROM 哪些資料表 WHERE 限制的條件.
     * @param column columns
     * @param tableName table name
     * @param condition condition
     * @return result set 存取所抓取之欄位
     */
    public ResultSet execSelect(String column, String tableName, String condition) {
        String sql = "SELECT " + column + " FROM " + tableName + " WHERE " + condition;
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}