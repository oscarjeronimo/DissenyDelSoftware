package baseNoStates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * DirectoryUserGroups - Singleton Observable pattern implementation
 *
 * This class manages all user groups and users in the access control system.
 * It uses the Singleton pattern to ensure only one instance exists throughout
 * the application lifecycle, maintaining a single authoritative registry of users.
 *
 * Why Singleton?
 * - Centralized user management: All parts of the system access the same user database
 * - Consistency: Prevents multiple conflicting user registries from existing
 * - Security: Single point of control for user authentication and authorization
 * - Resource efficiency: User groups and schedules are created and maintained only once
 * - Global access point: Any component can access user information through getInstance()
 */
public final class DirectoryUserGroups extends Observable {
  // Unique instance - only one DirectoryUserGroups exists in the application
  private static DirectoryUserGroups uniqueInstance = null;

  // Collection of all user groups in the system
  private final List<UserGroup> userGroups = new ArrayList<>();

  /**
   * Private constructor prevents direct instantiation.
   * Use getInstance() to access the singleton instance.
   */
  private DirectoryUserGroups() {}

  /**
   * Returns the singleton instance of DirectoryUserGroups.
   * Creates the instance on first call (lazy initialization).
   */
  public static DirectoryUserGroups getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new DirectoryUserGroups();
    }
    return uniqueInstance;
  }

  /**
   * Creates all user groups with their respective permissions, schedules, and users.
   * Initializes Administrators, Managers, Employees, and Blank (no permissions) groups.
   * Notifies observers when user group creation is complete.
   */
  public void makeUserGroups() {
    userGroups.clear();

    // Actions per group
    ArrayList<String> adminActions = new ArrayList<>();
    adminActions.add("open");
    adminActions.add("close");
    adminActions.add("lock");
    adminActions.add("unlock");
    adminActions.add("unlock_shortly");

    ArrayList<String> managerActions = new ArrayList<>();
    managerActions.add("open");
    managerActions.add("close");
    managerActions.add("lock");
    managerActions.add("unlock");
    managerActions.add("unlock_shortly");

    ArrayList<String> employeeActions = new ArrayList<>();
    employeeActions.add("open");
    employeeActions.add("close");
    employeeActions.add("unlock_shortly");

    // ===== Areas =====
    ArrayList<Area> allAreas = new ArrayList<>();
    addAreaIfExists(allAreas, "parking");
    addAreaIfExists(allAreas, "hall");
    addAreaIfExists(allAreas, "room1");
    addAreaIfExists(allAreas, "room2");
    addAreaIfExists(allAreas, "room3");
    addAreaIfExists(allAreas, "corridor");
    addAreaIfExists(allAreas, "IT");
    addAreaIfExists(allAreas, "exterior");
    addAreaIfExists(allAreas, "stairs_basement");
    addAreaIfExists(allAreas, "stairs_ground");
    addAreaIfExists(allAreas, "stairs_floor1");

    ArrayList<Area> managerAreas = new ArrayList<>(allAreas);

    ArrayList<Area> employeeAreas = new ArrayList<>();
    addAreaIfExists(employeeAreas, "hall");
    addAreaIfExists(employeeAreas, "room1");
    addAreaIfExists(employeeAreas, "room2");
    addAreaIfExists(employeeAreas, "room3");
    addAreaIfExists(employeeAreas, "corridor");
    addAreaIfExists(employeeAreas, "IT");
    addAreaIfExists(employeeAreas, "exterior");
    addAreaIfExists(employeeAreas, "stairs_basement");
    addAreaIfExists(employeeAreas, "stairs_ground");
    addAreaIfExists(employeeAreas, "stairs_floor1");

    // ===== Horaris =====
    LocalDate today = LocalDate.now();

    LocalDate adminStart = LocalDate.of(today.getYear(), 1, 1);
    LocalDate adminEnd   = LocalDate.of(2100, 1, 1);
    ArrayList<DayOfWeek> allDays = new ArrayList<>();
    allDays.add(DayOfWeek.MONDAY);
    allDays.add(DayOfWeek.TUESDAY);
    allDays.add(DayOfWeek.WEDNESDAY);
    allDays.add(DayOfWeek.THURSDAY);
    allDays.add(DayOfWeek.FRIDAY);
    allDays.add(DayOfWeek.SATURDAY);
    allDays.add(DayOfWeek.SUNDAY);
    Schedule adminSchedule = new Schedule(adminStart, adminEnd, allDays,
        LocalTime.of(0, 0), LocalTime.of(23, 59));

    LocalDate managerStart = LocalDate.of(today.getYear(), 9, 1);
    LocalDate managerEnd   = LocalDate.of(today.getYear() + 1, 3, 1);
    ArrayList<DayOfWeek> managerDays = new ArrayList<>();
    managerDays.add(DayOfWeek.MONDAY);
    managerDays.add(DayOfWeek.TUESDAY);
    managerDays.add(DayOfWeek.WEDNESDAY);
    managerDays.add(DayOfWeek.THURSDAY);
    managerDays.add(DayOfWeek.FRIDAY);
    managerDays.add(DayOfWeek.SATURDAY);
    Schedule managerSchedule = new Schedule(managerStart, managerEnd, managerDays,
        LocalTime.of(8, 0), LocalTime.of(20, 0));

    LocalDate employeeStart = LocalDate.of(today.getYear(), 9, 1);
    LocalDate employeeEnd   = LocalDate.of(today.getYear() + 1, 3, 1);
    ArrayList<DayOfWeek> employeeDays = new ArrayList<>();
    employeeDays.add(DayOfWeek.MONDAY);
    employeeDays.add(DayOfWeek.TUESDAY);
    employeeDays.add(DayOfWeek.WEDNESDAY);
    employeeDays.add(DayOfWeek.THURSDAY);
    employeeDays.add(DayOfWeek.FRIDAY);
    Schedule employeeSchedule = new Schedule(employeeStart, employeeEnd,
        employeeDays, LocalTime.of(9, 0), LocalTime.of(17, 0));

    // ===== Groups =====
    UserGroup adminGroup    = new UserGroup("Administrators", adminActions,    allAreas,     adminSchedule);
    UserGroup managerGroup  = new UserGroup("Managers",       managerActions,  managerAreas, managerSchedule);
    UserGroup employeeGroup = new UserGroup("Employees",      employeeActions, employeeAreas, employeeSchedule);

    //Users
    User ana = new User("Ana", "11343", adminGroup);
    adminGroup.addUser(ana);

    User manel = new User("Manel", "95783", managerGroup);
    User marta = new User("Marta", "05827", managerGroup);
    managerGroup.addUser(manel);
    managerGroup.addUser(marta);

    User ernest  = new User("Ernest",  "74984", employeeGroup);
    User eulalia = new User("Eulalia", "43295", employeeGroup);
    employeeGroup.addUser(ernest);
    employeeGroup.addUser(eulalia);

    ArrayList<String> noActions = new ArrayList<>();
    ArrayList<Area> noAreas = new ArrayList<>();
    Schedule noSchedule = new Schedule(today, today.minusDays(1), new ArrayList<>(),
        LocalTime.of(0, 0), LocalTime.of(0, 0));
    UserGroup blankGroup = new UserGroup("Blank", noActions, noAreas, noSchedule);
    User bernat = new User("Bernat", "12345", blankGroup);
    User blai   = new User("Blai",   "77532", blankGroup);
    blankGroup.addUser(bernat);
    blankGroup.addUser(blai);

    userGroups.add(blankGroup);
    userGroups.add(employeeGroup);
    userGroups.add(managerGroup);
    userGroups.add(adminGroup);

    // Notify observers that user groups have been created
    setChanged();
    notifyObservers("usergroups_created");
  }

  /**
   * Finds a user by their credential (ID card or access code).
   * Searches through all user groups to locate the user.
   */
  public User findUserByCredential(String credential) {
    for (UserGroup group : userGroups) {
      for (User user : group.getUsers()) {
        if (user.getCredential().equals(credential)) return user;
      }
    }
    return null;
  }

  /**
   * Helper method to add an area to a list if it exists in the directory.
   * Safely handles cases where areas might not be initialized yet.
   */
  private void addAreaIfExists(ArrayList<Area> areaList, String areaId) {
    Area a = DirectoryAreas.getInstance().findAreaById(areaId);
    if (a != null) areaList.add(a);
  }

  /**
   * Notifies observers that a specific user has changed.
   * Used for reactive updates when user state or permissions are modified.
   */
  public void notifyUserChanged(User user) {
    setChanged();
    notifyObservers(user);
  }
}