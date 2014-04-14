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
    List<MenuItem> menuItems;
    MenuListAdapter menuAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    protected void onStart() {
    	super.onStart();
    	//TODO - Just for testing
        menuItems = new ArrayList<MenuItem>();
        menuItems.add(new MenuItem("Menü 1", "", "", "gesunde Mahlzeit", ""));
        menuItems.add(new MenuItem("Menü 2", "", "", "ausgewogene Mahlzeit", ""));
        loadMenus(menuItems);
    }
    
    public void loadMenus(List<MenuItem> menus)
    {
        menuAdapter = new MenuListAdapter(this, menus);
        ListView items = (ListView) findViewById(R.id.listMenuItems);
        items.setAdapter(menuAdapter);
    }
}
