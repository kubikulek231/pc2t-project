INSERT INTO movies_animated (id, mov_name, mov_director, mov_year, mov_rating, mov_review, mov_age) 
VALUES (1, 'Toy Story', 'John Lasseter', 1995, 9, 'A classic animated movie', 3);

INSERT INTO movies_animated (id, mov_name, mov_director, mov_year, mov_rating, mov_review, mov_age)
VALUES (2, 'Finding Nemo', 'Andrew Stanton', 2003, 8, 'A heartwarming story of a clownfish', 7);

INSERT INTO movies_live (id, mov_name, mov_director, mov_year, mov_stars, mov_review) 
VALUES (1, 'Jurassic Park', 'Steven Spielberg', 1993, 5, 'A thrilling adventure movie');

INSERT INTO movies_live (id, mov_name, mov_director, mov_year, mov_stars, mov_review) 
VALUES (2, 'The Shawshank Redemption', 'Frank Darabont', 1994, 5, 'A classic drama movie');

INSERT INTO movies_live (id, mov_name, mov_director, mov_year, mov_stars, mov_review) 
VALUES (3, 'The Godfather', 'Francis Ford Coppola', 1972, 5, 'A masterpiece of cinema');

INSERT INTO actors (id, act_name, act_surname, act_movie_id)
VALUES (1, 'Tom', 'Hanks', 1);

INSERT INTO actors (id, act_name, act_surname, act_movie_id)
VALUES (2, 'Tim', 'Allen', 1);

INSERT INTO actors (id, act_name, act_surname, act_movie_id)
VALUES (3, 'Ellen', 'DeGeneres', 2);

INSERT INTO animators (id, act_name, act_surname, act_movie_id)
VALUES (1, 'John', 'Lasseter', 1);

INSERT INTO animators (id, act_name, act_surname, act_movie_id)
VALUES (2, 'Andrew', 'Stanton', 2);

INSERT INTO animators (id, act_name, act_surname, act_movie_id)
VALUES (3, 'Harry', 'Potter', 2);
