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
		this.songList = new ArrayList<>();
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
					if (SongSet.DB_DRIVER.addSongToSongSet(song.getId(), super.songSetKey)) {
						logger.info("Song Set was successfully saved to the database");
					} else {
						logger.warn("An error was encountered while saving " + song.getSongName() + " to the database.");
						// alert user that the change was not successful
					}
				}
				
				// Save Song Set name to database
				SongSet.DB_DRIVER.updateSongSet("name", songSetName, songSetKey);
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

		if (super.songSetKey == 0 || SongSet.DB_DRIVER.addSongToSongSet(addedSong.getId(), super.songSetKey)) {
			this.songList.add(addedSong);
			// handle if the song was successfully added
			return true;
		} else {
			// alert user that the change was not successful
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

		if (super.songSetKey == 0 || SongSet.DB_DRIVER.removeSongFromSet(songToRemove.getId(), super.songSetKey)) {
			this.songList.remove(songToRemove);
			// handle when change was successful
			return true;
		} else {
			// alert user that change was not successful
			return false;
		}

	}
}
