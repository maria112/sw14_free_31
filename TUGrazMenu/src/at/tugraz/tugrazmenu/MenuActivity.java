package at.tugraz.tugrazmenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

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
	        	loadMenus(DataStore.getRestaurants(), DataStore.getRestaurantMenus());
	        }
		});
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
