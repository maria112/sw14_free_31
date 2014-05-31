package at.tugraz.tugrazmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class PageToday extends Fragment implements DataStore.DataStoreNotification {

    MenuListAdapter menuAdapter;
    int dayOfWeek = Calendar.MONDAY;

    public static Fragment getPageForDay(int dayOfWeek) {
        PageToday page = new PageToday();
        page.dayOfWeek = dayOfWeek;
        return page;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_today, container, false);

        ExpandableListView items = (ExpandableListView) rootView.findViewById(R.id.listMenuItems);

        items.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                MenuItem menu = (MenuItem) menuAdapter.getChild(groupPosition, childPosition);
                startDetailView(menu);

                //Toast toast = Toast.makeText(getActivity(), "Menü wurde geklickt", Toast.LENGTH_LONG);
                //toast.show();
                return false;
            }
        });

        String[] daysOfWeek = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"};
        TextView day = (TextView) rootView.findViewById(R.id.textrestaurant);
        int currentDay = dayOfWeek - 2;
        day.setText(daysOfWeek[currentDay]);

        return rootView;
    }

    public void startDetailView(MenuItem menue) {
        Intent intent = new Intent(getActivity(), RestaurantActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("address", menue.restaurant.address);
        bundle.putString("name", menue.restaurant.name);
        bundle.putString("telNr", menue.restaurant.telephoneNumber);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onStart() {
        super.onStart();

        DataStore.loadData(this);
    }

    public void onDataLoaded() {
        // Update view in main UI thread (cannot be updated from other threads)
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Restaurant> currentRestaurants = new ArrayList<>();
                List<List<MenuItem>> currentRestaurantMenus = new ArrayList<>();
                filterMenus(DataStore.getRestaurants(), DataStore.getRestaurantMenus(),
                        currentRestaurants, currentRestaurantMenus);
                loadMenus(currentRestaurants, currentRestaurantMenus);
            }
        });
    }

    public void filterMenus(List<Restaurant> allRestaurants, List<List<MenuItem>> allRestaurantMenus,
                            List<Restaurant> currentRestaurants, List<List<MenuItem>> currentRestaurantMenus) {
        Calendar today = new GregorianCalendar();

        for (int restaurantIndex = 0; restaurantIndex < allRestaurants.size(); restaurantIndex++) {
            List<MenuItem> currentMenus = new ArrayList<>();

            for (int menuIndex = 0; menuIndex < allRestaurantMenus.get(restaurantIndex).size(); menuIndex++) {
                MenuItem currentMenu = allRestaurantMenus.get(restaurantIndex).get(menuIndex);
                if (currentMenu.date.get(Calendar.ERA) == today.get(Calendar.ERA) &&
                        currentMenu.date.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                        currentMenu.date.get(Calendar.WEEK_OF_YEAR) == today.get(Calendar.WEEK_OF_YEAR) &&
                        currentMenu.date.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                    currentMenus.add(currentMenu);
                }
            }
            if (!currentMenus.isEmpty()) {
                currentRestaurantMenus.add(currentMenus);
                currentRestaurants.add(allRestaurants.get(restaurantIndex));
            }
        }
    }

    public void loadMenus(List<Restaurant> restaurants, List<List<MenuItem>> menus) {
        menuAdapter = new MenuListAdapter(getActivity(), restaurants, menus);
        ExpandableListView items = (ExpandableListView) getView().findViewById(R.id.listMenuItems);
        items.setAdapter(menuAdapter);

        //TODO - Show all menus (or only restaurants)?
        //  for (int i = 0; i < menuAdapter.getGroupCount(); i++ ) {
        //  	items.expandGroup(i);
        //  }
    }
}
