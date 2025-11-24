package baseNoStates;

// Visitor that searches for an Area by its ID.
// Area handles the structure; this visitor handles the search logic.

public class FindAreaVisitor implements AreaVisitor {
  private final String targetId;
  private Area foundArea;

  public FindAreaVisitor(String targetId) {
    this.targetId = targetId;
    this.foundArea = null;
  }

  @Override
  public void visit(Space space) {
    if (foundArea != null) return; // Stop if already found

    if (space.getId().equals(targetId)) {
      foundArea = space;
    }
  }

  @Override
  public void visit(Partition partition) {
    if (foundArea != null) return; // Stop if already found

    if (partition.getId().equals(targetId)) {
      foundArea = partition;
    }
  }

  public Area getFoundArea() {
    return foundArea;
  }
}
