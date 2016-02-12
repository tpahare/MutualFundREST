package com.model;

import org.genericdao.RollbackException;

import com.databean.EmployeeBean;

public class DataLoader {
	public DataLoader(EmployeeDAO employeeDAO)throws RollbackException{
		EmployeeBean employee = new EmployeeBean();
		employee.setFirstname("John");
		employee.setLastname("Doe");
		employee.setUsername("johndoe");
		employee.setPassword("password");
		
		employeeDAO.create(employee);
	}
}
