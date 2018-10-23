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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	private Short song_length;
	private ArrayList<String> genre_list;
	private Short song_year;
	private Short bpm;
	/*
	 * Song key is represented as a number from 1-7 corresponding to (A, B, C, D, E, F, G), respectively.
	 * A negative value indicates a Minor key, while a positive number represents a Major key
	 */
	private Byte song_key; 
	private String additional_notes;
	private ArrayList<String> playlists_containing_song;
	private String music_service;
	private URL song_url;
	private Byte song_user_rating;
	private String song_lyrics;
	
	// For tracking purposes when an update needs to happen
	private int db_row_id;
	
	
	/**
	 * Create a song object from the Result Set given.
	 * This object not call .next() one time, thus can be safely used
	 * so that none of the objects are skipped over.
	 *  
	 * @param set SQL query response from which to build this Song Object
	 */
	public Song(ResultSet rs) {
		try {
			this.song_pk = rs.getInt("SONG_ID");
			this.song_name = rs.getString("SONG_NAME");
			this.artist_name = rs.getString("ARTIST_NAME");
			this.album_name = rs.getString("ALBUM_NAME");
			this.song_length = rs.getShort("SONG_LENGTH");
			String genres = rs.getString("GENRES");
			if(genres != null) {
				this.genre_list = new ArrayList<String>(Arrays.asList(genres.split(",\\s*")));
				Collections.sort(this.genre_list);
			}
			this.song_year = rs.getShort("SONG_YEAR");
			this.bpm = rs.getShort("BPM");
			this.song_key = rs.getByte("SONG_KEY");
			this.additional_notes = rs.getString("NOTES");
			String playlists = rs.getString("PLAYLISTS_CONTAINING_SONG");
			if(playlists != null) {
				this.playlists_containing_song = new ArrayList<String>(Arrays.asList(playlists.split(",\\s*")));
				Collections.sort(this.playlists_containing_song);
			}
			this.music_service = rs.getString("MUSIC_SERVICE");
			this.song_url = rs.getURL("SONG_URL");
			this.song_user_rating = rs.getByte("USER_RATING");
			this.song_lyrics = rs.getString("SONG_LYRICS");
			
			
			this.db_row_id = rs.getRow();
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
		
		if(this.song_key != null || other.song_key != null)
			characteristics_compared_on++;
		if(this.song_key != null && other.song_key != null && 
				this.song_key.byteValue() == other.song_key.byteValue())
			current_sum += 1.0f;
		
		if(this.music_service != null || other.music_service != null)
			characteristics_compared_on++;
		if(this.music_service != null && other.music_service != null && 
				this.song_key.equals(other.song_key))
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
		
		if(this.genre_list != null || other.genre_list != null)
			characteristics_compared_on++;
		if(this.genre_list != null && other.genre_list != null && 
				(this.genre_list.size() > 0 || other.genre_list.size() > 0)) {
			float incr = 1.0f / Math.max(this.genre_list.size(), other.genre_list.size());
			for(int i = 0; i < Math.min(this.genre_list.size(), other.genre_list.size()); i++) {
				if(this.genre_list.get(i).equals(other.genre_list.get(i)))
					current_sum += incr;	
			}
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

	public Short getSongLength() {
		return song_length;
	}

	public ArrayList<String> getGenreList() {
		return genre_list;
	}

	public Short getSongYear() {
		return song_year;
	}

	public Short getBpm() {
		return bpm;
	}

	public Byte getSongKey() {
		return song_key;
	}

	public String getAdditionalNotes() {
		return additional_notes;
	}

	public ArrayList<String> getPlaylistsContainingSong() {
		return playlists_containing_song;
	}

	public String getMusicService() {
		return music_service;
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
		triggerDBUpdate("SONG_NAME");
	}

	public void setArtistName(String artist_name) {
		this.artist_name = artist_name;
		triggerDBUpdate("ARTIST_NAME");
	}

	public void setAlbumName(String album_name) {
		this.album_name = album_name;
		triggerDBUpdate("ALBUM_NAME");
	}

	public void setSongLength(Short song_length) {
		this.song_length = song_length;
		triggerDBUpdate("SONG_LENGTH");
	}

	public void setGenreList(ArrayList<String> genre_list) {
		this.genre_list = genre_list;
		triggerDBUpdate("GENRES");
	}

	public void setSongYear(Short song_year) {
		this.song_year = song_year;
		triggerDBUpdate("SONG_YEAR");
	}

	public void setBpm(Short bpm) {
		this.bpm = bpm;
		triggerDBUpdate("BPM");
	}

	public void setSongKey(Byte song_key) {
		this.song_key = song_key;
		triggerDBUpdate("SONG_KEY");
	}

	public void setAdditionalNotes(String additional_notes) {
		this.additional_notes = additional_notes;
		triggerDBUpdate("NOTES");
	}

	public void setPlaylistsContainingSong(ArrayList<String> playlists_containing_song) {
		this.playlists_containing_song = playlists_containing_song;
		triggerDBUpdate("PLAYLISTS_CONTAINING_SONG");
	}

	public void setMusicService(String music_service) {
		this.music_service = music_service;
		triggerDBUpdate("MUSIC_SERVICE");
	}

	public void setSongUrl(URL song_url) {
		this.song_url = song_url;
		triggerDBUpdate("SONG_URL");
	}

	public void setSongUserRating(Byte song_user_rating) {
		this.song_user_rating = song_user_rating;
		triggerDBUpdate("USER_RATING");
	}

	public void setSongLyrics(String song_lyrics) {
		this.song_lyrics = song_lyrics;
		triggerDBUpdate("SONG_LYRICS");
	}

}
