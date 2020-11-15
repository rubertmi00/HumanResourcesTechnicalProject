package model.users;

/**
 * An implementation of the {@link IUser} interface which represents an Administrative user of the
 * application.
 * <p>
 * NOTE: This class was made under the assumption that an "Administrative user" is not meant to be a
 * representation of a normal employee with a salary etc., but rather a utility account with high
 * level control and permissions over all of the information in the system. It can be seen as
 * similar to the "root" user in a Linux system.
 *
 * @author Michael Ruberto
 */
public class Administrator implements IUser {

  private static int currentId = 0;
  private int id;
  private String name;
  private String password;

  /**
   * Constructs an instance of an Administrator. The IDs will be automatically assigned to the
   * {@code currentId} value, which will then be decremented.
   *
   * @param name     The name of this administrator.
   * @param password The password for this administrator.
   * @throws IllegalArgumentException If the name is null or the password is null or empty.
   * @author Michael Ruberto
   */
  public Administrator(String name, String password) throws IllegalArgumentException {
    //VALIDATE INPUTS
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (password == null || password.equals("")) {
      throw new IllegalArgumentException("Password must be a non-empty String.");
    }

    this.id = this.currentId;
    this.currentId--;

    this.name = name;
    this.password = password;
  }

  /**
   * Changes {@code currentId} to the given value so that the next Administrator created will have
   * that ID.
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
    return "Administrator";
  }

  @Override
  public boolean isInHumanResources() {
    return false;
  }
}
