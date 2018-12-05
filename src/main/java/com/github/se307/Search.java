package com.github.se307;

import java.util.PriorityQueue;
import java.util.List;

public class Search {
   /** 
     * The following methods will make compKey() easier to follow
     */
   private int compC(String key2) {
      if(key2.equals("Dm"))
         return 16;
      else if(key2.equals("Em"))
         return 14;
      else if(key2.equals("F"))
         return 12;
      else if(key2.equals("G"))
         return 10;
      else if(key2.equals("Am"))
         return 8;
      return 0;
   }

   private int compG(String key2) {
      if(key2.equals("Am"))
         return 16;
      else if(key2.equals("Bm"))
         return 14;
      else if(key2.equals("C"))
         return 12;
      else if(key2.equals("D"))
         return 10;
      else if(key2.equals("Em"))
         return 8;
      return 0;
   }

   private int compD(String key2) {
      if(key2.equals("Em"))
         return 16;
      else if(key2.equals("F#m"))
         return 14;
      else if(key2.equals("G"))
         return 12;
      else if(key2.equals("A"))
         return 10;
      else if(key2.equals("Bm"))
         return 8;   
      return 0;
   }

   private int compA(String key2) {
      if(key2.equals("Bm"))
         return 16;
      else if(key2.equals("C#m"))
         return 14;
      else if(key2.equals("D"))
         return 12;
      else if(key2.equals("E"))
         return 10;
      else if(key2.equals("F#m"))
         return 8;
      return 0;
   }

   private int compE(String key2) {
      if(key2.equals("F#m"))
         return 16;
      else if(key2.equals("G#m"))
         return 14;
      else if(key2.equals("A"))
         return 12;
      else if(key2.equals("B"))
         return 10;
      else if(key2.equals("C#m"))
         return 8;
      return 0;
   }

   private int compFS(String key2) {
      if(key2.equals("G#m"))
         return 16;
      else if(key2.equals("A#m"))
         return 14;
      else if(key2.equals("B"))
         return 12;
      else if(key2.equals("C#"))
         return 10;
      else if(key2.equals("D#m"))
         return 8;
      return 0;
   }

   private int compGb(String key2) {
      if(key2.equals("Ab"))
         return 16;
      else if(key2.equals("Bb"))
         return 14;
      else if(key2.equals("Cb"))
         return 12;
      else if(key2.equals("Db"))
         return 10;
      else if(key2.equals("Eb"))
         return 8;
      return 0;
   }

   private int compCs(String key2) {
      if(key2.equals("D#m"))
         return 16;
      else if(key2.equals("E#m"))
         return 14;
      else if(key2.equals("F#"))
         return 12;
      else if(key2.equals("G#"))
         return 10;
      else if(key2.equals("A#m"))
         return 8;
      return 0;
   }

   private int compDb(String key2) {
      if(key2.equals("Eb"))
         return 16;
      else if(key2.equals("f"))
         return 14;
      else if(key2.equals("Gb"))
         return 12;
      else if(key2.equals("Ab"))
         return 10;
      else if(key2.equals("Bb"))
         return 8;
      return 0;
   }

   private int compAb(String key2) {
      if(key2.equals("B#m"))
         return 16;
      else if(key2.equals("Cm"))
         return 14;
      else if(key2.equals("Db"))
         return 12;
      else if(key2.equals("Eb"))
         return 10;
      else if(key2.equals("Fm"))
         return 8;
      return 0;
   }

   private int compEb(String key2) {
      if(key2.equals("Fm"))
         return 16;
      else if(key2.equals("Gm"))
         return 14;
      else if(key2.equals("Ab"))
         return 12;
      else if(key2.equals("Bb"))
         return 10;
      else if(key2.equals("Cm"))
         return 8;
      return 0;
   }

