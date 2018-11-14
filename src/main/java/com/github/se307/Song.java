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
import java.sql.Connection;
import java.sql.PreparedStatement;

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
	private String song_url;
	

	private static final String UPDATE_STATEMENT_CONST = "UPDATE song SET ? = ? WHERE id = ?;";
	private static final String DELETE_STATEMENT_CONST = "DELETE FROM song WHERE id = ?";
	
	private static PreparedStatement updateStatement;
	private static PreparedStatement deleteStatement;
	
	/**
	 * Create a song object from the Result Set given.
	 * This object not call .next() one time, thus can be safely used
	 * so that none of the objects are skipped over.
	 *  
	 * @param set SQL query response from which to build this Song Object
	 */
	public Song(ResultSet rs) {
		try {	
			
			if (Song.updateStatement == null || Song.deleteStatement == null) {
				Connection dbConnection = DatabaseDriver.getConnection();
				
				Song.updateStatement = dbConnection.prepareStatement(Song.UPDATE_STATEMENT_CONST);
				Song.deleteStatement = dbConnection.prepareStatement(Song.DELETE_STATEMENT_CONST);
			}
			
			this.song_pk = rs.getInt("id");
			this.song_name = rs.getString("name");
			this.artist_name = rs.getString("artist_name");
			this.album_name = rs.getString("album_name");
			this.song_length = rs.getInt("song_length");
			this.genre_id = rs.getInt("genre_id");
			this.song_year = rs.getInt("song_year");
			this.bpm = rs.getInt("bpm");
			this.additional_notes = rs.getString("additional_notes");
			this.song_url = rs.getString("uri");
			
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

	
	public void deleteSong() {
		try {
			Song.deleteStatement.setInt(1, this.song_pk);
						
			Song.deleteStatement.executeUpdate();
			
			// Mark this song Object as having been deleted. 
			this.song_pk = -1;
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isDeleted() {
		return (this.song_pk < 0);
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

	public String getSongUrl() {
		return song_url;
	}

	
	/*
	 * Setter methods
	 * All setters will trigger an update to the database.
	 */
	
	public void setSongName(String song_name) {
		try {
			Song.updateStatement.setString(1, "name");
			Song.updateStatement.setString(2, song_name);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.song_name = song_name;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setArtistName(String artist_name) {
		try {
			Song.updateStatement.setString(1, "artist_name");
			Song.updateStatement.setString(2, artist_name);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.artist_name = artist_name;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setAlbumName(String album_name) {
		try {
			Song.updateStatement.setString(1, "album_name");
			Song.updateStatement.setString(2, album_name);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.album_name = album_name;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setSongLength(Integer song_length) {
		try {
			Song.updateStatement.setString(1, "song_length");
			Song.updateStatement.setInt(2, song_length);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.song_length = song_length;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setGenreList(Integer genre_id) {
		try {
			Song.updateStatement.setString(1, "genre_id");
			Song.updateStatement.setInt(2, genre_id);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.genre_id = genre_id;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setSongYear(Integer song_year) {
		try {
			Song.updateStatement.setString(1, "song_year");
			Song.updateStatement.setInt(2, song_year);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.song_year = song_year;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setBpm(Integer bpm) {
		try {
			Song.updateStatement.setString(1, "bpm");
			Song.updateStatement.setInt(2, bpm);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.bpm = bpm;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setAdditionalNotes(String additional_notes) {
		try {
			Song.updateStatement.setString(1, "additional_notes");
			Song.updateStatement.setString(2, additional_notes);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.additional_notes = additional_notes;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setSongUrl(String song_url) {
		try {
			Song.updateStatement.setString(1, "song_url");
			Song.updateStatement.setString(2, song_url);
			Song.updateStatement.setInt(3, this.song_pk);
						
			Song.updateStatement.executeUpdate();
			this.song_url = song_url;	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

