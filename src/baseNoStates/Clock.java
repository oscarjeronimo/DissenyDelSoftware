package baseNoStates;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clock class sends notifications to all registered observers (e.g., doors in unlock shortly state).
 * Implements the Singleton pattern to ensure a single instance manages time synchronization across the system.
 * Uses the Observer pattern to separate the clock's behavior from the objects it affects.
 */
public class Clock extends Observable {
  private static final Logger logger = LoggerFactory.getLogger(Clock.class);
  private static Clock uniqueInstance = null; // Singleton pattern
  private final Timer timer;

  private Clock() {
    timer = new Timer(true); // daemon thread
    logger.info("Clock instance created and Timer initialized");
  }

  /**
   * Returns the singleton instance of Clock (lazy initialization).
   * Not thread-safe - use synchronized version if needed in multi-threaded environment.
   */
  public static Clock getInstance() {
    if (uniqueInstance == null) { // lazy initialization
      uniqueInstance = new Clock();
      uniqueInstance.startClock();
      logger.info("Clock singleton instance created and started.");
    }
    return uniqueInstance;
  }

  /**
   * Starts the clock to tick every second and notify observers.
   */
  public void startClock() {
    logger.info("Clock started.");
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        logger.debug("Clock ticked and notifying observers");
        setChanged();
        notifyObservers(System.currentTimeMillis()); // Pass timestamp to observers
      }
    };
    timer.scheduleAtFixedRate(task, 0, 1000); // tick every second
    logger.info("Clock scheduled to tick every second.");
  }
}