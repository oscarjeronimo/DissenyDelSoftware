package baseNoStates;

import java.util.ArrayList;

//Implementation of the AreaVisitor interface to collect all doors in a structure.

public class GetDoorsVisitor implements AreaVisitor {
  // Aquí acumularemos el resultado
  private final ArrayList<Door> doors = new ArrayList<>();

  @Override
  public void visit(Space space) {
    // Si es un espacio añadimos sus puertas a nuestra lista
    doors.addAll(space.getDoors());
  }

  @Override
  public void visit(Partition partition) {
    // Las particiones no tienen puertas directas, así que no hacemos nada aquí.
    // El recorrido hacia los hijos se hace automáticamente en el metodo 'accept' de Partition.
  }

  public ArrayList<Door> getDoors() {
    return doors;
  }
}