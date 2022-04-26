package standings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MatchDAO {
  // データベースアクセス用データ
  private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/standings";
  private static final String DB_USER = "sampleUser";
  private static final String DB_PASSWORD = "SU_pass";

  private Match createMatchFromResultSet(ResultSet rset) 
      throws InternalErrorException
  {
    try {
      Match match = new Match();
      match.setSection(rset.getInt("section"));
      match.setDate(rset.getDate("date").toLocalDate());
      match.setHome(rset.getString("home"));
      match.setAway(rset.getString("away"));
      match.setGoalsFor(rset.getInt("goals_for"));
      match.setGoalsAgainst(rset.getInt("goals_against"));
      return match;
    } catch (Exception ex) {
      throw new InternalErrorException("データベースからの試合情報読み出しでエラーが発生しました", ex);
    }
  }

  public Match findMatchBySectionAndTeam(int section, String team)
      throws InternalErrorException
  {
    Connection conn = null;
    PreparedStatement pstmt = null;
    Match match = null;

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      pstmt = conn.prepareStatement(
          "SELECT * FROM matches WHERE section=? AND (home=? OR away=?)");
      pstmt.setInt(1, section);
      pstmt.setString(2, team);
      pstmt.setString(3, team);
      ResultSet rset = pstmt.executeQuery();
      if (rset.next()) {
        match = createMatchFromResultSet(rset);
      }
    } catch (Exception ex) {
      throw new InternalErrorException("データベースからの節・チーム名に基づく試合情報の読み出しでエラーが発生しました", ex);
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (SQLException se) {
      }
    }
    return match;
  }

  public Match findMatchByDateAndTeam(LocalDate date, String team)
      throws InternalErrorException
  {
    Connection conn = null;
    PreparedStatement pstmt = null;
    Match match = null;

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      pstmt = conn.prepareStatement(
          "SELECT * FROM matches WHERE date=? AND (home=? OR away=?)");
      pstmt.setDate(1, Date.valueOf(date));
      pstmt.setString(2, team);
      pstmt.setString(3, team);
      ResultSet rset = pstmt.executeQuery();
      if (rset.next()) {
        match = createMatchFromResultSet(rset);
      }
    } catch (Exception ex) {
      throw new InternalErrorException("データベースからの試合開催日・チーム名に基づく試合情報の読み出しでエラーが発生しました", ex);
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (SQLException se) {
      }
    }
    return match;
  }

  public Match findMatchByHomeAndAway(String home, String away) 
      throws InternalErrorException
  {
    Connection conn = null;
    PreparedStatement pstmt = null;
    Match match = null;

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      pstmt = conn.prepareStatement(
          "SELECT * FROM matches WHERE home=? AND away=?");
      pstmt.setString(1, home);
      pstmt.setString(2, away);
      ResultSet rset = pstmt.executeQuery();
      if (rset.next()) {
        match = createMatchFromResultSet(rset);
      }
    } catch (Exception ex) {
      throw new InternalErrorException("データベースからのホーム・アウェイチーム名に基づく試合情報の読み出しでエラーが発生しました", ex);
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (SQLException se) {
      }
    }
    return match;
  }

  public void store(Match match)
    throws InternalErrorException
  {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      pstmt = conn.prepareStatement(
        "INSERT INTO matches "
        + "(section, date, home, away, goals_for, goals_against) "
        + "VALUES "
        + "(?, ?, ?, ?, ?, ?)"
      );
      pstmt.setInt(1, match.getSection());
      System.out.println("section: " + match.getSection());
      pstmt.setDate(2, Date.valueOf(match.getDate()));
      System.out.println("date: " + Date.valueOf(match.getDate()));
      pstmt.setString(3, match.getHome());
      System.out.println("home: " + match.getHome());
      pstmt.setString(4, match.getAway());
      System.out.println("away: " + match.getAway());
      pstmt.setInt(5, match.getGoalsFor());
      System.out.println("goals_for: " + match.getGoalsFor());
      pstmt.setInt(6, match.getGoalsAgainst());
      System.out.println("goals_against: " + match.getGoalsAgainst());
      pstmt.executeUpdate();
    } catch (Exception ex) {
      throw new InternalErrorException("試合情報のデータベースへの登録でエラーが発生しました", ex);
    } finally {
      try {
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
      } catch (SQLException ex) {
      }
    }
  }

  public List<Standing> listStandings() 
      throws InternalErrorException
  {
    Connection conn = null;
    Statement stmt = null;
    List<Standing> standings = new ArrayList<Standing>();

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      stmt = conn.createStatement();

      ResultSet rset = stmt.executeQuery(
        "SELECT team, count(team) as played, sum(win) as won, sum(draw) as drawn, sum(lose) as lost, " +
        "  sum(goals_got) as gf, sum(goals_lost) as ga, sum(goals_difference) as gd, " +
        "  sum(point) as points " +
        "FROM (" +
        "  SELECT home as team, " + 
        "    if(goals_for > goals_against, 1, 0) as win, " +
        "    if(goals_for = goals_against, 1, 0) as draw, " +
        "    if(goals_for < goals_against, 1, 0) as lose, " +
        "    goals_for as goals_got, " +
        "    goals_against as goals_lost, " +
        "    goals_for - goals_against as goals_difference, " +
        "    if(goals_for > goals_against, 3, if(goals_for = goals_against, 1, 0)) as point " +
        "  FROM matches " + 
        "  UNION ALL " +
        "  SELECT away as team, " + 
        "    if(goals_against > goals_for, 1, 0) as win, " +
        "    if(goals_against = goals_for, 1, 0) as draw, " +
        "    if(goals_against < goals_for, 1, 0) as lose, " +
        "    goals_against as goals_got, " +
        "    goals_for as goals_lost, " +
        "    goals_against - goals_for as goals_difference, " +
        "    if(goals_against > goals_for, 3, if(goals_against = goals_for, 1, 0)) as point " +
        "  FROM matches " + 
        ") temp " +
        "GROUP BY team " + 
        "ORDER BY points DESC, gd DESC, gf DESC");
      while (rset.next()) {
        Standing standing = new Standing();
        standing.setTeam(rset.getString("team"));
        standing.setPlayed(rset.getInt("played"));
        standing.setWon(rset.getInt("won"));
        standing.setDrawn(rset.getInt("drawn"));
        standing.setLost(rset.getInt("lost"));
        standing.setGf(rset.getInt("gf"));
        standing.setGa(rset.getInt("ga"));
        standing.setGd(rset.getInt("gd"));
        standing.setPoints(rset.getInt("points"));
        standings.add(standing);
      }
    } catch (Exception ex) {
      throw new InternalErrorException("順位表の取得でエラーが発生しました", ex);
    } finally {
      try {
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
      } catch (SQLException ex) {
      }
    }
    return standings;
  }

  public List<Match> getMatchesBySection(int section) {
    String query = "SELECT * FROM matches WHERE section = " + section;

    return getMatchesBySQLQuery(query);
  }

  public List<Match> getMatchesByDate(LocalDate date) {
    String query = "SELECT * FROM matches WHERE date = '" + date + "'";
    return getMatchesBySQLQuery(query);
  }

  public List<Match> getMatchesByTeams(String home, String away) {
    String query = "SELECT * FROM matches WHERE home = '" + home + "'AND away = '" + away + "'";
    System.out.println("getMatchesByTeams: " + query);
    return getMatchesBySQLQuery(query);
  }

  private List<Match> getMatchesBySQLQuery(String query) {
    Connection conn = null;
    Statement stmt = null;
    ArrayList<Match> matches = new ArrayList<Match>();

    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(query);
      while (rset.next()) {
        Match match = new Match();
        match.setSection(rset.getInt("section"));
        match.setDate(rset.getDate("date").toLocalDate());
        match.setHome(rset.getString("home"));
        match.setAway(rset.getString("away"));
        match.setGoalsFor(rset.getInt("goals_for"));
        match.setGoalsAgainst(rset.getInt("goals_against"));
        matches.add(match);
      }
    } catch (Exception ex) {
    } finally {
      try {
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
      } catch (SQLException se) {
      }
    }
    return matches;
  }
}
