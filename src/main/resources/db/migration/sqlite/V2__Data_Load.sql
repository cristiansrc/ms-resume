INSERT INTO home(
    created, updated, deleted, greeting, greeting_eng, image_id, button_work_label, button_work_label_eng, button_contact_label, button_contact_label_eng)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, '', '', null, '', '', '', '');

INSERT INTO basic_data(
    created, updated, deleted, first_name, others_name, first_surname, others_sur_name, date_birth, located, located_eng, start_working_date, greeting, greeting_eng, email, instagram, linkedin, x, github, description, description_eng)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE, '', '', '', '', CURRENT_DATE, '', '', CURRENT_DATE, '', '', '', '', '', '', '', '', '');