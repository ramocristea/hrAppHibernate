package org.example.hrapphibernate;

import java.util.List;

import org.example.hrapphibernate.model.Country;
import org.example.hrapphibernate.model.Department;
import org.example.hrapphibernate.model.Employee;
import org.example.hrapphibernate.model.Job;
import org.example.hrapphibernate.model.Location;
import org.example.hrapphibernate.model.Region;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 * Hello world!
 *
 */
public class App {
	private static SessionFactory sessionFactory;

	public static void main(String[] args) {
		sessionFactory = new Configuration().configure().buildSessionFactory();

		try {
			getAllJobs();
			// addJob(new Job("MCR", "Macelar", 10, 1000));
			Job job = getJobById("MCR");

			job.setMinSalary(100);
			updateJob(job);

			getJobById("MCR");

			getAllRegions();

			getAllEmployees();

			Employee emp = getEmployeeById(200);

			System.out.println(emp.getFirstName() + " " + emp.getJob().getTitle() + " " + emp.getManager().getSalary());
			
			Employee employee = getEmployeeById(100);
			employee.setManager(employee);
			updateEmployee(employee);
			
			getAllCountries();
			
			getEmployeesByJobTitle("Shipping Clerk");
			
			getAllLocations();
			
			getAllDepartments();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		sessionFactory.close();

	}

	private static void getAllJobs() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		List<Job> jobs = session.createQuery("from Job").list();

		for (Job job : jobs) {
			System.out.println(job);
		}

		transaction.commit();

		session.close();

	}

	private static void addJob(Job job) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			session.save(job);

			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			session.close();
		}
	}

	private static Job getJobById(String jobId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Job job = session.find(Job.class, jobId);

		System.out.println(job);

		transaction.commit();
		session.close();

		return job;
	}

	private static void updateJob(Job job) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			session.update(job);

			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			session.close();
		}
	}

	private static void getAllRegions() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		List<Region> regions = session.createQuery("from Region").list();

		for (Region region : regions) {
			System.out.println(region);
		}

		transaction.commit();

		session.close();

	}

	private static void getAllEmployees() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		List<Employee> employees = session.createQuery("from Employee").list();

		for (Employee emp : employees) {
			System.out.println(emp.getFirstName() + " " + emp.getJob().getTitle() + " " + emp.getManager() != null
					? emp.getManager().getLastName() : "");
		}

		transaction.commit();

		session.close();
	}

	private static Employee getEmployeeById(int id) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Employee emp = session.find(Employee.class, id);
		
		System.out.println(emp.getFirstName() + " " + emp.getJob().getTitle() + " " + emp.getManager().getSalary());

		transaction.commit();

		session.close();

		return emp;
	}

	private static void updateEmployee(Employee employee) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			session.update(employee);

			transaction.commit();
			session.close();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}

			session.close();
		}
	}
	
	private static void getAllCountries() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<Country> countries = session.createQuery("from Country").list();
		
		for(Country country : countries) {
			System.out.println(country);
		}
		
		transaction.commit();
		session.close();
	}
	
	private static void getEmployeesByJobTitle(String jobTitle) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Query query = session.createQuery("from Employee as e where e.job.title = :jobTitle");
		query.setParameter("jobTitle", jobTitle);
		
		List<Employee> employees = query.list();
		
		for(Employee emp : employees) {
			System.out.println(emp.getLastName() + " " + emp.getJob().getTitle());
		}
		
		transaction.commit();
		session.close();
	}
	
	private static void getAllLocations() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<Location> locations = session.createQuery("from Location").list();
		
		for(Location location : locations) {
			System.out.println(location);
		}
		
		transaction.commit();
		session.close();
	}
	
	private static void getAllDepartments() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		List<Department> departments = session.createQuery("from Department").list();
		
		for(Department department : departments) {
			System.out.println(department.getName());
		}
		
		transaction.commit();
		session.close();
	}
	
}
