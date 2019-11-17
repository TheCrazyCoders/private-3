
DROP TABLE IF EXISTS m_application_statuses;

CREATE TABLE m_application_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_ht22n12ys00jvj7j8nc5ume7h (code),
  UNIQUE KEY UK_frcytj30igbs680jtskosu8fx (value)
);


DROP TABLE IF EXISTS m_appointment_statuses;

CREATE TABLE m_appointment_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_418439dif4bl7uk9nnfqkab9x (code),
  UNIQUE KEY UK_kjccuaojooj0c5l9kj2vi9tns (value)
);


DROP TABLE IF EXISTS m_appointment_types;
CREATE TABLE m_appointment_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_kf2r0j0gel3dp8jvn6ktr8s7j (code),
  UNIQUE KEY UK_9aldd5ajpp72sju6vuggfug47 (value)
);


DROP TABLE IF EXISTS m_audit_hold_reasons;
CREATE TABLE m_audit_hold_reasons (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_eau8n0593hipktarmobx71xfl (code),
  UNIQUE KEY UK_ln8mjhpfg4fa1jb1q86dddo07 (value)
);


DROP TABLE IF EXISTS m_audit_statuses;
CREATE TABLE m_audit_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_atdbs0yqvyu10el5h7g81uqei (code),
  UNIQUE KEY UK_bmi0svolgad3vev3b4x4yvf4p (value)
);


DROP TABLE IF EXISTS m_audit_types;
CREATE TABLE m_audit_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_5pf9kurn11gd4mkp7ca3rs33c (code),
  UNIQUE KEY UK_g370larregefso0msvv2hkb55 (value)
);


DROP TABLE IF EXISTS m_biometric_scan_types;
CREATE TABLE m_biometric_scan_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_bo608dgijfitlwkvmmbcetn1n (code),
  UNIQUE KEY UK_2xu3x2597n3q5slr3o9ge6x6b (value)
);


DROP TABLE IF EXISTS m_biometric_statuses;
CREATE TABLE m_biometric_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_25w6h71pads60vyiivixajyn6 (code),
  UNIQUE KEY UK_s3wmdvjf6uern8bh7rfjp1t5n (value)
);

DROP TABLE IF EXISTS m_counter_types;
CREATE TABLE m_counter_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL, 
  remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_jtjxg5qwdqoib3k0qwuxni7fw (code),
  UNIQUE KEY UK_1mf9qsv1mp70fhpm754ycd3nn (value)
);

DROP TABLE IF EXISTS m_regions;
CREATE TABLE m_regions (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
   remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_hb12llggn9op4m2nbwdoe9p6g (code),
  UNIQUE KEY UK_5mmnnhjmo8stc8xmidl5d3pmw (value)
);



DROP TABLE IF EXISTS m_countries;
CREATE TABLE m_countries (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  region_id int(11) DEFAULT NULL,
   remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_60chfd3qa8skjp11p39kgk06n (code),
  UNIQUE KEY UK_5j9o3e2ehepochwbuwr7encsp (value)
);


DROP TABLE IF EXISTS m_currency_exchange_rates;
CREATE TABLE m_currency_exchange_rates (
  id int(11) NOT NULL AUTO_INCREMENT,
  created_on datetime NOT NULL,
  exchange_rate varchar(255) NOT NULL,
  updated_on datetime DEFAULT NULL,
  country_id int(11) NOT NULL,
  created_by int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS m_delivery_statuses;
CREATE TABLE m_delivery_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_qms2doi4wqabcj3wba12r9qnw (code),
  UNIQUE KEY UK_4mqcooeu4iv85uu320b3voven (value)
);

DROP TABLE IF EXISTS m_delivery_types;
CREATE TABLE m_delivery_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_8lvjjvsh0by2saqh7mxs26ndo (code),
  UNIQUE KEY UK_e6fb1uhmily08cy61o5q2q5o (value)
);

DROP TABLE IF EXISTS m_document_types;
CREATE TABLE m_document_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
    remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_8yqaxusjh9a1hngoko7cdhqr8 (code),
  UNIQUE KEY UK_5akbje6cajk12xmc6sk960lld (value)
) ;

DROP TABLE IF EXISTS m_fee_types;
CREATE TABLE m_fee_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
     remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_9lclc9rmr2mcm60yct57xbhhw (code),
  UNIQUE KEY UK_9eo23qgwef8e1feuyi4bj80cl (value)
) ;

