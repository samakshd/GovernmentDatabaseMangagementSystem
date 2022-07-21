alter table CITIZEN_DETAILS
    drop FOREIGN KEY fk_quota;

alter table PHONE_RECORDS
    drop FOREIGN KEY fk_citizen;
    

drop table CITIZEN_DETAILS;
drop table QUOTA_DETAILS;
drop table PHONE_RECORDS;

drop database govtdb;