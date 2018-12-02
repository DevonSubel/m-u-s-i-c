package com.github.se307;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author Maxence Weyrich
 *
 */
public abstract class SongSet {

	protected static final SongSetDatabaseDriver DB_DRIVER = SongSetDatabaseDriver.getInstance();
	protected static final Logger logger = LogManager.getLogger();
	
	/*
	 * SongSet pk: > 0 is a PK to a set in the database 0 is the default PK assigned
	 * when a set is not stored to the db < 0 is the PK assigned to a set that has
	 * been deleted (or should no longer be available)
	 */
	protected long songSetKey;
	protected String songSetName;

	/**
	 * Create a new (empty) SongSet with no connection to the database
	 */
	public SongSet() {
		songSetKey = 0;
		songSetName = "Untitled";
	}

	/**
	 * Create a SongSet using the database values
	 * 
	 * @param songSet the key of the entry into the database table for SongSets
	 */
	public SongSet(long databasePrimaryKey) {
		songSetKey = databasePrimaryKey;
		songSetName = (String) SongSet.DB_DRIVER.querySongSet("name", databasePrimaryKey);
	}

	/**
	 * Get all the songs from the Song Set. The Song objects are not inflated by
	 * default.
	 * 
	 * @return list containing all the songs. List is not modifiable.
	 */
	public abstract List<Song> getSongs();

	/**
	 * Saves the Song Set to the database A name is automatically chosen if not
	 * given.
	 */
	public abstract void save();

	/**
	 * Set the SongSet name
	 * 
	 * @param songSetName the new name of the Set
	 * @return true if the song was not in the set and was successfully added
	 */
	public boolean setSongSetName(String songSetName) {
		if (songSetKey == 0 || SongSet.DB_DRIVER.updateSongSet("name", songSetName, songSetKey)) {
			this.songSetName = songSetName;
			return true;
		} else {
			logger.error("Failed to set song name to %s", songSetName);
			return false;
		}
	}

	/**
	 * Delete the SongSet from the database
	 * 
	 * @return true on success
	 */
	public boolean deleteSongSet() {
		if (songSetKey == 0 || SongSet.DB_DRIVER.removeSongSet(songSetKey)) {
			// Mark this song as having been deleted
			songSetKey = -1;
			return true;
		} else {
			logger.error("Failed to delete song set: %ld", songSetKey);
			return false;
		}
	}

	/**
	 * Get the name of the SongSet
	 * 
	 * @return
	 */
	public String getSongSetName() {
		return songSetName;
	}

	/**
	 * Get whether this SongSet has been deleted
	 * 
	 * @return
	 */
	public boolean isDeleted() {
		return (songSetKey < 0);
	}

}
