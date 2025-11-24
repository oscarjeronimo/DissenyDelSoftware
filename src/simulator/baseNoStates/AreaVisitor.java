package baseNoStates;

public interface AreaVisitor {
    void visit(Space space);
    void visit(Partition partition);
}

