import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;


public class Test {

    public static void main(String[] args){

        Instant.now().toEpochMilli();
        test(10000, 10, JaMoEventQueue.class);

        System.out.print(test(10000, 10, JaMoEventQueue.class));
        System.out.print(" microseconds per Event in average");

    }

    public static long test(int elements, int iterations, Class queueType) {

        long runtime = 0;

        Constructor queueConstructor = null;
        try {
            queueConstructor = queueType.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < iterations; i++){

            IEventQueue queue = null;
            try {
                queue = (IEventQueue) queueConstructor.newInstance(new Object[] {});
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            long now = Instant.now().toEpochMilli() * 1000;

            for (int m = 0; m < elements; m++){
                queue.enqueue(Math.random(), "Test" + m);
            }

            for (int m = 0; m < elements; m++){
                queue.dequeue();
            }

            runtime += Instant.now().toEpochMilli() * 1000 - now;

        }

        return runtime/elements/iterations;
    }

}
