import java.util.PriorityQueue;
import java.lang.Integer;
import java.util.ArrayList;

public class Alg {
   /*Compares how similar the key is (MAX ADDITION: 18) */
   private int compKey(String key1, String key2) {
      if(key1.equals(key2))
         return 18;
      if(key1.equals("C")) {
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
      }
      else if(key1.equals("G")) {
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
      }
      else if(key1.equals("D")) {
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
      }
      else if(key1.equals("A")) {
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
      }
      else if(key1.equals("E")) {
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
      }
      else if(key1.equals("F#")) {
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
      } 
      else if(key1.equals("Gb")) {
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
      }
      else if(key1.equals("C#")) {
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
      }
      else if(key1.equals("Db")) {
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
      }
      else if(key1.equals("Ab")) {
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
      }
      else if(key1.equals("Eb")) {
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
      }
      else if(key1.equals("F")) {
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
      }
      else if(key1.equals("C")) {
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
      }
      else if(key1.equals("Am")) {
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
      }
      else if(key1.equals("Em")) {
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
      }
      else if(key1.equals("Bm")) {
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
      }
      else if(key1.equals("F#")) {
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
      }
      else if(key1.equals("C#m")) {
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
      }
      else if(key1.equals("G#m")) {
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
      }
      else if(key1.equals("Abm")) {
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
      }
      else if(key1.equals("D#m")) {
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
      }
      else if(key1.equals("Ebm")) {
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
      }
      else if(key1.equals("A#m")) {
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
      }
      else if(key1.equals("Bbm")) {
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
      }
      else if(key1.equals("Fm")) {
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
      }
      else if(key1.equals("Cb")) {
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
      }
      else if(key1.equals("Gm")) {
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
      }
      else if(key1.equals("Dm")) {
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
      }
      else if(key1.equals("Am")) {
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
      }
      return 0;
   }

   /*Compares how similar the song year is (MAX ADDITION: 5) */
   private int compSongYear(int year1, int year2) {
      int diff = Math.abs(year1-year2);
      if(diff == 0)
         return 5;
      else if(diff == 1)
         return 4;
      else if(diff == 2)
         return 3;
      else if(diff == 3)
         return 2;
      else if(diff == 4)
        return 1;
      return 0;
   }

   /*Compares how similar the song length is (MAX ADDITION: 2) */
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

   /*Compares how similar the BPM is (MAX ADDITION: 10) */
   private int compBPM(int bpm1, int bpm2) {
      int diff = Math.abs(bpm1-bpm2);
      if(diff == 0)
         return 10;
      else if(diff == 5) 
         return 8;
      else if(diff == 10)
         return 6;
      else if(diff == 15)
         return 4;
      else if(diff == 20)
         return 2;
      return 0;
   }

   /*Compares how similar each genre is (MAX ADDITION: 20) */
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

   public PriorityQueue<SongWrapper> matchingAlg(Song test, int[] tags) {
      PriorityQueue<SongWrapper> queue = new PriorityQueue<SongWrapper>(25);
      for(int i = 0; i < 1268; i++) {
         /*Set up other song*/
         Song other = new Song(i);
         other.inflate();
         SongWrapper sw = new SongWrapper(other);
         sw.s = other;

         /*Compairsons*/
         sw.comp += compKey(test.getKey(), other.getKey()); /*Comparing key*/
         if(test.getSongName().equals(other.getSongName())) /*Comapring song name (ADDITION IS 50)*/
            sw.comp += 50;
         if(test.getArtistName().equals(other.getArtistName())) /*Comparing artist name (ADDITION IS 25)*/
            sw.comp += 25;
         if(test.getAlbumName().equals(other.getAlbumName())) /*Comparing Album name (ADDITION IS 30)*/
            sw.comp += 30;
         if(test.getMode().equals(other.getMode())) /*Comparing mode (ADDITION IS 15)*/
            sw.comp += 15;
         sw.comp += compSongLenth(test.getSongLength(), other.getSongLength()); /*Comapring song length*/
         sw.comp += compGenre(test.getGenreList().get(0),other.getGenreList().get(0)); /*Comparing genre*/
         sw.comp += compSongYear(test.getSongYear(), other.getSongYear()); /*Compare song year*/
         sw.comp += comBPM(test.getBPM(), other.getBPM); /*Compare BPM*/

         /*Deal with Prioirty Queue*/
         if(queue.size() <= 25)
           queue.add(sw);
         else {
            queue.poll();
            queue.add(sw);
         } 
      }
      return queue;
   }
}

class SongWrapper implements Comparable<SongWrapper> {
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
