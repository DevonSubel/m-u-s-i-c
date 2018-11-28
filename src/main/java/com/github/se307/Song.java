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
	private Song(SongBuilder sb) {
		this.songName = sb.songName;
		this.artistName = sb.artistName;
		this.albumName = sb.albumName;
		this.songLength = sb.songLength;
		this.genreID = sb.genreID;
		this.songYear = sb.songYear;
		this.bpm = sb.bpm;
		this.additionalNotes = sb.additionalNotes;
		this.songURL = sb.songURL;

		this.isInflated = true;
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
		if (!this.isInflated && this.songKey > 0) {

			SongBuilder sb = Song.DB_DRIVER.getSong(this.songKey);

			if (sb != null) {

				this.songName = sb.songName;
				this.artistName = sb.artistName;
				this.albumName = sb.albumName;
				this.songLength = sb.songLength;
				this.genreID = sb.genreID;
				this.songYear = sb.songYear;
				this.bpm = sb.bpm;
				this.additionalNotes = sb.additionalNotes;
				this.songURL = sb.songURL;

				this.isInflated = true;
			}
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
		if(songName == null) {
			throw new IllegalArgumentException("Song name cannot be null");
		}
		
		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.NAME_F, songName, this.songKey)) {
			this.songName = songName;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setArtistName(String artistName) {
		if(artistName == null) {
			throw new IllegalArgumentException("Artist name cannot be null");
		}
		
		if (Song.DB_DRIVER.updateSong(SongDatabaseDriver.ARTIST_NAME_F, artistName, this.songKey)) {
			this.artistName = artistName;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setAlbumName(String albumName) {
		if(albumName == null) {
			throw new IllegalArgumentException("Album name cannot be null");
		}
		
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

	public static class SongBuilder {

		private String songName;
		private String artistName;
		private String albumName;
		private Integer songLength;
		private Integer genreID;
		private Integer songYear;
		private Integer bpm;
		private String additionalNotes;
		private String songURL;

		public SongBuilder() {
			// No operation/setup necessary for the builder pattern
		}

		/*
		 * Builder pattern allows for setting the values of object by chaining setter
		 * methods together.
		 */

		public SongBuilder setSongName(String v) {
			this.songName = v;
			return this;
		}

		public SongBuilder setArtistName(String v) {
			this.artistName = v;
			return this;
		}

		public SongBuilder setAlbumName(String v) {
			this.albumName = v;
			return this;
		}

		public SongBuilder setSongLength(Integer v) {
			this.songLength = v;
			return this;
		}

		public SongBuilder setGenreID(Integer v) {
			this.genreID = v;
			return this;
		}

		public SongBuilder setSongYear(Integer v) {
			this.songYear = v;
			return this;
		}

		public SongBuilder setBPM(Integer v) {
			this.bpm = v;
			return this;
		}

		public SongBuilder setNotes(String v) {
			this.additionalNotes = v;
			return this;
		}

		public SongBuilder setURL(String v) {
			this.songURL = v;
			return this;
		}

		/**
		 * Creates a new Song using the specified fields
		 * 
		 * @return newly build song object
		 * @throws IllegalArgumentException if required fields are not set
		 */
		public Song build() {
			if (songName == null || artistName == null || albumName == null) {
				throw new IllegalArgumentException("Song name, artist name, and album name must be defined.");
			}
			return new Song(this);
		}
	}
}
