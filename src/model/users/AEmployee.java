package model.users;

import java.util.ArrayList;

/**
 * An abstract implementation of the {@link IUser} interface which represents an employee in the
 * application.
 *
 * @author Michael Ruberto
 */
public abstract class AEmployee implements IUser {

  private static int currentId = 1;
  private int id;
  private String name;
  private String password;
  private double salary;
  private ArrayList<Double> salaryHistory;
  private int vacationBalance;
  private double annualBonus;
  private boolean inHumanResources;
  private Manager manager;

  /**
   * Constructs an instance of an AEmployee. The IDs will be automatically assigned to the {@code
   * currentId} value, which will then be incremented. Initially, the AEmployee will not report to
   * any {@link Manager}.
   *
   * @param name             The name of the employee.
   * @param password         The password of the employee.
   * @param salary           The salary of the employee.
   * @param vacationBalance  The vacation balance of the employee.
   * @param annualBonus      The annual bonus of the employee.
   * @param inHumanResources Whether or not the employee works in Human Resources.
   * @throws IllegalArgumentException If name is {@code null}, or if password is {@code null} or
   *                                  empty, or if any of the salary, vacationBalance, or
   *                                  annualBonus is negative.
   * @author Michael Ruberto
   */
  public AEmployee(String name, String password, double salary, int vacationBalance,
      double annualBonus, boolean inHumanResources) throws IllegalArgumentException {
    //VALIDATE INPUTS
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (password == null || password.equals("")) {
      throw new IllegalArgumentException("Password must be a non-empty String.");
    }
    if (salary < 0 || vacationBalance < 0 || annualBonus < 0) {
      throw new IllegalArgumentException(
          "Salary, Vacation Balance, and Annual Bonus must be non-negative.");
    }

    this.id = this.currentId;
    this.currentId++;

    this.name = name;
    this.password = password;
    this.salary = salary;
    this.salaryHistory = new ArrayList<Double>();
    this.vacationBalance = vacationBalance;
    this.annualBonus = annualBonus;
    this.inHumanResources = inHumanResources;
    this.manager = null;
  }

  /**
   * Changes {@code currentId} to the given value so that the next AEmployee created will have that
   * ID.
   *
   * @param id The next ID.
   * @author Michael Ruberto
   */
  public static void setNextID(int id) {
    currentId = id;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean correctPassword(String guess) {
    return password.equals(guess);
  }

  @Override
  public String getUserType() {
    return "AEmployee";
  }

  @Override
  public double getSalary() {
    return salary;
  }

  @Override
  public void setSalary(double salary) throws IllegalArgumentException {
    //VALIDATE INPUTS
    if (salary < 0) {
      throw new IllegalArgumentException("Salary must be non-negative.");
    }

    salaryHistory.add(this.salary);
    this.salary = salary;
  }

  @Override
  public ArrayList<Double> getSalaryHistory() {
    return salaryHistory;
  }

  @Override
  public int getVacationBalance() {
    return vacationBalance;
  }

  @Override
  public void setVacationBalance(int vacationBalance) {
    //VALIDATE INPUTS
    if (vacationBalance < 0) {
      throw new IllegalArgumentException("Vacation Balance must be non-negative.");
    }

    this.vacationBalance = vacationBalance;
  }

  @Override
  public double getAnnualBonus() {
    return annualBonus;
  }

  @Override
  public void setAnnualBonus(double annualBonus) {
    //VALIDATE INPUTS
    if (annualBonus < 0) {
      throw new IllegalArgumentException("Annual Bonus must be non-negative.");
    }
    this.annualBonus = annualBonus;
  }

  @Override
  public boolean isInHumanResources() {
    return inHumanResources;
  }

  @Override
  public void setInHumanResources(boolean inHumanResources) {
    this.inHumanResources = inHumanResources;
  }

  @Override
  public Manager getManager() throws IllegalStateException {
    if (manager == null) {
      throw new IllegalStateException("This user has no assigned manager.");
    }
    return manager;
  }

  @Override
  public void setManager(Manager manager) {
    this.manager = manager;
  }
}
