package model;

import java.util.ArrayList;
import model.users.IUser;

/**
 * An {@code interface} which represents a model for a Human Resources application. A model which
 * implements this interface will be able to add and remove {@link IUser}s from the system, and be
 * able to access and edit a user's information, such as their salaries, bonuses, whether or not
 * they work in Human Resources, etc.
 *
 * @author Michael Ruberto
 */
public interface IHumanResourcesModel {

  /**
   * Changes whether or not the specified {@link model.users.AEmployee} works in Human Resources.
   *
   * @param id               The unique ID number of the employee
   * @param inHumanResources Whether or not the employee works in Human Resources
   * @throws IllegalStateException If there is no user with the given {@code id} in the system, or
   *                               if the current user doesn't have permission to execute this
   *                               command.
   * @author Michael Ruberto
   */
  void changeHRStatus(int id, boolean inHumanResources)
      throws IllegalStateException;

  /**
   * Adds a new {@link model.users.AEmployee} to the system.
   *
   * @param employeeType     A string representing the type of employee to be created (Standard
   *                         Employee, Manager).
   * @param name             The name of the new employee.
   * @param password         The password of the new employee.
   * @param salary           The salary of the new employee.
   * @param vacationBalance  The vacation balance of the new employee.
   * @param annualBonus      The annual bonus of the new employee.
   * @param inHumanResources Whether or not the new employee works in Human Resources.
   * @throws IllegalArgumentException If {@code employeeType} doesn't represent a valid type of
   *                                  {@link model.users.AEmployee}, or if any of the inputs do not
   *                                  represent valid information for an {@link model.users.AEmployee}.
   * @author Michael Ruberto
   */
  void addEmployee(String employeeType, String name, String password, double salary,
      int vacationBalance, double annualBonus, boolean inHumanResources)
      throws IllegalArgumentException;

  /**
   * Adds a new {@link model.users.Administrator} to the system.
   *
   * @param name     The name of the new Administrator account.
   * @param password The password for the new Administrator account.
   * @throws IllegalArgumentException If any of the inputs do not represent valid information for an
   *                                  {@link model.users.Administrator}.
   * @author Michael Ruberto
   */
  void addAdministrator(String name, String password) throws IllegalArgumentException;

  /**
   * Removes the specified {@link IUser} from the system.
   *
   * @param id The unique ID of the user to be removed from the system.
   * @return The user who was just removed
   * @throws IllegalStateException If there is no user with the given ID, or if the user to be
   *                               removed is the current user, or if the current user doesn't have
   *                               permission to execute this command.
   * @author Michael Ruberto
   */
  IUser removeUser(int id) throws IllegalStateException;

  /**
   * Links the specified {@link model.users.AEmployee} and {@link model.users.Manager}. Adds the
   * employee to the manager's list of reporting employees and sets the manager as the employee's
   * manager. If the employee already had a manager, they will be removed from the old manager's
   * list.
   *
   * @param employeeID The unique ID of the employee.
   * @param managerID  The unique ID of the manager.
   * @throws IllegalStateException If the {@code employeeID} and {@code managerID} don't correcpond
   *                               to a valid {@link model.users.AEmployee} and {@link
   *                               model.users.Manager} respectively, or if the current user doesn't
   *                               have permission to execute this command.
   * @author Michael Ruberto
   */
  void linkEmployeeAndManager(int employeeID, int managerID) throws IllegalStateException;

  /**
   * Promotes the specified {@link model.users.AEmployee} to be a {@link model.users.Manager}.
   *
   * @param id The ID of the employee to be promoted.
   * @throws IllegalStateException If there is no user with the given ID, or if the user is already
   *                               a manager, or if the current user doesn't have permission to
   *                               execute this command.
   * @author Michael Ruberto
   */
  void promoteToManager(int id) throws IllegalStateException;

  /**
   * Demotes the specified {@link model.users.AEmployee} to be a {@link
   * model.users.StandardEmployee}.
   *
   * @param id The ID of the employee to be demoted.
   * @throws IllegalStateException If there is no user with the given ID, or if the user is already
   *                               a Standard Employee, or if the current user doesn't have
   *                               permission to execute this command.
   * @author Michael Ruberto
   */
  void demoteToStandard(int id) throws IllegalStateException;

  /**
   * Gets the salary of the specified {@link model.users.AEmployee}.
   *
   * @param id The unique ID of the employee.
   * @return The employee's salary.
   * @throws IllegalStateException If there is no employee with the given ID or if the current user
   *                               doesn't have permission to execute this command.
   * @author Michael Ruberto
   */
  double getSalary(int id) throws IllegalStateException;

  /**
   * Changes the salary of the specified {@link model.users.AEmployee}.
   *
   * @param id     The unique ID of the employee.
   * @param salary The employee's new salary.
   * @throws IllegalStateException    If there is no employee with the given ID or if the current
   *                                  user doesn't have permission to execute this command.
   * @throws IllegalArgumentException If the salary is negative.
   * @author Michael Ruberto
   */
  void setSalary(int id, double salary) throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets the salary history of the specified {@link model.users.AEmployee}.
   *
   * @param id The unique ID of the employee.
   * @return The employee's salary history.
   * @throws IllegalStateException If there is no employee with the given ID or if the current user
   *                               doesn't have permission to execute this command.
   * @author Michael Ruberto
   */
  ArrayList<Double> getSalaryHistory(int id) throws IllegalStateException;

  /**
   * Gets the vacation balance of the specified {@link model.users.AEmployee}.
   *
   * @param id The unique ID of the employee.
   * @return The employee's vacation balance.
   * @throws IllegalStateException If there is no employee with the given ID or if the current user
   *                               doesn't have permission to execute this command.
   * @author Michael Ruberto
   */
  int getVacationBalance(int id) throws IllegalStateException;

  /**
   * Changes the vacation balance of the specified {@link model.users.AEmployee}.
   *
   * @param id              The unique ID of the employee.
   * @param vacationBalance The employee's new vacation balance.
   * @throws IllegalStateException    If there is no employee with the given ID or if the current
   *                                  user doesn't have permission to execute this command.
   * @throws IllegalArgumentException If the vacation balance is negative.
   * @author Michael Ruberto
   */
  void setVacationBalance(int id, int vacationBalance)
      throws IllegalStateException, IllegalArgumentException;

  /**
   * Gets the annual bonus of the specified {@link model.users.AEmployee}.
   *
   * @param id The unique ID of the employee.
   * @return The employee's annual bonus.
   * @throws IllegalStateException If there is no employee with the given ID or if the current user
   *                               doesn't have permission to execute this command.
   * @author Michael Ruberto
   */
  double getAnnualBonus(int id) throws IllegalStateException;

  /**
   * Changes the annual bonus of the specified {@link model.users.AEmployee}.
   *
   * @param id          The unique ID of the employee.
   * @param annualBonus The employee's new annual bonus.
   * @throws IllegalStateException    If there is no employee with the given ID or if the current
   *                                  user doesn't have permission to execute this command.
   * @throws IllegalArgumentException If the annual bonus is negative.
   * @author Michael Ruberto
   */
  void setAnnualBonus(int id, double annualBonus)
      throws IllegalStateException, IllegalArgumentException;
}
