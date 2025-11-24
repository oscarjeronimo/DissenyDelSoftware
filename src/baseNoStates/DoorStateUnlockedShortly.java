package baseNoStates;

import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * State: UnlockedShortly. Temporarily unlocked (≈10 s) before re-locking or propped.
 */
public class DoorStateUnlockedShortly extends DoorState implements Observer {
  private static final Logger logger = LoggerFactory.getLogger(DoorStateUnlockedShortly.class);
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
      logger.info("Door {} short unlock period expired ({}s). Checking status...", door.getId(), elapsed);
      Clock.getInstance().deleteObserver(this);
      if (door.isClosed()) {
        logger.info("Door {} is closed. Auto-locking.", door.getId());
        door.setState(new DoorStateLocked(door));
      } else {
        logger.warn("Door {} remained open! Entering PROPPED state.", door.getId()); // WARN importante
        door.setState(new DoorStatePropped(door));
      }
    }
  }

  @Override
  public void open() {
    logger.debug("Opening door {} (unlocked shortly)", door.getId());
    door.setClosed(false);
  }

  @Override
  public void close() {
    logger.debug("Closing door {} (unlocked shortly)", door.getId());
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

  @Override
  public void unlockShortly() {
    logger.warn("Door {} is already in unlocked_shortly state", door.getId());
  }
}