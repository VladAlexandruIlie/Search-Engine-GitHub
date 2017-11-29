import java.nio.file.FileSystemNotFoundException;
import java.util.*;

public class SearchEngine {
    public static void main(String[] args) {
        System.out.println("Welcome to the SearchEngine!");
        if (args.length != 1) {
            System.out.println("Error: Filename is missing");
            return;
        }

        Scanner sc = new Scanner(System.in);

        Index sIndex = new SimpleIndex();
        InvertedIndex hashIndex = new InvertedIndexHashMap();
        InvertedIndex treeIndex = new InvertedIndexTreeMap();

        List<Website> sites = FileHelper.parseFile(args[0]);

        sIndex.build(sites);
        hashIndex.build(sites);
        treeIndex.build(sites);

        QueryHandler queryHandler = new QueryHandler(hashIndex);
        Map<String, List<Website>> results;
        System.out.println("Please provide a query word");
        while (sc.hasNext())
        {
            String line = sc.nextLine();
            //System.out.println(line);
            long startTime = System.nanoTime();

            results = queryHandler.getMatchingWebsites(line);


            printAll(results);

            long endTime = System.nanoTime();
            if (!results.isEmpty()) System.out.println("Inverted Index execution time: " + (endTime-startTime)/100000000.00 + " seconds!");



            System.out.println("Please provide the next query word");
        }
    }

    private static void printAll(Map<String, List<Website>> sites) {
        if (!sites.isEmpty()) {
            for (String s : sites.keySet()) {
                System.out.println("Query: " + s + " was found on: ");
                for (Website w : sites.get(s)){
                    System.out.println(w.getUrl());
                }
            }
        }
        else {
            System.out.println("Query not found!");
        }
    }
}
