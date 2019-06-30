package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
  @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
  private final AccountService accountService;

  public SignUpServlet(AccountService accountService) {
    this.accountService = accountService;
  }

  //get public user profile
  public void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    //todo: module 2 home work
  }

  //sign up
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String login = request.getParameterNames().nextElement();
    UserProfile userProfile = new UserProfile(login);
    accountService.addNewUser(userProfile);
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
