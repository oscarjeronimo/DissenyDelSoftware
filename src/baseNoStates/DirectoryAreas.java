package baseNoStates;

//Registry class that manages all Area objects (Spaces and Partitions) in the system.

public final class DirectoryAreas {

    private DirectoryAreas() {}

    private static Partition rootArea;
    public static Partition getRootArea() { return rootArea; }

    public static void makeAreas() {
        Partition building    = new Partition("building", "Building", null);
        Partition basement    = new Partition("basement", "Basement", building);
        Partition groundFloor = new Partition("ground_floor", "Ground floor", building);
        Partition floor1      = new Partition("floor1", "First floor", building);

        Space exterior = new Space("exterior", "Exterior", building);

        Space stairsB  = new Space("stairs_basement", "Stairs (basement)", basement);
        Space stairsG  = new Space("stairs_ground",   "Stairs (ground)",   groundFloor);
        Space stairsF1 = new Space("stairs_floor1",   "Stairs (floor1)",   floor1);

        Space parking  = new Space("parking", "Parking", basement);

        Space hall      = new Space("hall",      "Hall",      groundFloor);
        Space room1     = new Space("room1",     "Room 1",    groundFloor);
        Space room2     = new Space("room2",     "Room 2",    groundFloor);
        Space restRoomG = new Space("rest_room", "Rest room", groundFloor); // opcional

        Space corridor = new Space("corridor", "Corridor", floor1);
        Space room3    = new Space("room3",    "Room 3",   floor1);
        Space it       = new Space("IT",       "IT",       floor1);

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
    }

    public static Area findAreaById(String id) {
        if (rootArea == null || id == null) return null;

        // Creamos el visitante configurado para buscar ese ID
        FindAreaVisitor visitor = new FindAreaVisitor(id);

        // Le decimos al árbol que acepte al visitante
        rootArea.accept(visitor);

        // Devolvemos lo que el visitante haya encontrado
        return visitor.getFoundArea();
    }

    private static void link(String doorId, Space from, Space to) {
        Door d = DirectoryDoors.findDoorById(doorId);
        if (d == null) throw new IllegalStateException("Puerta no encontrada: " + doorId);

        d.setFromSpace(from.getId());
        d.setToSpace(to.getId());

        to.addDoorGivingAccess(d);
    }
}
