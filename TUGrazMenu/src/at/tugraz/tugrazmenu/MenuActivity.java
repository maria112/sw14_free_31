package at.tugraz.tugrazmenu;

import android.app.Activity;
import android.net.ParseException;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MenuActivity extends Activity {

    MenuListAdapter menuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void fetchRssFeed(List<List<MenuItem>> menus, List<Restaurant> restaurantList) throws IOException, ParserConfigurationException, SAXException, ParseException, java.text.ParseException {
        XmlHelper xmlHelper = new XmlHelper();
        String content = xmlHelper.getSiteContent("http://rss.tugraz.at/menue.xml");
        NodeList nodes = xmlHelper.splitBodyIntoSingleItems(content, "item");
        List<MenuItemContainer> menuItemContainers = xmlHelper.createMenuList(nodes);
        ContainerToMenuItemConverter converter = new ContainerToMenuItemConverter();
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        for (int i = 0; i < menuItemContainers.size(); i++) {
            converter.convertAndAddToList(menuItemContainers.get(i), menuItemList, restaurantList);
        }        
        for(int i = 0; i < restaurantList.size(); i++) {
        	menus.add(new ArrayList<MenuItem>());
        }
        for(int i = 0; i < menuItemList.size(); i++) {
        	Restaurant currentRestaurant = menuItemList.get(i).restaurant;
        	int currentPosition = restaurantList.indexOf(currentRestaurant);
        	menus.get(currentPosition).add(menuItemList.get(i));
        }
    }
    
    protected void onStart() {
        super.onStart();

        // Download RSS feed in new thread
        // Networking is not allowed on the main UI thread
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
	            final List<List<MenuItem>> menusList = new ArrayList<List<MenuItem>>();
	            final List<Restaurant> restaurants = new ArrayList<Restaurant>();
				try {
					fetchRssFeed(menusList, restaurants);
				} catch (ParseException | IOException
						| ParserConfigurationException | SAXException
						| java.text.ParseException e) {
					e.printStackTrace();
				}
				// Update view in main UI thread (cannot be updated from other threads)
				MenuActivity.this.runOnUiThread(new Runnable() {
			        @Override
			        public void run() {
			        	loadMenus(restaurants, menusList);
			        }
				});
            }
        });

        thread.start();    
    }

    public void loadMenus(List<Restaurant> restaurants, List<List<MenuItem>> menus) {
        menuAdapter = new MenuListAdapter(this, restaurants, menus);
        ExpandableListView items = (ExpandableListView) findViewById(R.id.listMenuItems);
        items.setAdapter(menuAdapter);
        
        //TODO - Show all menus (or only restaurants)?
//        for (int i = 0; i < menuAdapter.getGroupCount(); i++ ) {
//        	items.expandGroup(i);
//        }
    }
}
