package dbService.dao;

import accounts.UserProfile;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

  private Executor executor;

  public UsersDAO(Connection connection) {
    this.executor = new Executor(connection);
  }

  public UserProfile getUserProfile(String login) throws SQLException {
    return executor.execQuery("SELECT * FROM users WHERE user_name='" + login + "'",
            result -> {
                      result.next();
                      return new UserProfile(result.getString(2));
                      });
  }

  public void insertUser(UserProfile userProfile) throws SQLException {
    executor.execUpdate("INSERT INTO users (user_name, password) VALUES "
            + "('" + userProfile.getEmail() + "', " + "'" + userProfile.getPass() + "');");
  }

  public void createTable() throws SQLException {
    executor.execUpdate("CREATE TABLE IF NOT EXISTS users (id bigint auto_increment," +
            " user_name varchar(256), password varchar(256), primary key (id))");
  }

  public void dropTable() throws SQLException {
    executor.execUpdate("DROP TABLE users");
  }
}
