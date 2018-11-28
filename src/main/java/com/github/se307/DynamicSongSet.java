package com.github.se307;

import java.util.Collections;
import java.util.List;

/**
 * @author Maxence Weyrich
 *
 */
public class DynamicSongSet extends SongSet {

	
	private String dbQuery;
	
	public DynamicSongSet() {
		super();
		this.dbQuery = "";
	}
	
	public DynamicSongSet(long songSetKey) {
		throw new UnsupportedOperationException("Dynamic SongSets cannot be instatiated from the database (yet).");
	}
	
	public DynamicSongSet(String query) {
		super();
		this.dbQuery = query;
	}
	
	/**
	 * Get all the songs from the Song Set. The Song objects are not inflated by default.
	 * 
	 * @return 		list containing all the songs. List is not modifiable.
	 */
	@Override
	public List<Song> getSongs() {
		return Collections.unmodifiableList(SongSet.DB_DRIVER.querySong(this.dbQuery));
	}

	/**
	 * Converts the DynamicSongSet to a StaticSongSet and saves it to the database.
	 * This is because DynamicSongSets cannot be saved to the database (yet)
	 */
	@Override
	public void save() {
		throw new UnsupportedOperationException("Dynamic SongSets cannot be saved from the database (yet).\nConvert to a StaticSongSet then save");
	}
	
	/**
	 * Converts the DynamicSongSet to a new StaticSongSet.
	 * 
	 * @return 		the new StaticSongSet
	 */
	public StaticSongSet toStaticSongSet() {
		StaticSongSet staticSet = new StaticSongSet();
		
		staticSet.setSongSetName(super.songSetName);
		
		List<Song> songsInSet = getSongs();
		for(Song s : songsInSet) {
			staticSet.addSong(s);
		}
		
		return staticSet;
	}
	
	
}
