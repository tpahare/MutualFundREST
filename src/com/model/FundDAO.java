package com.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.databean.FundBean;

public class FundDAO extends GenericDAO<FundBean> {
	public FundDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FundBean.class, tableName, cp);
	}

	public FundBean[] getFundList() throws RollbackException {
		try {
			Transaction.begin();
			FundBean[] fundList = match();
			Transaction.commit();
			return fundList;
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}

	}

	public FundBean[] getFundListBySearch(String fundname) throws RollbackException {
		try {
			Transaction.begin();
			FundBean[] fundList = match(MatchArg.containsIgnoreCase("fundName", fundname));
			Transaction.commit();
			return fundList;
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

}