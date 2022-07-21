alter table CITIZEN_DETAILS
add constraint fk_quota FOREIGN KEY (quota) REFERENCES QUOTA_DETAILS(quota_id)
on delete cascade
;


alter table PHONE_RECORDS
add constraint fk_citizen FOREIGN KEY (citi_id) REFERENCES CITIZEN_DETAILS(citizen_id)
on delete cascade
;