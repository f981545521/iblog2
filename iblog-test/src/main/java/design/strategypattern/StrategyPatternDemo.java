package design.strategypattern;

/**
 * 在策略模式（Strategy Pattern）中，一个类的行为或其算法可以在运行时更改。这种类型的设计模式属于行为型模式。
 *
 * 意图：定义一系列的算法,把它们一个个封装起来, 并且使它们可相互替换。
 * 主要解决：在有多种算法相似的情况下，使用 if...else 所带来的复杂和难以维护。
 * 何时使用：一个系统有许多许多类，而区分它们的只是他们直接的行为。
 *
 * 如何解决：将这些算法封装成一个一个的类，任意地替换。
 * 关键代码：实现同一个接口。
 * 优点： 1、算法可以自由切换。 2、避免使用多重条件判断。 3、扩展性良好。
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 02:31]
 **/
public class StrategyPatternDemo {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationSubstract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
    }
}
