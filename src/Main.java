import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String firstUrl = "http://vamsi.com/home/0";

        HtmlParser htmlParser = new HtmlParserImpl();
        System.out.println(" START CRAWLING ");

        List<String> crawledUrls = crawl(firstUrl,htmlParser);
        System.out.println(crawledUrls);


        System.out.println(" DONE CRAWLING : total size of urls crawled " + crawledUrls.size());

    }


    private static List<String> crawl(String url,HtmlParser htmlParser){


        CustomExecutor service = new CustomExecutorImpl(3,5);

        String domain = url.split("/")[2];

        Set<String> visitedSet = Collections.synchronizedSet(new HashSet<>());
        AtomicInteger numCurrentRunningTasks = new AtomicInteger(1);

        visitedSet.add(url);
        service.execute(new SingleThreadCrawlerTask(url,htmlParser,domain,visitedSet,service,numCurrentRunningTasks));

        while(numCurrentRunningTasks.get()>0){
        }

        //service.shutDownNow();

        return new ArrayList<>(visitedSet);
    }
}