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
    return executor.execQuery("select * from users where user_name='" + login + "'",
            result -> {
                      result.next();
                      return new UserProfile(result.getString(2));
                      });
  }

  public void insertUser(UserProfile userProfile) throws SQLException {
    executor.execUpdate("insert into users (user_name, password) values "
            + "('" + userProfile.getEmail() + "', " + "'" + userProfile.getPass() + "');");
  }

  public void createTable() throws SQLException {
    executor.execUpdate("create table if not exists users (id bigint auto_increment," +
            " user_name varchar(256), password varchar(256), primary key (id))");
  }

  public void dropTable() throws SQLException {
    executor.execUpdate("drop table users");
  }
}
