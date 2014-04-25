package at.tugraz.tugrazmenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.net.ParseException;

public class DataStore {
	
	public interface DataStoreNotification {
		public void onDataLoaded();
	}
	
    static List<Restaurant> restaurants = new ArrayList<Restaurant>();
    static List<List<MenuItem>> restaurantMenus = new ArrayList<List<MenuItem>>();
	
	public static void loadData(final DataStoreNotification handler) {
		// Download RSS feed in new thread
	    // Networking is not allowed on the main UI thread
	    Thread thread = new Thread(new Runnable(){
	        @Override
	        public void run() {
	        	if(!restaurants.isEmpty()) {
	        		handler.onDataLoaded();
	        		return;
	        	}
				try {
					fetchRssFeed(restaurants, restaurantMenus);
					handler.onDataLoaded();
				} catch (ParseException | IOException
						| ParserConfigurationException | SAXException
						| java.text.ParseException e) {
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

	private static void fetchRssFeed(List<Restaurant> restaurants, List<List<MenuItem>> restaurantMenus) throws IOException, ParserConfigurationException, SAXException, ParseException, java.text.ParseException {
        XmlHelper xmlHelper = new XmlHelper();
        String content = xmlHelper.getSiteContent("http://rss.tugraz.at/menue.xml");
        NodeList nodes = xmlHelper.splitBodyIntoSingleItems(content, "item");
        List<MenuItemContainer> menuItemContainers = xmlHelper.createMenuList(nodes);
        ContainerToMenuItemConverter converter = new ContainerToMenuItemConverter();
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        for (int i = 0; i < menuItemContainers.size(); i++) {
            converter.convertAndAddToList(menuItemContainers.get(i), menuItemList, restaurants);
        }        
        for(int i = 0; i < restaurants.size(); i++) {
        	restaurantMenus.add(new ArrayList<MenuItem>());
        }
        for(int i = 0; i < menuItemList.size(); i++) {
        	Restaurant currentRestaurant = menuItemList.get(i).restaurant;
        	int currentPosition = restaurants.indexOf(currentRestaurant);
        	restaurantMenus.get(currentPosition).add(menuItemList.get(i));
        }
    }
    
}

