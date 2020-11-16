package model;

import static org.junit.Assert.*;

import model.users.AEmployee;
import model.users.Administrator;
import model.users.Manager;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the class {@link HRModel} to ensure that all of its public methods work properly.
 *
 * @author Michael Ruberto
 */
public class HRModelTest {

  @Before
  public void resetIDs() {
    Administrator.setNextID(0);
    AEmployee.setNextID(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHRModelConstructorNoPassword() {
    HRModel model = new HRModel(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHRModelConstructorEmptyPassword() {
    HRModel model = new HRModel("");
  }

  @Test
  public void testHRModelConstructor() {
    HRModel model = new HRModel("Password");
    assertNotNull(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLogInNoPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLogInEmptyPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "");
  }

  @Test(expected = IllegalStateException.class)
  public void testLogInBadID() {
    HRModel model = new HRModel("Password");
    model.logIn(7, "Password");
  }

  @Test(expected = IllegalStateException.class)
  public void testLogInBadPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "password");
  }

  @Test
  public void testLogIn() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    assertEquals("Default Admin", model.getCurrentUser().getName());
  }

  @Test
  public void testLogOut() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    assertEquals("Default Admin", model.getCurrentUser().getName());
    model.logOut();
    assertNull(model.getCurrentUser());
  }

  @Test(expected = IllegalStateException.class)
  public void testChangeHRStatusBadUser() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Test", "Test", 0, 0, 0, false);
    model.logOut();

