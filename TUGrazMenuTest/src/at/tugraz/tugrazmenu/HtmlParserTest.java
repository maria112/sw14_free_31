package at.tugraz.tugrazmenu;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class HtmlParserTest extends TestCase {

    public void testGetDateString() {
        String input = "  <center>\n" +
                "    <hr><h1>\n" +
                "     Vorschau f&uuml;r die Woche ab 12.5.2014\n" +
                "    </h1><hr>\n" +
                "   </center>";
        String expectedDateString = "12.5.2014";

        HtmlParser htmlParser = new HtmlParser();
        Assert.assertEquals(expectedDateString, htmlParser.getDateString(input));
    }

    public void testGetDateStringInvalidInput() throws Exception {
        String input = "Invalid Input";
        HtmlParser htmlParser = new HtmlParser();
        try{
            htmlParser.getDateString(input);
            Assert.fail("Exception expected");
        }catch(Exception e)
        {
        }
    }

    public void testStringToGregorianCalendar() throws Exception {
        HtmlParser htmlParser = new HtmlParser();
        String input = "3.3.2003";
        GregorianCalendar actual = htmlParser.stringToGregorianCalendar(input);
        GregorianCalendar expected = new GregorianCalendar(2003, 2, 3);
        Assert.assertEquals(expected, actual);

        input = "21.12.2050";
        actual = htmlParser.stringToGregorianCalendar(input);
        expected = new GregorianCalendar(2050, 11, 21);
        Assert.assertEquals(expected, actual);
    }

    public void testWeekDates() {
        HtmlParser parser = new HtmlParser();
        List<GregorianCalendar> weekDates = parser.getWeekDates(new GregorianCalendar(2013, 0, 1));
        Assert.assertEquals(5,weekDates.size());
        for (int i = 0; i < 5; i++) {
            Assert.assertEquals(new GregorianCalendar(2013, 0, i + 1), weekDates.get(i));
        }
    }

    public void testPopulateItemAndRestaurantLists(){
        HtmlParser parser = new HtmlParser();
        List<Restaurant> restaurantList = new ArrayList<>();
        List<MenuItem> menuItemList = new ArrayList<>();
        restaurantList.add(new Restaurant("Ich und Du", "address", "tel"));
        restaurantList.add(new Restaurant("Der Grieche", "address", "tel"));

        String content = "<h2><a class=\"li_blank\" name=\"Montag\">Montag</a></h2>\n" +
                "   <h3><a class=\"li_blank\" href=\"#galileo\">Galileo</a></h3>\n" +
                "  <ul style=\"margin-top: 0px\">\n" +
                "   <li>Tagessuppe, Spaghetti Bolognese und Salat [EUR 6,50]</li>\n" +
                "   <li>Alternativmen&Uuml;: Tagessuppe, Wiener Schwein/Pute mit Reis/Pommes und Men&uuml;salat [EUR 6,90]</li>\n" +
                "  </ul>\n" +
                "   <h3><a class=\"li_blank\" href=\"#grieche\">Der Grieche</a></h3>\n" +
                "  <ul style=\"margin-top: 0px\">\n" +
                "   <li>Griechische Omelett, Dessert [EUR 6,10]</li>\n" +
                "   <li>Kotelett gegrillt mit Ofenkartoffeln, Salat [EUR 6,50]</li>\n" +
                "  </ul>\n" +
                "   <h3><a class=\"li_blank\" href=\"#ichunddu\">Ich und Du</a></h3>\n" +
                "  <ul style=\"margin-top: 0px\">\n" +
                "   <li>Nudelsuppe, Wienerschnitzel, Kartoffel, Salat [EUR 6,70]</li>\n" +
                "  </ul>" +
                "<h2><a class=\"li_blank\" name=\"galileo\">Galileo (Lessingstraße 25, Tel. 0316-873/5799)</a></h2>\n";
        parser.populateItemAndRestaurantLists(new GregorianCalendar(2003, 3, 3), content, restaurantList, menuItemList);
        Assert.assertEquals(3, restaurantList.size());
        Assert.assertEquals(5, menuItemList.size());
        Assert.assertEquals("Galileo", restaurantList.get(2).name);
        Assert.assertEquals("Lessingstraße 25", restaurantList.get(2).address);
        Assert.assertEquals("0316-873/5799", restaurantList.get(2).telephoneNumber);
        Assert.assertEquals("Tagessuppe, Spaghetti Bolognese und Salat [EUR 6,50]", menuItemList.get(0).content);
        Assert.assertEquals("Galileo", menuItemList.get(0).restaurant.name);
    }

    public void testMenuItemExistsInListAt(){
        List<MenuItem> menuItemList = new ArrayList<>();
        MenuItem itemInList = new MenuItem(new Restaurant("inList", null,null),"menu1",new GregorianCalendar(2001,1,1));
        MenuItem itemNotInList = new MenuItem(new Restaurant("inList", null,null),"menu1",new GregorianCalendar(2001,2,1));
        menuItemList.add(itemInList);
        HtmlParser parser = new HtmlParser();
        Assert.assertEquals(0, parser.menuItemExistsInListAt(menuItemList, itemInList));
        Assert.assertEquals(-1, parser.menuItemExistsInListAt(menuItemList, itemNotInList));
    }
}
