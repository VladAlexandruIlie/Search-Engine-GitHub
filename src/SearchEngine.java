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
        while (sc.hasNext()) {
            String line = sc.nextLine();
            long startTime = System.nanoTime();
            Map<Website,Float> scoreMap = new HashMap<>();

            results = queryHandler.getMatchingWebsites(line);
            scoreMap = queryHandler.getMatchingScore(scoreMap, results, hashIndex);


            /*
             * Assignment 2: Modifying the Basic Search Engine
             * Checks if the results map is empty and prints the results and the scores if it is not empty
             * if it is empty the program outputs "No website contains the query word."
             */

            //printAllQueryMatches(results);
            if (line.contains("site:") ) printAllQueryMatches(results);
             printAllScoreMatches(scoreMap);



            /*
             * Assignment 3.1
             * Prints the execution time needed to answer one query
             */
            long endTime = System.nanoTime();
            if (!results.isEmpty()) System.out.printf("Execution time: %.4f seconds!\n", (endTime-startTime)/100000000.00);
             System.out.println("Please provide the next query word");
        }
    }

    private static void printAllScoreMatches(Map<Website, Float> scoreMap) {
        if (!scoreMap.keySet().isEmpty()){
            for (Website website: scoreMap.keySet()) {
                System.out.printf("BM25 Score: %.4f | on: %s \n", scoreMap.get(website), website.getUrl());
//                System.out.printf("TF Score: %.4f ; IDF Score: %.4f ; TFIDF Score: %.4f ; BM25 Score: %.4f | on: %s \n",
//                        scoreMap.get(website), website.getUrl());
            }
        }
        else System.out.println("Score Map is Empty !!!");
    }

    private static void printAllQueryMatches(Map<String, List<Website>> sites) {
        if (!sites.keySet().isEmpty()) {
            for (String s : sites.keySet()) {
                //System.out.println("Query: " + s + " was found on: ");
                for (Website w: sites.get(s)) {
                    System.out.println(w.getUrl());
                }
            }
        }
        else {
            System.out.println("Query not found!");
        }
    }
}
