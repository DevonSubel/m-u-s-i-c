CREATE TABLE IF NOT EXISTS genre
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    name varchar(50) NOT NULL
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
    FOREIGN KEY (genre_id) REFERENCES genre (id)
);

CREATE TABLE IF NOT EXISTS song_set
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    name varchar(40) NOT NULL
);
CREATE INDEX IF NOT EXISTS song_set_name_index ON song_set (name);

CREATE TABLE IF NOT EXISTS song_to_set
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    song_id integer NOT NULL,
    song_set_id integer NOT NULL,
    FOREIGN KEY (song_id) REFERENCES song (id) ON DELETE CASCADE,
    FOREIGN KEY (song_set_id) REFERENCES song_set (id) ON DELETE CASCADE
);


CREATE TABLE temp_table
(
  id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  name varchar(40) NOT NULL
);


INSERT INTO temp_table(name) VALUES("Blues"),
  ("Country"),
  ("Folk"),
  ("Jazz"),
  ("Reggae"),
  ("Rock"),
  ("Pop"),
  ("Electronic"),
  ("Rap"),
  ("Metal"),
  ("Latin"),
  ("World"),
  ("RnB"),
  ("Punk");

INSERT INTO genre(name)
SELECT name
FROM temp_table
WHERE name NOT IN (SELECT name FROM genre);

DROP TABLE temp_table;
