package unsw.loopmania;

public interface EnemyPositionSubject {
  public void attach(EnemyPositionObserver observer);
  public void detach(EnemyPositionObserver observer);
  public void updateObservers();
}
