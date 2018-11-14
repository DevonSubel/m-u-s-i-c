package com.github.se307;

import java.util.List;
import java.util.logging.Logger;

public abstract class SongSet {
	
	protected static final SongSetDatabaseDriver DB_DRIVER = SongSetDatabaseDriver.getInstance();
	protected static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/*
	 * SongSet pk: 
	 * > 0 is a PK to a set in the database
	 * 0 is the default PK assigned when a set is not stored to the db
	 * < 0 is the PK assigned to a set that has been deleted (or should no longer be available)
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
	 * @param songSet		the key of the entry into the database table for SongSets
	 */
	public SongSet(long databasePrimaryKey) {
		songSetKey = databasePrimaryKey;
		songSetName = (String)SongSet.DB_DRIVER.querySongSet("name", databasePrimaryKey);
	}
	
	
	/**
	 * Get all the songs from the Song Set
	 * 
	 * If the list is not already loaded, this will hit the database and create the Song objects.
	 * @return 	list containing all the songs
	 */
	public abstract List<Song> getSongs();
	

	
	/**
	 * Saves the Song Set to the database
	 * A name is automatically chosen if not given.
	 * TODO: consider requiring user to input the song set title
	 */
	public abstract void save();
	
	
	/**
	 * Not implemented, but ideas on what song sets could contain
	 */
	// public void reverse();
	// public void sortBy();
		
}
