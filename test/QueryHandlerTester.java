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

public class QueryHandlerTester {

    InvertedIndex hashIndex = new InvertedIndexHashMap();
    QueryHandler queryHandler = new QueryHandler(hashIndex);
    Map<String, List<Website>> results;
    List<Website> sites = FileHelper.parseFile("SPWS17_GroupS\\data\\enwiki-small.txt");
    List<Website> sitesToCheck;

    @Test
    void test1() throws Exception {
        List<Website> sitesToContain = FileHelper.parseFile("SPWS17_GroupS\\test-resources\\master-test.txt");
        hashIndex.build(sites);
        results = queryHandler.getMatchingWebsites("master");
        Iterator itr = results.keySet().iterator();
        assertEquals(sitesToContain.size(),results.get("master").size());
    }

    @Test
    void test2() throws Exception{

        List<Website> sitesToContain2 = FileHelper.parseFile("SPWS17_GroupS\\test-resources\\stuttgart-aalen-test.txt");
        hashIndex.build(sites);
        results = queryHandler.getMatchingWebsites("stuttgart aalen");
        Iterator itr = results.keySet().iterator();
        assertEquals(sitesToContain2.size(),results.get("stuttgart aalen").size());

    }

}