   private int compF(String key2) {
      if(key2.equals("Gm"))
         return 16;
      else if(key2.equals("Am"))
         return 14;
      else if(key2.equals("Bb"))
         return 12;
      else if(key2.equals("C"))
         return 10;
      else if(key2.equals("Dm"))
         return 8;
      return 0;
   }

   private int compAm(String key2) {
      if(key2.equals("C"))
         return 16;
      else if(key2.equals("Dm"))
         return 14;
      else if(key2.equals("Em"))
         return 12;
      else if(key2.equals("F"))
         return 10;
      else if(key2.equals("G"))
         return 8;
      return 0;
   }

   private int compEm(String key2) {
      if(key2.equals("G"))
         return 16;
      else if(key2.equals("Am"))
         return 14;
      else if(key2.equals("Bm"))
         return 12;
      else if(key2.equals("C"))
         return 10;
      else if(key2.equals("D"))
         return 8;
      return 0;
   }

   private int compBm(String key2) {
      if(key2.equals("D"))
         return 16;
      else if(key2.equals("Em"))
         return 14;
      else if(key2.equals("F#"))
         return 12;
      else if(key2.equals("G"))
         return 10;
      else if(key2.equals("A"))
         return 8;
      return 0;
   }
 
   private int compFs(String key2) {
      if(key2.equals("A"))
         return 16;
      else if(key2.equals("Bm"))
         return 14;
      else if(key2.equals("C#m"))
         return 12;
      else if(key2.equals("D"))
         return 10;
      else if(key2.equals("E"))
         return 8;
      return 0;

   }

   private int compCsm(String key2) {
      if(key2.equals("E"))
         return 16;
      else if(key2.equals("F#m"))
         return 14;
      else if(key2.equals("G#m"))
         return 12;
      else if(key2.equals("A"))
         return 10;
      else if(key2.equals("B"))
         return 8;
      return 0;
   }

   private int compGsm(String key2) {
      if(key2.equals("B"))
         return 16;
      else if(key2.equals("C#m"))
         return 14;
      else if(key2.equals("D#m"))
         return 12;
      else if(key2.equals("E"))
         return 10;
      else if(key2.equals("F#"))
         return 8;
      return 0;
   }

   private int compAbm(String key2) {
      if(key2.equals("Cb"))
         return 16;
      else if(key2.equals("Dbm"))
         return 14;
      else if(key2.equals("Ebm"))
         return 12;
      else if(key2.equals("Fb"))
         return 10;
      else if(key2.equals("Gb"))
         return 8;
      return 0;
   }

   private int compDsm(String key2) {
      if(key2.equals("F#"))
         return 16;
      else if(key2.equals("G#m"))
         return 14;
      else if(key2.equals("A#m"))
         return 12;
      else if(key2.equals("B"))
         return 10;
      else if(key2.equals("C#"))
         return 8;
      return 0;
   }

   private int compEbm(String key2) {
      if(key2.equals("Gb"))
         return 16;
      else if(key2.equals("Abm"))
         return 14;
      else if(key2.equals("Bbm"))
         return 12;
      else if(key2.equals("Cb"))
         return 10;
      else if(key2.equals("Db"))
         return 8;
      return 0;
   }

   private int compAsm(String key2) {
      if(key2.equals("C#"))
         return 16;
      else if(key2.equals("D#m"))
         return 14;
      else if(key2.equals("E#m"))
         return 12;
      else if(key2.equals("F#"))
         return 10;
      else if(key2.equals("G#"))
         return 8; 
      return 0;  
   }
   
   private int compBbm(String key2) {
      if(key2.equals("Db"))
          return 16;
      else if(key2.equals("Ebm"))
         return 14;
      else if(key2.equals("Fm"))
         return 12;
      else if(key2.equals("Gb"))
         return 10;
      else if(key2.equals("Ab"))
         return 8;
      return 0;
   }
   
