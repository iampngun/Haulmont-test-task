drop table prescriptions if exists;
drop table doctors if exists;
drop table patients if exists;

create table patients (
	id integer not null generated by default as identity(START WITH 0, INCREMENT BY 1),
	name varchar(100),
	surname varchar(100),
	patronymic varchar(100),
	phone varchar(13),
	primary key(id)
);

create table doctors (
	id integer not null generated by default as identity(START WITH 0, INCREMENT BY 1),
	name varchar(100),
	surname varchar(100),
	patronymic varchar(100),
	specialization varchar(100),
	primary key(id)
);

create table prescriptions (
	id integer not null generated by default as identity(START WITH 0, INCREMENT BY 1),
	description varchar(256),
	patient integer not null,
	doctor integer not null,
	creationDate varchar(100),
	validityDate varchar(100),
	priority varchar(100),
	primary key(id)
);

alter table prescriptions add foreign key (patient) references patients(id);
alter table prescriptions add foreign key (doctor) references doctors(id);