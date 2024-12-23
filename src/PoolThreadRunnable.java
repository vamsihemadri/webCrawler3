import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;

public class PoolThreadRunnable implements Runnable{

    boolean isStopped=  false;
    ArrayBlockingQueue taskQueue;
    Thread t;

    public PoolThreadRunnable(ArrayBlockingQueue taskQueue){
        isStopped = false;
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        this.t = Thread.currentThread();
        while(!isStopped){
            try{
                Runnable command = (Runnable) taskQueue.take();
                command.run();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("There occurred an exception ");
            }
        }
    }

    public synchronized void stop(){
        isStopped = false;
        this.t.interrupt();
    }


    public synchronized boolean isStopped(){
        return isStopped;
    }
}
