package design.templatepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 02:35]
 **/
public abstract class Game {
    abstract void initialize();
    abstract void startPlay();
    abstract void endPlay();

    //模板
    public final void play(){

        //初始化游戏
        initialize();

        //开始游戏
        startPlay();

        //结束游戏
        endPlay();
    }
}
