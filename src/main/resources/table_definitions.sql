CREATE TABLE IF NOT EXISTS genre
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    name varchar(40) NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS genre_name_uindex ON genre (name);

CREATE TABLE IF NOT EXISTS song
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    name varchar(250) NOT NULL,
    artist_name varchar(250) NOT NULL,
    album_name varchar(250) NOT NULL,
    song_length int,
    genre_id int,
    song_year int,
    bpm int,
    song_music_key int,
    song_mode int,
    additional_notes varchar(500),
    uri varchar(250),
    liked int,
    CONSTRAINT song_genre_id_fk FOREIGN KEY (genre_id) REFERENCES genre (id)
);

CREATE TABLE IF NOT EXISTS song_set
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    name varchar(40) NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS song_set_name_uindex ON song_set (name);

CREATE TABLE IF NOT EXISTS song_to_set
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    song_id integer NOT NULL,
    song_set_id integer NOT NULL,
    CONSTRAINT song_to_set_song_id_fk FOREIGN KEY (song_id) REFERENCES song (id) ON DELETE CASCADE,
    CONSTRAINT song_to_set_song_set_id_fk FOREIGN KEY (song_set_id) REFERENCES song_set (id) ON DELETE CASCADE
);