DROP TABLE IF EXISTS m_genders;
CREATE TABLE m_genders (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_n79fk51pqnwp7ppphgpbywo8g (code),
  UNIQUE KEY UK_h17au4k6vb4obdjmo6adjydj1 (value)
);

DROP TABLE IF EXISTS m_holidays;
CREATE TABLE m_holidays (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  date date NOT NULL,
  value varchar(255) NOT NULL,
  country_id int(11) NOT NULL,
  region_id int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_okxxol4h1w9oghk15n8f5va27 (code),
  UNIQUE KEY UK_l8isk0uq202xtbkbu0adqyfln (value)
);


DROP TABLE IF EXISTS m_marital_statuses;
CREATE TABLE m_marital_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_licepe57u5ox922e4broagr0g (code),
  UNIQUE KEY UK_6uqe06odq7evj23h3mlqy7sec (value)
);

DROP TABLE IF EXISTS m_mode_of_entries;
CREATE TABLE m_mode_of_entries (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
    remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_mh00l84ltv7ho1m41epsimibs (code),
  UNIQUE KEY UK_dc2m0y0g8ige9kex8klp62qhx (value)
);

DROP TABLE IF EXISTS m_nationalities;
CREATE TABLE m_nationalities (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
   remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_6mj2cntkbucunamn6b6cyyr2r (code),
  UNIQUE KEY UK_mynyy6nfbocfykd0ir1i5d5qu (value)
);


DROP TABLE IF EXISTS m_passport_type_configs;
CREATE TABLE m_passport_type_configs (
  id int(11) NOT NULL AUTO_INCREMENT,
  nationality_id int(11) NOT NULL,
  pp_type_id int(11) NOT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS m_passport_types;
CREATE TABLE m_passport_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
    remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_91091aq3ibxjh3qq482as9wlv (code),
  UNIQUE KEY UK_ejw8w0akasxed4irjc71nhe6b (value)
);

DROP TABLE IF EXISTS m_payment_modes;
CREATE TABLE m_payment_modes (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
    remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_rsrm1f3yxmrj8p98fly1gn5a (code),
  UNIQUE KEY UK_j89okd6abhl04f4i4nuhdti4e (value)
) ;


DROP TABLE IF EXISTS m_payment_statuses;
CREATE TABLE m_payment_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_2y65hnxvqh1fe6p2iwuf775h2 (code),
  UNIQUE KEY UK_41sc6t1trcpcoukvb2wbrigm8 (value)
);


DROP TABLE IF EXISTS m_port_of_entries;
CREATE TABLE m_port_of_entries (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
    remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_nxwlfe9tq8t31dclpqrm8pfb0 (code),
  UNIQUE KEY UK_cgvmufur42p3kbkeq5nqjcjg8 (value)
);


DROP TABLE IF EXISTS m_process_bo_stages;
CREATE TABLE m_process_bo_stages (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_nav1htu9uoh4y5y4kkur4ax9s (code),
  UNIQUE KEY UK_maikbaikliyo7ltr5k9akdpvf (value)
);


DROP TABLE IF EXISTS m_process_bo_statuses;
CREATE TABLE m_process_bo_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_n27po738e64ec7s1v19h5wh2u (code),
  UNIQUE KEY UK_goglyds3otvc1bo5a8t31qd5s (value)
);


DROP TABLE IF EXISTS m_process_fo_stages;
CREATE TABLE m_process_fo_stages (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_4fifv2u6qdlrft429hgmc74f6 (code),
  UNIQUE KEY UK_ltolqaxsbmp8nuxep3uk5y68n (value)
);



DROP TABLE IF EXISTS m_process_fo_statuses;
CREATE TABLE m_process_fo_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_4sog22sfshu4peea46gm91p4p (code),
  UNIQUE KEY UK_6fyr5c8dt5apd5q7qhjc7w8o5 (value)
);


DROP TABLE IF EXISTS m_process_stage_status_mapping;
CREATE TABLE m_process_stage_status_mapping (
  id int(11) NOT NULL AUTO_INCREMENT,
  current_stage int(11) NOT NULL,
  current_status int(11) NOT NULL,
  next_stage int(11) NOT NULL,
  next_status int(11) NOT NULL,
  process_type int(11) NOT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS m_process_types;
CREATE TABLE m_process_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  display_status tinyint(4) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_rbkfimv2hopb9tgudny0as050 (code),
  UNIQUE KEY UK_8osjhmh2wp4n4m42g1otcuclf (value)
);


