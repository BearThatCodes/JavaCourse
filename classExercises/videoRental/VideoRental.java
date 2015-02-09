import java.util.Random;

public class VideoRental{
	public static void main(String argv[]){
        Video video;
        Video video2;
        video = new Video("Citizen Kane",1941);
        video2 = new Video("Blargh",3848);
        System.out.println(video);

        Random ready = new Random();

        for(int i=0;i<10;i++){
            System.out.println(ready.nextInt());
        }

        System.out.println("Count: " + Video.getCount());
    }
}