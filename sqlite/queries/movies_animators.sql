CREATE TABLE movies_animators (
    animator_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    PRIMARY KEY (animator_id, movie_id),
    FOREIGN KEY(movie_id) REFERENCES movies_animated(id),
    FOREIGN KEY(animator_id) REFERENCES animators(id)
);