DROP TABLE IF EXISTS m_religions;
CREATE TABLE m_religions (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_242ph791shmnokxb76fti7gmg (code),
  UNIQUE KEY UK_ssx7xauqemwte2y10h8cp5uqa (value)
);



DROP TABLE IF EXISTS m_service_center_types;
CREATE TABLE m_service_center_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_2h1q3tpfo24yc7p2km42194dk (code),
  UNIQUE KEY UK_s8pcwvftqg8qak58mc12337va (value)
);


DROP TABLE IF EXISTS m_service_centers;
CREATE TABLE m_service_centers (
  id int(11) NOT NULL AUTO_INCREMENT,
  address varchar(255) NOT NULL,
  city varchar(255) NOT NULL,
  code varchar(255) NOT NULL,
  parent_id varchar(255) DEFAULT NULL,
  reserve_1 varchar(255) DEFAULT NULL,
  reserve_2 varchar(255) DEFAULT NULL,
  reserve_3 varchar(255) DEFAULT NULL,
  reserve_4 varchar(255) DEFAULT NULL,
  reserve_5 varchar(255) DEFAULT NULL,
  country_id int(11) NOT NULL,
  type_id int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_47q29f2e1f8tfnbyg60pxgpfd (code)
);



DROP TABLE IF EXISTS m_time_slot_configurations;
CREATE TABLE m_time_slot_configurations (
  id int(11) NOT NULL AUTO_INCREMENT,
  created_on datetime NOT NULL,
  duration int(11) NOT NULL,
  end_time time NOT NULL,
  slot_no int(11) NOT NULL,
  start_time time NOT NULL,
  updated_on datetime NOT NULL,
  created_by int(11) NOT NULL,
  service_center_id int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ;


DROP TABLE IF EXISTS m_travel_types;
CREATE TABLE m_travel_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_sisupegf7i7iac7vxt2nrlfw9 (code),
  UNIQUE KEY UK_3dowo3txqklfc6ar8lyie91ym (value)
) ;



DROP TABLE IF EXISTS m_type_of_entries;
CREATE TABLE m_type_of_entries (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
    remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_jo8bp0kn5035pysu67n3djhw (code),
  UNIQUE KEY UK_akluwc4wcu2f66gxlyarxakct (value)
);


DROP TABLE IF EXISTS m_typing_statuses;

CREATE TABLE m_typing_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_6yo7c3r29y617ugpjph0hw3jx (code),
  UNIQUE KEY UK_ihpo5fv2tbcjkaiouvok3ue9n (value)
);



DROP TABLE IF EXISTS m_user_statuses;
CREATE TABLE m_user_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_kpfv52a0ue8rjbqxhjyntbu7f (code),
  UNIQUE KEY UK_fmce6s7j82apugswobcuumihx (value)
);



DROP TABLE IF EXISTS m_visa_fees;
CREATE TABLE m_visa_fees (
  id int(11) NOT NULL AUTO_INCREMENT,
  amount decimal(18,2) NOT NULL,
  reserve_1 varchar(255) DEFAULT NULL,
  reserve_2 varchar(255) DEFAULT NULL,
  reserve_3 varchar(255) DEFAULT NULL,
  reserve_4 varchar(255) DEFAULT NULL,
  reserve_5 varchar(255) DEFAULT NULL,
  country_id int(11) NOT NULL,
  fee_type_id int(11) NOT NULL,
  sc_id int(11) NOT NULL,
  toe_id int(11) NOT NULL,
  visa_type_id int(11) NOT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS m_visa_statuses;
CREATE TABLE m_visa_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_7x7ewd1pob3vqmi7mjhk4s9i5 (code),
  UNIQUE KEY UK_ag3hy1ib5v2072bsfyl5cbc5o (value)
);




DROP TABLE IF EXISTS m_visa_types;

CREATE TABLE m_visa_types (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  value varchar(255) NOT NULL,
  process_type_id int(11) NOT NULL,
  remarks varchar(255),
  status int(11),
  PRIMARY KEY (id),
  UNIQUE KEY UK_f6pxffsbekgt5pjx95q1utvwo (code),
  UNIQUE KEY UK_am45bqr89d511uankde3p7x7b (value)
);




DROP TABLE IF EXISTS m_vsc_counters;
CREATE TABLE m_vsc_counters (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL,
  counter_no varchar(255) NOT NULL,
  counter_status tinyint(4) NOT NULL,
  counter_type_id int(11) NOT NULL,
  sc_id int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_3axg30uo93e04y3s3jeaiv1du (code)
);



