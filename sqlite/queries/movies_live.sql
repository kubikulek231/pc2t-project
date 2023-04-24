create table movies_live (
	id integer primary key,
	mov_name varchar(128),
	mov_director varchar(64),
	mov_year year,
	mov_stars integer check(mov_stars >=1 AND mov_stars <= 5),
	mov_review varchar(128)
)