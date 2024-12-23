public interface CustomExecutor {

    void execute(Runnable command);

    void shutDownNow();

}
