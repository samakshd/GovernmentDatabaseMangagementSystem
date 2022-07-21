create database govtdb;
use govtdb;

create table CITIZEN_DETAILS (
    citizen_id Integer,
    citizen_name varchar(30) NOT NULL,
    aadhar_no Integer UNIQUE,
    address_field varchar(1000),
    date_of_birth date,
    quota Integer,

    constraint pk_citizen_details PRIMARY KEY (citizen_id)
);

create table QUOTA_DETAILS (
    quota_id Integer,
    quota_name varchar(30),
    quota_benefits varchar(100),
    
    constraint pk_quota_details PRIMARY KEY (quota_id)
);

create table PHONE_RECORDS (
    phone_record_id Integer,
    citi_id Integer,
    phone_no varchar(10) NOT NULL,

    constraint pk_phone_records PRIMARY KEY (phone_record_id)
);