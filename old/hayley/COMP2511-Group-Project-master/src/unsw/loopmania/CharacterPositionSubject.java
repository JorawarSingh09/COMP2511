package unsw.loopmania;

public interface CharacterPositionSubject {
  public void attach(CharacterPositionObserver observer);
  public void detach(CharacterPositionObserver observer);
  public void updateObservers();
}
