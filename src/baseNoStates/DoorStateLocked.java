package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * State: Locked. Can't open until unlocked. Closing is allowed.
 */
public class DoorStateLocked extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(DoorStateLocked.class);
  public DoorStateLocked(Door door) {
    super(door, "locked");
  }

  @Override
  public void open() {
    logger.warn("Cannot open door {} because it is locked", door.getId());
  }

  @Override
  public void close() {
    // Ensure physically closed
    if (!door.isClosed()) {
      door.setClosed(true);
    }
    else {
      logger.debug("Door {} is already closed", door.getId());
    }
  }

  @Override
  public void lock() {
    logger.warn("Door {} is already locked", door.getId());
  }

  @Override
  public void unlock() {
    door.setState(new DoorStateUnlocked(door));
    logger.info("Door {} is now unlocked", door.getId());

  }

  @Override
  public void unlockShortly() {
    door.setState(new DoorStateUnlockedShortly(door));
  }
}
