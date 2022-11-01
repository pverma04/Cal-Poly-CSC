import java.util.*;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator() {
      Arrays.sort(songs, new ArtistComparator());
      final Song[] expected = new Song[] {
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
      };
      assertArrayEquals(expected, songs);
   }

   @Test
   public void testLambdaTitleComparator() {
      Comparator<Song> titleComparatorLambda = (Song s1, Song s2) -> s1.getTitle().compareTo(s2.getTitle());
      Arrays.sort(songs, titleComparatorLambda);
      final Song[] expected = new Song[] {
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005)
      };
      assertArrayEquals(expected, songs);
   }

   @Test
   public void testYearExtractorComparator() {
      Comparator<Song> yearExtractorComparator = Comparator.comparingInt(Song::getYear);
      Arrays.sort(songs, yearExtractorComparator.reversed());
      final Song[] expected = new Song[] {
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975)
      };
      assertArrayEquals(expected, songs);
   }

   @Test
   public void testComposedComparator() {
      
   }

   @Test
   public void testThenComparing() {
      Comparator<Song> titleComparator = Comparator.comparing(Song::getTitle);
      Comparator<Song> artistThenComparator = titleComparator.thenComparing(Comparator.comparing(Song::getArtist));
      Arrays.sort(songs, artistThenComparator);
      final Song[] expected = new Song[] {
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005)
      };
      assertArrayEquals(expected, songs);
   }

//   @Test
//   public void runSort() {
//      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
//      List<Song> expectedList = Arrays.asList(
//         new Song("Avett Brothers", "Talk on Indolence", 2006),
//         new Song("City and Colour", "Sleeping Sickness", 2007),
//         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
//         new Song("Foo Fighters", "Baker Street", 1997),
//         new Song("Gerry Rafferty", "Baker Street", 1978),
//         new Song("Gerry Rafferty", "Baker Street", 1998),
//         new Song("Queen", "Bohemian Rhapsody", 1975),
//         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
//         );
//
////      songList.sort(
////         // pass comparator here
////      );
//
//      assertEquals(songList, expectedList);
//   }
}
