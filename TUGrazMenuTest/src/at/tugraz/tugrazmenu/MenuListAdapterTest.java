package at.tugraz.tugrazmenu;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;
import android.view.View;
import at.tugraz.tugrazmenu.MenuItemContainer;
import at.tugraz.tugrazmenu.MenuListAdapter;


public class MenuListAdapterTest extends AndroidTestCase {
    MenuListAdapter adapter;
    List<List<MenuItem>> menusList;
    List<Restaurant>restaurants; 

    protected void setUp() {
    	menusList = new ArrayList<List<MenuItem>>();
        restaurants = new ArrayList<Restaurant>();
         
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
         
         adapter = new MenuListAdapter(getContext(), restaurants, menusList); 
    }

    public void testRestaurantsCount() {
        int countItems = adapter.getGroupCount();
        assertEquals(2, countItems);
    }

    public void testRestaurants() {
        assertEquals(restaurants.get(0), adapter.getGroup(0));
        assertEquals(restaurants.get(1), adapter.getGroup(1));
    }

    public void testRestaurantsId() {
        assertEquals(0, adapter.getGroupId(0));
        assertEquals(1, adapter.getGroupId(1));
    }

    public void testGetRestaurantsView() {
        View view = adapter.getGroupView(0, false, null, null);
        assertNotNull(view);
        assertSame(view, adapter.getGroupView(1, false, view, null));
    }

}
