package at.tugraz.tugrazmenu;

import android.util.Xml;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;
import java.net.URL;
import java.util.List;

public class FeedReaderTest extends TestCase {
    public void testFeedReaderConstructor() throws Exception{
        File testfile = new File("testfeed.xml");
        URL url = new URL("file://" + testfile.getPath());
        FeedReader reader = new FeedReader(url);
        assertNotNull (reader.parser);

    }

    public void testReadFeed() throws Exception {
        File testfile = new File("testfeed.xml");
        URL url = new URL("file://" + testfile.getPath());
        FeedReader feedReader = new FeedReader(url);
        List<MenuItem> menuItemList = feedReader.readFeed();
        assertEquals(menuItemList.size(), 1);
        MenuItem firstItem;
        if(menuItemList.size() > 0){
            firstItem = menuItemList.get(0);

            assertEquals(firstItem.description, "Some Description");
            assertEquals(firstItem.pubDate, "Thu, 10 Apr 2014 07:25:00 +0200");
            assertEquals(firstItem.guid, "test.urll");
            assertEquals(firstItem.title, "testtitle");
        }else{
            Assert.fail();

        }
    }

}
