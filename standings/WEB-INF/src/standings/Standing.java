package standings;

public class Standing {
  private String team;
  private int played;
  private int won;
  private int drawn;
  private int lost;
  private int gf;
  private int ga;
  private int gd;
  private int points;

  public void setTeam(String team) {
    this.team = team;
  }
  public String getTeam() {
    return team;
  }
  public void setPlayed(int played) {
    this.played = played;
  }
  public int getPlayed() {
    return played;
  }
  public void setWon(int won) {
    this.won = won;
  }
  public int getWon() {
    return won;
  }
  public void setDrawn(int drawn) {
    this.drawn = drawn;
  }
  public int getDrawn() {
    return drawn;
  }
  public void setLost(int lost) {
    this.lost = lost;
  }
  public int getLost() {
    return lost;
  }
  public void setGf(int gf) {
    this.gf = gf;
  }
  public int getGf() {
    return gf;
  }
  public void setGa(int ga) {
    this.ga = ga;
  }
  public int getGa() {
    return ga;
  }
  public void setGd(int gd) {
    this.gd = gd;
  }
  public int getGd() {
    return gd;
  }
  public void setPoints(int points) {
    this.points = points;
  }
  public int getPoints() {
    return points;
  }
}
