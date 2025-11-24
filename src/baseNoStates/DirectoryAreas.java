package baseNoStates;

import java.util.Observable;

/**
 * DirectoryAreas - Singleton Observable pattern implementation
 *
 * This class manages all areas (partitions and spaces) in the building hierarchy.
 * It uses the Singleton pattern to ensure only one instance exists throughout
 * the application lifecycle, maintaining a single source of truth for all area data.
 *
 * Why Singleton?
 * - Centralized area management: All parts of the system access the same area hierarchy
 * - Consistency: Prevents multiple conflicting area structures from existing
 * - Resource efficiency: Building hierarchy is created and maintained only once
 * - Global access point: Any component can access area information through getInstance()
 */
public final class DirectoryAreas extends Observable {
  // Unique instance - only one DirectoryAreas exists in the application
  private static DirectoryAreas uniqueInstance = null;

  // Root of the area hierarchy tree
  private Partition rootArea;

  /**
   * Private constructor prevents direct instantiation.
   * Use getInstance() to access the singleton instance.
   */
  private DirectoryAreas() {}

  /**
   * Returns the singleton instance of DirectoryAreas.
   * Creates the instance on first call (lazy initialization).
   */
  public static DirectoryAreas getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new DirectoryAreas();
    }
    return uniqueInstance;
  }

  /**
   * Gets the root partition of the building hierarchy.
   */
  public Partition getRootArea() {
    return rootArea;
  }

  /**
   * Constructs the complete area hierarchy for the building.
   * Creates all partitions (floors) and spaces (rooms), establishes
   * parent-child relationships, and links doors between spaces.
   * Notifies observers when construction is complete.
   */
  public void makeAreas() {
    // Create partitions (logical groupings)
    Partition building    = new Partition("building", "Building", null);
    Partition basement    = new Partition("basement", "Basement", building);
    Partition groundFloor = new Partition("ground_floor", "Ground floor", building);
    Partition floor1      = new Partition("floor1", "First floor", building);

    // Create spaces (physical rooms)
    Space exterior = new Space("exterior", "Exterior", building);

    Space stairsB  = new Space("stairs_basement", "Stairs (basement)", basement);
    Space stairsG  = new Space("stairs_ground",   "Stairs (ground)",   groundFloor);
    Space stairsF1 = new Space("stairs_floor1",   "Stairs (floor1)",   floor1);

    Space parking  = new Space("parking", "Parking", basement);

    Space hall      = new Space("hall",      "Hall",      groundFloor);
    Space room1     = new Space("room1",     "Room 1",    groundFloor);
    Space room2     = new Space("room2",     "Room 2",    groundFloor);
    Space restRoomG = new Space("rest_room", "Rest room", groundFloor);

    Space corridor = new Space("corridor", "Corridor", floor1);
    Space room3    = new Space("room3",    "Room 3",   floor1);
    Space it       = new Space("IT",       "IT",       floor1);

    // Build the hierarchy tree
    building.addChild(basement);
    building.addChild(groundFloor);
    building.addChild(floor1);
    building.addChild(exterior);

    basement.addChild(parking);
    basement.addChild(stairsB);

    groundFloor.addChild(hall);
    groundFloor.addChild(room1);
    groundFloor.addChild(room2);
    groundFloor.addChild(restRoomG);
    groundFloor.addChild(stairsG);

    floor1.addChild(corridor);
    floor1.addChild(room3);
    floor1.addChild(it);
    floor1.addChild(stairsF1);

    // Link spaces with doors
    link("D1", exterior, parking);
    link("D2", stairsB,  parking);
    link("D3", exterior, hall);
    link("D4", stairsG,  hall);
    link("D5", hall,     room1);
    link("D6", hall,     room2);
    link("D7", stairsF1, corridor);
    link("D8", corridor, room3);
    link("D9", corridor, it);

    rootArea = building;

    // Notify observers that the area hierarchy has been created
    setChanged();
    notifyObservers("areas_created");
  }

  /**
   * Finds an area by its unique identifier using the Visitor pattern.
   * Searches recursively through the entire area hierarchy.
   */
  public Area findAreaById(String id) {
    if (rootArea == null || id == null) return null;

    // Use Visitor pattern to traverse the area tree
    FindAreaVisitor visitor = new FindAreaVisitor(id);
    rootArea.accept(visitor);
    return visitor.getFoundArea();
  }

  /**
   * Links two spaces with a door.
   * Establishes bidirectional connection and registers the door
   * as providing access to the destination space.
   */
  private void link(String doorId, Space from, Space to) {
    Door d = DirectoryDoors.getInstance().findDoorById(doorId);
    if (d == null) throw new IllegalStateException("Puerta no encontrada: " + doorId);

    d.setFromSpace(from.getId());
    d.setToSpace(to.getId());

    to.addDoorGivingAccess(d);
  }

  /**
   * Notifies observers that a specific area has changed.
   * Used for reactive updates when area state is modified.
   */
  public void notifyAreaChanged(Area area) {
    setChanged();
    notifyObservers(area);
  }
}