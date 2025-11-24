package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * State: Unlocked. Allows opening/closing. Can lock only if door is physically closed.
 */
public class DoorStateUnlocked extends DoorState {
    private static final Logger logger = LoggerFactory.getLogger(DoorStateUnlocked.class);

    public DoorStateUnlocked(Door door) {
        super(door, "unlocked"); // "unlocked"
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
        if (door.isClosed()) {
            door.setState(new DoorStateLocked(door));
        } else {
            logger.warn("Cannot lock door {} because it is open (must be closed first)", door.getId());
        }
    }

    @Override
    public void unlock(){
        logger.warn("Door {} is already unlocked", door.getId());
    }
}