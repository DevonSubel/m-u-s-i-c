//potential change == actual method to see if items are same
import java.util.PriorityQueue;
import java.lang.Integer;
import java.util.ArrayList;

public class alg
{
   public PriorityQueue<SongWrapper> matchingAlg(Song test, int[] tags)
   {
      Song minCompare;
      int maxCompares = -1;
      int compares = 0;
      int lowestComp = -1;
      int songsInList = 0;
      PriorityQueue<SongWrapper> queue = new PriorityQueue<SongWrapper>(25);
      for(int i = 0; i < 1268; i++)
      {
         Song other = new Song(i);
         other.inflate();
         SongWrapper sw = new SongWrapper(other);
         sw.s = other;
         if(test.getKey() == other.getKey())
            sw.comp++;
         if(test.getSongName().equals(other.getSongName()))
            sw.comp++;
         if(test.getArtistName().equals(other.getArtistName()))
            sw.comp++;
         if(test.getAlbumName().equals(other.getAlbumName()))
            sw.comp++;
         if(test.getSongLength() == other.getSongLength())
            sw.comp++;
         if(test.getGenreList().get(0).equals(other.getGenreList().get(0)))
            sw.comp++;
         if(test.getSongYear() == other.getSongYear())
            sw.comp++;
         if(test.getAdditionalNotes().equals(other.getAdditionalNotes()));
            sw.comp++;
         if(other.getSongLyrics().contains(test.getSongLyrics()))
            sw.comp++;
         if(queue.size() <= 25)
           queue.add(sw);
         else
         {
            queue.poll();
            queue.add(sw);
         } 
      }
      return queue;
   }
}

class SongWrapper implements Comparable<SongWrapper>
{
   public Song s;
   public int comp;

   public SongWrapper(Song s)
   {
      this.s = s;
      comp = 0;
   }

   public int compareTo(SongWrapper other)
   {
      if(this.comp < other.comp)
         return 1;
      else if(this.comp > other.comp)
         return -1;
      else
         return 0;
   }
}
