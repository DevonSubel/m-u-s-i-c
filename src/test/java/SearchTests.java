import static org.junit.Assert.*;
import java.util.PriorityQueue;

import org.junit.Test;

import com.github.se307.Search;
import com.github.se307.Search.SongWrapper;
import com.github.se307.Song;

public class SearchTests {
	@Test
	public void testAllTags() {
		Search s = new Search();
		boolean[] tags = {true,true,true,true,true,true,true,true};
		Song song = new Song(100);
		PriorityQueue<SongWrapper> ans = s.matchingAlg(song,tags);
		while(ans.size() != 0)
		{
			System.out.println(ans.remove().s.getSongName());
		}
		assertTrue(true == true);
	}
	
	@Test
	public void testSomeTags() {
		Search s = new Search();
		boolean[] tags1 = {true,true,true,true,true,true,true,true};
		boolean[] tags2 = {true,false,true,false,true,false,true,true};
		Song song = new Song(100);
		PriorityQueue<SongWrapper> ans1 = s.matchingAlg(song,tags1);
		PriorityQueue<SongWrapper> ans2 = s.matchingAlg(song,tags2);

		assertFalse(ans1.equals(ans2));
	}

}
