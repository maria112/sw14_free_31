package at.tugraz.tugrazmenu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class RestaurantActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);

		String name = getIntent().getExtras().getString("name");
		String adress = getIntent().getExtras().getString("adress");
		String telNr = getIntent().getExtras().getString("telNr");
		
		TextView nameField = (TextView) findViewById(R.id.textrestaurant);
		TextView adressField = (TextView) findViewById(R.id.textadress);
		TextView telField = (TextView) findViewById(R.id.texttelefon);

		nameField.setText(name);
		adressField.setText(adress);
		telField.setText(telNr);
	}
}
