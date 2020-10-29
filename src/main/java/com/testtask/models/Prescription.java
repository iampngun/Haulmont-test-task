package com.testtask.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;


import lombok.Data;

@DynamicUpdate
@Entity
@Data
@Table(name = "prescriptions")
public class Prescription extends Model {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private Long patient;
	private Long doctor;
	private String creationDate;
	private String validityDate;
	private String priority;
	
	public Prescription(){}
	
	public Prescription(String description, Long patient, Long doctor, String priority) {
		this.description = description;
		this.patient = patient;
		this.doctor = doctor;
		SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd");
		this.creationDate = df.format(new Date());
		this.validityDate = df.format(new Date().getTime() + 604800000);
		this.priority = priority;
	}
}
