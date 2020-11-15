package model.users;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the class {@link Manager} to ensure that all of its public methods work properly.
 *
 * @author Michael Ruberto
 */
public class ManagerTest {

  @Before
  public void resetIDs() {
    AEmployee.setNextID(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullName() {
    Manager man = new Manager(null, "Test", 0, 0, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullPW() {
    Manager man = new Manager("Name", null, 0, 0, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptyPW() {
    Manager man = new Manager("Name", "", 0, 0, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegSalary() {
    Manager man = new Manager("Name", "Password", -1, 0, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegVacation() {
    Manager man = new Manager("Name", "Password", 0, -1, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegBonus() {
    Manager man = new Manager("Name", "Password", 0, 0, -1, true);
  }

  @Test
  public void testConstructor() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertNotNull(man);
  }

  @Test
  public void testGetID() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(1, man.getId());
  }

  @Test
  public void testGetName() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals("Name", man.getName());
  }

  @Test
  public void testCorrectPassword() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertFalse(man.correctPassword("password"));
    assertTrue(man.correctPassword("Password"));
  }

  @Test
  public void testGetUserType() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals("AEmployee, Manager", man.getUserType());
  }

  @Test
  public void testGetSalary() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getSalary(), 0.01);
  }

  @Test
  public void testSetSalary() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getSalary(), 0.01);
    man.setSalary(100);
    assertEquals(100, man.getSalary(), 0.01);
    assertEquals(0, man.getSalaryHistory().get(0), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetSalaryNeg() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getSalary(), 0.01);
    man.setSalary(-100);
  }

  @Test
  public void testGetSalaryHistory() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(new ArrayList<Double>(), man.getSalaryHistory());
  }

  @Test
  public void testGetVacationBalance() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getVacationBalance());
  }

  @Test
  public void testSetVacationBalance() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getVacationBalance());
    man.setVacationBalance(100);
    assertEquals(100, man.getVacationBalance());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetVacationBalanceNeg() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getVacationBalance());
    man.setVacationBalance(-100);
  }

  @Test
  public void testGetAnnualBonus() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getAnnualBonus(), 0.01);
  }

  @Test
  public void testSetAnnualBonus() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getAnnualBonus(), 0.01);
    man.setAnnualBonus(100);
    assertEquals(100, man.getAnnualBonus(), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetAnnualBonusNeg() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(0, man.getAnnualBonus(), 0.01);
    man.setAnnualBonus(-100);
  }

  @Test
  public void testIsInHR() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    Manager man2 = new Manager("Name", "Password", 0, 0, 0, false);
    assertTrue(man.isInHumanResources());
    assertFalse(man2.isInHumanResources());
  }

  @Test
  public void testSetInHR() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertTrue(man.isInHumanResources());
    man.setInHumanResources(false);
    assertFalse(man.isInHumanResources());
  }

  @Test
  public void testGetManager() {
    Manager emp = new Manager("Emp", "Test", 0, 0, 0, true);
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    emp.setManager(man);
    assertEquals("Name", emp.getManager().getName());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetManagerNoManager() {
    Manager emp = new Manager("Emp", "Test", 0, 0, 0, true);
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    emp.getManager();
  }

  @Test
  public void testSetManager() {
    Manager emp = new Manager("Emp", "Test", 0, 0, 0, true);
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);

    boolean noManager = false;
    try {
      emp.getManager();
    } catch (IllegalStateException ise) {
      noManager = true;
    }
    assertTrue(noManager);

    emp.setManager(man);

    assertEquals("Name", emp.getManager().getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddReportingEmployeeNull() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(new HashMap<Integer, AEmployee>(), man.getReportingEmployees());
    man.addReportingEmployee(null);
  }

  @Test
  public void testAddReportingEmployee() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(new HashMap<Integer, AEmployee>(), man.getReportingEmployees());
    man.addReportingEmployee(new StandardEmployee("Emp", "Test", 0, 0, 0, true));
    assertEquals(1, man.getReportingEmployees().size());
  }

  @Test
  public void testRemoveReportingEmployee() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(new HashMap<Integer, AEmployee>(), man.getReportingEmployees());
    man.addReportingEmployee(new StandardEmployee("Emp", "Test", 0, 0, 0, true));
    assertEquals(1, man.getReportingEmployees().size());
    assertNotNull(man.removeReportingEmployee(2));
  }

  @Test
  public void testRemoveReportingEmployeeNoSuchEmployee() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(new HashMap<Integer, AEmployee>(), man.getReportingEmployees());
    man.addReportingEmployee(new StandardEmployee("Emp", "Test", 0, 0, 0, true));
    assertEquals(1, man.getReportingEmployees().size());
    assertNull(man.removeReportingEmployee(22));
  }

  @Test
  public void testGetReportingEmployees() {
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    assertEquals(new HashMap<Integer, AEmployee>(), man.getReportingEmployees());
  }
}