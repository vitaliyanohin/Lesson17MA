package servlets;

import accounts.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
  @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
  private final AccountService accountService;

  public SignInServlet(AccountService accountService) {
    this.accountService = accountService;
  }

  //sign up
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String login = request.getParameterNames().nextElement();
    if (accountService.getUserByLogin(login) == null) {
      response.setContentType("text/html;charset=utf-8");
      response.getWriter().println("Unauthorized");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    } else {
      response.setContentType("text/html;charset=utf-8");
      response.getWriter().println("Authorized: " + login);
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  //change profile
  public void doPut(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    //todo: module 2 home work
  }

  //unregister
  public void doDelete(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    //todo: module 2 home work
  }
}
