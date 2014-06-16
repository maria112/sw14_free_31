package at.tugraz.tugrazmenu;

import android.os.Environment;
import android.util.Log;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class XmlHelperTest extends TestCase {
	public void testGetSiteContent() {
		try {
			String expectedContent = "Expected Result";
			XmlHelper xmlHelper = new XmlHelper();
			String fileDirectory = Environment.getExternalStorageDirectory()
					+ File.separator + "testfile";
			File testFile = new File(fileDirectory);

			OutputStream outputStream = new FileOutputStream(testFile);
			outputStream.write(expectedContent.getBytes(Charset
					.forName("UTF-8")));
			outputStream.close();
			String content = xmlHelper.getSiteContent(testFile.toURI().toURL()
					.toString());
			assertEquals(expectedContent, content);

		} catch (IOException e) {
			Log.d("at.tugraz", "Caught exception" + e.getMessage());
			Assert.fail();

		}

	}

	public void testSplitBodyIntoSingleItems() throws Exception {
		XmlHelper xmlHelper = new XmlHelper();
		String inputString = "<root><item>item1</item><should_be_ignored>"
				+ "</should_be_ignored> <item><expected>item2</expected></item></root>";
		NodeList items = xmlHelper
				.splitBodyIntoSingleItems(inputString, "item");

		if (items.getLength() > 0) {
			assertEquals(2, items.getLength());
			assertEquals("item2", items.item(1).getChildNodes().item(0)
					.getTextContent());
			assertEquals("item1", items.item(0).getTextContent());
		} else {
			Assert.fail();
		}
	}

	public void testCreateMenuList() throws Exception {
		XmlHelper xmlHelper = new XmlHelper();
		String expectedDescription = "some description";
		String expectedLink = "some link";
		String expectedTitle = "some description";
		String expectedPubdate = "some date";
		String expectedGuid = "some guid";
		String xmlFormattedString = " <item> <title>" + expectedTitle
				+ "</title><link>" + expectedLink + "</link><guid>"
				+ expectedGuid + "</guid><description>" + expectedDescription
				+ "</description><pubDate>" + expectedPubdate
				+ "</pubDate></item>";

		NodeList nodes = xmlHelper.splitBodyIntoSingleItems(xmlFormattedString,
				"item");

		List<MenuItemContainer> itemContainers = xmlHelper
				.createMenuList(nodes);
		if (itemContainers.size() > 0) {
			assertEquals(expectedDescription, itemContainers.get(0).description);
			assertEquals(expectedLink, itemContainers.get(0).link);
			assertEquals(expectedTitle, itemContainers.get(0).title);
			assertEquals(expectedPubdate, itemContainers.get(0).pubDate);
			assertEquals(expectedGuid, itemContainers.get(0).guid);
		} else {
			fail();
		}
	}

	public void testCreateMenuListException() throws IOException, SAXException,
			ParserConfigurationException {
		XmlHelper xmlHelper = new XmlHelper();
		NodeList nodes = xmlHelper.splitBodyIntoSingleItems("<item></item>",
				"item");
		try {
			xmlHelper.createMenuList(nodes);
			fail();
		} catch (IllegalStateException e) {

		}
	}
}
