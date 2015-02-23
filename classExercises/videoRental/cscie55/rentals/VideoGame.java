package cscie55.rentals;

/**
 * Created by Isaac on 2/15/2015.
 */
public class VideoGame implements Rentable {
    enum PLATFORM {XBOX360,PS,PSONE};
    private PLATFORM platform = PLATFORM.XBOX360;
    private String title;

    public String getTitle(){
        return title + ":" + platform;
    }

    public int getYear(){
        int returnValue;
        switch (platform){
            case XBOX360:
                returnValue = 2003;
                break;
            case PS:
            case PSONE:
                returnValue = 2000;
            default:
                returnValue = 1900;
        }
    }
}
