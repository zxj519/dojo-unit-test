package com.dojo.repository;

import com.dojo.domain.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Test
  public void should_find_employee_with_same_id_after_save_employee_to_db() {
    Employee employee = new Employee("Steve Jobs");
    employeeRepository.save(employee);
    Long id = employee.getId();
    Employee actualEmployee = employeeRepository.findById(id).get();
    assertEquals("Steve Jobs", actualEmployee.getName());
  }
}