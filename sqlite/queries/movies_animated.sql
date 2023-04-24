create table movies_animated (
	id integer primary key,
	mov_name varchar(128),
	mov_director varchar(64),
	mov_year year,
	mov_rating integer check(mov_rating >=1 AND mov_rating <= 10),
	mov_review varchar(128),
	mov_age integer check(mov_age >=0 AND mov_age <= 99)
)