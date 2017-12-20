import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankingTest {

    //calculate ranking manually for a few queries vs. websites
    //

    private float BM25Score;
/*    private float TFScore;
    private float IDFScore;*/
    InvertedIndex hashIndex = new InvertedIndexHashMap();
    QueryHandler queryHandler = new QueryHandler(hashIndex);
    Map<String, List<Website>> results;


    @Before
    public void setUp() throws Exception {

        List<Website> sites = FileHelper.parseFile("SPWS17_GroupS\\data\\enwiki-small.txt");
        hashIndex.build(sites);
        Score bm25 = new BM25Score();

    }


    @Test
     void test1() throws Exception {

        //Currently we have just copied the results, so the tests are not in fact that valuable
        //We either need to calculate these or be upfront about it in the report.
        //In either case we show that we can use Junit testing.
        Float[] scoresDenmark = {(float) 0.44719586, (float) 0.24037087, (float)0, (float) 0, (float) 0,(float)0,(float)0,(float)0,(float)0};
        int i = 0;
        results = queryHandler.getMatchingWebsites("aarhus");
        Map<Website,Float> scoreMap = new HashMap<>();
        scoreMap = queryHandler.getMatchingScore(scoreMap, results, hashIndex);
        Iterator it = scoreMap.keySet().iterator();
        // assertEquals(,0.265);
        while(it.hasNext()){
            Object w = it.next();
           // System.out.println("test " + i + " for website: " + w.toString());
            assertEquals(scoreMap.get(w), scoresDenmark[i]);
           // System.out.println("passed!");
            i++;
        }
    }

    @Test
    void test2() throws Exception {

        Float[] scoresDenmark = {(float) 0.47460628, (float) 0.32975665, (float)0.30843785, (float) 0.2897049, (float) 0.1810011,(float)0.11093154,(float)0.0965734,(float)0.09290652,(float)0};
        int i = 0;
        results = queryHandler.getMatchingWebsites("arctic");
        Map<Website,Float> scoreMap = new HashMap<>();
        scoreMap = queryHandler.getMatchingScore(scoreMap, results, hashIndex);
        Iterator it = scoreMap.keySet().iterator();
        // assertEquals(,0.265);
        while(it.hasNext()){
            Object w = it.next();
            //System.out.println("test " + i + " for website: " + w.toString());
            assertEquals(scoreMap.get(w), scoresDenmark[i]);
           // System.out.println("passed!");
            i++;
        }
    }
}
