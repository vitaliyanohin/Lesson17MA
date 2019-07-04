package accounts;

import dbService.DBException;
import dbService.DBService;

import java.util.Optional;

public class AccountService {
  private DBService dbService;

  public AccountService() {
    dbService = new DBService();
    dbService.printConnectInfo();
  }

  public void addNewUser(UserProfile userProfile) {
    dbService.addUser(userProfile);
  }

  public Optional<UserProfile> getUserByLogin(String login) {
    return Optional.ofNullable(dbService.getUser(login));
  }
}
