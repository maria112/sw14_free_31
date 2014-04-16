package at.tugraz.tugrazmenu;

import java.util.ArrayList;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

public class MenuActivityTest extends ActivityInstrumentationTestCase2<MenuActivity> {
    private Solo solo;
    private MenuActivity activity;
    private ListView listMenus;

    public MenuActivityTest() {
        super(MenuActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        activity = getActivity();
        listMenus = (ListView) activity.findViewById(R.id.listMenuItems);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @UiThreadTest
    public void testLoadData() {
        List<MenuItemContainer> menuItems = new ArrayList<MenuItemContainer>();
        menuItems.add(new MenuItemContainer("Menü 1", "", "", "gesunde Mahlzeit", ""));
        menuItems.add(new MenuItemContainer("Menü 2", "", "", "ausgewogene Mahlzeit", ""));
        activity.loadMenus(menuItems);
        assertEquals(2, listMenus.getCount());
        for (int i = 0; i < menuItems.size(); i++) {
            ViewGroup item = (ViewGroup) listMenus.getAdapter().getView(i, null, null);
            TextView txtTitle = (TextView) item.findViewById(R.id.textTitle);
            TextView txtDescription = (TextView) item.findViewById(R.id.textDescription);
            assertEquals(menuItems.get(i).title, txtTitle.getText());
            assertEquals(menuItems.get(i).description, txtDescription.getText());
        }

    }


}
