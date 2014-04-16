package at.tugraz.tugrazmenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends Activity {

    String urlString = "http://rss.tugraz.at/menue.xml";
    URL feedURL;
    List<MenuItemContainer> menuItems;
    MenuListAdapter menuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    protected void onStart() {
    	super.onStart();
    	//TODO - Just for testing
        menuItems = new ArrayList<MenuItemContainer>();
        menuItems.add(new MenuItemContainer("Menü 1", "", "", "gesunde Mahlzeit", ""));
        menuItems.add(new MenuItemContainer("Menü 2", "", "", "ausgewogene Mahlzeit", ""));
        loadMenus(menuItems);
    }
    
    public void loadMenus(List<MenuItemContainer> menus)
    {
        menuAdapter = new MenuListAdapter(this, menus);
        ListView items = (ListView) findViewById(R.id.listMenuItems);
        items.setAdapter(menuAdapter);
    }
}
