package standings;

import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;


public class StandingsList extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException
  {
    HttpSession session = null;
    try {
      List<Standing> standings = Match.listStandings();
      session = request.getSession(true);
      session.setAttribute("standings", standings);
      response.sendRedirect("standings_list.jsp");
    } catch (InternalErrorException ex) {
      if (session == null)
        session = request.getSession(true);
      session.setAttribute("message", ex.getMessage());
      response.sendRedirect("internal_error.jsp");
    }
  }
}
