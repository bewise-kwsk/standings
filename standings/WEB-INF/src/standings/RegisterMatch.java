package standings;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class RegisterMatch extends HttpServlet {
  public void doPost(HttpServletRequest req, HttpServletResponse resp) 
    throws IOException, ServletException 
  {
    HttpSession session = null;
    Match new_match = null;
    try {
      resp.setContentType("text/html; charset=UTF-8");
      session = req.getSession(false);
      if (session == null || (new_match = (Match)session.getAttribute("match")) == null) {
        if (session != null && (new_match = (Match)session.getAttribute("stored_match")) != null) {
          // 登録完了後の再訪
          resp.sendRedirect("stored_match.jsp");
        } else {
          // 登録試合情報が取得できないエラー
          if (session == null)
            session = req.getSession(true);
          session.setAttribute("message", "登録する試合情報を受け取れませんでした");
          resp.sendRedirect("internal_error.jsp");
        }
      } else {
        // 試合をデータベースに登録する
        new_match.store();
        session.removeAttribute("match");
        session.setAttribute("stored_match", new_match);
        resp.sendRedirect("register_match.jsp");
      }
    } catch (Exception ex) {
      if (session == null)
        session = req.getSession(true);
      session.setAttribute("message", ex.getMessage());
      resp.sendRedirect("internal_error.jsp");
    }
  }
}
