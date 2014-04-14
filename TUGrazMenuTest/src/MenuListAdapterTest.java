import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.view.View;
import at.tugraz.tugrazmenu.MenuItem;
import at.tugraz.tugrazmenu.MenuListAdapter;


public class MenuListAdapterTest extends AndroidTestCase {
	MenuListAdapter adapter;
	List<MenuItem>  menuItems; 
	
	protected void setUp(){
		menuItems = new ArrayList<MenuItem>();
	    menuItems.add(new MenuItem("Menü 1", "", "", "gesunde Mahlzeit", ""));
	    menuItems.add(new MenuItem("Menü 2", "", "", "ausgewogene Mahlzeit", ""));	
	    adapter = new MenuListAdapter(getContext(), menuItems);
	}
	
    public void testCount(){
    	int countItems = adapter.getCount();
    	assertEquals(2, countItems);
	}
    
    public void testPosition(){
    	assertEquals(menuItems.get(0), adapter.getItem(0));
    	assertEquals(menuItems.get(1), adapter.getItem(1));
    }
    
    public void testItemId(){
    	assertEquals(0, adapter.getItemId(0));
    	assertEquals(1, adapter.getItemId(1));
    }
    
    public void testGetView(){
    	View view = adapter.getView(0, null, null); 
    	assertNotNull(view); 
    	assertSame(view, adapter.getView(1, view, null));
    }

}
