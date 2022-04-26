package standings;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Match implements Serializable {
  private int section;
  private LocalDate date;
  private String home;
  private String away;
  private int goals_for;
  private int goals_against;

  private final List<String> teams = Arrays.asList(
    "札幌", "鹿島", "浦和", "柏", "FC東京", "川崎F", "横浜FM", "湘南", "清水",
    "磐田", "名古屋", "京都", "G大阪", "C大阪", "神戸", "広島", "福岡", "鳥栖"
  );
  private static final DateTimeFormatter date_format = DateTimeFormatter.ofPattern("yyyy/MM/dd");


  public void setSection(int section) throws InvalidMatchParameterException {
    if (section < 1 || section > 34) {
      throw new InvalidMatchParameterException("節の値 (" + section + ")が不正です。1から34までの整数を指定してください");
    }
    this.section = section;
  }
  public int getSection() {
    return section;
  }
  public void setDate(LocalDate date) throws InvalidMatchParameterException {
    this.date = date;
  }
  public LocalDate getDate() {
    return date;
  }
  public void setHome(String home) throws InvalidMatchParameterException {
    if (!teams.contains(home)) {
      throw new InvalidMatchParameterException("ホームチーム名「" + home + "」が間違っています");
    }
    if (home.equals(away)) {
      throw new InvalidMatchParameterException("ホームチーム名とアウェイチーム名が同じです");
    }
    this.home = home;
  }
  public String getHome() {
    return home;
  }
  public void setAway(String away) throws InvalidMatchParameterException {
    if (!teams.contains(away)) {
      throw new InvalidMatchParameterException("アウェイチーム名「" + away + "」が間違っています");
    }
    if (away.equals(home)) {
      throw new InvalidMatchParameterException("ホームチーム名とアウェイチーム名が同じです");
    }
    this.away = away;
  }
  public String getAway() {
    return away;
  }
  public void setGoalsFor(int goals_for) throws InvalidMatchParameterException {
    if (goals_for < 0) {
      throw new InvalidMatchParameterException("ホームチーム得点の値 (" + goals_for + ")が負の値です");
    }
    this.goals_for = goals_for;
  }
  public int getGoalsFor() {
    return goals_for;
  }
  public void setGoalsAgainst(int goals_against) throws InvalidMatchParameterException {
    if (goals_against < 0) {
      throw new InvalidMatchParameterException("アウェイチーム得点の値 (" + goals_against + ")が負の値です");
    }
    this.goals_against = goals_against;
  }
  public int getGoalsAgainst() {
    return goals_against;
  }

  public void checkDuplication() throws DuplicatedMatchException {
    MatchDAO match_dao = new MatchDAO();
    List<Match> matches_by_section = match_dao.getMatchesBySection(section);
    List<Match> matches_by_date = match_dao.getMatchesByDate(date);
    List<Match> matches_by_teams = match_dao.getMatchesByTeams(home, away);

    Match ref_match = null;
    if ((ref_match = findMatchByTeam(home, matches_by_section)) != null) {
      // ホームチームの試合が同じ節にすでに登録済み
      throw new DuplicatedMatchException(
          "指定されたホームチーム「" + home + "」は" + section + "節の試合がすでに登録済みです",
          this, ref_match);
    }
    if ((ref_match = findMatchByTeam(away, matches_by_section)) != null) {
      // アウェイチームの試合が同じ節にすでに登録済み
      throw new DuplicatedMatchException(
          "指定されたアウェイチーム「" + away + "」は" + section + "節の試合がすでに登録済みです",
          this, ref_match);
    }
    if ((ref_match = findMatchByTeam(home, matches_by_date)) != null) {
      // ホームチームの試合が同じ節にすでに登録済み
      throw new DuplicatedMatchException(
          "指定されたホームチーム「" + home + "」は試合日" + date.format(date_format) + "の試合がすでに登録済みです",
          this, ref_match);
    }
    if ((ref_match = findMatchByTeam(away, matches_by_date)) != null) {
      // アウェイチームの試合が同じ節にすでに登録済み
      throw new DuplicatedMatchException(
          "指定されたアウェイチーム「" + away + "」は試合日" + date.format(date_format) + "の試合がすでに登録済みです",
          this, ref_match);
    }
    if (matches_by_teams != null && !matches_by_teams.isEmpty()) {
      // 同じ組み合わせの試合が他の節ですでに登録済み
      throw new DuplicatedMatchException(
          "指定されたホームチーム「" + home + "」と指定されたアウェイチーム「" + away + "」の試合は別の節ですでに登録済みです",
          this, matches_by_teams.get(0));
    }
  }

  public void store()
    throws InternalErrorException
  {
    MatchDAO match_dao = new MatchDAO();

    match_dao.store(this);
  }

  private Match findMatchByTeam(String team, List<Match> matches) {
    Match found = null;

    for (Match match: matches) {
      if (match.getHome().equals(team)
          || match.getAway().equals(team)) {
        found = match;
        break;
      }
    }
    return found;
  }

  public static List<Standing> listStandings() 
    throws InternalErrorException
  {
    MatchDAO match_dao = new MatchDAO();

    return match_dao.listStandings();
  }
}
