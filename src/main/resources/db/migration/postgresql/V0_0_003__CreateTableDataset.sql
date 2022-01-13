create table dataset (
                         id int8 generated by default as identity,
                         creation_date timestamp,
                         description varchar(255),
                         name varchar(255),
                         original_file_name varchar(255),
                         uuid uuid,
                         primary key (id)
)