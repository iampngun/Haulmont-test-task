package com.testtask.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.testtask.models.Doctor;
import com.testtask.models.Model;
import com.testtask.models.Patient;
import com.testtask.models.Prescription;
import com.testtask.utils.HibernateUtil;

public class ModelDAO {
	public List<Patient> getPatients() {
		List<Patient> patients = (List<Patient>)  HibernateUtil.getSessionFactory().openSession().createQuery("From Patient").list();
        return patients;
	};
	
	public List<Doctor> getDoctors() {
		List<Doctor> doctors = (List<Doctor>)  HibernateUtil.getSessionFactory().openSession().createQuery("From Doctor").list();
        return doctors;
	};
	
	public List<Prescription> getPrescriptions() {
		List<Prescription> prescriptions = (List<Prescription>)  HibernateUtil.getSessionFactory().openSession().createQuery("From Prescription").list();
        return prescriptions;
	};
	
	public void saveModel(Model model) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(model);
        tx1.commit();
        session.close();
	};
	
	public void updateModel(Model model) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(model);
        tx1.commit();
        session.close();
	};
	
	public void deleteModel(Model model) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(model);
        tx1.commit();
        session.close();
	};
}
