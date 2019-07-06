package servlets;

import accounts.AccountService;
import accounts.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {

  @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
  private final AccountService accountService;

  public SignUpServlet(AccountService accountService) {
    this.accountService = accountService;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    String login = request.getParameterNames().nextElement();
    UserProfile userProfile = new UserProfile(login);
    accountService.addNewUser(userProfile);
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
