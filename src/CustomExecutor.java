public interface CustomExecutor {

    void execute(Runnable command);

    void shutDownNow();

    void waitUntilAlltasksFinished();
}
