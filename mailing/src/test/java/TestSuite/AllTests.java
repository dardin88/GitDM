package TestSuite;

import bean.MsgTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import search.IndexerTest;
import search.LuceneTesterTest;
import search.SearcherTest;
import search.TextFileFilterTest;
import store.StoreTest;

/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 *
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
    StoreTest.class,
    TextFileFilterTest.class,
    SearcherTest.class,
    LuceneTesterTest.class,
    IndexerTest.class,
    MsgTest.class
})
public class AllTests {
}
