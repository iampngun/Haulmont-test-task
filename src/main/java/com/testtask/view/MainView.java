package com.testtask.view;

import java.util.ArrayList;
import java.util.List;

import com.testtask.models.Doctor;
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
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
	private Grid<Patient> patientGrid = new Grid<>(Patient.class);
	private Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
	private Grid<Prescription> prescriptionGrid = new Grid<>(Prescription.class);
	
	public MainView() {
		Service service = new Service();
		
		HorizontalLayout buttons = new HorizontalLayout();
		
		Button getPatients = new Button("Пациенты");
		Button getDoctors = new Button("Врачи");
		Button getPrescriptions = new Button("Рецепты");
		
		Button addModel = new Button("Добавить");
		Button changeModel = new Button("Изменить");
		Button deleteModel = new Button("Удалить");
		
		buttons.add(getPatients, getDoctors, getPrescriptions, addModel, changeModel, deleteModel);
		
		Dialog dialog = new Dialog();
		
		getPatients.addClickListener(e -> {
			service.setName("patient");
			setGrid(service, addModel, changeModel, deleteModel, dialog);
		});
		getDoctors.addClickListener(e -> {
			service.setName("doctor");
			setGrid(service, addModel, changeModel, deleteModel, dialog);
		});
		getPrescriptions.addClickListener(e -> {
			service.setName("prescription");
			setGrid(service, addModel, changeModel, deleteModel, dialog);
		});
		
		add(buttons);
	}
	
	private void setGrid(Service service, Button addModel, Button changeModel, Button deleteModel, Dialog dialog) {
		patientGrid.removeAllColumns();
		doctorGrid.removeAllColumns();
		prescriptionGrid.removeAllColumns();
		remove(patientGrid);
		remove(doctorGrid);
		remove(prescriptionGrid);
		
		switch(service.getName()) {
			case("patient"):
				add(patientGrid);
				ListDataProvider provider = new ListDataProvider<>(service.getPatients());
				patientGrid.setDataProvider(provider); //почитать про дата провайдер
				patientGrid.setItems(service.getPatients());
				break;
			case("doctor"):
				add(doctorGrid);
				doctorGrid.setItems(service.getDoctors());
				break;
			case("prescription"):
				add(prescriptionGrid);
				prescriptionGrid.setItems(service.getPrescriptions());
				break;
		}
		
		addModel.addClickListener(e -> showDialog(dialog, "Добавить", service));
		changeModel.addClickListener(e -> showDialog(dialog, "Изменить", service));
		deleteModel.addClickListener(e -> deleteModel());
	}
	
	private void showDialog(Dialog dialog, String dialogType, Service service) {
		dialog.removeAll();
		
		Button okButton = new Button("ОК");
		Button cancelButton = new Button("Отменить");
		
		List<TextField> textFields = createTextFields(service);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		
		verticalLayout.add(new Text(dialogType));
		for(int i = 0; i < textFields.size(); i++) {
			verticalLayout.add(textFields.get(i));
		}
		horizontalLayout.add(okButton, cancelButton);
		verticalLayout.add(horizontalLayout);
		
		cancelButton.addClickListener(e -> dialog.close());
		okButton.addClickListener(e -> okCheckAndDo(service, textFields, dialogType, dialog));
		
		dialog.add(verticalLayout);
		dialog.setCloseOnOutsideClick(false);
		dialog.setModal(true);
		dialog.open();
	}
	
	private List<TextField> createTextFields(Service service) {
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
				patient.setHelperText("Пациент");
				doctor.setHelperText("Врач");
				priority.setHelperText("Приоритет");
				textFields.add(description);
				textFields.add(patient);
				textFields.add(doctor);
				textFields.add(priority);
				break;
		}
		
		return textFields;
	}
	
	private void okCheckAndDo(Service service, List<TextField> textFields, String dialogType, Dialog dialog) {
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
						dialog.close();
					} else {
						
					}
				}
				break;
			case("doctor"):
				
				break;
			case("prescription"):
				
				break;
		}
	}
	
	private void deleteModel() {
		
	}
}
