package baseNoStates;

import java.util.ArrayList;
/*
 * Represents a specific physical space (room, hall, corridor, etc.) in the building.
 * It is the leaf in the composite pattern.
 */
public class Space extends Area {
    private final ArrayList<Door> doorsGivingAccess = new ArrayList<>();

    public Space(String id, String description, Partition parent) {
        super(id, description, parent);
    }

    public void addDoorGivingAccess(Door d) {
        if (!doorsGivingAccess.contains(d)) doorsGivingAccess.add(d);
    }

    public ArrayList<Door> getDoors() { return new ArrayList<>(doorsGivingAccess); }

    public ArrayList<Door> getDoorsGivingAccess() {
        return getDoors();
    }

    @Override
    public void accept(AreaVisitor v) {
        v.visit(this);
    }

    @Override
    public ArrayList<Space> getSpaces() {
        ArrayList<Space> res = new ArrayList<>();
        res.add(this);
        return res;
    }
}
