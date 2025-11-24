package baseNoStates;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A central clock that ticks every second and notifies observers.
 */
public class Clock extends Observable {
  private static Clock instance = new Clock(); // singleton
  private Timer timer;

  private Clock() {
    timer = new Timer(true); // daemon thread
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        setChanged();
        notifyObservers(System.currentTimeMillis());
      }
    }, 0, 1000); // every 1 second
  }

  public static Clock getInstance() {
    return instance;
  }
}
