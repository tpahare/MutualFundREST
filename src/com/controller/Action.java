package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xuesong Zhang (Andrew ID: xuesongz)
 */
public abstract class Action {
    // Returns the name of the action, used to match the request in the hash table
    public abstract String getName();

    // Returns the name of the jsp used to render the output.
    public abstract String perform(HttpServletRequest request);

    //
    // Class methods to manage dispatching to Actions
    //
    private static Map<String,Action> hash = new HashMap<String,Action>();

    public static void add(Action a) {
    	synchronized (hash) {
    		if (hash.get(a.getName()) != null) {
    			throw new AssertionError("Two actions with the same name (" + a.getName() + "): " + a.getClass().getName() + " and " + hash.get(a.getName()).getClass().getName());
    		}
    		
    		hash.put(a.getName(),a);
    	}
    }

    public static String perform(String name,HttpServletRequest request) {
        Action a;
        System.out.println("Inside perform");
        synchronized (hash) {
        	a = hash.get(name);
        }
        System.out.println(a);
        if (a == null) {
        	System.out.println("Null action");
        	return null;
        }
        return a.perform(request);
    }
}
