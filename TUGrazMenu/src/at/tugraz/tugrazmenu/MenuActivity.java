package at.tugraz.tugrazmenu;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


public class MenuActivity extends FragmentActivity {
    
	static Fragment[] pages = {PageToday.getPageForDay(Calendar.MONDAY), 
		PageToday.getPageForDay(Calendar.TUESDAY),
		PageToday.getPageForDay(Calendar.WEDNESDAY),
		PageToday.getPageForDay(Calendar.THURSDAY),
		PageToday.getPageForDay(Calendar.FRIDAY),
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
    }
    
    
    public static class PageAdapter extends FragmentPagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            return pages[position];
        }
    }

}
