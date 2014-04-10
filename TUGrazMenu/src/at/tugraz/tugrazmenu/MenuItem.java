package at.tugraz.tugrazmenu;


public class MenuItem {
    public String title;
    public String link;
    public String guid;
    public String description;
    public String pubDate;

    public MenuItem(String title, String link, String guid, String description, String pubDate) {
        this.title = title;
        this.link = link;
        this.guid = guid;
        this.description = description;
        this.pubDate = pubDate;
    }
}
