package baseNoStates;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
/* Represents a subdivision of the building that contains other areas
 *  Implements Composite role in the composite pattern
 */

public class Partition extends Area {
    private final ArrayList<Area> children = new ArrayList<>();

    public JSONObject toJson(int depth) {
        // for depth=1 only the root and children,
        // for recusive = all levels use Integer.MAX_VALUE
        JSONObject json = new JSONObject();
        json.put("class", "partition");
        json.put("id", id);
        JSONArray jsonAreas = new JSONArray();
        if (depth > 0) {
            for (Area a : getChildren()) {
                jsonAreas.put(a.toJson(depth - 1));
            }
            json.put("areas", jsonAreas);
        }
        return json;
    }

    public Partition(String id, String description, Partition parent) {
        super(id, description, parent);
    }

    public void addChild(Area a) { children.add(a); }

    public ArrayList<Area> getChildren() {
        return new ArrayList<>(children);
    }

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
