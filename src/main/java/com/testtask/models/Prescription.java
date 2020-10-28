package com.testtask.models;

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
}
