/**
 * 
 */
package com.github.se307;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Maxence Weyrich
 *
 */
public class DynamicSongSetTest {

	//private static long[] songKeys = new long[] { 1000l, 1001l, 1002l, 1003l, 1004l, 1005l };
	
	private static ArrayList<Song> songs;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DynamicSongSetTest.songs = new ArrayList<>();
		
		for (int l = 0; l < 5; l++) {
			
			Song.SongBuilder sb = new Song.SongBuilder();
			DynamicSongSetTest.songs.add(sb.setSongName("a song" + l)
										   .setAlbumName("" + l)
										   .setArtistName("An artist")
										   .build());
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		for (Song l : DynamicSongSetTest.songs) {
			l.deleteSong();
		}
	}

	@Test
	public void testDynamicSongSetSimple() {
		String query = "SELECT id FROM song";
		
		long[] expected = new long[DynamicSongSetTest.songs.size()];
		
		for(int i = 0; i < expected.length; i++) {
			expected[i] = DynamicSongSetTest.songs.get(i).getId();
		}
		
		DynamicSongSet ds = new DynamicSongSet(query);
		
		List<Song> ls = ds.getSongs();
		
		long[] testArr = new long[DynamicSongSetTest.songs.size()];
		
		for(int i = 0; i < testArr.length; i++) {
			testArr[i] = ls.get(i).getId();
		}
		
		Arrays.sort(testArr);
		Arrays.sort(expected);
		
		assertArrayEquals(expected, testArr);
	}
	
	
	@Test
	public void testDynamicSongSetMoreComplex() {
		String query = "SELECT sg.id FROM song AS sg WHERE sg.album_name = 1";
		long[] expected = new long[] { DynamicSongSetTest.songs.get(1).getId() };
		
		DynamicSongSet ds = new DynamicSongSet(query);
		
		List<Song> ls = ds.getSongs();
		
		long[] testArr = new long[expected.length];
		
		for(int i = 0; i < ls.size(); i++) {
			testArr[i] = ls.get(i).getId();
		}
		
		Arrays.sort(testArr);
		
		assertArrayEquals(expected, testArr);
	}

	
	@Test
	public void testDynamicSetToStatic() {
		String query = "SELECT sg.id FROM song AS sg WHERE sg.album_name = 1 OR sg.album_name = 2";
		long[] expected = new long[] { DynamicSongSetTest.songs.get(1).getId(), DynamicSongSetTest.songs.get(2).getId() };
		
		DynamicSongSet ds = new DynamicSongSet(query);
		
		StaticSongSet sss = ds.toStaticSongSet();
		
		List<Song> ls = sss.getSongs();
		
		long[] testArr = new long[expected.length];
		
		for(int i = 0; i < ls.size(); i++) {
			testArr[i] = ls.get(i).getId();
		}
		
		Arrays.sort(testArr);
		
		assertArrayEquals(expected, testArr);
	}

	
}
