package baseNoStates;

import java.util.ArrayList;

public abstract class Area {
    protected final String id;
    protected final String description;
    protected final Partition parent;

    protected Area(String id, String description, Partition parent) {
        this.id = id;
        this.description = description;
        this.parent = parent;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public Partition getParent() { return parent; }

    public abstract void accept(AreaVisitor v);

    public abstract ArrayList<Space> getSpaces();

    @Override public String toString() { return id; }
}
