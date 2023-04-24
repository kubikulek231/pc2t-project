CREATE TABLE movies_actors (
    actor_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    PRIMARY KEY (actor_id, movie_id),
    FOREIGN KEY(movie_id) REFERENCES movies_live(id),
    FOREIGN KEY(actor_id) REFERENCES actors(id)
);
