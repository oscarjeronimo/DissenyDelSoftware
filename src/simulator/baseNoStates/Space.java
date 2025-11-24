package baseNoStates;

import java.util.ArrayList;

public class Space extends Area {
    private final ArrayList<Door> doorsGivingAccess = new ArrayList<>();

    public Space(String id, String description, Partition parent) {
        super(id, description, parent);
    }

    public void addDoorGivingAccess(Door d) {
        if (!doorsGivingAccess.contains(d)) doorsGivingAccess.add(d);
    }

    public ArrayList<Door> getDoors() { return new ArrayList<>(doorsGivingAccess); }

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
