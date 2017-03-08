package com.infotech.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.infotech.dao.EmployeeDAO;
import com.infotech.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public void setNamedParameterJdbcTemplate(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public Employee getEmployeeById(int employeeId) {
		
		String SQL="SELECT *FROM employee_table WHERE employee_id=:empId";
		
		MapSqlParameterSource inputMap = new MapSqlParameterSource();
		inputMap.addValue("empId", employeeId);
		
		 Employee employee = namedParameterJdbcTemplate.queryForObject(SQL, inputMap, new EmployeeRowMapper());
		return employee;
	}

	@Override
	public void deleteEmployeeById(int employeeId) {
		String SQL="DELETE FROM employee_table WHERE employee_Id=:empId";
		
		MapSqlParameterSource inputMap = new MapSqlParameterSource();
		inputMap.addValue("empId", employeeId);
		
		int update = namedParameterJdbcTemplate.update(SQL, inputMap);
		if(update>0)
			System.out.println("Employee is deleted..");
	}

	@Override
	public void updateEmployeeEmailById(String newEmail, int employeeId) {
		
		String SQL="UPDATE employee_table set email=:newEmail WHERE employee_Id=:empId";
		
		MapSqlParameterSource inputMap = new MapSqlParameterSource();
		inputMap.addValue("empId", employeeId);
		inputMap.addValue("newEmail", newEmail);
		
		int update = namedParameterJdbcTemplate.update(SQL, inputMap);
		if(update>0)
			System.out.println("Email is updated..");

	}

	@Override
	public List<Employee> getAllEmployeesDetails() {
		String SQL="SELECT *FROM employee_table";
		
		List<Employee> empLst = namedParameterJdbcTemplate.query(SQL, new EmployeeRowMapper());
		return empLst;
	}

	@Override
	public void createEmployee(Employee employee) {
		String SQL ="INSERT INTO employee_table(employee_name,email,gender,salary) VALUES(:empName,:email,:gender,:sal)";
		
		MapSqlParameterSource inputMap = new MapSqlParameterSource();
		
		inputMap.addValue("empName", employee.getEmployeeName());
		inputMap.addValue("email", employee.getEmail());
		inputMap.addValue("gender", employee.getGender());
		inputMap.addValue("sal", employee.getSalary());
		
		int update = namedParameterJdbcTemplate.update(SQL, inputMap);
	
		if(update>0)
			System.out.println("Employee is created...");
	}
}
