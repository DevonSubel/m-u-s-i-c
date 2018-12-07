package com.github.se307;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Maxence Weyrich
 *
 */
public class SongTest {

	@Test
	public void testCreateNewSong() {
		Song.SongBuilder sb = new Song.SongBuilder();
		Song s = sb.setSongName("Dye")
				   .setArtistName("Tycho")
				   .setAlbumName("Awake")
				   .setSongLength(126)
				   .setGenreID(0)
				   .setSongYear(2012)
				   .setBPM(null)
				   .setNotes("This is the song used in all the tests")
				   .setURL(null)
				   .build();
		
		long songKey = s.getId();
		
		Song o = new Song(songKey);
		
		assertTrue(s.getSongName().equals(o.getSongName()) && 
				   s.getArtistName().equals(o.getArtistName()) &&
				   s.getAlbumName().equals(o.getAlbumName()) &&
				   s.getSongLength().equals(o.getSongLength()) &&
				   s.getGenreId().equals(o.getGenreId()) &&
				   s.getSongYear().equals(o.getSongYear()) &&
				   s.getBpm() == o.getBpm() &&
				   s.getAdditionalNotes().equals(o.getAdditionalNotes()) &&
				   s.getSongUrl() == o.getSongUrl());
		
		s.deleteSong();
		
	}
	
	@Test
	public void testChangeSongFields() {
		Song.SongBuilder sb = new Song.SongBuilder();
		Song s1 = sb.setSongName("Dye")
				    .setArtistName("Tycho")
				    .setAlbumName("Awake")
				    .setSongLength(126)
				    .setGenreID(0)
				    .setSongYear(2012)
				    .setBPM(null)
				    .setNotes("This is the song used in all the tests")
				    .setURL(null)
				    .build();
		
		s1.setSongName("Awake");
		s1.setSongYear(2014);
		s1.setAdditionalNotes(null);
		
		Song other = new Song(s1.getId());
		
		assertTrue(other.getSongName().equals("Awake") && 
				   other.getArtistName().equals("Tycho") &&
				   other.getAlbumName().equals("Awake") &&
				   other.getSongLength().equals(126) &&
				   other.getGenreId().equals(0) &&
				   other.getSongYear().equals(2014) &&
				   other.getBpm() == null &&
				   other.getAdditionalNotes() == null &&
				   other.getSongUrl() == null);
		
		s1.deleteSong();
	}

	@Test
	public void testChangeSongFieldsTwo() {
		Song.SongBuilder sb = new Song.SongBuilder();
		Song s1 = sb.setSongName("Dye")
				    .setArtistName("Tycho")
				    .setAlbumName("Awake")
				    .setSongLength(126)
				    .setGenreID(0)
				    .setSongYear(2012)
				    .setBPM(null)
				    .setNotes("This is the song used in all the tests")
				    .setURL(null)
				    .build();
		
		s1.setSongName("Abrasive");
		s1.setArtistName("Ratatat");
		s1.setAlbumName("Magnifique");
		s1.setSongLength(243);
		s1.setGenreList(1);
		s1.setSongYear(2015);
		s1.setBpm(120);
		s1.setAdditionalNotes("these have changed");
		s1.setSongMusicKey(3);
		s1.setSongMusicKeyMode(0);
		s1.setLiked(true);
		s1.setSongUrl("http://www.google.com");
		
		Song other = new Song(s1.getId());
		
		assertTrue(other.getSongName().equals("Abrasive") && 
				   other.getArtistName().equals("Ratatat") &&
				   other.getAlbumName().equals("Magnifique") &&
				   other.getSongLength().equals(243) &&
				   other.getGenreId().equals(1) &&
				   other.getSongYear().equals(2015) &&
				   other.getBpm().equals(120) &&
				   other.getAdditionalNotes().equals("these have changed") &&
				   other.getSongUrl().equals("http://www.google.com") &&
				   other.getSongKey().equals(3) &&
				   other.getSongKeyMode().equals(0) &&
				   other.getLiked() == true);
		
		s1.deleteSong();
	}
	
}
