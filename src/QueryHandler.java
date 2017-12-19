import java.util.*;

/**
 * The QueryHandler class is responsible for answering queries to the search engine.
 * he allWebsites
 */
public class QueryHandler {
     /**
     * The index the QueryHandler uses for answering queries.
     */
    private Index index;

    /**
     * The constructor
     *
     * @param idx The index used by the QueryHandler.
     */
    public QueryHandler(Index idx) {
        this.index = idx;
    }

    /**
     * getMachingWebsites answers queries of the type: "subquery1 OR subquery2 OR subquery3 ...".
     * A "subquery" has the form "word1 word2 word3 ...". A website matches a "subquery" if all the words
     * occur on the website. A website matches the whole query, if it matches at least one "subquery".
     *
     * @param query the query string
     * @return the list of websites that matches the query
     */
    public Map<String, List<Website>> getMatchingWebsites(String query) {
        // a Map that connects all multiple word queries parts to the list of Websites that contains them
        Map<String, List<Website>> qPartToWebsites = new HashMap<>();

        // we make the assumption that all queries contain more than one multiple word query
        String queryType[];

        // splitting the query around the ORs
        queryType = query.split("(?i)" + " OR ");

        // saving the multiple word query parts as strings in a list of stings
        List<String> queryParts = Arrays.asList(queryType);

        // if there is no OR in the query, the following for-loop will be executed once
        for (int i = 0; i < queryParts.size(); i++) {
            // the "words" variable is the text between ORs
            String words = queryParts.get(i);

            // if it is a multiple word query
            if (words.contains(" ")) {
                // querypart = individual words in a multiple word query
                String[] querypart = words.split("\\s+");

                // queryPart is an array that holds individual words from the multiple word query
                List<String> queryPart = new ArrayList<>();
                queryPart.addAll(Arrays.asList(querypart));

                // AllResults is an array that holds all websites
                // on which a word from the multiple word query might be found
                List<Website> AllResults = new ArrayList<>();
                for (String word : queryPart) {
                    if (index.lookup(word) != null) {
                        AllResults.addAll(index.lookup(word));
                    }
                }
                // for each word in the queryPart
                for (String word : queryPart) {
                    // test if it can be found in the index
                    if (index.lookup(word) != null) {
                        // create a copy of the AllResults array because I have been taught that it is a really
                        // bad idea to iterate through an array and delete at the same time
                        List<Website> AllResultsCopy = new ArrayList<Website>(AllResults);

                        // iterating through the copy
                        for (Website w : AllResultsCopy) {
                            // delete from the original unless this website matches all queryPart(s)
                            if (!index.lookup(word).contains(w)) AllResults.remove(w);
                        }
                    }
                }

                List<Website> uniquePartialQueryResults = new ArrayList<Website>();
                for (Website w : AllResults) {
                    if (!uniquePartialQueryResults.contains(w)) {
                        //results.add(w);
                        //System.out.println(w.getUrl());
                        uniquePartialQueryResults.add(w);
                    }
                }
                qPartToWebsites.putIfAbsent(words, AllResults);
            }
            // URL filter
            else if (queryParts.get(i).startsWith("site:")) {

                List<Website> allWebsites = index.lookupAll();

                List<Website> URLSearchWebsites = new ArrayList<Website>();
                String word = queryParts.get(i);
                if (word.startsWith("site:")) {
                    String url = word.substring(5);
                    //System.out.println(url);
                    for (Website s : allWebsites) {
                        //System.out.println(s.getUrl());
                        if (s.getUrl().contains(url)) {
                            //System.out.println(s.getUrl() + " - " + url);
                            URLSearchWebsites.add(s);
                        }
                    }
                }
                qPartToWebsites.put(word, URLSearchWebsites);
            }
            // prefix filter
            else if(queryParts.get(i).contains("*")){
                String qType[];
                // splitting the query around the spaces
                qType = query.split("\\s+");
                // saving the multiple word query parts as strings in a list of stings
                List<String> qParts = Arrays.asList(queryType);

                for (String word: qParts){
                    if (word.endsWith("*")){
                        List<Website> websites = index.lookup(word);
                        for (Website w : websites){
                            System.out.println(w.getUrl());
                        }
                    }
                }
            }
            else if (index.lookup(words) != null) {
                qPartToWebsites.putIfAbsent(words, index.lookup(words));
            }
        }
        return qPartToWebsites;
    }

    public Map<Website, Float> getMatchingScore(Map<Website, Float> scoreMap,
                              // String line,
                                                Map<String, List<Website>> results,
                                                Index hashIndex){

        for (String multipleWordQuery : results.keySet()) {
            if (!multipleWordQuery.startsWith("site:"))
                if (multipleWordQuery.contains(" ")) {
                for (Website website : results.get(multipleWordQuery)) {
                        Score score = new BM25Score();
                        //Float TFscore = score.get
                        Float siteScore = score.getScore(multipleWordQuery, website, hashIndex);
                        scoreMap.putIfAbsent(website, siteScore);
                    }
                }
                else {
                    String[] queryparts = multipleWordQuery.split("\\s+");
                    List<String> queryParts = new ArrayList<String>();
                    queryParts.addAll(Arrays.asList(queryparts));
                    for (String queryPart: queryParts) {
                        //System.out.println(queryPart);

                        for (Website website : results.get(multipleWordQuery)) {
                            float queryScore = 0;
                            for (String queryPart2 : queryParts) {
                                Score score = new BM25Score();
                                Float siteScore = score.getScore(queryPart2, website, hashIndex);
                                queryScore = queryScore + siteScore;
                                scoreMap.put(website, queryScore);
                            }
                        }
                    }
                }
        }

        List<Map.Entry<Website, Float>> list = new ArrayList<Map.Entry<Website, Float>>(scoreMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Website, Float>>() {
            @Override
            public int compare(Map.Entry<Website, Float> o1, Map.Entry<Website, Float> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<Website, Float> sortedMap = new LinkedHashMap<Website, Float>();
        for (Map.Entry<Website, Float> entry : list){
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}

