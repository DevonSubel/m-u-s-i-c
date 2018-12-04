package com.github.se307;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class StaticSongSetTest {


	@Test
	/**
	 * Test that a new StaticSongSet is empty (has no songs by default)
	 */
	public void testEmptyNewSongSet() {
		StaticSongSet songSet = new StaticSongSet();
		
		List<Song> returnList = songSet.getSongs();
		
		assertTrue(returnList.isEmpty());
	}
	
	
	@Test
	/**
	 * Test that creating a song set with songs and loading them back 
	 * from the database correctly loads the Song objects back
	 */
	public void testSaveSongSetToDatabase() {
		Song[] list = { new Song(1), new Song(2), new Song(3) };
		
		// Create new song set
		StaticSongSet songSet = new StaticSongSet();
		
		for (Song s : list) {
			songSet.addSong(s);
		}
		// Save to the DB
		songSet.save();
		long newSongSetKey = songSet.getSongSetKey();
		
		// Create new song set with the key
		StaticSongSet secondSongSet = new StaticSongSet(newSongSetKey);
		
		// Get the songs
		List<Song> returnList = secondSongSet.getSongs();
		
		for(int i = 0; i < Math.max(returnList.size(), list.length); i++) {
			assertTrue(list[i].getId() == returnList.get(i).getId());
		}
		
		songSet.deleteSongSet();
	}
	
	@Test
	/**
	 * Test that creating a song set with no songs but with a name correctly loads back up
	 */
	public void testSaveSongSetToDatabaseNoSongs() {
		
		// Create new song set
		StaticSongSet songSet = new StaticSongSet();
		
		songSet.setSongSetName("My Great Playlist");
		// Save to the DB
		songSet.save();
		long newSongSetKey = songSet.getSongSetKey();
		
		// Create new song set with the key
		StaticSongSet secondSongSet = new StaticSongSet(newSongSetKey);
		
		// Get the songs
		assertEquals(songSet.getSongSetName(), secondSongSet.getSongSetName());
		
		songSet.deleteSongSet();
	}
	
	
	@Test
	/**
	 * Test that creating a song set with songs
	 * but then deleting them after committing to the database
	 * correctly updates the database
	 */
	public void testSaveSongSetToDatabaseRemovingSongs() {
		Song[] list = { new Song(1), new Song(2), new Song(3) };
		
		// Create new song set
		StaticSongSet songSet = new StaticSongSet();
		
		songSet.setSongSetName("My Great Playlist");
		for (Song s : list) {
			songSet.addSong(s);
		}
		
		// Save to the DB
		songSet.save();
		
		songSet.removeSong(new Song(1));
		songSet.removeSong(new Song(2));
		
		long newSongSetKey = songSet.getSongSetKey();
		
		// Create new song set with the key
		StaticSongSet secondSongSet = new StaticSongSet(newSongSetKey);
		
		// Get the songs
		List<Song> csongs = songSet.getSongs();
		List<Song> osongs = secondSongSet.getSongs();
		
		assertTrue(csongs.size() == osongs.size() && 
				   csongs.get(0).getId() == osongs.get(0).getId());
		
		songSet.deleteSongSet();
	}
	

}
