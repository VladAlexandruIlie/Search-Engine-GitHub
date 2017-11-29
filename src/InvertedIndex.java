import java.util.*;

public class InvertedIndex implements Index{
    public Map<String, List<Website>> invertedIndex;

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
        /*
        //method for printing the entire inverted index
        for (String words : invertedIndexHash.keySet()) {

            String word = words;
            System.out.print("(" + word + ") -> {");
            for (Website site : invertedIndexHash.get(word)) {
                System.out.print( site.getUrl() + ", " );
            }
            System.out.println("}");
        }
        */
    }

    public List<Website> lookup(String query)
    {
        if (invertedIndex.get(query)!=null) {
            List<Website> websites = invertedIndex.get(query);
            return websites;
        } else if (query.equals("*")){
            List<Website> websites = new ArrayList<Website>();
            for (String words : invertedIndex.keySet()) {
                //System.out.print("(" + words + ") -> {");
                for (Website site : invertedIndex.get(words)) {
                   if (!websites.contains(site))
                       websites.add(site);
                }
                //System.out.println("}");
            }
        return websites;

        }
        else return null;
    }
}