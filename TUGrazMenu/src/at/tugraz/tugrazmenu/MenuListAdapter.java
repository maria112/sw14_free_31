package at.tugraz.tugrazmenu;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class MenuListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Restaurant> restaurants;
    private List<List<MenuItem>> menus;

    public MenuListAdapter(Context context, List<Restaurant> restaurants, List<List<MenuItem>> menus) {
        this.context = context;
        this.restaurants = restaurants;
        this.menus = menus;
    }

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return menus.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_menuitem, null);
		}
		TextView txtContent = (TextView) convertView.findViewById(R.id.textContent);
		txtContent.setText(menus.get(groupPosition).get(childPosition).content);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return menus.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return restaurants.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return restaurants.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_restaurant, null);
		}
		TextView txtRestaurant = (TextView) convertView.findViewById(R.id.textRestaurant);
		txtRestaurant.setText(restaurants.get(groupPosition).name);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
