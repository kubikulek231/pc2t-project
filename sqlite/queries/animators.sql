create table animators (
	id integer primary key,
	act_name varchar(64),
	act_surname varchar(64),
	act_movie_id integer,
	FOREIGN KEY(act_movie_id) REFERENCES movies_animated(id)
)