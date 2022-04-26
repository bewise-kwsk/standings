package standings;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class AddMatch extends HttpServlet {
  private Match validateRequestParameters(HttpServletRequest req) 
    throws InvalidMatchParameterException {
    Match match = new Match();
    // 節 (section) は1から34までの整数であることを検査
    String section_str = req.getParameter("section").trim();
    int section;
    if (section_str == null || section_str.equals("")) {
      throw new InvalidMatchParameterException("節が指定されていません");
    }
    try {
      section = Integer.parseInt(section_str);
    } catch (NumberFormatException ex) {
      throw new InvalidMatchParameterException("節の値 (" + section_str + ")が不正です。整数値を指定してください");
    }
    match.setSection(section);

    // 試合開催日 (date) は有効な日付であることを検査
    String date_str = req.getParameter("date").trim();
    LocalDate date;
    if (date_str == null || date_str.equals("")) {
      throw new InvalidMatchParameterException("試合開催日が指定されていません");
    }
    try {
      date = LocalDate.parse(date_str);
    } catch (DateTimeParseException ex) {
      throw new InvalidMatchParameterException("試合開催日の値 (" + date_str + ")が不正です。有効な日付をYYYY-MM-DD形式で指定してください");
    }
    match.setDate(date);

    // ホームチーム名が有効であることを検査
    String home = req.getParameter("home").trim();
    if (home == null || home.equals("")) {
      throw new InvalidMatchParameterException("ホームチームが指定されていません");
    }
    match.setHome(home);

    // アウェイチーム名が有効であることを検査
    String away = req.getParameter("away").trim();
    if (away == null || away.equals("")) {
      throw new InvalidMatchParameterException("アウェイチーム名が指定されていません");
    }
    match.setAway(away);

    // ホームチーム得点 (goals_for) 有効であることを検査
    String goals_for_str = req.getParameter("goals_for").trim();
    int goals_for;
    if (goals_for_str == null || goals_for_str.equals("")) {
      throw new InvalidMatchParameterException("ホームチーム得点が指定されていません");
    }
    try {
      goals_for = Integer.parseInt(goals_for_str);
    } catch (NumberFormatException ex) {
      throw new InvalidMatchParameterException("ホームチーム得点の値 (" + goals_for_str + ")が不正です。0以上の整数で指定してください");
    }
    match.setGoalsFor(goals_for);

    // アウェイチーム得点 (goals_against) 有効であることを検査
    String goals_against_str = req.getParameter("goals_against").trim();
    int goals_against;
    if (goals_against_str == null || goals_against_str.equals("")) {
      throw new InvalidMatchParameterException("アウェイチーム得点が指定されていません");
    }
    try {
      goals_against = Integer.parseInt(goals_against_str);
    } catch (NumberFormatException ex) {
      throw new InvalidMatchParameterException("アウェイチーム得点の値 (" + goals_against_str + ")が不正です。0以上の整数で指定してください");
    }
    match.setGoalsAgainst(goals_against);

    return match;
  }

  public void doPost(HttpServletRequest req, HttpServletResponse resp) 
    throws IOException 
  {
    try {
      req.setCharacterEncoding("UTF-8");
      resp.setContentType("text/html; charset=UTF-8");

      // リクエストパラメータの検査
      Match new_match = validateRequestParameters(req);
      new_match.checkDuplication();

      // 登録内容を確認し、登録ページに移行する
      HttpSession session = req.getSession(true);
      session.setAttribute("match", new_match);
      resp.sendRedirect("add_match.jsp");

    } catch (InvalidMatchParameterException ex) {
      HttpSession session = req.getSession(true);
      session.setAttribute("message", ex.getMessage());
      resp.sendRedirect("invalid_params.jsp");
    } catch (DuplicatedMatchException ex) {
      HttpSession session = req.getSession(true);
      session.setAttribute("message", ex.getMessage());
      session.setAttribute("match", ex.getDuplicatedMatch());
      session.setAttribute("ref_match", ex.getRefMatch());
      resp.sendRedirect("duplicated_params.jsp");
    }
  }
}
