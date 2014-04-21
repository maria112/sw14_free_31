package at.tugraz.tugrazmenu;

import java.util.ArrayList;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

public class MenuActivityTest extends ActivityInstrumentationTestCase2<MenuActivity> {
    private Solo solo;
    private MenuActivity activity;
    private ExpandableListView listMenus;

    public MenuActivityTest() {
        super(MenuActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        activity = getActivity();
        listMenus = (ExpandableListView) activity.findViewById(R.id.listMenuItems);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @UiThreadTest
    public void testLoadData() {        
        
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
        
        activity.loadMenus(restaurants,menusList);
        assertEquals(5, listMenus.getCount());
        for (int i = 0; i < restaurants.size(); i++) {
            ViewGroup itemview = (ViewGroup) listMenus.getExpandableListAdapter().getGroupView(i, false, null, null);
            TextView txtRestaurant = (TextView) itemview.findViewById(R.id.textRestaurant);
            assertEquals(restaurants.get(i).name, txtRestaurant.getText());
            
            for(int j = 0; j < menusList.get(i).size(); j++) {
            	 ViewGroup menuview = (ViewGroup) listMenus.getExpandableListAdapter().getChildView(i, j, false, null, null);
            	 TextView txtMenu = (TextView) menuview.findViewById(R.id.textContent);
            	 assertEquals(menusList.get(i).get(j).content, txtMenu.getText());
            	 
            	 //ToDO Anzeige vom zweiten Textfeld noch testen
            }
           
        }

    }

}
