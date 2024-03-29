# MutualFundREST
The Rest API for CFS Mutual Fund Management Application

In this project, a RESTful API is designed to expose the functionalities offered by the Mutual Funds Trading Web Application that was designed for Carnegie Financial Services (CFS). This project is academic in nature. The API provides access to the system for both Clients of CFS as well as CFS employees.

The API provides the following functionalities for the CFS employees:
1. Create a new customer account
2. Create a new fund
3. Perform the transition day action. This action allows transition from one financial day to another and completing all queued up transactions which could be updating fund prices and buying and selling funds.
4. View a customer account

For the CFS clients, the following functionalities are provided:
1. View the portfolio of a fund
2. Buy a fund
3. Sell a fund

The application is based on the MVC framework. It uses GenericDAO library designed by Prof. Jefferey Eppinger at CMU for ORM and Google's Gson library to parse data as JSON objects.
