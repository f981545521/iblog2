package design.adapterpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-08 下午 05:23]
 **/
public class VlcPlayer implements AdvancedMediaPlayer{
    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file. Name: "+ fileName);
    }

    @Override
    public void playMp4(String fileName) {
        //什么也不做
    }
}
