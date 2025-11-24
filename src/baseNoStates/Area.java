package baseNoStates;

import java.util.ArrayList;

/**
 * Abstract base class for the Composite Design Pattern representing building structures.
 * It serves as the "Component" in the Composite pattern, where Space is the "Leaf"
 * and Partition is the "Composite".
 */
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
