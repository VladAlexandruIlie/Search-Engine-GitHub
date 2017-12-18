import java.util.*;

/**
 * The QueryHandler class is responsible for answering queries to the search engine.
 */
public class QueryHandler {
    private List<Website> allWebsites = new ArrayList<>();
    private List<String> allURLs = new ArrayList<>();

    /**
     * The index the QueryHandler uses for answering queries.
     */
    private Index index = null;
    /**
     * The constructor
     * @param idx The index used by the QueryHandler.
     */
    public QueryHandler(Index idx) {
        this.index = idx;
        this.allWebsites = index.lookupAll();
    }

    /**
     * getMachingWebsites answers queries of the type: "subquery1 OR subquery2 OR subquery3 ...".
     * A "subquery" has the form "word1 word2 word3 ...". A website matches a "subquery" if all the words
     * occur on the website. A website matches the whole query, if it matches at least one "subquery".
     *
     * @param query the query string
     * @return the list of websites that matches the query
     */
    public Map<String ,List<Website>> getMatchingWebsites(String query) {
        // a Map that connects all multiple word queries parts to the list of Websites that contains them
        Map<String, List<Website>> qPartToWebsites = new HashMap<>();

        // we make the assumption that all queries contain more than one multiple word query
        String queryType[];

        // splitting the query around the ORs
        queryType = query.split("(?i)" + " OR ");

        // saving the multiple word query parts as strings in a list of stings
        List<String> queryParts =  Arrays.asList(queryType);

        // initializing the List of Website objects
        //List<Website> results = new ArrayList<Website>();

        // if there is no OR in the query, the following for-loop will be executed once
        for (int i=0 ; i< queryParts.size(); i++){
            String words = queryParts.get(i);
            //System.out.println(words);

            if(words.contains(" ")) {

                String[] querypart = words.split("\\s+");
                List<String> queryPart = new ArrayList<>();
                queryPart.addAll(Arrays.asList(querypart));

                List<Website> AllResults = new ArrayList<>();
                for (String word : queryPart) {
                    if (index.lookup(word) != null) {
                        AllResults.addAll(index.lookup(word));
                    }
                }

                for (String word : queryPart) {
                    List<Website> partialQueryResults = new ArrayList<Website>();
                    if (index.lookup(word) != null)
                        partialQueryResults.addAll(index.lookup(word));

                    List<Website> AllResultsCopy = new ArrayList<Website>();
                    AllResultsCopy.addAll(AllResults);


//                   for(int j=0; i< AllResultsCopy.size(); i++){
//                       for (int k =0; k< AllResultsCopy.size(); k++) {
//                          if(j != k && AllResultsCopy.get(j).equals(AllResultsCopy.get(k))) AllResults.remove(k);
//                       }
//
//                    }

                    for (Website w : AllResultsCopy) {
                        if (!partialQueryResults.contains(w)) AllResults.remove(w);
                    }
                }

                List<Website> uniquePartialQueryResults = new ArrayList<Website>();
                for (Website w: AllResults) {
                    if (!uniquePartialQueryResults.contains(w)) {
                        //results.add(w);
                        //System.out.println(w.getUrl());
                        uniquePartialQueryResults.add(w);
                        }
                }
                qPartToWebsites.putIfAbsent(words, uniquePartialQueryResults);
            } else {
                String word = queryParts.get(i);
                if (word.startsWith("site:")) {
                    String url = word.substring(5);
                    //System.out.println(url);
                    for (Website s : allWebsites) {
                        String siteURL = s.getUrl();
                        if (siteURL.contains(url)) {
                            word = word.substring(5);
                            //results.add(s);
                            //System.out.println(s.getUrl());
                            qPartToWebsites.putIfAbsent(word,index.lookup(word));

                        }
                    }
                }
                else if (index.lookup(word) != null) {
                    /*
                    for (Website w : index.lookup(word)) {
                        if (!results.contains(w)) {
                            results.add(w);
                            System.out.println(w.getUrl());
                        }
                    }
                     */
                    qPartToWebsites.putIfAbsent(word, index.lookup(word));

                }
            }
        }
        return qPartToWebsites;
    }
}