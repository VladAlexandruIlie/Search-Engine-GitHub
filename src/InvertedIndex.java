import java.util.*;

/**
 * The InvertedIndex data structure implements the Index data structure in a reversed manner. Instead of storing the
 * entire list of websites and iterating through them, the inverted index uses a Map to match the connection between
 * any word, that appear in the input file, and a list of Website objects, all of which containing it the word.
 * Such an implementation highly simplifies the lookup method.
 *
 * Abstract view of the inverted index data structure:
 *          word1 -> {site1.1, site1.2, site1.3, ... site1.m}
 *          word2 -> {site2.1, site2.2, site2.3, ... site2.m}
 *                  .
 *                  .
 *                  .
 *          wordn -> {siten.1, siten.2, siten.3, ... siten.m}
 */
public class InvertedIndex implements Index{
    public Map<String, List<Website>> invertedIndex;

    /**
     * This method analyses a list of Website objects and creates a map between each word
     * and all the websites that contain it.
     *
     * @param sites The list of websites that should be indexed
     */
    public void build(List<Website> sites) {
        for (Website site : sites ) {
           for (String word: site.getWords()) {
               if (!invertedIndex.containsKey(word)) {
                   List<Website> websites = new ArrayList<>();
                   websites.add(site);
                   invertedIndex.put(word,websites);
               }
               else {
                   List<Website> websites = invertedIndex.get(word);
                   if (!websites.contains(site)) {
                       websites.add(site);
                   }
                   invertedIndex.put(word, websites);
               }
           }
        }
//        //method for printing the entire inverted index
//        for (String words : invertedIndexHash.keySet()) {
//
//            String word = words;
//            System.out.print("(" + word + ") -> {");
//            for (Website site : invertedIndexHash.get(word)) {
//                System.out.print( site.getUrl() + ", " );
//            }
//            System.out.println("}");
//        }
    }

    /**
     * Searches for a query in the InvertedIndex map
     * @param query A query input given by the user
     * @return a list of Website objects that contain the query
     *         if the query is not found, this method returns null
     */
    public List<Website> lookup(String query)
    {
        if (invertedIndex.get(query)!=null) {
            List<Website> websites = invertedIndex.get(query);
            return websites;
        }
        else return null;
    }

    public List<Website> lookupAll() {
        List<Website> websites = new ArrayList<Website>();
        for (String words : invertedIndex.keySet()) {
            for (Website site : invertedIndex.get(words)) {
                if (!websites.contains(site))
                    websites.add(site);
            }
        }
        return websites;
    }
}