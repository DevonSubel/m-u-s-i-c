package com.github.se307;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SongSet {
	
	private int song_set_pk;
	private String song_set_name;
	
	private ArrayList<Song> song_list;
 		
	public SongSet(ResultSet songSet, ResultSet manyToManyMapping, HashMap<Integer, Song> allSongs) {
		song_list = new ArrayList<Song>();
		
		try {
			song_set_pk = songSet.getInt("id");
			song_set_name = songSet.getString("name");
			
			while(!manyToManyMapping.isAfterLast()) {
				
				int song_id = manyToManyMapping.getInt("song_id");
				song_list.add(allSongs.get(song_id));
				
				manyToManyMapping.next();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}


	public String getSongSetName() {
		return song_set_name;
	}

	private void updateDB(String field_name) {
		//Insert code to update the database with the new changes
	}
	
	public void setSongSetName(String song_set_name) {
		this.song_set_name = song_set_name;
		updateDB("name");
	}
	
	public void addSongToSet(Song added_song) {
		song_list.add(added_song);
		// Update the mapping table (rather than the playlist)
	}
	
}
