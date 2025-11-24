package baseNoStates;

import java.util.ArrayList;

// Implementation of the AreaVisitor interface to collect all doors in a structure.

public class GetDoorsVisitor implements AreaVisitor {
  // Here we will accumulate the result
  private final ArrayList<Door> doors = new ArrayList<>();

  @Override
  public void visit(Space space) {
    // If it is a space, we add its doors to our list
    doors.addAll(space.getDoors());
  }

  @Override
  public void visit(Partition partition) {
    // Partitions do not have direct doors, so we do nothing here.
    // The traversal to the children is done automatically in the 'accept' method of Partition.
  }

  public ArrayList<Door> getDoors() {
    return doors;
  }
}
