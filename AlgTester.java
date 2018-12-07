package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logic.AlgHelperFunctions;

/*Devon Subel is in charge of this test suit*/
/*Please refer to AlgHelperFunctions.java for functions not Alg.java*/
public class AlgTester {

	@Test
	public void yearTest() {
		assertEquals(AlgHelperFunctions.compSongYear(1980,1980),5);
		assertEquals(AlgHelperFunctions.compSongYear(1980,1981),4);
		assertEquals(AlgHelperFunctions.compSongYear(1981,1980),4);
		assertEquals(AlgHelperFunctions.compSongYear(1980,1982),3);
		assertEquals(AlgHelperFunctions.compSongYear(1982,1980),3);
		assertEquals(AlgHelperFunctions.compSongYear(1980,1983),2);
		assertEquals(AlgHelperFunctions.compSongYear(1983,1980),2);
		assertEquals(AlgHelperFunctions.compSongYear(1980,1984),1);
		assertEquals(AlgHelperFunctions.compSongYear(1984,1980),1);
		assertEquals(AlgHelperFunctions.compSongYear(1980,1985),0);
		assertEquals(AlgHelperFunctions.compSongYear(1985,1980),0);
	}
	
	@Test
	public void BPMTest() {
		/*Equals*/
		assertEquals(AlgHelperFunctions.compBPM(150,150),10);
		
		/* 0 < diff <= 5 */
		assertEquals(AlgHelperFunctions.compBPM(150,145),8);
		assertEquals(AlgHelperFunctions.compBPM(150, 148),8);
		assertEquals(AlgHelperFunctions.compBPM(145,150),8);
		
		/* 5 < diff <= 10 */
		assertEquals(AlgHelperFunctions.compBPM(150,140),6);
		assertEquals(AlgHelperFunctions.compBPM(150,142),6);
		assertEquals(AlgHelperFunctions.compBPM(140,150),6);
		
		/* 10 < diff <= 15 */
		assertEquals(AlgHelperFunctions.compBPM(150,135),4);
		assertEquals(AlgHelperFunctions.compBPM(150,137),4);
		assertEquals(AlgHelperFunctions.compBPM(135,150),4);
		
		/* 15 < diff <= 20 */
		assertEquals(AlgHelperFunctions.compBPM(150,130),2);
		assertEquals(AlgHelperFunctions.compBPM(150,132),2);
		assertEquals(AlgHelperFunctions.compBPM(130,150),2);
		
		/* diff > 20*/
		assertEquals(AlgHelperFunctions.compBPM(150,120),0);
	}
	
	@Test
	public void compSongLengthTest() {
		assertEquals(AlgHelperFunctions.compSongLength(200,200),2);
		assertEquals(AlgHelperFunctions.compSongLength(100,90),1);
		assertEquals(AlgHelperFunctions.compSongLength(90,100),1);
		assertEquals(AlgHelperFunctions.compSongLength(80,100),1);
		assertEquals(AlgHelperFunctions.compSongLength(100,80),1);
		assertEquals(AlgHelperFunctions.compSongLength(70,100),0);
		assertEquals(AlgHelperFunctions.compSongLength(100,70),0);
	}
	
	@Test
	public void compKeyTest() {
		assertEquals(AlgHelperFunctions.compKey("C","Dm"),16);
		assertEquals(AlgHelperFunctions.compKey("B#m","B#m"),18);
	}
	
	@Test
	public void compGenreTest() {
		assertEquals(AlgHelperFunctions.compGenre("Rock","Metal"), AlgHelperFunctions.compGenre("Metal","Rock"));
		assertEquals(AlgHelperFunctions.compGenre("Rock","Metal"),13);
	}

}