DROP TABLE IF EXISTS m_vsc_roles;
CREATE TABLE m_vsc_roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_99mfp5r6v3q372rgdrblmym5o (role_name)
);


DROP TABLE IF EXISTS m_vsc_user_roles;
CREATE TABLE m_vsc_user_roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  role_id int(11) NOT NULL,
  user_id int(11) NOT NULL,
  PRIMARY KEY (id)
);




DROP TABLE IF EXISTS m_vsc_users;
CREATE TABLE m_vsc_users (
  id int(11) NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  user_id varchar(255) NOT NULL,
  user_name varchar(255) NOT NULL,
  sc_id int(11) NOT NULL,
  status int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_h8i4kx374yfdupbb02vo8ejkn (email),
  UNIQUE KEY UK_6pddokqdjidvaw723xl8fh0gx (user_id)
);


DROP TABLE IF EXISTS o_applications;
CREATE TABLE o_applications (
  id int(11) NOT NULL AUTO_INCREMENT,
  address_city varchar(255) DEFAULT NULL,
  arabic_father_name varchar(255) DEFAULT NULL,
  arabic_first_name varchar(255) DEFAULT NULL,
  arabic_grand_name varchar(255) DEFAULT NULL,
  arabic_last_name varchar(255) DEFAULT NULL,
  area varchar(255) DEFAULT NULL,
  building_no varchar(255) DEFAULT NULL,
  date_of_birth date NOT NULL,
  date_of_expiry date NOT NULL,
  date_of_issue date NOT NULL,
  e_number varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  enjaz_fee_trans_num varchar(255) DEFAULT NULL,
  father_name varchar(255) DEFAULT NULL,
  first_name varchar(255) NOT NULL,
  house_no varchar(255) DEFAULT NULL,
  is_audit_mandate tinyint(4) NOT NULL,
  last_name varchar(255) DEFAULT NULL,
  maharam_name varchar(255) DEFAULT NULL,
  maharam_relation varchar(255) DEFAULT NULL,
  medical_fee_trans_num varchar(255) DEFAULT NULL,
  mobile_no varchar(255) DEFAULT NULL,
  occupation varchar(255) DEFAULT NULL,
  passport_no varchar(255) NOT NULL,
  pincode varchar(255) DEFAULT NULL,
  place_of_birth varchar(255) NOT NULL,
  place_of_issue varchar(255) NOT NULL,
  prime_applicant tinyint(4) NOT NULL,
  qualification varchar(255) DEFAULT NULL,
  reserve_1 varchar(255) DEFAULT NULL,
  reserve_2 varchar(255) DEFAULT NULL,
  reserve_3 varchar(255) DEFAULT NULL,
  reserve_4 varchar(255) DEFAULT NULL,
  reserve_5 varchar(255) DEFAULT NULL,
  state varchar(255) DEFAULT NULL,
  visa_fee_trans_num varchar(255) DEFAULT NULL,
  status int(11) NOT NULL,
  app_id int(11) DEFAULT NULL,
  address_country int(11) DEFAULT NULL,
  gender_id int(11) NOT NULL,
  marital_status_id int(11) NOT NULL,
  nationality_id int(11) NOT NULL,
  pp_type_id int(11) NOT NULL,
  religion_id int(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_hy4g4s5yx1hb6ngqt4vs4kqxe (e_number)
);


DROP TABLE IF EXISTS o_appointments;
CREATE TABLE o_appointments (
  id int(11) NOT NULL AUTO_INCREMENT,
  app_date date NOT NULL,
  appref_id varchar(255) NOT NULL,
  creation_time datetime NOT NULL,
  invitation_no varchar(255) DEFAULT NULL,
  is_courier_enabled tinyint(4) NOT NULL,
  is_invitation tinyint(4) DEFAULT NULL,
  is_lounge_enabled tinyint(4) NOT NULL,
  is_sms_enabled tinyint(4) NOT NULL,
  last_modified_time datetime DEFAULT NULL,
  no_applicants int(11) NOT NULL,
  reschedule_count int(11) DEFAULT NULL,
  reserve_1 varchar(255) DEFAULT NULL,
  reserve_2 varchar(255) DEFAULT NULL,
  reserve_3 varchar(255) DEFAULT NULL,
  reserve_4 varchar(255) DEFAULT NULL,
  reserve_5 varchar(255) DEFAULT NULL,
  saudi_mission int(11) NOT NULL,
  sponsor_address varchar(255) DEFAULT NULL,
  sponsor_id varchar(255) DEFAULT NULL,
  sponsor_name varchar(255) DEFAULT NULL,
  sponsor_ph_no varchar(255) DEFAULT NULL,
  sponsor_purpose varchar(255) DEFAULT NULL,
  total_visa_fee decimal(19,2) DEFAULT NULL,
  status int(11) NOT NULL,
  app_type_id int(11) NOT NULL,
  created_by int(11) NOT NULL,
  moe_id int(11) NOT NULL,
  poe_id int(11) NOT NULL,
  process_type_id int(11) NOT NULL,
  sc_id int(11) NOT NULL,
  travel_type_id int(11) NOT NULL,
  toe_id int(11) NOT NULL,
  last_modified_by int(11) DEFAULT NULL,
  visa_type_id int(11) NOT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS o_time_slot_bookings;
CREATE TABLE o_time_slot_bookings (
  id int(11) NOT NULL AUTO_INCREMENT,
  status  int(11) NOT NULL,
  created_on datetime NOT NULL,
  day datetime DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  appointment_id int(11) DEFAULT NULL,
  created_by int(11) DEFAULT NULL,
  time_slot_config_id int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS o_vsc_daily_token_configs;
CREATE TABLE o_vsc_daily_token_configs (
  id int(11) NOT NULL AUTO_INCREMENT,
  token_no int(11) NOT NULL,
  work_date date NOT NULL,
  sc_id int(11) NOT NULL,
  PRIMARY KEY (id)
);



DROP TABLE IF EXISTS o_vsc_fo_trackers;
CREATE TABLE o_vsc_fo_trackers (
  id int(11) NOT NULL AUTO_INCREMENT,
  comment varchar(255) DEFAULT NULL,
  created_on datetime NOT NULL,
  applicant_id int(11) NOT NULL,
  appointment_id int(11) DEFAULT NULL,
  stage int(11) NOT NULL,
  status int(11) NOT NULL,
  vsc_user_id int(11) NOT NULL,
  app_id int(11) DEFAULT '0',
  PRIMARY KEY (id)
);




DROP TABLE IF EXISTS o_vsc_tokens;
CREATE TABLE o_vsc_tokens (
  id int(11) NOT NULL AUTO_INCREMENT,
  created_on datetime NOT NULL,
  token_date date NOT NULL,
  token_no varchar(255) NOT NULL,
  updated_on datetime DEFAULT NULL,
  appointment_id int(11) NOT NULL,
  assigned_to int(11) DEFAULT NULL,
  counter_id int(11) NOT NULL,
  created_by int(11) NOT NULL,
  process_stage_id int(11) NOT NULL,
  process_status_id int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);




DROP TABLE IF EXISTS o_vsc_payments;

CREATE TABLE o_vsc_payments (
  id int(11) NOT NULL AUTO_INCREMENT,
  amount decimal(13,3) NOT NULL,
  card_no varchar(255) DEFAULT NULL,
  created_on datetime DEFAULT NULL,
  transaction_date datetime DEFAULT NULL,
  transaction_ref_no varchar(255) DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  appointment_id int(11) NOT NULL,
  counter_id int(11) DEFAULT NULL,
  created_by int(11) DEFAULT NULL,
  payment_mode_id int(11) DEFAULT NULL,
  status int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);




DROP TABLE IF EXISTS o_vsc_payment_details;
CREATE TABLE o_vsc_payment_details (
  id int(11) NOT NULL AUTO_INCREMENT,
  amount decimal(13,3) NOT NULL,
  created_on datetime NOT NULL,
  status tinyint(4) NOT NULL,
  transaction_no varchar(255) DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  applicant_id int(11) NOT NULL,
  created_by int(11) NOT NULL,
  payment_id int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS o_vsc_refunds;
CREATE TABLE o_vsc_refunds (
  id int(11) NOT NULL AUTO_INCREMENT,
  amount decimal(13,3) NOT NULL,
  created_on datetime NOT NULL,
  reference_no varchar(255) DEFAULT NULL,
  status tinyint(4) NOT NULL,
  transaction_date datetime DEFAULT NULL,
  transaction_no varchar(255) DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  application_id int(11) NOT NULL,
  appointment_id int(11) NOT NULL,
  created_by int(11) NOT NULL,
  mode_of_refund int(11) DEFAULT NULL,
  updated_by int(11) DEFAULT NULL,
  counter_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
  );

  
DROP TABLE IF EXISTS o_audit_details;
CREATE TABLE o_audit_details (
  id int(11) NOT NULL AUTO_INCREMENT,
  applicant_name varchar(255) NOT NULL,
  created_on datetime NOT NULL,
  enumber varchar(255) NOT NULL,
  passport_no varchar(255) NOT NULL,
  remarks varchar(255) DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  audit_id int(11) NOT NULL,
  hold_reason_id int(11) DEFAULT NULL,
  audit_status_id int(11) NOT NULL,
  created_by int(11) NOT NULL,
  nationality_id int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  visa_type_id int(11) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS o_audits;
CREATE TABLE o_audits (
  id int(11) NOT NULL AUTO_INCREMENT,
  audit_date date DEFAULT NULL,
  created_on datetime NOT NULL,
  no_of_applications int(11) DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  created_by int(11) NOT NULL,
  spoke_id int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  audit_status_id int(11) NOT NULL,
  audit_type_id int(11) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS o_bo_batch_details;
CREATE TABLE o_bo_batch_details (
  id int(11) NOT NULL AUTO_INCREMENT,
  created_on datetime NOT NULL,
  updated_on datetime DEFAULT NULL,
  batch_id int(11) NOT NULL,
  scan_id int(11) NOT NULL,
  created_by int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS o_bo_batches;
CREATE TABLE o_bo_batches (
  id int(11) NOT NULL AUTO_INCREMENT,
  batch_no varchar(255) NOT NULL,
  created_on datetime NOT NULL,
  no_of_applications int(11) NOT NULL,
  remarks varchar(255) DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  created_by int(11) NOT NULL,
  outscan_to_center_id int(11) NOT NULL,
  status int(11) NOT NULL,
  center_id int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  awb_number varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS o_bo_scan_details;
CREATE TABLE o_bo_scan_details (
  id int(11) NOT NULL AUTO_INCREMENT,
  applicant_name varchar(255) NOT NULL,
  created_on datetime NOT NULL,
  enumber varchar(255) NOT NULL,
  is_audit_mandate tinyint(4) NOT NULL,
  passport_no varchar(255) NOT NULL,
  remarks varchar(255) DEFAULT NULL,
  updated_on datetime DEFAULT NULL,
  audit_status_id int(11) NOT NULL,
  create_by int(11) NOT NULL,
  inscan_from_center_id int(11) NOT NULL,
  nationality_id int(11) NOT NULL,
  status_id int(11) NOT NULL,
  center_id int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  visa_type_id int(11) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS o_vsc_biometric_details;
CREATE TABLE o_vsc_biometric_details (
  id int(11) NOT NULL AUTO_INCREMENT,
  created_on datetime NOT NULL,
  status tinyint(4) NOT NULL,
  updated_on datetime DEFAULT NULL,
  applicant_id int(11) NOT NULL,
  appointment_id int(11) NOT NULL,
  created_by int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  biometric_id int(11) NOT NULL,
  counter_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS o_vsc_biometric_scan_details;
CREATE TABLE o_vsc_biometric_scan_details (
  id int(11) NOT NULL AUTO_INCREMENT,
  created_on datetime NOT NULL,
  scan_comments longtext,
  scan_data longtext,
  updated_on datetime DEFAULT NULL,
  applicant_id int(11) NOT NULL,
  appointment_id int(11) NOT NULL,
  scan_type_id int(11) NOT NULL,
  status int(11) NOT NULL,
  created_by int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  biometric_details_id int(11) NOT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS o_vsc_biometrics;
CREATE TABLE o_vsc_biometrics (
  id int(11) NOT NULL AUTO_INCREMENT,
  created_on datetime NOT NULL,
  updated_on datetime DEFAULT NULL,
  appointment_id int(11) NOT NULL,
  status int(11) NOT NULL,
  created_by int(11) NOT NULL,
  updated_by int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS o_vsc_bo_trackers;
CREATE TABLE o_vsc_bo_trackers (
  id int(11) NOT NULL AUTO_INCREMENT,
  action_time datetime NOT NULL,
  e_number varchar(255) NOT NULL,
  message varchar(255) DEFAULT NULL,
  security_tag varchar(255) DEFAULT NULL,
  status_code varchar(255) NOT NULL,
  application_id int(11),
  stage int(11) NOT NULL,
  status int(11) NOT NULL,
  vsc_user_id int(11) NOT NULL,
  PRIMARY KEY (id)
);
  
