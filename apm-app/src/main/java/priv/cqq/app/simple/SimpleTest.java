package priv.cqq.app.simple;

/**
 * Created by QQ.Cong on 2023-09-22 / 14:49
 *
 * @Description
 */
public class SimpleTest {

    public static void main(String[] args) {
        Executor executor = new Executor("p1", "p2");
        executor.instanceMethod("arg1", "arg2");
        executor.loggerInstanceMethod("arg1", "arg2");
        Executor.staticMethod("arg1", "arg2");
        Executor.loggerStaticMethod("arg1", "arg2");
    }
}