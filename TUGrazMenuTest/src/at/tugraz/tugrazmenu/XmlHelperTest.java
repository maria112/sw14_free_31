package at.tugraz.tugrazmenu;

import android.os.Environment;
import android.util.Log;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class XmlHelperTest extends TestCase {
    public void testGetSiteContent(){
        Log.d("at.tugraz", "Trying GetSitecontent");
        try {
            Log.d("at.tugraz", "Trying GetSitecontent");
            String expectedContent = "Expected Result";
            XmlHelper xmlHelper = new XmlHelper();
            String fileDirectory = Environment.getExternalStorageDirectory() + File.separator + "testfile";
            File testFile = new File(fileDirectory);

            testFile.createNewFile();
            OutputStream outputStream = new FileOutputStream(testFile);
            outputStream.write(expectedContent.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            Log.d("at.tugraz", testFile.toURI().toURL().toString());
            String content = xmlHelper.getSiteContent(testFile.toURI().toURL().toString());
            assertEquals(expectedContent, content);


        } catch (IOException e) {
            Log.d("at.tugraz","Caught exception"+ e.getMessage());
        }

    }

    public void testSplitBodyIntoSingleItems() throws Exception {
        XmlHelper xmlHelper = new XmlHelper();
        List<String> itemContent = xmlHelper.splitBodyIntoSingleItems("<item>item1</item> nothing <item>item2</item> ");
        assertEquals(itemContent.size(), 2);
        if (itemContent.size() > 0) {
            assertEquals(itemContent.get(0), "item1");
            assertEquals(itemContent.get(1), "item2");
        }
    }

    public void testCreateMenuList() throws Exception {
        XmlHelper xmlHelper = new XmlHelper();
        List<String> stringItems = new ArrayList<String>();
        String expectedDescription = "some description";
        String expectedLink = "some link";
        String expectedTitle = "some description";
        String expectedPubdate = "some date";
        String expectedGuid = "some guid";
        stringItems.add(" <item> <title>"
                + expectedTitle + "</title><link>" + expectedLink + "</link><guid>" + expectedGuid + "</guid><description>" + expectedDescription +
                "</description><pubDate>" + expectedPubdate + "</pubDate></item>");
        List<MenuItemContainer> itemContainers = xmlHelper.createMenuList(stringItems);
        assertEquals(itemContainers.get(0).description, expectedDescription);
        assertEquals(itemContainers.get(0).link, expectedLink);
        assertEquals(itemContainers.get(0).title, expectedTitle);
        assertEquals(itemContainers.get(0).pubDate, expectedPubdate);
        assertEquals(itemContainers.get(0).guid, expectedGuid);
    }
}
