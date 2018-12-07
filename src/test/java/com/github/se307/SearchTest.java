package com.github.se307;

import static org.junit.Assert.*;


import java.util.ArrayList;
import org.junit.Test;
import com.github.se307.Search;
import com.github.se307.Song;

public class SearchTest {

	@Test
	public void testAllTags() {
		Search s = new Search();
		boolean[] tags = {true,true,true,true,true,true,true,true};
		Song song = new Song(100);
		ArrayList<Song> ans = s.matchingAlg(song,tags);


		assertTrue(ans.size() > 0);
	}
	
	@Test
	public void testSomeTags() {
		Search s = new Search();
		boolean[] tags1 = {true,true,true,true,true,true,true,true};
		boolean[] tags2 = {false,false,false,false,false,false,false};
		Song song = new Song(100);
		ArrayList<Song> ans1 = s.matchingAlg(song,tags1);
		ArrayList<Song> ans2 = s.matchingAlg(song,tags2);

		assertFalse(ans1.equals(ans2));
	}

}
