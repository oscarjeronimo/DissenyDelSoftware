package baseNoStates;

// Implementation of the AreaVisitor interface to find an Area by its ID.
// Areaclass manages structure while this visitor manages search logic

public class FindAreaVisitor implements AreaVisitor {
  private final String targetId;
  private Area foundArea;

  public FindAreaVisitor(String targetId) {
    this.targetId = targetId;
    this.foundArea = null;
  }

  @Override
  public void visit(Space space) {
    if (foundArea != null) return; // Pequeña optimización: si ya lo encontramos, no seguimos mirando

    if (space.getId().equals(targetId)) {
      foundArea = space;
    }
  }

  @Override
  public void visit(Partition partition) {
    if (foundArea != null) return; // Pequeña optimización

    if (partition.getId().equals(targetId)) {
      foundArea = partition;
    }
  }

  public Area getFoundArea() {
    return foundArea;
  }
}
