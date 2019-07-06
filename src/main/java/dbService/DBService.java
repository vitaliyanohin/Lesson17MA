package dbService;

import accounts.UserProfile;
import dbService.dao.UsersDAO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DBService {

  private static final Logger LOGGER =  Logger.getLogger(DBService.class);

  private final Connection connection;

  public DBService() {
    this.connection = getH2Connection();
  }

  public Optional<UserProfile> getUser(String login) {
    try {
      return  Optional.of(new UsersDAO(connection).getUserProfile(login));
    } catch (SQLException e) {
       LOGGER.log(Level.ALL, "Error: ", e);
    }
    return Optional.empty();
  }

  public Optional<UserProfile> addUser(UserProfile userProfile) {
    try {
      connection.setAutoCommit(false);
      UsersDAO dao = new UsersDAO(connection);
      dao.createTable();
      dao.insertUser(userProfile);
      connection.commit();
      return Optional.of(dao.getUserProfile(userProfile.getLogin()));
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ex) {
         LOGGER.log(Level.ALL, "Error: ", ex);
      }
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException e) {
         LOGGER.log(Level.ALL, "Error: ", e);
      }
    }
    return Optional.empty();
  }

  public void cleanUp() {
    UsersDAO dao = new UsersDAO(connection);
    try {
      dao.dropTable();
    } catch (SQLException e) {
       LOGGER.log(Level.ALL, "Error: ", e);
    }
  }

  public void printConnectInfo() {
    try {
      System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
      System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
      System.out.println("Driver: " + connection.getMetaData().getDriverName());
      System.out.println("Autocommit: " + connection.getAutoCommit());
    } catch (SQLException e) {
       LOGGER.log(Level.ALL, "Error: ", e);
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
       LOGGER.log(Level.ALL, "Error: ", e);
    }
    return null;
  }
}
