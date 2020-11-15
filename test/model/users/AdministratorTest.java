package model.users;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the class {@link Administrator} to ensure that all of its public methods work
 * properly.
 *
 * @author Michael Ruberto
 */
public class AdministratorTest {

  @Before
  public void resetIDs() {
    Administrator.setNextID(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullName() {
    Administrator admin = new Administrator(null, "Test");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullPW() {
    Administrator admin = new Administrator("Name", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptyPW() {
    Administrator admin = new Administrator("Name", "");
  }

  @Test
  public void testConstructor() {
    Administrator admin = new Administrator("Name", "Test");
    assertNotNull(admin);
  }

  @Test
  public void testGetID() {
    Administrator admin = new Administrator("Name", "Password");
    assertEquals(0, admin.getId());
  }

  @Test
  public void testGetName() {
    Administrator admin = new Administrator("Name", "Password");
    assertEquals("Name", admin.getName());
  }

  @Test
  public void testCorrectPassword() {
    Administrator admin = new Administrator("Name", "Password");
    assertFalse(admin.correctPassword("password"));
    assertTrue(admin.correctPassword("Password"));
  }

  @Test
  public void testGetUserType() {
    Administrator admin = new Administrator("Name", "Password");
    assertEquals("Administrator", admin.getUserType());
  }

  @Test
  public void testInHR() {
    Administrator admin = new Administrator("Name", "Password");
    assertFalse(admin.isInHumanResources());
  }
}