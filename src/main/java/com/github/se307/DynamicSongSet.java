package com.github.se307;

import java.util.List;

public class DynamicSongSet extends SongSet {

	
	/**
	 * Add the specified song to the Song Set
	 * Dynamic Song Sets are implementation specific and may either throw
	 * an exception or add the song to the set, even if it does not match with the dynamic query
	 * @param newSong
	 * @return true if the song was not in the set and was successfully added
	 */
	public boolean addSong(Song newSong) {
		return false;
	}
	
	/**
	 * Remove the song from the Song Set.
	 * Dynamic Song Sets are implementation specific and may either throw
	 * an exception or remove the song from the set, even if it matches with the dynamic query
	 * @param removeSong
	 * @return true on success, regardless whether the song was removed or not
	 */
	public boolean removeSong(Song removeSong) {
		return false;
	}

	@Override
	public List<Song> getSongs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
	
	
}
