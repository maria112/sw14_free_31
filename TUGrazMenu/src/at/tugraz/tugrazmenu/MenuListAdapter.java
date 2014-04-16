package at.tugraz.tugrazmenu;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {
	private Context context;
	private List<MenuItemContainer> list;
	
	public MenuListAdapter(Context context, List<MenuItemContainer> list) {
		this.context = context; 
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.fragment_menuitem, null);
		}
		TextView txtTitle = (TextView) convertView.findViewById(R.id.textTitle); 
		TextView txtDescription = (TextView) convertView.findViewById(R.id.textDescription);
		txtTitle.setText(list.get(position).title);
		txtDescription.setText(list.get(position).description);
		
		return convertView;
	}
}
