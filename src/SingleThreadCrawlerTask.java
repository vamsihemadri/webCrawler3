import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleThreadCrawlerTask implements Runnable{

    private String url;
    private HtmlParser htmlParser;
    private String domain;

    private Set<String> visitedSet;
    private CustomExecutor executor;
    private AtomicInteger numCurrentRunningTasks;

    public SingleThreadCrawlerTask(String url, HtmlParser htmlParser, String domain, Set<String> visitedSet, CustomExecutor executor, AtomicInteger numCurrentRunningTasks){

        this.url = url;
        this.htmlParser = htmlParser;
        this.domain = domain;
        this.visitedSet  = visitedSet;
        this.numCurrentRunningTasks = numCurrentRunningTasks;
        this.executor = executor;



    }
    @Override
    public void run() {
        for(String childUrl : htmlParser.getUrls(url)){
            if(childUrl.split("/")[2].equals(domain) && visitedSet.add(childUrl)){
                numCurrentRunningTasks.incrementAndGet();
                executor.execute(new SingleThreadCrawlerTask(childUrl,htmlParser,domain,visitedSet,executor, numCurrentRunningTasks));
            }
        }

        numCurrentRunningTasks.decrementAndGet();
    }
}
