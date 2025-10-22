package baseNoStates;

public class Main {
    public static void main(String[] args) {
        DirectoryDoors.makeDoors();
        DirectoryAreas.makeAreas();
        DirectoryUserGroups.makeUserGroups();
        new WebServer();
    }
}
