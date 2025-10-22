package baseNoStates;

import org.json.JSONObject;

public class Door {
  private final String id;
  private boolean closed;

  private DoorState state;

  private String fromSpace;
  private String toSpace;

  public Door(String id) {
    this.id = id;
    this.closed = true;
    this.state = new DoorStateUnlocked(this);
    this.fromSpace = "hall";
    this.toSpace = "office";
  }

  public String getId() {
    return id;
  }

  public boolean isClosed() {
    return closed;
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }

  /**
   * Nombre del estado lógico para el JSON que usa el simulador.
   */
  public String getStateName() {
    return state.getName();
  }

  public void setState(DoorState newState) {
    this.state = newState;
  }

  public String getFromSpace() {
    return fromSpace;
  }

  public String getToSpace() {
    return toSpace;
  }

  public void setFromSpace(String s) {
    this.fromSpace = s;
  }

  public void setToSpace(String s) {
    this.toSpace = s;
  }

  public void doAction(String action) {
    String a = Actions.canonicalize(action);
    if (a == null) return;

    if (Actions.OPEN.equals(a)) {
      state.open();
    } else if (Actions.CLOSE.equals(a)) {
      state.close();
    } else if (Actions.LOCK.equals(a)) {
      state.lock();
    } else if (Actions.UNLOCK.equals(a)) {
      state.unlock();
    } else if (Actions.UNLOCK_SHORTLY.equals(a)) {
      state.unlockShortly();
    }
  }

  public void processRequest(baseNoStates.requests.RequestReader req) {
    String canonical = Actions.canonicalize(req.getAction());
    if (canonical == null) {
      req.addReason("Not allowed action");
      req.setDoorStateName(getStateName());
      return;
    }
    if (!req.isAuthorized()) {
      req.setDoorStateName(getStateName());
      return;
    }

    doAction(canonical);
    req.setDoorStateName(getStateName());
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", getId());
    json.put("closed", isClosed());
    json.put("state", getStateName());
    json.put("fromSpace", getFromSpace());
    json.put("toSpace", getToSpace());
    return json;
  }

  @Override
  public String toString() {
    return "Door{ id='" + id + "', closed=" + closed + ", state=" + getStateName()
        + ", fromSpace='" + fromSpace + "', toSpace='" + toSpace + "' }";
  }
}
