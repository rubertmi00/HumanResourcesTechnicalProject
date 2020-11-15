package model;

import java.util.ArrayList;
import java.util.HashMap;
import model.users.AEmployee;
import model.users.Administrator;
import model.users.StandardEmployee;
import model.users.IUser;
import model.users.Manager;

/**
 * An implementation of the {@link IHumanResourcesModel} interface which represents a model for a
 * Human Resources application. This model allows a user to to add and remove {@link IUser}s from
 * the system, and be able to access and edit a user's information, such as their salaries, bonuses,
 * whether or not they work in Human Resources, etc.
 * <p>
 * Before performing any actions in this model, the user must first log in using their unique ID and
 * their password to authenticate that they have the correct permissions to perform specific
 * functions. Note that in this implementation, passwords are stored as a {@code String}
 * representation of their {@code hashCode} to avoid storing them as plaintext for security
 * purposes. In a real application which contained actual personal information, I would utilize a
 * more secure hash function such as SHA-256 to make the passwords harder to crack.
 *
 * @author Michael Ruberto
 */
public class HRModel implements IHumanResourcesModel {

  private IUser currentUser;
  private HashMap<Integer, IUser> users;

  /**
   * Constructs an instance of this HRModel and adds a default {@link Administrator} account (ID 0)
   * which can be used to manage the {@link IUser}s and information in the system.
   *
   * @param defaultAdminPassword The password to be used for the default {@link Administrator}
   *                             account.
   * @throws IllegalArgumentException If the given password is invalid.
   * @author Michael Ruberto
   */
  public HRModel(String defaultAdminPassword) throws IllegalArgumentException {
    //VALIDATING INPUTS
    if (defaultAdminPassword == null || defaultAdminPassword.equals("")) {
      throw new IllegalArgumentException("The given password must be a non-empty string.");
    }

    Administrator admin = new Administrator("Default Admin", defaultAdminPassword.hashCode() + "");
    users = new HashMap<Integer, IUser>();
    users.put(admin.getId(), admin);
  }

  /**
   * Gets the {@link IUser} who is currently logged in.
   *
   * @return The current user.
   * @author Michael Ruberto
   */
  public IUser getCurrentUser() {
    return currentUser;
  }

  /**
   * Gets the {@link HashMap} which stores all of the {@link IUser}s in the system. The key values
   * in the map are each employee's unique ID values.
   *
   * @return The hash map containing the users.
   * @author Michael Ruberto
   */
  public HashMap<Integer, IUser> getUsers() {
    return users;
  }

  /**
   * Logs into this application by setting the {@code currentUser} to match the {@link IUser} with
   * the given ID, provided the password is correct. This will allow the user to perform any action
   * they are authorized to carry out.
   *
   * @param id     The unique ID for the user.
   * @param passwd The password for the user.
   * @throws IllegalArgumentException If the given password is invalid.
   * @throws IllegalStateException    If there is no user with the given ID, or if the password is
   *                                  incorrect.
   * @author Michael Ruberto
   */
  public void logIn(int id, String passwd) throws IllegalArgumentException, IllegalStateException {
    //VALIDATING INPUTS
    if (passwd == null || passwd.equals("")) {
      throw new IllegalArgumentException("The given password must be a non-empty string.");
    }
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    }

