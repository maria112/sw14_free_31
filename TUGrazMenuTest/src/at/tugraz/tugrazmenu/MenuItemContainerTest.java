package at.tugraz.tugrazmenu;

import junit.framework.TestCase;

public class MenuItemContainerTest extends TestCase {
    public MenuItemContainerTest() {
    }

    public void testMenuItemContainerConstructor() {
        String title = "title";
        String link = "link";
        String guid = "guid";
        String description = "description";
        String pubDate = "pubDate";

        MenuItemContainer menuItemContainer = new MenuItemContainer(title, link, guid, description, pubDate);

        assertEquals(menuItemContainer.title, title);
        assertEquals(menuItemContainer.link, link);
        assertEquals(menuItemContainer.guid, guid);
        assertEquals(menuItemContainer.description, description);
        assertEquals(menuItemContainer.pubDate, pubDate);

    }
}


