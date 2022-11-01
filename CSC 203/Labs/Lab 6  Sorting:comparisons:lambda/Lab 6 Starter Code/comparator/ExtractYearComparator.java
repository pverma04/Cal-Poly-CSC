import java.util.Comparator;

public class ExtractYearComparator implements Comparator<Song> {

    @Override
    public Comparator<Song> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public int compare(Song s1, Song s2) {
        return s1.getYear() - s2.getYear();
    }
}
