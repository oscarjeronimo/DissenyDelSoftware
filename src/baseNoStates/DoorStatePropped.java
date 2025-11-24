package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** State: Propped. Door left open too long. Closes → Locked. */
public class DoorStatePropped extends DoorState {
    private static final Logger logger = LoggerFactory.getLogger(DoorStatePropped.class);
    public DoorStatePropped(Door door) {
        super(door, "propped");
    }

    @Override
    public void open() {
        logger.warn("Door {} is already open (propped state)", door.getId());
    }

    @Override
    public void close() {
        logger.info("Door {} closed (was propped). Returning to Locked state.", door.getId());
        door.setClosed(true);
        door.setState(new DoorStateLocked(door));
    }

    @Override
    public void lock() {
        logger.warn("Cannot lock door {} directly because it is propped open. Close it first.", door.getId());
    }

    @Override
    public void unlock() {
        door.setState(new DoorStateUnlocked(door));
    }
}
