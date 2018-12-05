package com.github.se307;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

public class Search {
   /**
     * Compares how similar the song year is (MAX ADDITION: 5) 
     */
   private int compSongYear(int year1, int year2) {
      int diff = Math.abs(year1-year2);
      switch(diff){
         case 0:
            return 5;
         case 1:
            return 4;
         case 2: 
            return 3;
         case 3: 
            return 2;
         case 4:
            return 1;
         default: 
        	 return 0;
      }
   }

   /**
     * Compares how similar the song length is (MAX ADDITION: 2) 
     */
   private int compSongLength(int len1, int len2) {
      float diff;

      if(len1 > len2)
         diff = len2/len1;
      else
         diff = len1/len2;

      if(diff == 1)
        return 2;
      if(diff >= .8)
        return 1;
      return 0;
   }

   /**
     * Compares how similar the BPM is (MAX ADDITION: 10) 
     */
   private int compBPM(int bpm1, int bpm2) {
      int diff = Math.abs(bpm1-bpm2);
      if(diff == 0)
    	  return 10;
      else if(diff <= 5)
    	  return 8;
      else if(diff <= 10)
    	  return 6;
      else if(diff <= 15)
    	  return 4;
      else if(diff <= 20)
    	  return 2;
      return 0;
   }

   /**
     * Compares how similar each genre is (MAX ADDITION: 20) 
     */
   private int compGenre(int g1, int g2) {
	 ArrayList<Integer> genres = new ArrayList<Integer>();

	 /*
	  * Adding genre IDs to the ArrayList. Definitions of the genre IDs can be found in the DB File
	  */
     genres.add(11);
     genres.add(14);
     genres.add(6);
     genres.add(5);
     genres.add(3);
     genres.add(1);
     genres.add(2);
     genres.add(4);
     genres.add(11);
     genres.add(12);
     genres.add(13);
     genres.add(9);
     genres.add(7);
     genres.add(8);
     return 12-(Math.abs(genres.indexOf(g1)-genres.indexOf(g2)));
   }
   
   /**
    * Will find the 25 most "similar" songs to the provided one based on song charactersitics
    *
    * NOTE: tag[] is a list of flags to see if the user wants those camparisons to be testedin the following format:
    * tag[0] = key
    * tag[1] = Artist Name
    * tag[2] = Album Name
    * tag[3] = Mode
    * tag[4] = Song Length
    * tag[5] = Song Genre
    * tag[6] = Song Year
    * tag[7] = Song BPM
    */
   public PriorityQueue<SongWrapper> matchingAlg(Song test, boolean[] tags) {
      PriorityQueue<SongWrapper> queue = new PriorityQueue<SongWrapper>(25);
      List<Long> songList = SongDatabaseDriver.getInstance().getAllSongs();
      for(int i = 0; i < songList.size(); i++) /*Always does the same number of steps*/
      {
         SongWrapper sw = new SongWrapper(new Song(songList.get(i)));
         if(tags[0] && test.getSongKey().equals(sw.s.getSongKey()) && sw.s.getSongKeyMode().equals(sw.s.getSongKeyMode()))
        	 sw.comp += 16;
         if(test.getArtistName().equals(sw.s.getArtistName()) && tags[1]) /*Comparing artist name (ADDITION IS 25)*/
            sw.comp += 25;
         if(test.getAlbumName().equals(sw.s.getAlbumName()) && tags[2]) /*Comparing Album name (ADDITION IS 30)*/
            sw.comp += 30;
         if(test.getSongKeyMode().equals(sw.s.getSongKeyMode()) && tags[3]) 
            sw.comp += 15;
         if(tags[4])
            sw.comp += compSongLength(test.getSongLength(), sw.s.getSongLength()); /*Comapring song length*/
         if(tags[5])
            sw.comp += compGenre(test.getGenreId(),sw.s.getGenreId()); 
         if(tags[6])
            sw.comp += compSongYear(test.getSongYear(), sw.s.getSongYear()); /*Compare song year*/
         if(tags[7])
           sw.comp += compBPM(test.getBpm(), sw.s.getBpm()); /*Compare BPM*/

         /*Deal with Priority Queue*/
         if(queue.size() <= 25)
           queue.add(sw);
         else {
            queue.poll();
            queue.add(sw);
         }
      }
      return queue;
   }

   private class SongWrapper implements Comparable<SongWrapper> {
   public Song s;
   public int comp;

   public SongWrapper(Song s) {
      this.s = s;
      comp = 0;
   }

   public int compareTo(SongWrapper other) {
      if(this.comp < other.comp)
         return 1;
      else if(this.comp > other.comp)
         return -1;
      else
         return 0;
      }
   }
}
