package design.adapterpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-08 下午 05:23]
 **/
public class Mp4Player implements AdvancedMediaPlayer{

    @Override
    public void playVlc(String fileName) {
        //什么也不做
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file. Name: "+ fileName);
    }
}