package priv.cqq.app.simple;

/**
 * Created by QQ.Cong on 2023-09-22 / 14:49
 *
 * @Description
 */
public class SimpleTest {

    /**
     *  -javaagent:D:\development\idea\workspace\personal\apm-sinffer\apm-agent\target\plugins-dist\apm-agent-1.0-SNAPSHOT-jar-with-dependencies.jar
     */

    public static void main(String[] args) {
        Executor executor = new Executor();
        executor.execute();
    }
}