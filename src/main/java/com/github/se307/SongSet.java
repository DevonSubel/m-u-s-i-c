	package com.github.se307;

	import java.util.ArrayList;
	import java.util.HashMap;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	public class SongSet {
		
		private int song_set_pk;
		private String song_set_name;
		
		private ArrayList<Integer> song_list;
		
		
		private static boolean statementsCreated = false;
		private static final String UPDATE_SONG_SET_CONST = "UPDATE song_set SET ? = ? WHERE id = ?";
		private static final String ADD_SONG_TO_SET_CONST = "INSERT INTO song_to_set('song_id', 'song_set_id') VALUES(?, ?)";
		private static final String REMOVE_SONG_FROM_SET_CONST = "DELETE FROM song_to_set WHERE song_id = ? AND song_set_id = ?";
	 	private static final String REMOVE_SONG_SET_CONST = "DELETE FROM song_set WHERE id = ?";	
		
	 	private static PreparedStatement updateSongSet;
	 	private static PreparedStatement addSongToSongSet;
	 	private static PreparedStatement removeSongFromSet;
	 	private static PreparedStatement removeSongSet;
	 	
	 	
		public SongSet(ResultSet songSet, ResultSet manyToManyMapping) {
			song_list = new ArrayList<Integer>();
			
			try {
				// Set up the prepared statements if they have not already been created.
				if(!SongSet.statementsCreated) {
					Connection dbConnection = DatabaseDriver.getConnection();
					
					SongSet.updateSongSet = dbConnection.prepareStatement(SongSet.UPDATE_SONG_SET_CONST);
					SongSet.addSongToSongSet = dbConnection.prepareStatement(SongSet.ADD_SONG_TO_SET_CONST);
					SongSet.removeSongFromSet = dbConnection.prepareStatement(SongSet.REMOVE_SONG_FROM_SET_CONST);
					SongSet.removeSongSet = dbConnection.prepareStatement(SongSet.REMOVE_SONG_SET_CONST);

				}			
				
				song_set_pk = songSet.getInt("id");
				song_set_name = songSet.getString("name");
				
				// Load all songs in this set into the arraylist (keep track of song ids)
				while(!manyToManyMapping.isAfterLast()) {
					
					int song_id = manyToManyMapping.getInt("song_id");
					song_list.add(song_id);
					
					manyToManyMapping.next();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		

		public String getSongSetName() {
			return song_set_name;
		}

		public void setSongSetName(String song_set_name) {
			try {
				SongSet.updateSongSet.setString(1, "name");
				SongSet.updateSongSet.setString(2, song_set_name);
				SongSet.updateSongSet.setInt(3, this.song_set_pk);
				
				SongSet.updateSongSet.executeUpdate();
				this.song_set_name = song_set_name;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void addSongToSet(Song added_song) {
			try {
				SongSet.addSongToSongSet.setInt(1, added_song.getKey());
				SongSet.addSongToSongSet.setInt(2, this.song_set_pk);
							
				SongSet.addSongToSongSet.executeUpdate();
				this.song_list.add(added_song.getKey());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void removeFromSet(Song song_to_remove) {
			try {
				SongSet.removeSongFromSet.setInt(1, song_to_remove.getKey());
				SongSet.removeSongFromSet.setInt(2, this.song_set_pk);
							
				SongSet.removeSongFromSet.executeUpdate();
				
				// cast to ensure that remove(element) is used, not remove(index)
				this.song_list.remove((Object)(song_to_remove.getKey()));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void deleteSongSet() {
			try {
				SongSet.removeSongSet.setInt(1, this.song_set_pk);
						
				SongSet.removeSongSet.executeUpdate();	
				// Mark this song set as having been deleted
				this.song_set_pk = -1;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public boolean isDeleted() {
			return (this.song_set_pk < 0);
		}
		
	}