   private int compFm(String key2) {
      if(key2.equals("Ab"))
         return 16;
      else if(key2.equals("Bbm"))
         return 14;
      else if(key2.equals("Cb"))
         return 12;
      else if(key2.equals("Db"))
         return 10;
      else if(key2.equals("Eb"))
         return 8; 
      return 0;
   }

   private int compCb(String key2) {
      if(key2.equals("Eb"))
         return 16;
      else if(key2.equals("Fm"))
         return 14;
      else if(key2.equals("Gm"))
         return 12;
      else if(key2.equals("Ab"))
         return 10;
      else if(key2.equals("Bb"))
         return 8; 
      return 0;
   }
   
   private int compGm(String key2) {
      if(key2.equals("Bb"))
        return 16;
      else if(key2.equals("Cm"))
        return 14;
      else if(key2.equals("Dm"))
        return 12;
      else if(key2.equals("Eb"))
        return 10;
      else if(key2.equals("F"))
        return 8; 
      return 0;
  }
   
  private int compDm(String key2) {
     if(key2.equals("F"))
       return 16;
     else if(key2.equals("Gm"))
       return 14;
     else if(key2.equals("Am"))
       return 12;
     else if(key2.equals("Bb"))
       return 10;
     else if(key2.equals("C"))
       return 8; 
     return 0;
  }

   /**
    * Compares how similar the key is (MAX ADDITION: 18) 
    */
   private int compKey(String key1, String key2) {
      int ret = 0;
      if(key1.equals(key2))
         return 18;
      if(key1.equals("C")) {
         ret = compC(key2);
      }
      else if(key1.equals("G")) {
         ret = compG(key2);
      }
      else if(key1.equals("D")) {
         ret = compD(key2);
      }
      else if(key1.equals("A")) {
         ret = compA(key2);
      }
      else if(key1.equals("E")) {
         ret = compE(key2);
      }
      else if(key1.equals("F#")) {
         ret = compFS(key2);    
      } 
      else if(key1.equals("Gb")) {
         ret = compGb(key2);
      }
      else if(key1.equals("C#")) {
         ret = compCs(key2);
      }
      else if(key1.equals("Db")) {
         ret = compDb(key2);
      }
      else if(key1.equals("Ab")) {
         ret = compAb(key2);
      }
      else if(key1.equals("Eb")) {
         ret = compEb(key2);
      }
      else if(key1.equals("F")) {
         ret = compF(key2);
      }
      else if(key1.equals("Am")) {
         ret = compAm(key2);
      }
      else if(key1.equals("Em")) {
         ret = compEm(key2);
      }
      else if(key1.equals("Bm")) {
         ret = compBm(key2);
      }
      else if(key1.equals("F#")) {
         ret = compFs(key2);
      }
      else if(key1.equals("C#m")) {
         ret = compCsm(key2);
      }
      else if(key1.equals("G#m")) {
         ret = compGsm(key2);
      }
      else if(key1.equals("Abm")) {
         ret = compAbm(key2);
      }
      else if(key1.equals("D#m")) {
         ret = compDsm(key2);
      }
      else if(key1.equals("Ebm")) {
         ret = compEbm(key2);
      }
      else if(key1.equals("A#m")) {
         ret = compAsm(key2);
      }
      else if(key1.equals("Bbm")) {
         ret = compBbm(key2);
      }
      else if(key1.equals("Fm")) {
         ret = compFm(key2);
      }
      else if(key1.equals("Cb")) {
         ret = compCb(key2);
      }
      else if(key1.equals("Gm")) {
         ret = compGm(key2);
      }
      else if(key1.equals("Dm")) {
    	  ret = compDm(key2);
      }
      else if(key1.equals("Am")) {
    	  ret = compAm(key2);
      }
      return ret;
   }

