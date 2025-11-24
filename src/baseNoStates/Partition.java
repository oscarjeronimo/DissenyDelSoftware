package baseNoStates;

import java.util.ArrayList;
/* Represents a subdivision of the building that contains other areas
 *  Implements Composite role in the composite pattern
 */

public class Partition extends Area {
    private final ArrayList<Area> children = new ArrayList<>();

    public Partition(String id, String description, Partition parent) {
        super(id, description, parent);
    }

    public void addChild(Area a) { children.add(a); }

    /**
     * Accepts a visitor and propagates it to all children.
     * This is the core of the Visitor pattern for the Composite structure.
     */
    @Override
    public void accept(AreaVisitor v) {
        v.visit(this);
        // Una partición debe propagar la visita a sus hijos
        for (Area child : children) {
            child.accept(v);
        }
    }

    @Override
    public ArrayList<Space> getSpaces() {
        ArrayList<Space> res = new ArrayList<>();
        for (Area c : children) res.addAll(c.getSpaces());
        return res;
    }
}
