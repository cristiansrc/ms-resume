create table basic_data (
                            date_birth TEXT not null,
                            deleted boolean not null,
                            start_working_date TEXT not null,
                            created timestamp not null,
                            id integer,
                            updated timestamp not null,
                            description varchar(255) not null,
                            description_eng varchar(255) not null,
                            email varchar(255) not null,
                            first_name varchar(255) not null,
                            first_surname varchar(255) not null,
                            github varchar(255) not null,
                            greeting varchar(255) not null,
                            greeting_eng varchar(255) not null,
                            instagram varchar(255) not null,
                            linkedin varchar(255) not null,
                            located varchar(255) not null,
                            located_eng varchar(255) not null,
                            others_name varchar(255) not null,
                            others_sur_name varchar(255) not null,
                            x varchar(255) not null,
                            wrapper text null,
                            wrapper_eng text null,
                            description_pdf TEXT null,
                            description_pdf_eng TEXT null,
                            primary key (id)
);

create table blog (
                      deleted boolean not null,
                      blog_type_id bigint,
                      created timestamp not null,
                      id integer,
                      image_url_id bigint,
                      updated timestamp not null,
                      video_url_id bigint,
                      clean_url_title varchar(255) not null,
                      description varchar(255) not null,
                      description_eng varchar(255) not null,
                      description_short varchar(255) not null,
                      description_short_eng varchar(255) not null,
                      title varchar(255) not null,
                      title_eng varchar(255) not null,
                      primary key (id)
);

create table blog_type (
                           deleted boolean not null,
                           created timestamp not null,
                           id integer,
                           updated timestamp not null,
                           name varchar(255) not null,
                           name_eng varchar(255) not null,
                           primary key (id)
);

create table experience (
                            deleted boolean not null,
                            year_end TEXT not null,
                            year_start TEXT not null,
                            created timestamp not null,
                            id integer,
                            updated timestamp not null,
                            company varchar(255) not null,
                            company_eng varchar(255) not null,
                            location varchar(255) not null,
                            location_eng varchar(255) not null,
                            position varchar(255) not null,
                            position_eng varchar(255) not null,
                            summary text not null,
                            summary_eng text not null,
                            summary_pdf text not null,
                            summary_pdf_eng text not null,
                            description_items_pdf text not null,
                            description_items_pdf_eng text not null,
                            primary key (id)
);

create table experience_skill (
                                  deleted boolean not null,
                                  created timestamp not null,
                                  experience_id bigint not null,
                                  id integer,
                                  skill_son_id bigint not null,
                                  updated timestamp not null,
                                  primary key (id)
);

create table futured_project (
                                 deleted boolean not null,
                                 created timestamp not null,
                                 experience_id bigint not null,
                                 id integer,
                                 image_list_url_id bigint not null,
                                 image_url_id bigint not null,
                                 updated timestamp not null,
                                 description varchar(255) not null,
                                 description_eng varchar(255) not null,
                                 description_short varchar(255) not null,
                                 description_short_eng varchar(255) not null,
                                 name varchar(255) not null,
                                 name_eng varchar(255) not null,
                                 primary key (id)
);

create table home (
                      deleted boolean not null,
                      created timestamp not null,
                      id integer,
                      image_id bigint,
                      updated timestamp not null,
                      button_contact_label varchar(255) not null,
                      button_contact_label_eng varchar(255) not null,
                      button_work_label varchar(255) not null,
                      button_work_label_eng varchar(255) not null,
                      greeting varchar(255) not null,
                      greeting_eng varchar(255) not null,
                      primary key (id)
);

create table home_label_relational (
                                       deleted boolean not null,
                                       created timestamp not null,
                                       home_id bigint not null,
                                       id integer,
                                       label_id bigint not null,
                                       updated timestamp not null,
                                       primary key (id)
);

create table image_url (
                           deleted boolean not null,
                           created timestamp not null,
                           id integer,
                           updated timestamp not null,
                           name varchar(255) not null,
                           name_eng varchar(255) not null,
                           name_file_aws varchar(255) not null,
                           primary key (id)
);

create table label (
                       deleted boolean not null,
                       created timestamp not null,
                       id integer,
                       updated timestamp not null,
                       name varchar(255) not null,
                       name_eng varchar(255) not null,
                       primary key (id)
);

create table skill (
                       deleted boolean not null,
                       created timestamp not null,
                       id integer,
                       updated timestamp not null,
                       name varchar(255) not null,
                       name_eng varchar(255) not null,
                       primary key (id)
);

create table skill_son (
                           deleted boolean not null,
                           created timestamp not null,
                           id integer,
                           updated timestamp not null,
                           name varchar(255) not null,
                           name_eng varchar(255) not null,
                           primary key (id)
);

create table skill_son_relational (
                                      deleted boolean not null,
                                      created timestamp not null,
                                      id integer,
                                      skill_id bigint not null,
                                      skill_son_id bigint not null,
                                      updated timestamp not null,
                                      primary key (id)
);

create table skill_type (
                            deleted boolean not null,
                            created timestamp not null,
                            id integer,
                            updated timestamp not null,
                            name varchar(255) not null,
                            name_eng varchar(255) not null,
                            primary key (id)
);

create table skill_type_relational (
                                       deleted boolean not null,
                                       created timestamp not null,
                                       id integer,
                                       skill_id bigint not null,
                                       skill_type_id bigint not null,
                                       updated timestamp not null,
                                       primary key (id)
);

create table video_url (
                           deleted boolean not null,
                           created timestamp not null,
                           id integer,
                           updated timestamp not null,
                           name varchar(255) not null,
                           name_eng varchar(255) not null,
                           url varchar(255) not null,
                           primary key (id)
);

create table education (
                           deleted boolean not null,
                           start_date TEXT not null,
                           end_date TEXT not null,
                           created timestamp not null,
                           id integer,
                           updated timestamp not null,
                           institution varchar(255) not null,
                           area varchar(255) not null,
                           area_eng varchar(255) not null,
                           degree varchar(255) not null,
                           degree_eng varchar(255) not null,
                           location varchar(255) not null,
                           location_eng varchar(255) not null,
                           highlights TEXT not null,
                           highlights_eng TEXT not null,
                           primary key (id)
);
