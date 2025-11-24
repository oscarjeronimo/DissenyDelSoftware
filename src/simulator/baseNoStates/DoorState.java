package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class representing the state of a door.
 * Each door can have different states like Locked, Unlocked, or later Propped/UnlockedShortly.
 * The State pattern lets us change door behaviour depending on its current state.
 */
public abstract class DoorState {
  private static final Logger logger = LoggerFactory.getLogger(DoorState.class);
  protected final Door door;
  private final String name;

  protected DoorState(Door door, String name) {
    this.door = door;
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  public void open() {
    invalid("open");
  }

  public void close() {
    invalid("close");
  }

  public void lock() {
    invalid("lock");
  }

  public void unlock() {
    invalid("unlock");
  }

  public void unlockShortly() {
    invalid("unlock_shortly");
  }

  protected void invalid(String action) {
    logger.warn("Action '{}' not allowed in state '{}' for door {}", action, name, door.getId());
  }
}
