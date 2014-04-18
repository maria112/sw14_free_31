package at.tugraz.tugrazmenu;

import java.util.GregorianCalendar;


public class MenuItem {
    public String content;
    public Restaurant restaurant;
    public GregorianCalendar date;

    public MenuItem(Restaurant restaurant, String content, GregorianCalendar date) {
        this.restaurant = restaurant;
        this.content = content;
        this.date = date;
    }
}
