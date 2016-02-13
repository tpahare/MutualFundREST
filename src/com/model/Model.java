package com.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;


public class Model {
	private EmployeeDAO employeeDAO;
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private TrancDAO trancDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private PositionDAO positionDAO;
	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriverName = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriverName,jdbcURL);
	        
			employeeDAO = new EmployeeDAO(pool, "task8_employee");
            customerDAO = new CustomerDAO(pool,  "task8_customer");
            fundDAO = new FundDAO(pool, "task8_fund");
            trancDAO = new TrancDAO(pool, "task8_transaction");
            fundPriceHistoryDAO = new FundPriceHistoryDAO(pool, "task8_fund_price_history");
            positionDAO = new PositionDAO(pool, "task8_position");
            if(employeeDAO.getCount()==0) new DataLoader(employeeDAO);
		} catch (DAOException e) {
			throw new ServletException(e);
        } catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public FundPriceHistoryDAO getFundPriceHistoryDAO() { return fundPriceHistoryDAO;	}

	public EmployeeDAO getEmployeeDAO()  { return employeeDAO; }
	public CustomerDAO getCustomerDAO()  { return customerDAO; }
	public FundDAO getFundDAO() {return fundDAO;}
	public TrancDAO getTrancDAO() {return trancDAO;}
	public PositionDAO getPosDAO() {return positionDAO;}
}
