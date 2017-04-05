package beslimir.buckysqlite;

public class CPost {

    private int post_id;
    private String title_post;
    private String text_post;
    private int price;
    private String date_post;
    private String time_post;
    private int thanks_post;
    private String category_name;
    private String user_name;
    private String date_published;


    //Constructors

    public CPost() {

    }

    public CPost(String title_post, String text_post, int price, String date_post, String time_post, int thanks_post, String category_name, String user_name, String date_published) {
        this.title_post = title_post;
        this.text_post = text_post;
        this.price = price;
        this.date_post = date_post;
        this.time_post = time_post;
        this.thanks_post = thanks_post;
        this.category_name = category_name;
        this.user_name = user_name;
        this.date_published = date_published;
    }

    //Setters


    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public void setTitle_post(String title_post) {
        this.title_post = title_post;
    }

    public void setText_post(String text_post) {
        this.text_post = text_post;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDate_post(String date_post) {
        this.date_post = date_post;
    }

    public void setTime_post(String time_post) {
        this.time_post = time_post;
    }

    public void setThanks_post(int thanks_post) {
        this.thanks_post = thanks_post;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setDate_published(String date_published) {
        this.date_published = date_published;
    }


    //Getters


    public int getPost_id() {
        return post_id;
    }

    public String getTitle_post() {
        return title_post;
    }

    public String getText_post() {
        return text_post;
    }

    public int getPrice() {
        return price;
    }

    public String getDate_post() {
        return date_post;
    }

    public String getTime_post() {
        return time_post;
    }

    public int getThanks_post() {
        return thanks_post;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getDate_published() {
        return date_published;
    }
}
