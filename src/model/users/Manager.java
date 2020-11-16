package model.users;

import java.util.HashMap;

/**
 * A concrete implementation of {@link AEmployee} which represents a manager in the application. A
 * unique feature of a manager is that they may have one or more employees who report to them.
 *
 * @author Michael Ruberto
 */
public class Manager extends AEmployee {

  private HashMap<Integer, AEmployee> reportingEmployees;

  /**
   * Constructs an instance of a Manager. Initially, the manager will have no employees reporting to
   * them.
   *
   * @param name             The name of the manager.
   * @param password         The password of the manager.
   * @param salary           The salary of the manager.
   * @param vacationBalance  The vacation balance of the manager.
   * @param annualBonus      The annual bonus of the manager.
   * @param inHumanResources Whether or not the manager works in Human Resources.
   * @throws IllegalArgumentException If name is {@code null}, or if password is {@code null} or
   *                                  empty, or if any of the salary, vacationBalance, or
   *                                  annualBonus is negative.
   * @author Michael Ruberto
   */
  public Manager(String name, String password, double salary, int vacationBalance,
      double annualBonus, boolean inHumanResources) throws IllegalArgumentException {
    super(name, password, salary, vacationBalance, annualBonus, inHumanResources);
    this.reportingEmployees = new HashMap<Integer, AEmployee>();
  }

  /**
   * Constructs a new instance of Manager from an existing instance of {@link AEmployee} by copying
   * all of its information.
   *
   * @param emp The existing AEmployee to be copied.
   * @throws IllegalArgumentException The given AEmployee is null
   * @author Michael Ruberto
   */
  public Manager(AEmployee emp) throws IllegalArgumentException {
    super(emp);
    this.reportingEmployees = new HashMap<Integer, AEmployee>();
  }

  @Override
  public String getUserType() {
    return super.getUserType() + ", Manager";
  }

  /**
   * Adds the given {@link AEmployee} to the map of {@code reportingEmployees}. The employee's
   * unique ID will be used as the key value.
   *
   * @param e The employee to be added.
   * @throws IllegalArgumentException If the given employee is {@code null}.
   * @author Michael Ruberto
   */
  public void addReportingEmployee(AEmployee e) throws IllegalArgumentException {
    //VALIDATE INPUTS
    if (e == null) {
      throw new IllegalArgumentException("Cannot add null employee.");
    }

    reportingEmployees.put(e.getId(), e);
  }

  /**
   * Removes the {@link AEmployee} with the given ID from the map of {@code reportingEmployees}.
   *
   * @param id The unique ID of the employee to be removed.
   * @return The employee who was removed.
   * @author Michael Ruberto
   */
  public AEmployee removeReportingEmployee(int id) {
    return reportingEmployees.remove(id);
  }

  /**
   * Gets the map of {@code reportingEmployees}.
   *
   * @return The map of {@link AEmployee}s.
   * @author Michael Ruberto
   */
  public HashMap<Integer, AEmployee> getReportingEmployees() {
    return reportingEmployees;
  }
}
