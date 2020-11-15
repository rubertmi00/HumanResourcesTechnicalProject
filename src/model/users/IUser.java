package model.users;

import java.util.ArrayList;

/**
 * An {@code interface} which represents a user account for a Human Resources application.
 *
 * @author Michael Ruberto
 */
public interface IUser {

  /**
   * Gets the user's ID.
   *
   * @return The user's ID.
   * @author Michael Ruberto
   */
  int getId();

  /**
   * Gets the user's name.
   *
   * @return The user's name.
   * @author Michael Ruberto
   */
  String getName();

  /**
   * Compares the given String to this user's {@code password} to determine if they are equal.
   *
   * @param guess The guess that is being compared to the password.
   * @return Whether or not the guess was correct.
   */
  boolean correctPassword(String guess);

  /**
   * Gets a String representing the type of user this is.
   *
   * @return The user's type.
   * @author Michael Ruberto
   */
  String getUserType();

  /**
   * Does this user work in Human Resources?
   *
   * @return Whether or not the user works in HR.
   * @author Michael Ruberto
   */
  boolean isInHumanResources();

  //NOTE, METHODS BELOW THIS LINE DO NOT APPLY TO ADMINISTRATIVE USER ACCOUNTS. AS SUCH, THEY THROW
  //  UNSUPPORTED OPERATION EXCEPTIONS BY DEFAULT

  /**
   * Changes whether or not this user works in Human Resources.
   *
   * @param inHumanResources Whether or not this user works in HR.
   * @throws UnsupportedOperationException If this user cannot work in HR.
   * @author Michael Ruberto
   */
  default void setInHumanResources(boolean inHumanResources) throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") can not be in Human Resources.");
  }

  /**
   * Gets this user's salary.
   *
   * @return This user's salary.
   * @throws UnsupportedOperationException If this user cannot have a salary.
   * @author Michael Ruberto
   */
  default double getSalary() throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") does not have a salary.");
  }

  /**
   * Changes the salary of this user. Adds the old salary to this user's salary history.
   *
   * @param salary The new salary.
   * @throws UnsupportedOperationException If this user cannot have a salary.
   * @throws IllegalArgumentException      If the salary is negative.
   * @author Michael Ruberto
   */
  default void setSalary(double salary)
      throws UnsupportedOperationException, IllegalArgumentException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") does not have a salary.");
  }

  /**
   * Gets this user's salary history.
   *
   * @return This user's salary history.
   * @throws UnsupportedOperationException If this user cannot have a salary history.
   * @author Michael Ruberto
   */
  default ArrayList<Double> getSalaryHistory() throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") does not have a salary history.");
  }

  /**
   * Gets this user's vacation balance.
   *
   * @return This user's vacation balance.
   * @throws UnsupportedOperationException If this user cannot have a vacation balance.
   * @author Michael Ruberto
   */
  default int getVacationBalance() throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") does not have a vacation balance.");
  }

  /**
   * Changes the vacation balance of this user.
   *
   * @param vacationBalance The new vacation balance.
   * @throws UnsupportedOperationException If this user cannot have a vacation balance.
   * @throws IllegalArgumentException      If the vacation balance is negative.
   * @author Michael Ruberto
   */
  default void setVacationBalance(int vacationBalance)
      throws UnsupportedOperationException, IllegalArgumentException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") does not have a vacation balance.");
  }

  /**
   * Gets this user's annual bonus.
   *
   * @return This user's annual bonus.
   * @throws UnsupportedOperationException If this user cannot have an annual bonus.
   * @author Michael Ruberto
   */
  default double getAnnualBonus() throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") does not have an annual bonus.");
  }

  /**
   * Changes the annual bonus of this user.
   *
   * @param annualBonus The new annual bonus.
   * @throws UnsupportedOperationException If this user cannot have an annual bonus.
   * @throws IllegalArgumentException      If the annual bonus is negative.
   * @author Michael Ruberto
   */
  default void setAnnualBonus(double annualBonus)
      throws UnsupportedOperationException, IllegalArgumentException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") does not have an annual bonus.");
  }

  /**
   * Gets the {@link Manager} to whom this user reports.
   *
   * @return This user's manager.
   * @throws UnsupportedOperationException If this user cannot have a manager.
   * @author Michael Ruberto
   */
  default Manager getManager() throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") can not have a manager.");
  }

  /**
   * Changes the {@link Manager} to whom this user reports.
   *
   * @param manager The new manager.
   * @throws UnsupportedOperationException If this user cannot have a manager.
   * @author Michael Ruberto
   */
  default void setManager(Manager manager) throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "This type of user (" + getUserType() + ") can not have a manager.");
  }
}
