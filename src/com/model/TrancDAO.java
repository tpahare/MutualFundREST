package com.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.databean.*;
public class TrancDAO extends GenericDAO<TransactionBean>{
	public TrancDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(TransactionBean.class, tableName, cp);
	}
}