    model.logIn(1, "Test");
    model.changeHRStatus(1, true);
  }

  @Test
  public void testChangeHRStatus() {
    HRModel model = new HRModel("Password");

    model.logIn(0, "Password");
    model.addEmployee("Manager", "Test", "Test", 0, 0, 0, false);

    assertFalse(((Manager) model.getUsers().get(1)).isInHumanResources());
    model.changeHRStatus(1, true);
    assertTrue(((Manager) model.getUsers().get(1)).isInHumanResources());
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveUserBadUser() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Test", "Test", 0, 0, 0, false);
    model.logOut();
    model.logIn(1, "Test");
    model.removeUser(0);
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveUserNoSuchUser() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.removeUser(8675309);
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveUserRemoveSelf() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.removeUser(0);
  }

  @Test
  public void testRemoveUser() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Test", "Test", 0, 0, 0, false);
    assertNotNull(model.removeUser(1));
  }

  @Test
  public void testRemoveUserUnlink() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Test", "Test", 0, 0, 0, false);
    model.addEmployee("Standard Employee", "Employee", "Test", 0, 0, 0, false);

    Manager manager = (Manager) model.getUsers().get(1);
    assertTrue(manager.getReportingEmployees().size() == 0);
    model.linkEmployeeAndManager(2, 1);
    assertTrue(manager.getReportingEmployees().size() == 1);
    model.removeUser(2);
    assertTrue(manager.getReportingEmployees().size() == 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddStandardEmployeeNoPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Employee", null, 0, 0, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddStandardEmployeeEmptyPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Employee", "", 0, 0, 0, false);
  }

  @Test
  public void testAddStandardEmployee() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    assertTrue(model.getUsers().size() == 1);
    model.addEmployee("Standard Employee", "Employee", "Test", 0, 0, 0, false);
    assertTrue(model.getUsers().size() == 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddManagerNoPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", null, 0, 0, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddManagerEmptyPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", "", 0, 0, 0, false);
  }

  @Test
  public void testAddManager() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    assertTrue(model.getUsers().size() == 1);
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    assertTrue(model.getUsers().size() == 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddAdminNoPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addAdministrator("Admin", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddAdminEmptyPassword() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addAdministrator("Admin", "");
  }

  @Test
  public void testAddAdmin() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    assertTrue(model.getUsers().size() == 1);
    model.addAdministrator("Admin", "Test");
    assertTrue(model.getUsers().size() == 2);
  }

  @Test(expected = IllegalStateException.class)
  public void testLinkEmployeeManagerNoSuchEmployee() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    model.linkEmployeeAndManager(8675309, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testLinkEmployeeManagerNoSuchManager() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.linkEmployeeAndManager(1, 1234567890);
  }

  @Test(expected = IllegalStateException.class)
  public void testLinkEmployeeManagerTwoEmployees() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.addEmployee("Standard Employee", "Loyee", "Test", 0, 0, 0, false);
    model.linkEmployeeAndManager(1, 2);
  }

  @Test
  public void testLinkEmployeeManager() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    AEmployee emp = (AEmployee) model.getUsers().get(1);
    Manager man = (Manager) model.getUsers().get(2);

    boolean noManager = false;
    try {
      emp.getManager();
    } catch (IllegalStateException ise) {
      noManager = true;
    }
    assertTrue(noManager);
    assertTrue(man.getReportingEmployees().size() == 0);

    model.linkEmployeeAndManager(1, 2);

    assertNotNull(emp.getManager());
    assertTrue(man.getReportingEmployees().size() == 1);
  }

  @Test
  public void testLinkEmployeeManagerWithUnlink() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.addEmployee("Manager", "Old Man", "Test", 0, 0, 0, false);
    model.addEmployee("Manager", "New Man", "Test", 0, 0, 0, false);
    AEmployee emp = (AEmployee) model.getUsers().get(1);
    Manager oldMan = (Manager) model.getUsers().get(2);
    Manager newMan = (Manager) model.getUsers().get(3);

    boolean noManager = false;
    try {
      emp.getManager();
    } catch (IllegalStateException ise) {
      noManager = true;
    }
    assertTrue(noManager);
    assertTrue(oldMan.getReportingEmployees().size() == 0);
    assertTrue(newMan.getReportingEmployees().size() == 0);

    model.linkEmployeeAndManager(1, 2);

    assertEquals("Old Man", emp.getManager().getName());
    assertTrue(oldMan.getReportingEmployees().size() == 1);
    assertTrue(newMan.getReportingEmployees().size() == 0);

    model.linkEmployeeAndManager(1, 3);

    assertEquals("New Man", emp.getManager().getName());
    assertTrue(oldMan.getReportingEmployees().size() == 0);
    assertTrue(newMan.getReportingEmployees().size() == 1);
  }

  @Test(expected = IllegalStateException.class)
  public void testPromoteToManagerNotAdmin() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.logOut();
    model.promoteToManager(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testPromoteToManagerAdminUser() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addAdministrator("Admin", "Test");
    model.logOut();
    model.promoteToManager(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void testPromoteToManagerAlreadyManager() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    model.promoteToManager(1);
  }

  @Test
  public void testPromoteToManager() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    assertTrue(model.getUsers().size() == 2);
    assertTrue(model.getUsers().get(1).getName().equals("Emp"));
    assertTrue(model.getUsers().get(1).getUserType().equals("AEmployee, StandardEmployee"));
    model.promoteToManager(1);
    assertTrue(model.getUsers().size() == 2);
    assertTrue(model.getUsers().get(1).getName().equals("Emp"));
    assertTrue(model.getUsers().get(1).getUserType().equals("AEmployee, Manager"));
  }

  @Test(expected = IllegalStateException.class)
  public void testDemoteToStandardNotAdmin() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    model.logOut();
    model.demoteToStandard(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testDemoteToStandardAdminUser() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addAdministrator("Admin", "Test");
    model.logOut();
    model.demoteToStandard(-1);
  }

  @Test(expected = IllegalStateException.class)
  public void testDemoteToStandardAlreadyStandard() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.demoteToStandard(1);
  }

  @Test
  public void testDemoteToStandard() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    assertTrue(model.getUsers().size() == 2);
    assertTrue(model.getUsers().get(1).getName().equals("Man"));
    assertTrue(model.getUsers().get(1).getUserType().equals("AEmployee, Manager"));
    model.demoteToStandard(1);
    assertTrue(model.getUsers().size() == 2);
    assertTrue(model.getUsers().get(1).getName().equals("Man"));
    assertTrue(model.getUsers().get(1).getUserType().equals("AEmployee, StandardEmployee"));
  }

  @Test
  public void testReadAccessAdmin() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    assertEquals(0, model.getSalary(1), 0.01);
  }

  @Test
  public void testReadAccessManagerReporting() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.linkEmployeeAndManager(2, 1);
    model.logOut();
    model.logIn(1, "Test");
    assertEquals(0, model.getSalary(2), 0.01);
  }

  @Test
  public void testReadAccessHR() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, true);
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    model.logOut();
    model.logIn(1, "Test");
    assertEquals(0, model.getSalary(2), 0.01);
  }

  @Test
  public void testReadAccessSelf() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    model.logOut();
    model.logIn(1, "Test");
    assertEquals(0, model.getSalary(1), 0.01);
  }

  @Test(expected = IllegalStateException.class)
  public void testReadAccessManagerNotReporting() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.logOut();
    model.logIn(1, "Test");
    model.getSalary(2);
  }

  @Test(expected = IllegalStateException.class)
  public void testReadAccessDoubleHR() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, true);
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, true);
    model.logOut();
    model.logIn(1, "Test");
    model.getSalary(2);
  }

  @Test(expected = IllegalStateException.class)
  public void testReadAccessStandardSomeoneElse() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man1", "Test", 0, 0, 0, false);
    model.addEmployee("Manager", "Man2", "Test", 0, 0, 0, false);
    model.logOut();
    model.logIn(1, "Test");
    model.getSalary(2);
  }

  @Test(expected = IllegalStateException.class)
  public void testReadAccessLoggedOut() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Manager", "Man1", "Test", 0, 0, 0, false);
    model.addEmployee("Manager", "Man2", "Test", 0, 0, 0, false);
    model.logOut();
    model.getSalary(2);
  }

  @Test
  public void testWriteAccessAdmin() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    assertEquals(0, model.getSalary(1), 0.01);
    model.setSalary(1, 100);
    assertEquals(100, model.getSalary(1), 0.01);
  }

  @Test
  public void testWriteAccessManagerReporting() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    model.linkEmployeeAndManager(1, 2);
    assertEquals(0, model.getSalary(1), 0.01);
    model.logOut();
    model.logIn(2, "Test");
    model.setSalary(1, 100);
    assertEquals(100, model.getSalary(1), 0.01);
  }

  @Test(expected = IllegalStateException.class)
  public void testWriteAccessManagerNotReporting() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.addEmployee("Manager", "Man", "Test", 0, 0, 0, false);
    assertEquals(0, model.getSalary(1), 0.01);
    model.logOut();
    model.logIn(2, "Test");
    model.setSalary(1, 100);
  }

  @Test(expected = IllegalStateException.class)
  public void testWriteAccessOneHR() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, false);
    model.addEmployee("Standard Employee", "HR", "Test", 0, 0, 0, true);
    assertEquals(0, model.getSalary(1), 0.01);
    model.logOut();
    model.logIn(2, "Test");
    model.setSalary(1, 100);
  }

  @Test(expected = IllegalStateException.class)
  public void testWriteAccessTwoHR() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, true);
    model.addEmployee("Standard Employee", "HR", "Test", 0, 0, 0, true);
    assertEquals(0, model.getSalary(1), 0.01);
    model.logOut();
    model.logIn(2, "Test");
    model.setSalary(1, 100);
  }

  @Test(expected = IllegalStateException.class)
  public void testWriteAccessSelf() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, true);
    assertEquals(0, model.getSalary(1), 0.01);
    model.logOut();
    model.logIn(1, "Test");
    model.setSalary(1, 100);
  }

  @Test(expected = IllegalStateException.class)
  public void testWriteAccessLoggedOut() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.addEmployee("Standard Employee", "Emp", "Test", 0, 0, 0, true);
    assertEquals(0, model.getSalary(1), 0.01);
    model.logOut();
    model.setSalary(1, 100);
  }

  @Test(expected = IllegalStateException.class)
  public void testReadNoSuchUser() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.getSalary(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testWriteNoSuchUser() {
    HRModel model = new HRModel("Password");
    model.logIn(0, "Password");
    model.setSalary(1, 100);
  }
}