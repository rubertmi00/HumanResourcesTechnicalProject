package model.users;

/**
 * A concrete implementation of {@link AEmployee} which represents a standard employee in the
 * application.
 *
 * @author Michael Ruberto
 */
public class StandardEmployee extends AEmployee {

  /**
   * Constructs an instance of a StandardEmployee.
   *
   * @param name             The name of the standard employee.
   * @param password         The password of the standard employee.
   * @param salary           The salary of the standard employee.
   * @param vacationBalance  The vacation balance of the standard employee.
   * @param annualBonus      The annual bonus of the standard employee.
   * @param inHumanResources Whether or not the standard employee works in Human Resources.
   * @throws IllegalArgumentException If name is {@code null}, or if password is {@code null} or
   *                                  empty, or if any of the salary, vacationBalance, or
   *                                  annualBonus is negative.
   * @author Michael Ruberto
   */
  public StandardEmployee(String name, String password, double salary, int vacationBalance,
      double annualBonus, boolean inHumanResources) throws IllegalArgumentException {
    super(name, password, salary, vacationBalance, annualBonus, inHumanResources);
  }

  /**
   * Constructs a new instance of StandardEmployee from an existing instance of {@link AEmployee} by
   * copying all of its information.
   *
   * @param emp The existing AEmployee to be copied.
   * @throws IllegalArgumentException The given AEmployee is null
   * @author Michael Ruberto
   */
  public StandardEmployee(AEmployee emp) throws IllegalArgumentException {
    super(emp);
  }

  @Override
  public String getUserType() {
    return super.getUserType() + ", StandardEmployee";
  }
}
