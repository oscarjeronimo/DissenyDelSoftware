package baseNoStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("System starting up...");
        DirectoryDoors.makeDoors();
        DirectoryAreas.makeAreas();
        DirectoryUserGroups.makeUserGroups();
        new WebServer();
    }
}
