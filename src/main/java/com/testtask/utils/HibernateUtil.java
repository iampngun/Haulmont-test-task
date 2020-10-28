package com.testtask.utils;

import org.hibernate.cfg.Configuration;

import com.testtask.models.Doctor;
import com.testtask.models.Patient;
import com.testtask.models.Prescription;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	private HibernateUtil() {}
	
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			try {
				Configuration configuration = new Configuration().configure();
				configuration.addAnnotatedClass(Patient.class);
				configuration.addAnnotatedClass(Doctor.class);
				configuration.addAnnotatedClass(Prescription.class);
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
				sessionFactory = configuration.buildSessionFactory(builder.build());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return sessionFactory;
	}
}