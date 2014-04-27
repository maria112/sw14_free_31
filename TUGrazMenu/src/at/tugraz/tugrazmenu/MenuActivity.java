package at.tugraz.tugrazmenu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MenuActivity extends Activity implements DataStore.DataStoreNotification {

    MenuListAdapter menuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    protected void onStart() {
        super.onStart();
        
        DataStore.loadData(this);
    }
    
    public void onDataLoaded() {
		// Update view in main UI thread (cannot be updated from other threads)
		MenuActivity.this.runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	List<Restaurant> currentRestaurants = new ArrayList<Restaurant>();
	            List<List<MenuItem>> currentRestaurantMenus = new ArrayList<List<MenuItem>>();
	            filterMenus(DataStore.getRestaurants(), DataStore.getRestaurantMenus(),
	            		currentRestaurants, currentRestaurantMenus);
	        	loadMenus(currentRestaurants, currentRestaurantMenus);
	        }
		});
    }
    
    public void filterMenus(List<Restaurant> allRestaurants, List<List<MenuItem>> allRestaurantMenus,
    		List<Restaurant> currentRestaurants, List<List<MenuItem>> currentRestaurantMenus) {
    	Calendar today = new GregorianCalendar();
        today.add(Calendar.DAY_OF_MONTH, -2);
        
        for(int restaurantIndex = 0; restaurantIndex < allRestaurants.size(); restaurantIndex++){
        	List<MenuItem> currentMenus = new ArrayList<MenuItem>();
        	
        	for(int menuIndex = 0; menuIndex < allRestaurantMenus.get(restaurantIndex).size(); menuIndex++) {
        		MenuItem currentMenu = allRestaurantMenus.get(restaurantIndex).get(menuIndex);
        		if(currentMenu.date.get(Calendar.ERA) == today.get(Calendar.ERA) &&
        			currentMenu.date.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
        			currentMenu.date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
        			currentMenus.add(currentMenu);
        		}
        	}
        	if(!currentMenus.isEmpty()){
        		currentRestaurantMenus.add(currentMenus);
        		currentRestaurants.add(allRestaurants.get(restaurantIndex));
        	}
        }  
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
