import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class CustomExecutorImpl implements CustomExecutor {

    ArrayBlockingQueue taskQueue;
    int numThreads;
    boolean isStoppped;
    List<PoolThreadRunnable> runnables  = new ArrayList<>();


    public CustomExecutorImpl(int numThreads, int maxTasks){
        this.numThreads = numThreads;
        taskQueue = new ArrayBlockingQueue<Runnable>(maxTasks);
        isStoppped = false;
        for(int i = 0;i<numThreads;i++){
            PoolThreadRunnable poolThreadRunnable = new PoolThreadRunnable(taskQueue);
            runnables.add(poolThreadRunnable);
        }

        for(PoolThreadRunnable runnable : runnables){
            new Thread(runnable).start();
        }
    }

    @Override
    public synchronized void execute(Runnable command) {

        if(isStoppped){
            throw new IllegalStateException(" Thread pool stopped ");
        }

        this.taskQueue.offer(command);

    }

    @Override
    public synchronized void shutDownNow() {
        isStoppped = false;
        for(PoolThreadRunnable runnable: runnables){
            runnable.stop();
        }
    }
}
