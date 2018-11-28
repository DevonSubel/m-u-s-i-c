/**
 * This class to describe a single instance of a song, containing all information
 * about the song such as name, artist, album and other fields.
 *
 * This class shall be used to wrap the data contained within the song database so
 * that the rest of the program can interface with a Java object rather than the database
 * directly.
 *
 * @author Maxence Weyrich
 *
 */

package com.github.se307;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Maxence Weyrich
 *
 */
public class Song {

	private static final SongDatabaseDriver DB_DRIVER = SongDatabaseDriver.getInstance();

	private long songKey;

	private boolean isInflated;

	private String songName;
	private String artistName;
	private String albumName;
	private Integer songLength;
	private Integer genreID;
	private Integer songYear;
	private Integer bpm;
	private String additionalNotes;
	private String songURL;

	/**
	 * Create a Song object with the given unique key identifier. The Song object is
	 * not inflated, until requested.
	 *
	 * @param songKey the key of the Song entry
	 */
	public Song(long songKey) {
		this.songKey = songKey;
		this.isInflated = false;
	}

	/**
	 * Constructor to create a new song that does not exist in the database. This
	 * constructor should not be called directly, use the SongBuilder object
	 * instead.
	 */
	private Song(String songName, String artistName, String albumName, Integer songLength, Integer genreID,
			Integer songYear, Integer bpm, String additionalNotes, String songURL) {
		this.songName = songName;
		this.artistName = artistName;
		this.albumName = albumName;
		this.songLength = songLength;
		this.genreID = genreID;
		this.songYear = songYear;
		this.bpm = bpm;
		this.additionalNotes = additionalNotes;
		this.songURL = songURL;
	}

	/**
	 * Flatten does the opposite of inflate by removing the fields, thus allowing
	 * them to be garbage collected to decrease memory impact.
	 *
	 * This method is probably not necessary, but hey... we got an inflate so why
	 * not.
	 */
	public void flatten() {
		// TODO: flatten object
	}

	/**
	 * Inflate the object by querying the database for the fields associated with
	 * the Song object.
	 *
	 * Although inflate will be called before the fields of the object can be
	 * accessed, it is not necessary for inflate to run when setting fields.
	 */
	public void inflate() {
		if (!this.isInflated) {

			ResultSet rs = Song.DB_DRIVER.getSong(this.songKey);
			try {
				this.songName = rs.getString(SongDatabaseDriver.NAME_F);
				this.artistName = rs.getString(SongDatabaseDriver.ARTIST_NAME_F);
				this.albumName = rs.getString(SongDatabaseDriver.ALBUM_NAME_F);

				this.songLength = rs.getInt(SongDatabaseDriver.SONG_LENGTH_F);
				if (rs.wasNull())
					this.songLength = null;

				this.genreID = rs.getInt(SongDatabaseDriver.GENRE_ID_F);
				if (rs.wasNull())
					this.genreID = null;

				this.songYear = rs.getInt(SongDatabaseDriver.SONG_YEAR_F);
				if (rs.wasNull())
					this.songYear = null;

				this.bpm = rs.getInt(SongDatabaseDriver.BPM_F);
				if (rs.wasNull())
					this.bpm = null;

				this.additionalNotes = rs.getString(SongDatabaseDriver.ADDITIONAL_NOTES_F);
				this.songURL = rs.getString(SongDatabaseDriver.URI_F);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			this.isInflated = true;
		}
	}

	public void deleteSong() {

		if (Song.DB_DRIVER.removeSong(this.songKey)) {
			this.songKey = -1;
			// TODO: successful delete
		} else {
			// TODO: failed delete
		}

	}

	public boolean isDeleted() {
		return (this.songKey < 0);
	}

	/*
	 * Getter methods
	 *
	 * All but getKey() will auto-inflate the Song object before returning
	 */

	public long getKey() {
		return songKey;
	}

	public String getSongName() {
		this.inflate();
		return songName;
	}

	public String getArtistName() {
		this.inflate();
		return artistName;
	}

	public String getAlbumName() {
		this.inflate();
		return albumName;
	}

	public Integer getSongLength() {
		this.inflate();
		return songLength;
	}

	public Integer getGenreId() {
		this.inflate();
		return genreID;
	}

	public Integer getSongYear() {
		this.inflate();
		return songYear;
	}

	public Integer getBpm() {
		this.inflate();
		return bpm;
	}

	public String getAdditionalNotes() {
		this.inflate();
		return additionalNotes;
	}

	public String getSongUrl() {
		this.inflate();
		return songURL;
	}

	/*
	 * Setter methods
	 *
	 * All setters will trigger an update to the database. Song object does not
	 * necessarily have to be inflated to set the database
	 */

	public void setSongName(String songName) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.NAME_F, songName, this.songKey)) {
			this.songName = songName;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setArtistName(String artistName) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.ARTIST_NAME_F, artistName, this.songKey)) {
			this.artistName = artistName;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setAlbumName(String albumName) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.ALBUM_NAME_F, albumName, this.songKey)) {
			this.albumName = albumName;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setSongLength(Integer songLength) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.SONG_LENGTH_F, songLength, this.songKey)) {
			this.songLength = songLength;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setGenreList(Integer genreID) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.GENRE_ID_F, genreID, this.songKey)) {
			this.genreID = genreID;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setSongYear(Integer songYear) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.SONG_YEAR_F, songYear, this.songKey)) {
			this.songYear = songYear;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setBpm(Integer bpm) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.BPM_F, bpm, this.songKey)) {
			this.bpm = bpm;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setAdditionalNotes(String additionalNotes) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.ADDITIONAL_NOTES_F, additionalNotes, this.songKey)) {
			this.additionalNotes = additionalNotes;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setSongUrl(String songURL) {

		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.URI_F, songURL, this.songKey)) {
			this.songURL = songURL;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public class SongBuilder {

		public String songName;
		public String artistName;
		public String albumName;
		public Integer songLength;
		public Integer genreID;
		public Integer songYear;
		public Integer bpm;
		public String additionalNotes;
		public String songURL;

		public SongBuilder() {
		}

		/**
		 * Creates a new Song using the specified fields 
		 * 
		 * @return
		 * @throws IllegalArgumentException
		 */
		public Song build() throws IllegalArgumentException {
			if (songName == null || artistName == null || albumName == null) {
				throw new IllegalArgumentException("Song name, artist name, and album name must be defined.");
			}
			return new Song(songName, artistName, albumName, songLength, genreID, songYear, bpm, additionalNotes,
					songURL);
		}
	}
}
