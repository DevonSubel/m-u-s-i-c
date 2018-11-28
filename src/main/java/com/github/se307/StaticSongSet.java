package com.github.se307;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Maxence Weyrich
 *
 */
public class StaticSongSet extends SongSet {

	private List<Song> songList;

	/**
	 * Create a new (empty) SongSet with no connection to the database
	 */
	public StaticSongSet() {
		super();
		this.songList = new ArrayList<Song>();
	}

	/**
	 * Create a SongSet using the database values
	 * 
	 * @param songSet
	 *            the key of the entry into the database table for SongSets
	 */
	public StaticSongSet(long songSetKey) {
		super(songSetKey);
		this.songList = SongSet.DB_DRIVER.querySong(super.songSetKey);
	}

	/**
	 * Saves the Song Set to the database if it does not exist in the database.
	 * Otherwise do nothing as the set automatically saves when it is changed.
	 */
	@Override
	public void save() {

		// Create a new table if it does not exist in the database
		if (super.songSetKey == 0) {
			super.songSetKey = SongSet.DB_DRIVER.createSongSet(super.songSetName);

			if (super.songSetKey > 0) {
				// Save all songs in the set to the database
				for (Song song : this.songList) {
					if (SongSet.DB_DRIVER.addSongToSongSet(song.getKey(), super.songSetKey)) {
						// TODO: handle if the song was successfully added
					} else {
						// TODO: alert user that the change was not successful
					}
				}
			}
		}
	}

	/**
	 * Get all the songs from the Song Set. The Song objects are not inflated by
	 * default.
	 * 
	 * @return list containing all the songs. List is not modifiable.
	 */
	@Override
	public List<Song> getSongs() {
		return Collections.unmodifiableList(this.songList);
	}

	/*
	 * Setter Methods
	 */

	/**
	 * Add the specified song to the Song Set
	 * 
	 * @param newSong
	 * @return true if the song was not in the set and was successfully added
	 */
	public boolean addSong(Song addedSong) {

		if (super.songSetKey == 0 || SongSet.DB_DRIVER.addSongToSongSet(addedSong.getKey(), super.songSetKey)) {
			this.songList.add(addedSong);
			// TODO: handle if the song was successfully added
			return true;
		} else {
			// TODO: alert user that the change was not successful
			return false;
		}

	}

	/**
	 * Remove the song from the Song Set.
	 * 
	 * @param removeSong
	 * @return true on success; even if song was not in database
	 */
	public boolean removeSong(Song songToRemove) {

		if (super.songSetKey == 0 || SongSet.DB_DRIVER.removeSongFromSet(songToRemove.getKey(), super.songSetKey)) {
			this.songList.remove(songToRemove);
			// TODO: handle when change was successful
			return true;
		} else {
			// TODO: alert user that change was not successful
			return false;
		}

	}
}
