package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SessionsServlet extends HttpServlet {
  private final AccountService accountService;

  public SessionsServlet(AccountService accountService) {
    this.accountService = accountService;
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws IOException {
    Map<String, Object> pageVariables = createPageVariablesMap(request);
    pageVariables.put("message", "");
    response.getWriter().println(request.getParameter("key"));
    response.setContentType("text/html;charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);
  }
  //sign in
  public void doPost(HttpServletRequest request, HttpServletResponse response)
          throws IOException {
    String login = request.getParameter("login");
    UserProfile profile = accountService.getUserByLogin(login);
    accountService.addSession(request.getSession().getId(), profile);
    Gson gson = new Gson();
    String json = gson.toJson(profile);
    response.setContentType("text/html;charset=utf-8");
    response.getWriter().println(json);
    response.setStatus(HttpServletResponse.SC_OK);
  }
  private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
    Map<String, Object> pageVariables = new HashMap<>();
    pageVariables.put("method", request.getMethod());
    pageVariables.put("URL", request.getRequestURL().toString());
    pageVariables.put("pathInfo", request.getPathInfo());
    pageVariables.put("sessionId", request.getSession().getId());
    pageVariables.put("parameters", request.getParameterMap().toString());
    return pageVariables;
  }
}
