package beslimir.buckysqlite;

public class Ctg {
    private String title;
    private String subTitle;
    private int iconID;

    public Ctg(String title, String subTitle, int iconID){
        super();
        this.title = title;
        this.subTitle = subTitle;
        this.iconID = iconID;
    }

    public String getTitle(){
        return title;
    }
    public String getSubTitle(){
        return subTitle;
    }
    public int getIconID(){
        return iconID;
    }
}