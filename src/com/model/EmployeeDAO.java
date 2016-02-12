package com.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.databean.EmployeeBean;

public class EmployeeDAO extends GenericDAO<EmployeeBean> {
	public EmployeeDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(EmployeeBean.class, tableName, cp);
	}

	public void changePassword(String username, String newPassword) throws RollbackException {
		try {
			Transaction.begin();
			
			EmployeeBean[] employee = match(MatchArg.equals("username", username));
			
			if (employee.length == 0) {
				throw new RollbackException("Not found");
			}
			employee[0].setPassword(newPassword);
			update(employee[0]);
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}