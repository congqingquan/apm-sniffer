package priv.cqq.app.simple;

import java.lang.reflect.Field;

/**
 * Created by QQ.Cong on 2023-09-22 / 14:49
 *
 * @Description
 */
public class SimpleTest {

    public static void main(String[] args) throws Exception {
        Executor executor = new Executor("p1", "p2");
//        executor.instanceMethod("arg1", "arg2");
//        executor.loggerInstanceMethod("arg1", "arg2");
//        Executor.staticMethod("arg1", "arg2");
//        Executor.loggerStaticMethod("arg1", "arg2");
        
        Field field = executor.getClass().getFields()[1];
        Object logInterceptor = field.get(executor);
        Field[] declaredFields = logInterceptor.getClass().getDeclaredFields();
        declaredFields[1].setAccessible(true);
        declaredFields[1].get(logInterceptor).getClass();
        System.out.println(declaredFields);
    }
}