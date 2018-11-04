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

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.text.similarity.JaroWinklerDistance;

public class Song {
	
	public static final JaroWinklerDistance DISTANCE_CALCULATOR = new JaroWinklerDistance();
	
	private int song_pk;
	
	private String song_name;
	private String artist_name;
	private String album_name;
	private Integer song_length;
	private Integer genre_id;
	private Integer song_year;
	private Integer bpm;
	private String additional_notes;
	private URL song_url;
	private Byte song_user_rating;
	private String song_lyrics;
	
	
	/**
	 * Create a song object from the Result Set given.
	 * This object not call .next() one time, thus can be safely used
	 * so that none of the objects are skipped over.
	 *  
	 * @param set SQL query response from which to build this Song Object
	 */
	public Song(ResultSet rs) {
		try {
			this.song_pk = rs.getInt("id");
			this.song_name = rs.getString("name");
			this.artist_name = rs.getString("artist_name");
			this.album_name = rs.getString("album_name");
			this.song_length = rs.getInt("song_length");
			this.genre_id = rs.getInt("genre_id");
			this.song_year = rs.getInt("song_year");
			this.bpm = rs.getInt("bpm");
			this.additional_notes = rs.getString("additional_notes");
			
			try {
				this.song_url = new URL(rs.getString("uri"));
			} catch (MalformedURLException e) {
				this.song_url = null;
			}
			
			this.song_user_rating = rs.getByte("USER_RATING");
			this.song_lyrics = rs.getString("SONG_LYRICS");
			
		} catch(SQLException e) {
			e.printStackTrace();
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
		int characteristics_compared_on = 0;
		float current_sum = 0.0f;
		
		if(this.song_name != null || other.song_name != null)
			characteristics_compared_on++;
		if(this.song_name != null && other.song_name != null)
			current_sum += DISTANCE_CALCULATOR.apply(this.song_name, other.song_name);
		
		if(this.artist_name != null || other.artist_name != null)
			characteristics_compared_on++;
		if(this.artist_name != null && other.artist_name != null)
			current_sum += DISTANCE_CALCULATOR.apply(this.artist_name, other.artist_name);
		
		if(this.album_name != null || other.album_name != null)
			characteristics_compared_on++;
		if(this.album_name != null && other.album_name != null)
			current_sum += DISTANCE_CALCULATOR.apply(this.album_name, other.album_name);
		
		if(this.song_length != null || other.song_length != null)
			characteristics_compared_on++;
		if(this.song_length != null && other.song_length != null && 
				this.song_length.shortValue() == other.song_length.shortValue())
			current_sum += 1.0f;
		
		if(this.song_year != null || other.song_year != null)
			characteristics_compared_on++;
		if(this.song_year != null && other.song_year != null && 
				this.song_year.shortValue() == other.song_year.shortValue())
			current_sum += 1.0f;
		
		if(this.bpm != null || other.bpm != null) 
			characteristics_compared_on++;
		if(this.bpm != null && other.bpm != null && 
				this.bpm.shortValue() == other.bpm.shortValue())
			current_sum += 1.0f;
		
		if(this.additional_notes != null || other.additional_notes != null)
			characteristics_compared_on++;
		if(this.additional_notes != null && other.additional_notes != null)
			current_sum += DISTANCE_CALCULATOR.apply(this.additional_notes, other.additional_notes);
		
		if(this.song_user_rating != null || other.song_user_rating != null)
			characteristics_compared_on++;
		if(this.song_user_rating != null && other.song_user_rating != null &&
				this.song_user_rating.byteValue() == other.song_user_rating.byteValue())
			current_sum += 1.0f;
		
		if(this.genre_id != null || other.genre_id != null)
			characteristics_compared_on++;
		if(this.genre_id != null && other.genre_id != null && 
				(this.genre_id == other.genre_id)) {
				current_sum += 1.0f;
		}
		
		if (characteristics_compared_on == 0) {
			return 0.0f;
		} else {
			return current_sum / characteristics_compared_on;
		}
	}

	
	/*
	 * Getter methods
	 */
	
	public int getKey() {
		return song_pk;
	}
	
	public String getSongName() {
		return song_name;
	}

	public String getArtistName() {
		return artist_name;
	}

	public String getAlbumName() {
		return album_name;
	}

	public Integer getSongLength() {
		return song_length;
	}

	public Integer getGenreId() {
		return genre_id;
	}

	public Integer getSongYear() {
		return song_year;
	}

	public Integer getBpm() {
		return bpm;
	}

	public String getAdditionalNotes() {
		return additional_notes;
	}

	public URL getSongUrl() {
		return song_url;
	}

	public Byte getSongUserRating() {
		return song_user_rating;
	}

	public String getSongLyrics() {
		return song_lyrics;
	}
	
	/*
	 * Setter methods
	 * All setters will trigger an update to the database.
	 */

	private void triggerDBUpdate(String field_name) {
		//Insert code here that update the database 
	}
	
	
	public void setSongName(String song_name) {
		this.song_name = song_name;
		triggerDBUpdate("name");
	}

	public void setArtistName(String artist_name) {
		this.artist_name = artist_name;
		triggerDBUpdate("artist_name");
	}

	public void setAlbumName(String album_name) {
		this.album_name = album_name;
		triggerDBUpdate("album_name");
	}

	public void setSongLength(Integer song_length) {
		this.song_length = song_length;
		triggerDBUpdate("song_length");
	}

	public void setGenreList(Integer genre_id) {
		this.genre_id = genre_id;
		triggerDBUpdate("genre_id");
	}

	public void setSongYear(Integer song_year) {
		this.song_year = song_year;
		triggerDBUpdate("song_year");
	}

	public void setBpm(Integer bpm) {
		this.bpm = bpm;
		triggerDBUpdate("bpm");
	}

	public void setAdditionalNotes(String additional_notes) {
		this.additional_notes = additional_notes;
		triggerDBUpdate("additional_notes");
	}

	public void setSongUrl(URL song_url) {
		this.song_url = song_url;
		triggerDBUpdate("uri");
	}

}
