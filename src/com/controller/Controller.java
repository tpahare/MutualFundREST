/**
 * @author tpahare
 */
package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.databean.CustomerBean;
import com.databean.EmployeeBean;
import com.model.Model;


public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        
        Action.add(new IndexAction(model));
        Action.add(new EmployeeLoginAction(model));
        Action.add(new CustomerLoginAction(model));
        Action.add(new ChangeEmployeePasswordAction(model));
        Action.add(new ChangeCustomerPasswordAction(model));
        Action.add(new CreateEmployeeAction(model));
        Action.add(new CreateCustomerAction(model));
        Action.add(new ViewCustomerAccountAction(model));
        Action.add(new ViewCustomerAccountSearchAction(model));
        Action.add(new EmployeeLogoutAction());
        Action.add(new CreateFundAction(model));
        Action.add(new ChangePasswordAction(model));
        Action.add(new DepositeCheckAction(model));
        Action.add(new BuyFundAction(model));
        Action.add(new BuyFundFromResearchFundAction(model));
        Action.add(new ViewTransactionHistory(model));
        Action.add(new CustomerLogoutAction());
        Action.add(new ViewAccount(model));
        Action.add(new ViewSelfTransactionHistory(model));
        Action.add(new RequestCheckAction(model));
        Action.add(new DateAction(model));
        Action.add(new TransitionDayAction(model));
        Action.add(new ResearchFundAction(model));
        Action.add(new SellFundAction(model));
        Action.add(new FundInfoAction(model));
        Action.add(new FundInfoEmployeeAction(model));
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("checking 112122");
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = performTheAction(request);
        returnJSON(nextPage, request, response);
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        System.out.println("Check: "+request.getParameter("username"));
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");
        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
        String action = getActionName(servletPath);
        System.out.println("action name" + action);

//        if (customer == null && employee == null) {
//            // If the user hasn't logged in, so login is the only option
//            return Action.perform("Index.do", request);
//        }

        // Let the logged in user run his chosen action
        return Action.perform(action, request);
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
   /* private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }
*/
   private void returnJSON(String json, HttpServletRequest request, HttpServletResponse response) throws IOException{
	   if(json==null){
		   response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getServletPath());
	   }
	   response.setContentType("application/json");
	   PrintWriter out = response.getWriter();
	   out.append(json);
   }
    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}
