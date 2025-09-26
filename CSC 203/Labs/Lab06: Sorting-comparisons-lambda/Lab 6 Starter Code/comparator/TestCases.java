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
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      Collections.sort(songList, new ArtistComparator());
      final List<Song> expected = Arrays.asList(
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
      );
      assertEquals(expected, songList);
   }

   @Test
   public void testLambdaTitleComparator() {
      Comparator<Song> titleComparatorLambda = (Song s1, Song s2) -> s1.getTitle().compareTo(s2.getTitle());
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      Collections.sort(songList, titleComparatorLambda);
      final List<Song> expected = Arrays.asList(
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005)
      );
      assertEquals(expected, songList);
   }

   @Test
   public void testYearExtractorComparator() {
      Comparator<Song> yearExtractorComparator = Comparator.comparingInt(Song::getYear);
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      Collections.sort(songList, yearExtractorComparator.reversed());
      final List<Song> expected = Arrays.asList(
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975)
      );
      assertEquals(expected, songList);
   }

   @Test
   public void testComposedComparator() {
      Comparator<Song> yearExtractorComparator = Comparator.comparingInt(Song::getYear);
      Comparator<Song> titleComparatorLambda = Comparator.comparing(Song::getTitle);
      ComposedComparator cC = new ComposedComparator(yearExtractorComparator, titleComparatorLambda);
      final List<Song> songList = Arrays.asList(
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Foo Fighters", "Baker Street1", 1997)
              );
      final List<Song> expected = Arrays.asList(
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Foo Fighters", "Baker Street1", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1998)
      );
      Collections.sort(songList, cC);`

      assertEquals(expected, songList);

   }

   @Test
   public void testThenComparing() {
      Comparator<Song> titleComparator = Comparator.comparing(Song::getTitle);
      Comparator<Song> artistThenComparator = titleComparator.thenComparing(Song::getArtist);
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      Collections.sort(songList, artistThenComparator);
      final List<Song> expected = Arrays.asList(
              new Song("Foo Fighters", "Baker Street", 1997),
              new Song("Gerry Rafferty", "Baker Street", 1998),
              new Song("Gerry Rafferty", "Baker Street", 1978),
              new Song("Queen", "Bohemian Rhapsody", 1975),
              new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
              new Song("City and Colour", "Sleeping Sickness", 2007),
              new Song("Avett Brothers", "Talk on Indolence", 2006),
              new Song("Decemberists", "The Mariner's Revenge Song", 2005));

      assertEquals(expected, songList);
   }

   @Test
   public void runSort() {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );
      Comparator<Song> artistThenComparator = Comparator.comparing(Song::getArtist);
      Comparator<Song> titleThenComparator = artistThenComparator.thenComparing(Song::getTitle);
      Comparator<Song> yearThenComparator = artistThenComparator.thenComparing(Song::getYear);


      songList.sort(yearThenComparator);

      assertEquals(songList, expectedList);
   }
}
