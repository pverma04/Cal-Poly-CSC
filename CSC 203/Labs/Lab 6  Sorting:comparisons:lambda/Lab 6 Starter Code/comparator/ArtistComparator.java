import java.util.Comparator;

public class ArtistComparator implements Comparator<Song> {



    @Override
    public int compare(Song o1, Song o2) {
        if (o1.getArtist().compareTo(o2.getArtist()) < 0) {
            return -1;
        } else if(o1.getArtist().compareTo(o2.getArtist()) > 0) {
            return 1;
        }
        return 0;
    }
}
