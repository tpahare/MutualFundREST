package com.form;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
//import org.mybeans.form.FormBeanException;
//import org.mybeans.form.FormBeanFactory;
import org.mybeans.form.*;
/**
 * @author Xuesong Zhang (Andrew ID: xuesongz)
 */
public class LoginForm extends FormBean {
    private String username;
    private String password;
  //  private String action;
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

   /* public String getAction() {
        return action;
    }*/
    public void setUsername(String s)  { username = s.trim(); }
    public void setPassword(String s)  { password = s.trim(); }
//    public void setAction(String s)    { action   = s;        }
    
 /*   public boolean isPresent() {
		return action != null;
	}*/
    public List<String> getValidationErrors() {
    	System.out.println("Form Username: " + username);
        List<String> errors = new ArrayList<String>();
        if (username == null || username.trim().length() == 0 || password == null || password.length() == 0)
            errors.add("The username/password combination that you entered is not correct");
//        if (password == null || password.length() == 0)
//            errors.add("The username/password combination that you entered is not correct");
        
      /*  if (!action.equals("Login"))
            errors.add("Invalid action");*/

        if (errors.size() > 0)
            return errors;

     /*   if (action == null || !action.equals("Login"))
            errors.add("Invalid button");*/
        if (username.matches(".*[<>\"].*"))
            errors.add("Username may not contain angle brackets or quotes");

        return errors;
    }
}