    if (users.get(id).correctPassword(passwd.hashCode() + "")) {
      currentUser = users.get(id);
    } else {
      throw new IllegalStateException(
          "Incorrect password for " + users.get(id).getName() + " (ID: " + id + ")");
    }
  }

  /**
   * Logs the current user out of the application by setting the {@code currentUser} to {@code
   * null}.
   *
   * @author Michael Ruberto
   */
  public void logOut() {
    currentUser = null;
  }

  // Checks if the current user is an Administrator for purposes of permission-checking.
  // If an admin is signed in, do nothing. If not, throw an error.
  private void verifyAdministrator() throws IllegalStateException {
    if (currentUser == null) {
      throw new IllegalStateException("No user signed in.");
    } else if (!currentUser.getUserType().equals("Administrator")) {
      throw new IllegalStateException("The current user (" + currentUser.getName()
          + ") does not have permission to perform this action.");
    }
  }

  @Override
  public void changeHRStatus(int id, boolean inHumanResources)
      throws IllegalStateException {
    //VALIDATING INPUTS
    verifyAdministrator();
    IUser target = null;
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    } else {
      target = users.get(id);
    }

    target.setInHumanResources(inHumanResources);
  }

  @Override
  public void addEmployee(String employeeType, String name, String password, double salary,
      int vacationBalance, double annualBonus, boolean inHumanResources)
      throws IllegalArgumentException {
    //VALIDATING INPUTS
    verifyAdministrator();
    if (password == null || password.equals("")) {
      throw new IllegalArgumentException("Given password must be a non-empty string.");
    }
    if (employeeType == null || (!employeeType.equals("Standard Employee") && !employeeType
        .equals("Manager"))) {
      throw new IllegalArgumentException("Invalid employee type provided.");
    }

    AEmployee newEmployee = null;
    if (employeeType.equals("Standard Employee")) {
      newEmployee = new StandardEmployee(name, password.hashCode() + "", salary, vacationBalance,
          annualBonus, inHumanResources);
    } else {
      newEmployee = new Manager(name, password.hashCode() + "", salary, vacationBalance,
          annualBonus, inHumanResources);
    }

    users.put(newEmployee.getId(), newEmployee);
  }

  @Override
  public void addAdministrator(String name, String password)
      throws IllegalArgumentException {
    //VALIDATING INPUTS
    verifyAdministrator();
    if (password == null || password.equals("")) {
      throw new IllegalArgumentException("Given password must be a non-empty string.");
    }

    Administrator newAdmin = new Administrator(name, password.hashCode() + "");
    users.put(newAdmin.getId(), newAdmin);
  }

  @Override
  public IUser removeUser(int id) throws IllegalStateException {
    verifyAdministrator();
    //VALIDATING INPUTS
    if (users.get(id) == null) {
      throw new IllegalStateException("No user with ID " + id + " found.");
    }
    if (id == currentUser.getId()) {
      throw new IllegalStateException("Can't remove currently signed in user.");
    }

    // IF THE USER YOU ARE REMOVING HAS A MANAGER ASSIGNED TO THEM, UNLINK THEM
    IUser removed = users.remove(id);
    try {
      Manager manager = removed.getManager();

      // Unlink the manager and the employee
      manager.removeReportingEmployee(removed.getId());
      removed.setManager(null);
    } catch (Exception iseOrUSE) {
      // This user didn't have an assigned manager
    }

    return removed;
  }

  @Override
  public void linkEmployeeAndManager(int employeeID, int managerID) throws IllegalStateException {

    //VALIDATE INPUTS
    if (users.get(employeeID) == null || users.get(managerID) == null) {
      throw new IllegalStateException("Both IDs must correspond to valid users.");
    }
    if (!users.get(managerID).getUserType().equals("AEmployee, Manager") ||
        !users.get(employeeID).getUserType().contains("AEmployee")) {
      throw new IllegalStateException("Must provide one Manager and one AEmployee.");
    }

    Manager manager = (Manager) users.get(managerID);
    AEmployee employee = (AEmployee) users.get(employeeID);

    //Unlink employee from old manager if needed
    try {
      Manager oldManager = employee.getManager();
      oldManager.removeReportingEmployee(employeeID);
    } catch (IllegalStateException ise) {
      // This employee had no manager
    }

    employee.setManager(manager);
    manager.addReportingEmployee(employee);
  }

  // Checks if the current user has permission to read information about the user with the given ID
  // If the user has permission, do nothing. Else, throw an error.
  //    - If current user is an administrator, they can read any information
  //    - If current user is a manager, they can read information about reporting employees
  //    - If current user is in HR, they can read information from any non-HR user
  //    - All users can read their own information
  private void verifyReadAccess(int id) throws IllegalStateException {
    try {
      // If you're an administrator, you have full access.
      verifyAdministrator();
    } catch (IllegalStateException ise) {
      if (currentUser == null) {
        throw new IllegalStateException("You must log in to perform this action.");
      }

      // You are not an administrator.
      AEmployee user = (AEmployee) currentUser;

      // You can access your own information
      if (id == user.getId()) {
        return;
      }

      // If you're in HR, you can view the target info so long as they're not also in HR
      if (user.isInHumanResources() && !users.get(id).isInHumanResources()) {
        return;
      }

      // If you're a manager, you can view the target info if the employee reports to you
      if (user.getUserType().contains("Manager")) {
        Manager manager = (Manager) user;

        if (manager.getReportingEmployees().get(id) != null) {
          return;
        }
      }

      throw new IllegalStateException("The current user (" + currentUser.getName()
          + ") does not have permission to perform this action.");
    }
  }

  // Checks if the current user has permission to edit information about the user with the given ID
  // If the user has permission, do nothing. Else, throw an error.
  //    - If current user is an administrator, they can edit any information
  //    - If current user is a manager, they can edit information about reporting employees
  private void verifyWriteAccess(int id) throws IllegalStateException {
    try {
      // If you're an administrator, you have full access.
      verifyAdministrator();
    } catch (IllegalStateException ise) {
      if (currentUser == null) {
        throw new IllegalStateException("You must log in to perform this action.");
      }

      // You are not an administrator.
      AEmployee user = (AEmployee) currentUser;

      // If you're a manager, you can edit the target info if the employee reports to you
      if (user.getUserType().contains("Manager")) {
        Manager manager = (Manager) user;

        if (manager.getReportingEmployees().get(id) != null) {
          return;
        }
      }

      throw new IllegalStateException("The current user (" + currentUser.getName()
          + ") does not have permission to perform this action.");
    }
  }

  @Override
  public double getSalary(int id) throws IllegalStateException {
    //VALIDATING INPUTS
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    }
    verifyReadAccess(id);

    return users.get(id).getSalary();
  }

  @Override
  public void setSalary(int id, double salary)
      throws IllegalStateException, IllegalArgumentException {
    //VALIDATING INPUTS
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    }
    verifyWriteAccess(id);

    users.get(id).setSalary(salary);
  }

  @Override
  public ArrayList<Double> getSalaryHistory(int id) throws IllegalStateException {
    //VALIDATING INPUTS
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    }
    verifyReadAccess(id);

    return users.get(id).getSalaryHistory();
  }

  @Override
  public int getVacationBalance(int id) throws IllegalStateException {
    //VALIDATING INPUTS
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    }
    verifyReadAccess(id);

    return users.get(id).getVacationBalance();
  }

  @Override
  public void setVacationBalance(int id, int vacationBalance)
      throws IllegalStateException, IllegalArgumentException {
    //VALIDATING INPUTS
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    }
    verifyWriteAccess(id);

    users.get(id).setVacationBalance(vacationBalance);
  }

  @Override
  public double getAnnualBonus(int id) throws IllegalStateException {
    //VALIDATING INPUTS
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    }
    verifyReadAccess(id);

    return users.get(id).getAnnualBonus();
  }

  @Override
  public void setAnnualBonus(int id, double annualBonus)
      throws IllegalStateException, IllegalArgumentException {
    //VALIDATING INPUTS
    if (users.get(id) == null) {
      throw new IllegalStateException("No user found with ID " + id);
    }
    verifyWriteAccess(id);

    users.get(id).setAnnualBonus(annualBonus);
  }
}
