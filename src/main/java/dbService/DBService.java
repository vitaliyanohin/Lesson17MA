package dbService;

import accounts.UserProfile;
import dbService.dao.UsersDAO;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
  private final Connection connection;

  public DBService() {
    this.connection = getH2Connection();
  }

  public UserProfile getUser(String name) throws DBException {
    try {
      return (new UsersDAO(connection).getUserProfile(name));
    } catch (SQLException e) {
        throw new DBException(e);
    }
  }

  public UserProfile addUser(UserProfile userProfile) throws DBException {
    try {
      connection.setAutoCommit(false);
      UsersDAO dao = new UsersDAO(connection);
      dao.createTable();
      dao.insertUser(userProfile);
      connection.commit();
      return dao.getUserProfile(userProfile.getLogin());
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ignore) {
      }
      throw new DBException(e);
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException ignore) {
      }
    }
  }

  public void cleanUp() throws DBException {
    UsersDAO dao = new UsersDAO(connection);
    try {
      dao.dropTable();
    } catch (SQLException e) {
      throw new DBException(e);
    }
  }

  public void printConnectInfo() {
    try {
      System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
      System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
      System.out.println("Driver: " + connection.getMetaData().getDriverName());
      System.out.println("Autocommit: " + connection.getAutoCommit());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Connection getH2Connection() {
    try {
      String url = "jdbc:h2:./h2db";
      String name = "tully";
      String pass = "tully";

      JdbcDataSource ds = new JdbcDataSource();
      ds.setURL(url);
      ds.setUser(name);
      ds.setPassword(pass);

      Connection connection = DriverManager.getConnection(url, name, pass);
      return connection;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
