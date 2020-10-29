package com.testtask.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.testtask.models.Doctor;
import com.testtask.models.Model;
import com.testtask.models.Patient;
import com.testtask.models.Prescription;
import com.testtask.services.Service;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
	private Service service = new Service();
	
	private HorizontalLayout buttons = new HorizontalLayout();
	
	private Button getPatients = new Button("Пациенты");
	private Button getDoctors = new Button("Врачи");
	private Button getPrescriptions = new Button("Рецепты");
	
	private Grid<Patient> patientGrid = new Grid<>(Patient.class);
	private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
	private Grid<Prescription> prescriptionGrid = new Grid<>(Prescription.class);
	
	private Button addModel = new Button("Добавить");
	private Button changeModel = new Button("Изменить");
	private Button deleteModel = new Button("Удалить");
	
	private Text filterText = new Text("Фильтр");
	private TextField descriptionFilter = new TextField();
	private TextField priorityFilter = new TextField();
	private TextField patientFilter = new TextField();
	private Button confirmFilter = new Button("Применить");
	
	private Dialog dialog = new Dialog();
	
	public MainView() {
		addModel.setEnabled(false);
		changeModel.setEnabled(false);
		deleteModel.setEnabled(false);
		
		descriptionFilter.setHelperText("Описание");
		priorityFilter.setHelperText("Приоритет");
		patientFilter.setHelperText("Пациент ID");
		
		buttons.add(getPatients, getDoctors, getPrescriptions, addModel, changeModel, deleteModel);
		
		confirmFilter.addClickListener(e -> filterGrid());
		
		patientGrid.addItemClickListener(e -> {
			try {
				Model nullTest = patientGrid.getSelectedItems().iterator().next();
				changeModel.setEnabled(true);
				deleteModel.setEnabled(true);
			} catch(Exception ex) {
				changeModel.setEnabled(false);
				deleteModel.setEnabled(false);
			}
		});
		doctorGrid.addItemClickListener(e -> {
			try {
				Model nullTest = doctorGrid.getSelectedItems().iterator().next();
				changeModel.setEnabled(true);
				deleteModel.setEnabled(true);
			} catch(Exception ex) {
				changeModel.setEnabled(false);
				deleteModel.setEnabled(false);
			}
		});
		prescriptionGrid.addItemClickListener(e -> {
			try {
				Model nullTest = prescriptionGrid.getSelectedItems().iterator().next();
				changeModel.setEnabled(true);
				deleteModel.setEnabled(true);
			} catch(Exception ex) {
				changeModel.setEnabled(false);
				deleteModel.setEnabled(false);
			}
		});
		
		
		getPatients.addClickListener(e -> {
			service.setName("patient");
			setGrid();
		});
		getDoctors.addClickListener(e -> {
			service.setName("doctor");
			setGrid();
		});
		getPrescriptions.addClickListener(e -> {
			service.setName("prescription");
			setGrid();
		});
		
		add(buttons);
		
		patientGrid.removeAllColumns();
		doctorGrid.removeAllColumns();
		prescriptionGrid.removeAllColumns();
		
		patientGrid.addColumn(Patient::getId).setHeader("ID");
		patientGrid.addColumn(Patient::getName).setHeader("Имя");
		patientGrid.addColumn(Patient::getSurname).setHeader("Фамилия");
		patientGrid.addColumn(Patient::getPatronymic).setHeader("Отчество");
		patientGrid.addColumn(Patient::getPhone).setHeader("Телефон");
		
		doctorGrid.addColumn(Doctor::getId).setHeader("ID");
		doctorGrid.addColumn(Doctor::getName).setHeader("Имя");
		doctorGrid.addColumn(Doctor::getSurname).setHeader("Фамилия");
		doctorGrid.addColumn(Doctor::getPatronymic).setHeader("Отчество");
		doctorGrid.addColumn(Doctor::getSpecialization).setHeader("Специализация");
		
		prescriptionGrid.addColumn(Prescription::getId).setHeader("ID");
		prescriptionGrid.addColumn(Prescription::getDescription).setHeader("Описание");
		prescriptionGrid.addColumn(Prescription::getPatient).setHeader("Пациент ID");
		prescriptionGrid.addColumn(Prescription::getDoctor).setHeader("Доктор ID");
		prescriptionGrid.addColumn(Prescription::getCreationDate).setHeader("Дата создания");
		prescriptionGrid.addColumn(Prescription::getValidityDate).setHeader("Срок действия");
		prescriptionGrid.addColumn(Prescription::getPriority).setHeader("Приоритет");
	}
	
	private void setGrid() {
		remove(patientGrid);
		remove(doctorGrid);
		remove(prescriptionGrid);
		buttons.remove(filterText, descriptionFilter, priorityFilter, patientFilter, confirmFilter);
		descriptionFilter.setValue("");
		priorityFilter.setValue("");
		patientFilter.setValue("");
		
		switch(service.getName()) {
			case("patient"):
				add(patientGrid);
				patientGrid.setItems(service.getPatients());
				break;
			case("doctor"):
				add(doctorGrid);
				doctorGrid.setItems(service.getDoctors());
				break;
			case("prescription"):
				add(prescriptionGrid);
				prescriptionGrid.setItems(service.getPrescriptions());
				
				buttons.add(filterText, descriptionFilter, priorityFilter, patientFilter, confirmFilter);
				break;
		}
		
		addModel.setEnabled(true);
		changeModel.setEnabled(false);
		deleteModel.setEnabled(false);
		
		addModel.addClickListener(e -> showDialog("Добавить"));
		changeModel.addClickListener(e -> showDialog("Изменить"));
		deleteModel.addClickListener(e -> deleteModel());
	}
	
	private void filterGrid() {
		List<Prescription> filteredList = service.getPrescriptions();
		
		for(int i = 0; i < filteredList.size(); i++) {
			if((!filteredList.get(i).getDescription().contains(descriptionFilter.getValue()) && !descriptionFilter.isEmpty()) ||
					(!filteredList.get(i).getPriority().equals(priorityFilter.getValue()) && !priorityFilter.isEmpty()) ||
					(!filteredList.get(i).getPatient().toString().equals(patientFilter.getValue()) && !patientFilter.isEmpty())) {
				filteredList.remove(i);
			}
		}
		
		prescriptionGrid.setItems(filteredList);
		changeModel.setEnabled(false);
		deleteModel.setEnabled(false);
	}
	
	private void showDialog(String dialogType) {
		dialog.removeAll();
		
		Button okButton = new Button("ОК");
		Button cancelButton = new Button("Отменить");
		
		List<TextField> textFields = createTextFields();
		
		VerticalLayout verticalLayout = new VerticalLayout();
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		
		verticalLayout.add(new Text(dialogType));
		for(int i = 0; i < textFields.size(); i++) {
			verticalLayout.add(textFields.get(i));
		}
		horizontalLayout.add(okButton, cancelButton);
		verticalLayout.add(horizontalLayout);
		
		cancelButton.addClickListener(e -> dialog.close());
		okButton.addClickListener(e -> okCheckAndDo(textFields, dialogType));
		
		dialog.add(verticalLayout);
		dialog.setCloseOnOutsideClick(false);
		dialog.setModal(true);
		dialog.open();
	}
	
	private List<TextField> createTextFields() {
		List<TextField> textFields = new ArrayList<>();
		switch(service.getName()) {
			case("patient"):
				textFields.clear();
				TextField name = new TextField();
				TextField surname = new TextField();
				TextField patronymic = new TextField();
				TextField phone = new TextField();
				name.setHelperText("Имя");
				surname.setHelperText("Фамилия");
				patronymic.setHelperText("Отчество");
				phone.setHelperText("Телефон");
				textFields.add(name);
				textFields.add(surname);
				textFields.add(patronymic);
				textFields.add(phone);
				break;
			case("doctor"):
				textFields.clear();
				name = new TextField();
				surname = new TextField();
				patronymic = new TextField();
				TextField specialization = new TextField();
				name.setHelperText("Имя");
				surname.setHelperText("Фамилия");
				patronymic.setHelperText("Отчество");
				specialization.setHelperText("Специализация");
				textFields.add(name);
				textFields.add(surname);
				textFields.add(patronymic);
				textFields.add(specialization);
				break;
			case("prescription"):
				textFields.clear();
				TextField description = new TextField();
				TextField patient = new TextField();
				TextField doctor = new TextField();
				TextField priority = new TextField();
				description.setHelperText("Описание");
				patient.setHelperText("Пациент ID");
				doctor.setHelperText("Врач ID");
				priority.setHelperText("Приоритет");
				textFields.add(description);
				textFields.add(patient);
				textFields.add(doctor);
				textFields.add(priority);
				break;
		}
		
		return textFields;
	}
	
	private void okCheckAndDo(List<TextField> textFields, String dialogType) { 
		switch(service.getName()) {
			case("patient"):
				String pattern = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
				if(textFields.get(0).getValue().isEmpty() ||
						textFields.get(1).getValue().isEmpty() ||
						textFields.get(2).getValue().isEmpty() ||
						!textFields.get(3).getValue().matches(pattern)) {
					Notification.show("Ошибка!");
				} else {
					if(dialogType.equals("Добавить")) {
						service.addModel(textFields.get(0), textFields.get(1), textFields.get(2), textFields.get(3));
					} else {
						patientGrid.getSelectedItems().iterator().next().setName(textFields.get(0).getValue());
						patientGrid.getSelectedItems().iterator().next().setSurname(textFields.get(1).getValue());
						patientGrid.getSelectedItems().iterator().next().setPatronymic(textFields.get(2).getValue());
						patientGrid.getSelectedItems().iterator().next().setPhone(textFields.get(3).getValue());
						service.changeModel(patientGrid.getSelectedItems().iterator().next());
					}
					dialog.close();
					patientGrid.setItems(service.getPatients());
					changeModel.setEnabled(false);
					deleteModel.setEnabled(false);
				}
				break;
			case("doctor"):
				if(textFields.get(0).getValue().isEmpty() ||
						textFields.get(1).getValue().isEmpty() ||
						textFields.get(2).getValue().isEmpty() ||
						textFields.get(3).getValue().isEmpty()) {
					Notification.show("Ошибка!");
				} else {
					if(dialogType.equals("Добавить")) {
						service.addModel(textFields.get(0), textFields.get(1), textFields.get(2), textFields.get(3));
					} else {
						doctorGrid.getSelectedItems().iterator().next().setName(textFields.get(0).getValue());
						doctorGrid.getSelectedItems().iterator().next().setSurname(textFields.get(1).getValue());
						doctorGrid.getSelectedItems().iterator().next().setPatronymic(textFields.get(2).getValue());
						doctorGrid.getSelectedItems().iterator().next().setSpecialization(textFields.get(3).getValue());
						service.changeModel(doctorGrid.getSelectedItems().iterator().next());
					}
					dialog.close();
					doctorGrid.setItems(service.getDoctors());
					changeModel.setEnabled(false);
					deleteModel.setEnabled(false);
				}
				break;
			case("prescription"):
				if(textFields.get(0).getValue().isEmpty() ||
						!isInteger(textFields.get(1).getValue()) ||
						!isInteger(textFields.get(2).getValue()) ||
						(!textFields.get(3).getValue().equals("Нормальный") &&
								!textFields.get(3).getValue().equals("Срочный") &&
								!textFields.get(3).getValue().equals("Немедленный"))) {
					Notification.show("Ошибка!");
				} else {
					List<Patient> patientList = service.getPatients();
					List<Doctor> doctorList = service.getDoctors();
					boolean patientExists = false;
					for(int i = 0; i < patientList.size(); i++) {
						if(patientList.get(i).getId() == Long.parseLong(textFields.get(1).getValue())) {
							patientExists = true;
							break;
						}
					}
					boolean doctorExists = false;
					if(patientExists) {
						for(int i = 0; i < doctorList.size(); i++) {
							if(doctorList.get(i).getId() == Long.parseLong(textFields.get(2).getValue())) {
								doctorExists = true;
								break;
							}
						}
						if(doctorExists) {
							if(dialogType.equals("Добавить")) {
								service.addModel(textFields.get(0), textFields.get(1), textFields.get(2), textFields.get(3));
							} else {
								prescriptionGrid.getSelectedItems().iterator().next().setDescription(textFields.get(0).getValue());
								prescriptionGrid.getSelectedItems().iterator().next().setPatient(Long.parseLong(textFields.get(1).getValue()));
								prescriptionGrid.getSelectedItems().iterator().next().setDoctor(Long.parseLong(textFields.get(2).getValue()));
								prescriptionGrid.getSelectedItems().iterator().next().setPriority(textFields.get(3).getValue());
								service.changeModel(prescriptionGrid.getSelectedItems().iterator().next());
							}
							dialog.close();
							prescriptionGrid.setItems(service.getPrescriptions());
							changeModel.setEnabled(false);
							deleteModel.setEnabled(false);
						}
					}
				}
				break;
		}
	}
	
	private boolean isInteger(String string) {
		try {
            Long.parseLong(string);
        } catch (Exception e) {
            return false;
        }
        return true;
	}
	
	private void deleteModel() {
		switch(service.getName()) {
			case("patient"):
				try {
					service.deleteModel(patientGrid.getSelectedItems().iterator().next());
					patientGrid.setItems(service.getPatients());
				} catch (Exception e) {
					Notification.show("Ошибка!");
				}
				break;
			case("doctor"):
				try {
					service.deleteModel(doctorGrid.getSelectedItems().iterator().next());
					doctorGrid.setItems(service.getDoctors());
				} catch(Exception e) {
					Notification.show("Ошибка!");
				}
				break;
			case("prescription"):
				try {
					service.deleteModel(prescriptionGrid.getSelectedItems().iterator().next());
				} catch(Exception e) {
					Notification.show("Ошибка!");
				}
				break;
		}
		changeModel.setEnabled(false);
		deleteModel.setEnabled(false);
	}
}
