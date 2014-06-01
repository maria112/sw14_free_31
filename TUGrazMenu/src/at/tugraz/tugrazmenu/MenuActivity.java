package at.tugraz.tugrazmenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.Calendar;
import java.util.GregorianCalendar;


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

        GregorianCalendar currentDay = new GregorianCalendar();
        int day = currentDay.get(Calendar.DAY_OF_WEEK);
        if (day == 7 || day == 1)
        {    	
        	 Intent intent = new Intent(this, WeekendActivity.class);
        	 startActivity(intent);
        }
        else pager.setCurrentItem(day-2);
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
