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
       LOGGER.log(Level.ERROR, "Failed to get user by login: ", e);
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
       LOGGER.log(Level.ERROR, "Failed to set user: ", e);
      try {
        connection.rollback();
      } catch (SQLException ex) {
         LOGGER.log(Level.ERROR, "Failed to rollback user: ", ex);
      }
    } finally {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException e) {
         LOGGER.log(Level.ERROR, "Failed to set AutoCommit: ", e);
      }
    }
    return Optional.empty();
  }

  public void cleanUp() {
    UsersDAO dao = new UsersDAO(connection);
    try {
      dao.dropTable();
    } catch (SQLException e) {
       LOGGER.log(Level.ERROR, "Failed to drop table: ", e);
    }
  }

  public void printConnectInfo() {
    try {
      LOGGER.info("DB name: " + connection.getMetaData().getDatabaseProductName());
      LOGGER.info("DB version: " + connection.getMetaData().getDatabaseProductVersion());
      LOGGER.info("Driver: " + connection.getMetaData().getDriverName());
      LOGGER.info("Autocommit: " + connection.getAutoCommit());
    } catch (SQLException e) {
       LOGGER.log(Level.ERROR, "Failed to get table status: ", e);
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
       LOGGER.log(Level.ERROR, "Failed to get connection: ", e);
    }
    return null;
  }
}
