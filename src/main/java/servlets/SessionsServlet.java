package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionsServlet extends HttpServlet {
  private final AccountService accountService;

  public SessionsServlet(AccountService accountService) {
    this.accountService = accountService;
  }

  //sign in
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String login = request.getParameter("login");
    UserProfile profile = accountService.getUserByLogin(login);
    accountService.addSession(request.getSession().getId(), profile);
    Gson gson = new Gson();
    String json = gson.toJson(profile);
    response.setContentType("text/html;charset=utf-8");
    response.getWriter().println(json);
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
