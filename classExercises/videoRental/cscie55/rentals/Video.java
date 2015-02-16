package cscie55.rentals;

/**
 * Created by Isaac on 2/15/2015.
 */
public class Video implements Rentable{
    enum AVAILABILITY {AVAILABLE,UNAVAILABLE,OUT_OF_STOCK};
    private static int count;
    private final int year;
    private final String title;
    private AVAILABILITY available = AVAILABILITY.AVAILABLE;

    public String toString() {
        return title + ", " + year + ". " + available;
    }

    public Video(String title, int year) {
        this.title = title;
        this.year = year;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }
}
