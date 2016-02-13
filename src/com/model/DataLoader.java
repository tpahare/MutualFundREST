package com.model;

import org.genericdao.RollbackException;

import com.databean.EmployeeBean;

public class DataLoader {
	public DataLoader(EmployeeDAO employeeDAO)throws RollbackException{
		EmployeeBean employee = new EmployeeBean();
		employee.setFirstname("Jane");
		employee.setLastname("Admin");
		employee.setUsername("jadmin");
		employee.setPassword("admin");
		employee.setAddress1("123 Main Street");
		employee.setAddress2("Suite 305");
		employee.setCity("Pittsburgh");
		employee.setState("PA");
		employee.setZip("15214");
		employeeDAO.create(employee);
	}
}
/*First Name: Jane
Last Name: Admin
Username: jadmin
Password: admin
Address1: 123 Main street
Address2: Suite 305
City: Pittsburgh
State: Pa
Zip: 15143*/
