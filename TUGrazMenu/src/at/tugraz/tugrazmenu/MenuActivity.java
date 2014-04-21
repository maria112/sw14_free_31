package at.tugraz.tugrazmenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends Activity {

    MenuListAdapter menuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    protected void onStart() {
        super.onStart();
        //TODO - Just for testing
        List<List<MenuItem>> menusList = new ArrayList<List<MenuItem>>();
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        
        List<MenuItem> menus = new ArrayList<MenuItem>();
        Restaurant restaurant = new Restaurant("Galileo", "", "");
        MenuItem menu = new MenuItem(restaurant, "Menü 1", null);
        menus.add(menu);
        
        menu = new MenuItem(restaurant, "Menü 2", null);
        menus.add(menu);
        
        menusList.add(menus);
        restaurants.add(restaurant);
        
        menus = new ArrayList<MenuItem>();
        restaurant = new Restaurant("Mensa", "", ""); 
        menu = new MenuItem(restaurant, "Menü 3", null); 
        menus.add(menu); 
        
        menusList.add(menus);
        restaurants.add(restaurant);
        
        loadMenus(restaurants, menusList); 
     
    }

    public void loadMenus(List<Restaurant> restaurants, List<List<MenuItem>> menus) {
        menuAdapter = new MenuListAdapter(this, restaurants, menus);
        ExpandableListView items = (ExpandableListView) findViewById(R.id.listMenuItems);
        items.setAdapter(menuAdapter);
        
        for (int i = 0; i < menuAdapter.getGroupCount(); i++ ) {
        	items.expandGroup(i);
        }
    }
}
