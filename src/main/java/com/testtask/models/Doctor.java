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
@Table(name = "doctors")
public class Doctor extends Model {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String surname;
	private String patronymic;
	private String specialization;
	
	public Doctor(){}
	
	
}
