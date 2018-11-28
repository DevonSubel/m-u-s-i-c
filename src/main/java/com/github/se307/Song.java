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

import org.apache.commons.text.similarity.JaroWinklerDistance;

/**
 * @author Maxence Weyrich
 *
 */
public class Song {
	
	public static final JaroWinklerDistance DISTANCE_CALCULATOR = new JaroWinklerDistance();
	
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
	 * Create a Song object with the given unique key identifier.
	 * The Song object is not inflated, until requested.
	 * 
	 * @param songKey	the key of the Song entry
	 */
	public Song(long songKey) {
		this.songKey = songKey;
		this.isInflated = false;
	}
	
	/**
	 * Flatten does the opposite of inflate by removing the fields, 
	 * thus allowing them to be garbage collected to decrease memory impact.
	 * 
	 * This method is probably not necessary, but hey... we got an inflate so why not.
	 */
	public void flatten() {
		// TODO: flatten object
	}
	
	/**
	 * Inflate the object by querying the database for the fields associated with 
	 * the Song object.
	 * 
	 * Although inflate will be called before the fields of the object can be accessed, 
	 * it is not necessary for inflate to run when setting fields.
	 */
	public void inflate() {
		if(!this.isInflated) {
			
			ResultSet rs = Song.DB_DRIVER.getSong(this.songKey);
			try {	
				this.songName = rs.getString("name");
				this.artistName = rs.getString("artist_name");
				this.albumName = rs.getString("album_name");
				
				this.songLength = rs.getInt("song_length");
				if(rs.wasNull()) 
					this.songLength = null;
				
				this.genreID = rs.getInt("genre_id");
				if(rs.wasNull())
					this.genreID = null;
				
				this.songYear = rs.getInt("song_year");
				if(rs.wasNull())
					this.songYear = null;
				
				this.bpm = rs.getInt("bpm");
				if(rs.wasNull())
					this.bpm = null;
				
				this.additionalNotes = rs.getString("additional_notes");
				this.songURL = rs.getString("uri");
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			this.isInflated = true;
		}
	}
	
	/**
	 * Calculates how similar to the other song this song is.
	 * 1 indicates songs with identical characteristics, 
	 * while 0 indicates no similar characteristics
	 * 
	 * @param other The other song to compare this song to.
	 * @return float 0 <= f <= 1
	 */
	public float percentMatch(Song other) {
		int characteristicsComparedOn = 0;
		float currentSum = 0.0f;
		
		this.inflate();
		other.inflate();
		
		if(this.songName != null || other.songName != null)
			characteristicsComparedOn++;
		if(this.songName != null && other.songName != null)
			currentSum += DISTANCE_CALCULATOR.apply(this.songName, other.songName);
		
		if(this.artistName != null || other.artistName != null)
			characteristicsComparedOn++;
		if(this.artistName != null && other.artistName != null)
			currentSum += DISTANCE_CALCULATOR.apply(this.artistName, other.artistName);
		
		if(this.albumName != null || other.albumName != null)
			characteristicsComparedOn++;
		if(this.albumName != null && other.albumName != null)
			currentSum += DISTANCE_CALCULATOR.apply(this.albumName, other.albumName);
		
		if(this.songLength != null || other.songLength != null)
			characteristicsComparedOn++;
		if(this.songLength != null && other.songLength != null && 
				this.songLength.shortValue() == other.songLength.shortValue())
			currentSum += 1.0f;
		
		if(this.songYear != null || other.songYear != null)
			characteristicsComparedOn++;
		if(this.songYear != null && other.songYear != null && 
				this.songYear.shortValue() == other.songYear.shortValue())
			currentSum += 1.0f;
		
		if(this.bpm != null || other.bpm != null) 
			characteristicsComparedOn++;
		if(this.bpm != null && other.bpm != null && 
				this.bpm.shortValue() == other.bpm.shortValue())
			currentSum += 1.0f;
		
		if(this.additionalNotes != null || other.additionalNotes != null)
			characteristicsComparedOn++;
		if(this.additionalNotes != null && other.additionalNotes != null)
			currentSum += DISTANCE_CALCULATOR.apply(this.additionalNotes, other.additionalNotes);
		
		
		if(this.genreID != null || other.genreID != null)
			characteristicsComparedOn++;
		if(this.genreID != null && other.genreID != null && 
				(this.genreID == other.genreID)) {
				currentSum += 1.0f;
		}
		
		if (characteristicsComparedOn == 0) {
			return 0.0f;
		} else {
			return currentSum / characteristicsComparedOn;
		}
	}
	
	public void deleteSong() {
		
		if(Song.DB_DRIVER.removeSong(this.songKey)) {
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
	 * All setters will trigger an update to the database.
	 * Song object does not necessarily have to be inflated to set the database
	 */
	
	public void setSongName(String songName) {
		
		if(Song.DB_DRIVER.updateSong("name", songName, this.songKey)) {
			this.songName = songName;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setArtistName(String artistName) {
		
		if(Song.DB_DRIVER.updateSong("artist_name", artistName, this.songKey)) {
			this.artistName = artistName;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setAlbumName(String albumName) {
		
		if(Song.DB_DRIVER.updateSong("album_name", albumName, this.songKey)) {
			this.albumName = albumName;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setSongLength(Integer songLength) {
		
		if(Song.DB_DRIVER.updateSong("song_length", songLength, this.songKey)) {
			this.songLength = songLength;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setGenreList(Integer genreID) {
		
		if(Song.DB_DRIVER.updateSong("genre_id", genreID, this.songKey)) {
			this.genreID = genreID;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setSongYear(Integer songYear) {
		
		if(Song.DB_DRIVER.updateSong("song_year", songYear, this.songKey)) {
			this.songYear = songYear;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}

	public void setBpm(Integer bpm) {
		
		if(Song.DB_DRIVER.updateSong("bpm", bpm, this.songKey)) {
			this.bpm = bpm;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}
	
	public void setAdditionalNotes(String additionalNotes) {
		
		if(Song.DB_DRIVER.updateSong("additional_notes", additionalNotes, this.songKey)) {
			this.additionalNotes = additionalNotes;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}
	
	public void setSongUrl(String songURL) {
		
		if(Song.DB_DRIVER.updateSong("song_url", songURL, this.songKey)) {
			this.songURL = songURL;
			// TODO: successful update
		} else {
			// TODO: failed update
		}
	}
	
}

