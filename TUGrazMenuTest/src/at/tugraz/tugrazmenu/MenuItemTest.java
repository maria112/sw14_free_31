package at.tugraz.tugrazmenu;

import junit.framework.TestCase;

public class MenuItemTest extends TestCase {
    public MenuItemTest() {
    }

    public void testMenuItemConstructor() {
        String title = "title";
        String link = "link";
        String guid = "guid";
        String description = "description";
        String pubDate = "pubDate";

        MenuItem menuItem = new MenuItem(title, link, guid, description, pubDate);

        assertEquals(menuItem.title, title);
        assertEquals(menuItem.link, link);
        assertEquals(menuItem.guid, guid);
        assertEquals(menuItem.description, description);
        assertEquals(menuItem.pubDate, pubDate);

    }
}


