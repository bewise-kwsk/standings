package standings;

public class DuplicatedMatchException extends Exception {
  private Match duplicated_match;
  private Match ref_match;

  public DuplicatedMatchException(String message, Match duplicated_match, Match ref_match) {
    super(message);
    this.duplicated_match = duplicated_match;
    this.ref_match = ref_match;
  }

  public Match getDuplicatedMatch() {
    return duplicated_match;
  }

  public Match getRefMatch() {
    return ref_match;
  }
}
