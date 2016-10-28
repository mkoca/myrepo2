package com.mkoca.hibernate.service;

import java.util.List; 
import java.util.Date;
import java.util.Iterator; 
 
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mkoca.hibernate.pojo.Employee;

public class EmployeeService {
	private static SessionFactory factory; 
	   public static void main(String[] args) {
	      try{
	         factory = new Configuration().configure().buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	      EmployeeService empService = new EmployeeService();

	      /* Add few employee records in database */
	      Integer empID1 = empService.addEmployee("Zara", "Ali", 1000);
	      Integer empID2 = empService.addEmployee("Daisy", "Das", 5000);
	      Integer empID3 = empService.addEmployee("John", "Paul", 10000);

	      /* List down all the employees */
	      System.out.println("List_1");
	      empService.listEmployees();

	      /* Update employee's records */
	      empService.updateEmployee(empID1, 5000);

	      /* Delete an employee from the database */
	      empService.deleteEmployee(empID2);

	      /* List down new list of the employees */
	      System.out.println("List_2");
	      empService.listEmployees();
	   }
	   /* empServicethod to CREATE an employee in the database */
	   public Integer addEmployee(String fnaempService, String lnaempService, int salary){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Integer employeeID = null;
	      try{
	         //tx = session.beginTransaction();
	    	  
	         Employee employee = new Employee(fnaempService, lnaempService, salary);
	         employeeID = (Integer) session.save(employee); 
	         //tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return employeeID;
	   }
	   /* empServicethod to  READ all the employees */
	   public void listEmployees( ){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         //tx = session.beginTransaction();
	         List employees = session.createQuery("FROM Employee").list(); 
	         for (Iterator iterator = 
	                           employees.iterator(); iterator.hasNext();){
	            Employee employee = (Employee) iterator.next(); 
	            System.out.print("Id NaempService: " + employee.getId()); 
	            System.out.print("  First NaempService: " + employee.getFirstName()); 
	            System.out.print("  Last NaempService: " + employee.getLastName()); 
	            System.out.println("  Salary: " + employee.getSalary()); 
	         }
	         //tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	   /* empServicethod to UPDATE salary for an employee */
	   public void updateEmployee(Integer EmployeeID, int salary ){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Employee employee = 
	                    (Employee)session.get(Employee.class, EmployeeID); 
	         employee.setSalary( salary );
			 session.update(employee); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
	   /* empServicethod to DELETE an employee from the records */
	   public void deleteEmployee(Integer EmployeeID){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Employee employee = 
	                   (Employee)session.get(Employee.class, EmployeeID); 
	         session.delete(employee); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }
}
