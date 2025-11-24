package baseNoStates;

//Interface defining the Visitor Design Pattern for the Area hierarchy.

public interface AreaVisitor {
  void visit(Space space);
  void visit(Partition partition);
}