   /**
     * Compares how similar the song year is (MAX ADDITION: 5) 
     */
   private int compSongYear(int year1, int year2) {
      int diff = Math.abs(year1-year2);
      switch(1){
         case 1: diff = 0;
            return 5;
         case 2: diff = 1;
            return 4;
         case 3: diff = 2;
            return 3;
         case 4: diff = 3;
            return 2;
         case 5: diff = 4;
            return 1;
      }
      return 0;
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
   private int compGenre(String g1, String g2) {
      if(g1.equals(g2))
         return 20;
      if(g1.equals("Blues")) {
         if(g2.equals("Country"))
            return 10;
         else if(g2.equals("Folk"))
            return 15;
         else if(g2.equals("Jazz"))
            return 17;
         else if(g2.equals("Reggae"))
            return 10;
         else if(g2.equals("Rock"))
            return 13;
      }
      else if(g1.equals("Country")) {
         if(g2.equals("Blues"))
            return 10;
         else if(g2.equals("Folk"))
            return 12;
         else if(g2.equals("Pop"))
            return 8;
         else if(g2.equals("Rock"))
            return 10;
      }
      else if(g1.equals("Electronic")) {
         if(g2.equals("Rap"))
            return 10;
         else if(g2.equals("Reggae"))
            return 8;
         else if(g2.equals("Metal"))
            return 10;
      }
      else if(g1.equals("Folk")) {
         if(g2.equals("Blues"))
            return 15;
         else if(g2.equals("Country"))
            return 12;
         else if(g2.equals("Rock"))
            return 10;
      }
      else if(g1.equals("Jazz")) {
         if(g2.equals("Blues"))
            return 17;
         else if(g2.equals("Rock"))
            return 12;
         else if(g2.equals("Rap"))
            return 15;
      }
      else if(g1.equals("Latin")) {
         if(g2.equals("World"))
            return 12;
         else if(g2.equals("Jazz"))
            return 10;
         else if(g2.equals("Pop"))
            return 10;
         else if(g2.equals("Rap"))
            return 13;
         else if(g2.equals("RnB"))
            return 10;
      }
      else if(g1.equals("Metal")) {
         if(g2.equals("Electronic"))
            return 10;
         else if(g2.equals("Punk"))
            return 15;
         else if(g2.equals("Rock"))
            return 13;
      }
      else if(g1.equals("Pop")) {
         if(g2.equals("Country"))
            return 8;
         else if(g2.equals("Latin"))
            return 10;
         else if(g2.equals("Rap"))
            return 12;
         else if(g2.equals("RnB"))
            return 15;
         else if(g2.equals("World"))
            return 10;
      }
      else if(g1.equals("Punk")) {
         if(g2.equals("Metal"))
            return 15;
         else if(g2.equals("Rock"))
            return 15;
      }
      else if(g1.equals("Rap")) {
         if(g2.equals("Electronic"))
            return 10;
         else if(g2.equals("Jazz"))
            return 15;
         else if(g2.equals("Latin"))
            return 13;
         else if(g2.equals("RnB"))
            return 15;
      }
      else if(g1.equals("Reggae")) {
         if(g2.equals("Blues"))
            return 10;
         else if(g2.equals("Electronic"))
            return 8;
         else if(g2.equals("Rock"))
            return 12;
      }
      else if(g1.equals("RnB")) {
         if(g2.equals("Pop"))
            return 15;
         else if(g2.equals("Rap"))
            return 15;
         else if(g2.equals("Latin"))
            return 10;
         else if(g2.equals("World"))
            return 12;
     }
     else if(g1.equals("Rock")) {
        if(g2.equals("Country"))
           return 10;
        else if(g2.equals("Folk"))
           return 10;
        else if(g2.equals("Jazz"))
           return 12;
        else if(g2.equals("Metal"))
           return 13;
        else if(g2.equals("Punk"))
           return 15;
        else if(g2.equals("Blues"))
           return 13;
     }
     else if(g1.equals("World")) {
        if(g2.equals("Latin"))
           return 13;
        else if(g2.equals("Pop"))
           return 10;
        else if(g2.equals("Country"))
           return 10;
        else if(g2.equals("RnB"))
           return 12;
     }
     return 0;
   }
   
   private String idToGenre(int id) {
	   if(id == 1)
		   return "Blues";
	   else if(id == 2)
		   return "Country";
	   else if(id == 3)
		   return "Folk";
	   else if(id == 4)
		   return "Jazz";
	   else if(id == 5)
		   return "Reggae";
	   else if(id == 6)
		   return "Rock";
	   else if(id == 7)
		   return "Pop";
	   else if(id == 8)
		   return "Electronic";
	   else if(id == 9)
		   return "Rap";
	   else if(id == 10)
		   return "Metal";
	   else if(id == 11)
		   return "Latin";
	   else if(id == 12)
		   return "World";
	   else if(id == 13)
		   return "RnB";
	   else if(id == 14)
		   return "Punk";
	   return "Not Known";
   }
   
   private String idToKey(Integer key, int modeID) {
	   String mode;
	   if(modeID == 0)
		   mode = "Minor";
	   else 
		   mode = "Major";
	   if(key == 0 && mode.equals("Major"))
		   return "C";
	   else if(key == 0 && mode.equals("Minor"))
		   return "Am";
	   else if(key == 1 && mode.equals("Major"))
		   return "G";
	   else if(key == 1 && mode.equals("Minor"))
		   return "Em";
	   else if(key == 2 && mode.equals("Major"))
		   return "D";
	   else if(key == 2 && mode.equals("Minor"))
		   return "Bb";
	   else if(key == 3 && mode.equals("Major"))
		   return "A";
	   else if(key == 3 && mode.equals("Minor"))
		   return "Fsm";
	   else if(key == 4 && mode.equals("Major"))
		   return "E";
	   else if(key == 4 && mode.equals("Minor"))
		   return "Csm";
	   else if(key == 5 && mode.equals("Major"))
		   return "B";
	   else if(key == 5 && mode.equals("Minor"))
		   return "Gsm";
	   else if(key == 6 && mode.equals("Major"))
		   return "Gb";
	   else if(key == 6 && mode.equals("Minor"))
		   return "Dsm";
	   else if(key == 7 && mode.equals("Major"))
		   return "Db";
	   else if(key == 7 && mode.equals("Minor"))
		   return "Asm";
	   else if(key == 8 && mode.equals("Major"))
		   return "Ab";
	   else if(key == 8 && mode.equals("Minor"))
		   return "Fm";
	   else if(key == 9 && mode.equals("Major"))
		   return "Eb";
	   else if(key == 9 && mode.equals("Minor"))
		   return "Cm";
	   else if(key == 10 && mode.equals("Major"))
		   return "Bb";
	   else if(key == 10 && mode.equals("Minor"))
		   return "Gm";
	   else if(key == 11 && mode.equals("Major"))
		   return "F";
	   else if(key == 11 && mode.equals("Minor"))
		   return "Db";
	   return "Unkown key";
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
         if(tags[0]) 
            sw.comp += compKey(idToKey(test.getSongKey(),test.getSongKeyMode()), idToKey(sw.s.getSongKey(),sw.s.getSongKeyMode()));
         if(test.getArtistName().equals(sw.s.getArtistName()) && tags[1]) /*Comparing artist name (ADDITION IS 25)*/
            sw.comp += 25;
         if(test.getAlbumName().equals(sw.s.getAlbumName()) && tags[2]) /*Comparing Album name (ADDITION IS 30)*/
            sw.comp += 30;
         if(test.getSongKeyMode().equals(sw.s.getSongKeyMode()) && tags[3]) 
            sw.comp += 15;
         if(tags[4])
            sw.comp += compSongLength(test.getSongLength(), sw.s.getSongLength()); /*Comapring song length*/
         if(tags[5])
            sw.comp += compGenre(idToGenre(test.getGenreId()),idToGenre(sw.s.getGenreId())); 
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
