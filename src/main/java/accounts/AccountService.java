package accounts;

import dbService.DBException;
import dbService.DBService;

public class AccountService {
  private DBService dbService;

  public AccountService() {
    dbService = new DBService();
    dbService.printConnectInfo();
  }

  public void addNewUser(UserProfile userProfile) {
    try {
      dbService.addUser(userProfile.getLogin());
    } catch (DBException e) {
      e.printStackTrace();
    }
  }

  public UserProfile getUserByLogin(String login) {
    try {
      return dbService.getUser(login);
    } catch (DBException e) {
      e.printStackTrace();
    }
    return null;
  }
}
