package com.testtask.services;

import java.util.List;

import com.testtask.dao.ModelDAO;
import com.testtask.models.Doctor;
import com.testtask.models.Patient;
import com.testtask.models.Prescription;
import com.vaadin.flow.component.textfield.TextField;

import lombok.Data;

@Data
public class Service {
	private String name;
	
	private ModelDAO modelDAO = new ModelDAO();
	
	public Service() {
		
	}
	
	public List<Patient> getPatients() {
		System.out.println(modelDAO.getPatients());
		return modelDAO.getPatients();
	};
	
	public List<Doctor> getDoctors() {
		System.out.println(modelDAO.getDoctors());
		return modelDAO.getDoctors();
	};
	
	public List<Prescription> getPrescriptions() {
		System.out.println(modelDAO.getPrescriptions());
		return modelDAO.getPrescriptions();
	};
	
	public void addModel(TextField textField1, TextField textField2, TextField textField3, TextField textField4) {
		switch(name) {
			case("patient"):
				Patient patient = new Patient(textField1.getValue(), textField2.getValue(), 
						textField3.getValue(), textField4.getValue());
				modelDAO.saveModel(patient);
				break;
			case("doctor"):
				
				break;
			case("prescription"):
				
				break;
		}
	};
	
	public void changeModel() {
		
	};
	
	public void deleteModel() {
		
	};
}
