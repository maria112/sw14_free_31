package at.tugraz.tugrazmenu;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DataStore {

    public interface DataStoreNotification {
        public void onDataLoaded();
    }

    static boolean isFetchingData = false;
    static List<DataStoreNotification> handlers = new ArrayList<>();

    static List<Restaurant> restaurants = new ArrayList<>();
    static List<List<MenuItem>> restaurantMenus = new ArrayList<>();

    public static void loadData(final DataStoreNotification handler) {
        // Download RSS feed in new thread
        // Networking is not allowed on the main UI thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (DataStore.class) {
                    if (!restaurants.isEmpty() && !restaurantMenus.isEmpty()) {
                        handler.onDataLoaded();
                        return;
                    }
                    handlers.add(handler);
                    if (isFetchingData) {
                        return;
                    }
                    isFetchingData = true;
                }
                try {
                    fetchData(restaurants, restaurantMenus);
                    synchronized (DataStore.class) {
                        for (DataStoreNotification handler : handlers) {
                            handler.onDataLoaded();
                        }
                        handlers.clear();
                        isFetchingData = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public static List<List<MenuItem>> getRestaurantMenus() {
        return restaurantMenus;
    }

    private static void fetchRssData(List<Restaurant> restaurants, List<MenuItem> menuItemList) throws IOException, ParserConfigurationException, SAXException, java.text.ParseException {
        XmlHelper xmlHelper = new XmlHelper();
        String content = xmlHelper.getSiteContent("http://rss.tugraz.at/menue.xml");
        NodeList nodes = xmlHelper.splitBodyIntoSingleItems(content, "item");
        List<MenuItemContainer> menuItemContainers = xmlHelper.createMenuList(nodes);
        ContainerToMenuItemConverter converter = new ContainerToMenuItemConverter();

        for (MenuItemContainer menuItemContainer : menuItemContainers) {
            converter.convertAndAddToList(menuItemContainer, menuItemList, restaurants);
        }
    }


    public static void fetchHtmlData(List<Restaurant> restaurantList, List<MenuItem> menuItemList) throws Exception {
        String url = "http://menue.tugraz.at/";
        HtmlParser parser = new HtmlParser();
        String content = parser.getSiteContent(url);
        String firstDateString = parser.getDateString(content);
        GregorianCalendar startDate = parser.stringToGregorianCalendar(firstDateString);
        List<Restaurant> currentWeekRestaurants = new ArrayList<>();
        List<MenuItem> currentWeekMenus = new ArrayList<>();

        parser.populateItemAndRestaurantLists(startDate, content, currentWeekRestaurants, currentWeekMenus);

        for (MenuItem currentWeekMenu : currentWeekMenus) {
            GregorianCalendar today = new GregorianCalendar();
            if (currentWeekMenu.date.get(Calendar.ERA) == today.get(Calendar.ERA) &&
                    (currentWeekMenu.date.get(Calendar.YEAR) > today.get(Calendar.YEAR) ||
                            (currentWeekMenu.date.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                                    currentWeekMenu.date.get(Calendar.DAY_OF_YEAR) > today.get(Calendar.DAY_OF_YEAR)))) {
                menuItemList.add(currentWeekMenu);

                if (!restaurantList.contains(currentWeekMenu.restaurant)) {
                    restaurantList.add(currentWeekMenu.restaurant);
                }
            }
        }
    }

    private static void fetchData(List<Restaurant> restaurants, List<List<MenuItem>> restaurantMenus) throws Exception {
    	List<MenuItem> menuItemList = new ArrayList<>();
        fetchRssData(restaurants, menuItemList);

        fetchHtmlData(restaurants, menuItemList);

        for (Restaurant restaurant : restaurants) {
            restaurantMenus.add(new ArrayList<MenuItem>());
        }
        for (MenuItem aMenuItemList : menuItemList) {
            Restaurant currentRestaurant = aMenuItemList.restaurant;
            int currentPosition = restaurants.indexOf(currentRestaurant);
            restaurantMenus.get(currentPosition).add(aMenuItemList);
        }
    }

}

