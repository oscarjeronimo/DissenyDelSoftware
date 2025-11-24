package baseNoStates;

import java.util.Observable;
import java.util.Observer;

/**
 * State: UnlockedShortly. Temporarily unlocked (≈10 s) before re-locking or propped.
 */
public class DoorStateUnlockedShortly extends DoorState implements Observer {
  private long startMillis;

  public DoorStateUnlockedShortly(Door door) {
    super(door, "unlocked_shortly");
    this.startMillis = System.currentTimeMillis();
    Clock.getInstance().addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg) {
    long nowMillis = (Long) arg;
    long elapsed = (nowMillis - startMillis) / 1000;

    if (elapsed >= 10) { // after 10 seconds
      Clock.getInstance().deleteObserver(this);
      if (door.isClosed()) {
        door.setState(new DoorStateLocked(door));
      } else {
        door.setState(new DoorStatePropped(door));
      }
    }
  }

  @Override
  public void open() {
    door.setClosed(false);
  }

  @Override
  public void close() {
    door.setClosed(true);
  }

  @Override
  public void lock() {
    door.setState(new DoorStateLocked(door));
  }

  @Override
  public void unlock() {
    invalid("unlock");
  }
}