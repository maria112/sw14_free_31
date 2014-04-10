package at.tugraz.tugrazmenu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MenuActivity extends Activity {

    String urlString = "http://rss.tugraz.at/menue.xml";
    URL feedURL;
    List<MenuItem> menuItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }
}
