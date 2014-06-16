package at.tugraz.tugrazmenu;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ContainerToMenuItemConverterTest extends TestCase {
	public void testConvertAndAddToList() throws Exception {
		String title = "[Info] Essen rund um die TU am 16.4.2014";
		String link = "link";
		String guid = "guid";
		String description = "<br>  <b>Galileo</b> (Lessingstraße 25, Tel. 0316–873/5799)<br>  • Tagessuppe,"
				+ " Schinken/Käsespätzle und grüner Salat [6,50]<br>  • Alternativmenü: Tagessuppe, Wiener Schwein/Pute"
				+ " mit Reis/Pommes und Menüsalat [6,90]<br>  <br>  <b>Der Grieche</b> (Morellenfeldgasse 1, "
				+ "Tel. 0316–382370)<br>  • Griechischen Salat und Dessert [6,10]<br>";

		String pubDate = "pubDate";
		List<MenuItem> menuItems = new ArrayList<>();
		List<Restaurant> restaurants = new ArrayList<>();
		MenuItemContainer container = new MenuItemContainer(title, link, guid,
				description, pubDate);
		ContainerToMenuItemConverter converter = new ContainerToMenuItemConverter();

		converter.convertAndAddToList(container, menuItems, restaurants);

		GregorianCalendar calendar = new GregorianCalendar(2014, 3, 16);
		long expectedDate = calendar.getTimeInMillis();
		String expectedAddress = "Lessingstraße 25";
		String expectedTelephoneNumber = "0316–873/5799";
		String expectedContent = "Tagessuppe, Schinken/Käsespätzle und grüner Salat [6,50]";

		if (menuItems.size() > 0) {
			String actualContent = menuItems.get(0).content;
			long actualDate = menuItems.get(0).date.getTimeInMillis();
			String actualTelephoneNumber = menuItems.get(0).restaurant.telephoneNumber;
			String actualAddress = menuItems.get(0).restaurant.address;

			assertEquals(expectedAddress, actualAddress);
			assertEquals(expectedTelephoneNumber, actualTelephoneNumber);
			assertEquals(expectedContent, actualContent);
			assertEquals(expectedDate, actualDate);

		} else {
			fail();
		}
	}

	public void testExistsInRestaurantListAtIndex() {
		Restaurant listedRestaurant = new Restaurant("inList", "", "");
		Restaurant unlistedRestaurant = new Restaurant("notInList", "", "");
		List<Restaurant> restaurantList = new ArrayList<>();
		restaurantList.add(listedRestaurant);
		ContainerToMenuItemConverter containerToMenuItemConverter = new ContainerToMenuItemConverter();
		int i = containerToMenuItemConverter.existsInRestaurantListAtIndex(
				restaurantList, unlistedRestaurant);
		int j = containerToMenuItemConverter.existsInRestaurantListAtIndex(
				restaurantList, listedRestaurant);
		assertEquals(i, -1);
		assertEquals(j, 0);
	}
}