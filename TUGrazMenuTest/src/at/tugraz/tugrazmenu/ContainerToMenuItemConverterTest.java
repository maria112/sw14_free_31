package at.tugraz.tugrazmenu;

import junit.framework.TestCase;

import java.util.*;

public class ContainerToMenuItemConverterTest extends TestCase {
    public void testConvertAndAddToList() throws Exception {
        String title = "[Info] Essen rund um die TU am 16.4.2014";
        String link = "link";
        String guid = "guid";
        String description = "<br>  <b>Galileo</b> (Lessingstraße 25, Tel. 0316–873/5799)<br>  • Tagessuppe," +
                " Schinken/Käsespätzle und grüner Salat [6,50]<br>  • Alternativmenü: Tagessuppe, Wiener Schwein/Pute" +
                " mit Reis/Pommes und Menüsalat [6,90]<br>  <br>  <b>Der Grieche</b> (Morellenfeldgasse 1, " +
                "Tel. 0316–382370)<br>  • Griechischen Salat und Dessert [6,10]<br>  • gegrilltes Hühnerfilet mit " +
                "Petersilkartoffeln, Salat [6,50]<br>  <br>  <b>Ich und Du</b> (Moserhofgasse 42, Tel. 0664–5706035)<br>" +
                "  • Hühnereinmachsuppe, Wienerschnitzel, Erbsenreis, Salat [6,70]<br>  <br>  <b>Mensa</b> (Inffeldgasse" +
                " 10, Tel. 0316–873/4716)<br>  • Cremesuppe, Würzige Spätzlepfanne mit knackigem Gemüse, Zwiebel, Pilzen" +
                " und frischen Kräutern dazu Salat [EUR 4,95]<br>  • Cremesuppe, \"Steirerschnitzel\" von der Pute in der" +
                " Kürbiskernpanade dazu Erbsenreis, Preiselbeeren und Salat [EUR 5,15]<br>  <br>";

        String pubDate = "pubDate";
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        MenuItemContainer container = new MenuItemContainer(title, link, guid, description, pubDate);
        ContainerToMenuItemConverter converter = new ContainerToMenuItemConverter();
        converter.convertAndAddToList(container, menuItems);

        GregorianCalendar expectedDate = new GregorianCalendar(2014, 4, 16);
        String expectedAddress = "Lessignstraße 25";
        String expectedTelephoneNumber = "0316.873/5799";
        String expectedContent = "Tagessuppe, Schinken/Käsespätzle und grüner Salat ";
        double expectedPrice = 6.9;

        if (menuItems.size() > 0) {
            String actualContent = menuItems.get(0).content;
            GregorianCalendar actualDate = menuItems.get(0).date;
            String actualTelephoneNumber = menuItems.get(0).restaurant.telephoneNumber;
            String actualAddress = menuItems.get(0).restaurant.address;
            Double actualPrice = menuItems.get(0).price;

            assertEquals(expectedAddress, actualAddress);
            assertEquals(expectedTelephoneNumber, actualTelephoneNumber);
            assertEquals(expectedContent, actualContent);
            assertEquals(expectedPrice, actualPrice);
            assertEquals(expectedDate, actualDate);

        } else {
            fail();
        }

    }
}
