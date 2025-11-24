package baseNoStates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

/**
 * DirectoryDoors - Singleton Observable pattern implementation
 *
 * This class manages all doors in the building access control system.
 * It uses the Singleton pattern to ensure only one instance exists throughout
 * the application lifecycle, maintaining a single authoritative list of all doors.
 *
 * Why Singleton?
 * - Centralized door management: All parts of the system access the same door registry
 * - Consistency: Prevents multiple conflicting door lists from existing
 * - Resource efficiency: Door collection is created and maintained only once
 * - Global access point: Any component can access door information through getInstance()
 */
public final class DirectoryDoors extends Observable {
  // Unique instance - only one DirectoryDoors exists in the application
  private static DirectoryDoors uniqueInstance = null;

  // Collection of all doors in the system
  private ArrayList<Door> allDoors;

  /**
   * Private constructor prevents direct instantiation.
   * Use getInstance() to access the singleton instance.
   */
  private DirectoryDoors() {
    allDoors = new ArrayList<>();
  }

  /**
   * Returns the singleton instance of DirectoryDoors.
   * Creates the instance on first call (lazy initialization).
   */
  public static DirectoryDoors getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new DirectoryDoors();
    }
    return uniqueInstance;
  }

  /**
   * Creates all doors in the building.
   * Initializes doors for basement, ground floor, and first floor.
   * Notifies observers when door creation is complete.
   */
  public void makeDoors() {
    // basement
    Door d1 = new Door("D1"); // exterior, parking
    Door d2 = new Door("D2"); // stairs, parking

    // ground floor
    Door d3 = new Door("D3"); // exterior, hall
    Door d4 = new Door("D4"); // stairs, hall
    Door d5 = new Door("D5"); // hall, room1
    Door d6 = new Door("D6"); // hall, room2

    // first floor
    Door d7 = new Door("D7"); // stairs, corridor
    Door d8 = new Door("D8"); // corridor, room3
    Door d9 = new Door("D9"); // corridor, IT

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));

    // Notify observers that doors have been created
    setChanged();
    notifyObservers("doors_created");
  }

  /**
   * Finds a door by its unique identifier.
   */
  public Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null;
  }

  /**
   * Gets the complete list of all doors in the system.
   */
  public ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

  /**
   * Notifies observers that a specific door has changed state.
   * Used for reactive updates when door state is modified.
   */
  public void notifyDoorChanged(Door door) {
    setChanged();
    notifyObservers(door);
  }
}