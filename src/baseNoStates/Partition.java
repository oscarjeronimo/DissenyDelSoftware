package baseNoStates;

import java.util.ArrayList;

public class Partition extends Area {
    private final ArrayList<Area> children = new ArrayList<>();

    public Partition(String id, String description, Partition parent) {
        super(id, description, parent);
    }

    public void addChild(Area a) { children.add(a); }

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
