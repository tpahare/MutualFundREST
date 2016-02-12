package com.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.databean.FundPriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<FundPriceHistoryBean> {
	public FundPriceHistoryDAO(ConnectionPool cp, String tablename) throws DAOException {
		super(FundPriceHistoryBean.class, tablename, cp);
	}

	public String getMaxDate() throws ParseException, RollbackException {
		try {
			Transaction.begin();
			FundPriceHistoryBean[] fundHistoryBean = match();
			Transaction.commit();
			int i = 1;
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			if (fundHistoryBean.length != 0) {
				Date maxdate = sdf.parse(fundHistoryBean[0].getPricedate());
				while (i < fundHistoryBean.length) {
					Date temp = sdf.parse(fundHistoryBean[i].getPricedate());
					if (maxdate.compareTo(temp) < 0) {
						maxdate = temp;
					}
					i++;
				}
				return sdf.format(maxdate);
			}
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}

		}
		return null;
	}

	public long getRecentPrice(int fundid) throws ParseException, RollbackException {
		try {
			Transaction.begin();
			FundPriceHistoryBean[] fundHistoryBean = match(MatchArg.equals("fundid", fundid));
			Transaction.commit();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			int i = 1;
			long res = -1;
			if (fundHistoryBean.length != 0) {
				Date maxdate = sdf.parse(fundHistoryBean[0].getPricedate());
				res = fundHistoryBean[0].getPrice();
				while (i < fundHistoryBean.length) {
					Date temp = sdf.parse(fundHistoryBean[i].getPricedate());
					if (maxdate.compareTo(temp) < 0) {
						res = fundHistoryBean[i].getPrice();
					}
					i++;
				}
			}
			return res;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		} 
	}
}