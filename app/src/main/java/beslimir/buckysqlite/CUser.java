package beslimir.buckysqlite;

public class CUser {

    private int user_id;
    private String user_name;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String gender;
    private String date_registration;
    private int thanks_total;
    private int bookmark_total;
    private String city_name;


    //Constructors

    public CUser(){

    }

    public CUser(String user_name, String password, String email, String first_name, String last_name, String gender, String date_registration, int thanks_total, int bookmark_total, String city_name) {
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.date_registration = date_registration;
        this.thanks_total = thanks_total;
        this.bookmark_total = bookmark_total;
        this.city_name = city_name;
    }

    //Setters

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDate_registration(String date_registration) {
        this.date_registration = date_registration;
    }

    public void setThanks_total(int thanks_total) {
        this.thanks_total = thanks_total;
    }

    public void setBookmark_total(int bookmark_total) {
        this.bookmark_total = bookmark_total;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }


    //Getters

    public int getBookmark_total() {
        return bookmark_total;
    }

    public String getCity_name() {
        return city_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getDate_registration() {
        return date_registration;
    }

    public int getThanks_total() {
        return thanks_total;
    }
}
