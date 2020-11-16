package model.users;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the class {@link StandardEmployee} to ensure that all of its public methods work
 * properly.
 *
 * @author Michael Ruberto
 */
public class StandardEmployeeTest {

  @Before
  public void resetIDs() {
    AEmployee.setNextID(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullName() {
    StandardEmployee std = new StandardEmployee(null, "Test", 0, 0, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullPW() {
    StandardEmployee std = new StandardEmployee("Name", null, 0, 0, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorEmptyPW() {
    StandardEmployee std = new StandardEmployee("Name", "", 0, 0, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegSalary() {
    StandardEmployee std = new StandardEmployee("Name", "Password", -1, 0, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegVacation() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, -1, 0, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegBonus() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, -1, true);
  }

  @Test
  public void testConstructor() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertNotNull(std);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCopyConstructorNull() {
    StandardEmployee emp = new StandardEmployee(null);
  }

  @Test
  public void testCopyConstructor() {
    AEmployee man = new Manager("Man", "Test", 0, 0, 0, true);
    StandardEmployee emp = new StandardEmployee(man);
    assertEquals(emp.getName(), man.getName());
    assertEquals("AEmployee, StandardEmployee", emp.getUserType());
  }

  @Test
  public void testGetID() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(1, std.getId());
  }

  @Test
  public void testGetName() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals("Name", std.getName());
  }

  @Test
  public void testCorrectPassword() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertFalse(std.correctPassword("password"));
    assertTrue(std.correctPassword("Password"));
  }

  @Test
  public void testGetUserType() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals("AEmployee, StandardEmployee", std.getUserType());
  }

  @Test
  public void testGetSalary() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getSalary(), 0.01);
  }

  @Test
  public void testSetSalary() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getSalary(), 0.01);
    std.setSalary(100);
    assertEquals(100, std.getSalary(), 0.01);
    assertEquals(0, std.getSalaryHistory().get(0), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetSalaryNeg() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getSalary(), 0.01);
    std.setSalary(-100);
  }

  @Test
  public void testGetSalaryHistory() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(new ArrayList<Double>(), std.getSalaryHistory());
  }

  @Test
  public void testGetVacationBalance() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getVacationBalance());
  }

  @Test
  public void testSetVacationBalance() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getVacationBalance());
    std.setVacationBalance(100);
    assertEquals(100, std.getVacationBalance());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetVacationBalanceNeg() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getVacationBalance());
    std.setVacationBalance(-100);
  }

  @Test
  public void testGetAnnualBonus() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getAnnualBonus(), 0.01);
  }

  @Test
  public void testSetAnnualBonus() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getAnnualBonus(), 0.01);
    std.setAnnualBonus(100);
    assertEquals(100, std.getAnnualBonus(), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetAnnualBonusNeg() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertEquals(0, std.getAnnualBonus(), 0.01);
    std.setAnnualBonus(-100);
  }

  @Test
  public void testIsInHR() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    StandardEmployee std2 = new StandardEmployee("Name", "Password", 0, 0, 0, false);
    assertTrue(std.isInHumanResources());
    assertFalse(std2.isInHumanResources());
  }

  @Test
  public void testSetInHR() {
    StandardEmployee std = new StandardEmployee("Name", "Password", 0, 0, 0, true);
    assertTrue(std.isInHumanResources());
    std.setInHumanResources(false);
    assertFalse(std.isInHumanResources());
  }

  @Test
  public void testGetManager() {
    StandardEmployee emp = new StandardEmployee("Emp", "Test", 0, 0, 0, true);
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    emp.setManager(man);
    assertEquals("Name", emp.getManager().getName());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetManagerNoManager() {
    StandardEmployee emp = new StandardEmployee("Emp", "Test", 0, 0, 0, true);
    Manager man = new Manager("Name", "Password", 0, 0, 0, true);
    emp.getManager();
  }

  @Test
  public void testSetManager() {
    StandardEmployee emp = new StandardEmployee("Emp", "Test", 0, 0, 0, true);
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
}