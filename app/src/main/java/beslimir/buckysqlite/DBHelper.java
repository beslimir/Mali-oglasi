package beslimir.buckysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "eStudent.db";
    private static final int VERSION = 8;
    //TABLE_0 - Following user
    public static final String TABLE_0 = "follower_user";
    public static final String COL0_1 = "id_followed_users";
    public static final String COL0_2 = "user_name";
    public static final String COL0_3 = "user_name_followed";
    //TABLE_1 - Following post
    public static final String TABLE_1 = "follower_post";
    public static final String COL1_1 = "id_followers_post";
    public static final String COL1_2 = "user_name";
    public static final String COL1_3 = "user_name_post_followed";
    public static final String COL1_4 = "title_post_followed";
    //TABLE_2 - Likes
    public static final String TABLE_2 = "likes";
    public static final String COL2_1 = "id_likes";
    public static final String COL2_2 = "user_name";
    public static final String COL2_3 = "user_name_liked";
    public static final String COL2_4 = "title_post_liked";
    //TABLE_3 - User
    public static final String TABLE_3 = "user";
    public static final String COL3_1 = "user_id";
    public static final String COL3_2 = "user_name";
    public static final String COL3_3 = "password";
    public static final String COL3_4 = "email";
    public static final String COL3_5 = "first_name";
    public static final String COL3_6 = "last_name";
    public static final String COL3_7 = "gender";
    public static final String COL3_8 = "date_registration";
    public static final String COL3_9 = "thanks_total";
    public static final String COL3_10 = "bookmark_total";
    public static final String COL3_11 = "city_name";
    //TABLE_4 - Login
    public static final String TABLE_4 = "login";
    public static final String COL4_1 = "login_id";
    public static final String COL4_2 = "date_time";
    public static final String COL4_3 = "user_name";
    //TABLE_5 - Remember me
    public static final String TABLE_5 = "remember_me";
    public static final String COL5_1 = "user_name";
    public static final String COL5_2 = "password";
    //TABLE_6 - Post
    public static final String TABLE_6 = "post";
    public static final String COL6_1 = "_id";
    public static final String COL6_2 = "title_post";
    public static final String COL6_3 = "text_post";
    public static final String COL6_4 = "price";
    public static final String COL6_5 = "date_post";
    public static final String COL6_6 = "time_post";
    public static final String COL6_8 = "thanks_post";
    public static final String COL6_9 = "category_name";
    public static final String COL6_10 = "user_name";
    public static final String COL6_11 = "date_published";

    public static final String[] ALL_POST_DATA = new String[]{COL6_1, COL6_2, COL6_3, COL6_4, COL6_5, COL6_6, COL6_8, COL6_9, COL6_10, COL6_11};

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_0 + " (id_followed_users integer primary key autoincrement not null, user_name text not null, user_name_followed text not null)");
        db.execSQL("CREATE TABLE " + TABLE_1 + " (id_followers_post integer primary key autoincrement not null, user_name text not null, user_name_post_followed text not null, title_post_followed text not null)");
        db.execSQL("CREATE TABLE " + TABLE_2 + " (id_likes integer primary key autoincrement not null, user_name text not null, user_name_liked text not null, title_post_liked text not null)");
        db.execSQL("CREATE TABLE " + TABLE_3 + " (user_id integer primary key autoincrement not null, user_name text not null, password text not null, email text not null, first_name text, last_name text, gender text, date_registration text not null, thanks_total integer not null, bookmark_total integer not null, city_name text not null)");
        db.execSQL("CREATE TABLE " + TABLE_4 + " (login_id integer primary key autoincrement not null, date_time text not null, user_name text not null, foreign key(user_name) references " + TABLE_3 + " (user_name))");
        db.execSQL("CREATE TABLE " + TABLE_5 + " (user_name text primary key not null, password text not null)");
        db.execSQL("CREATE TABLE " + TABLE_6 + " (_id integer primary key autoincrement not null, title_post text not null, text_post text not null, price integer not null, date_post text not null, time_post text not null, thanks_post integer not null, category_name text not null, date_published text not null, user_name text not null, foreign key(user_name) references " + TABLE_3 + " (user_name))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_0);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_6);
        onCreate(db);
    }

    //Register
    public void register(CUser user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL3_2, user.getUser_name());
        contentValues.put(COL3_3, user.getPassword());
        contentValues.put(COL3_4, user.getEmail());
        contentValues.put(COL3_5, user.getFirst_name());
        contentValues.put(COL3_6, user.getLast_name());
        contentValues.put(COL3_7, user.getGender());
        contentValues.put(COL3_8, user.getDate_registration());
        contentValues.put(COL3_9, user.getThanks_total());
        contentValues.put(COL3_10, user.getBookmark_total());
        contentValues.put(COL3_11, user.getCity_name());
        db.insert(TABLE_3, null, contentValues);
        db.close();
    }

    //Insert
    public void post(CPost post){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL6_2, post.getTitle_post());
        contentValues.put(COL6_3, post.getText_post());
        contentValues.put(COL6_4, post.getPrice());
        contentValues.put(COL6_5, post.getDate_post());
        contentValues.put(COL6_6, post.getTime_post());
        contentValues.put(COL6_8, post.getThanks_post());
        contentValues.put(COL6_9, post.getCategory_name());
        contentValues.put(COL6_10, post.getUser_name());
        contentValues.put(COL6_11, post.getDate_published());
        db.insert(TABLE_6, null, contentValues);
        db.close();
    }

    //display post
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT title_post FROM " + TABLE_6;
        //Curson point to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to first row in results
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("title_post")) != null) {
                dbString += c.getString(c.getColumnIndex("title_post"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    };

    //getId
    public Cursor getId(CUser user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getIdNow = db.rawQuery("select user_id from user where user_name = '" + user.getUser_name() + "'", null);
        return getIdNow;
    }

    //check for registration user_name
    public Cursor checkRegisterUsername(String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor checkName = db.rawQuery("select user_name from user where user_name = '" + user_name + "'", null);
        return checkName;
    }
    //check for registration email
    public Cursor checkRegisterEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor checkName = db.rawQuery("select email from user where email = '" + email + "'", null);
        return checkName;
    }

    //delete the user acc
    public void deleteUser(String user_name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete from " + TABLE_3 + " where user_name = '" + user_name + "'", null);
    }

    //see who is logged in and when
    public boolean login(String date_time, String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4_2, date_time);
        contentValues.put(COL4_3, user_name);
        long result = db.insert(TABLE_4, null, contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //get user_name
    public Cursor get_user_name(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT user_name from " + TABLE_4 + " ORDER BY login_id desc limit 1", null);
        return c;
    }

    //get thanks_total
    public Cursor get_thanks_total(String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT thanks_total from " + TABLE_3 + " WHERE user_name = '" + user_name + "'", null);
        return c;
    }

    //get followers_total
    public Cursor get_followers_total(String user_name_followed){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * from " + TABLE_0 + " WHERE user_name_followed = '" + user_name_followed + "'", null);
        return c;
    }

    //remember user
    public boolean rememberTheName(String user_name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL5_1, user_name);
        contentValues.put(COL5_2, password);
        long result = db.insert(TABLE_5, null, contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //delete the remembered user
    public Cursor deleteTheName(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor deleteTheNameNow = db.rawQuery("DELETE from " + TABLE_5, null);
        return deleteTheNameNow;
    }

    //check if a user is remembered
    public Cursor isRemembered(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor isRememberedNow = db.rawQuery("SELECT COUNT(*) from " + TABLE_5, null);
        return isRememberedNow;
    }

    //get remembered user_name
    public Cursor getRememberedUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor getNameNow = db.rawQuery("select user_name from " + TABLE_5, null);
        return getNameNow;
    }

    //check if a user is remembered
    public Cursor isLoginValid(String user_name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor isLoginValidNow = db.rawQuery("SELECT user_name, password from " + TABLE_3 + " WHERE user_name = '" + user_name + "'" + " and password = '" + password + "'", null);
        return isLoginValidNow;
    }

    //get all data by categorys
    public Cursor getAllDataCategory(String parameter){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        if(parameter.equals("null")){
            c = db.query(true, TABLE_6, ALL_POST_DATA, null, null, null, null, null, null);
            if(c != null){
                c.moveToFirst();
            }
        }else{
            c = db.query(true, TABLE_6, ALL_POST_DATA, COL6_9 + "=?", new String[]{parameter}, null, null, null, null);
            if(c != null){
                c.moveToFirst();
            }
        }
        return c;
    }

    //get followed users
    public Cursor getFollowedUsers(String user_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT user_name_followed from " + TABLE_0 + " WHERE user_name = '" + user_name + "'", null);
        return c;
    }

    //get all data by followed users
    public Cursor getFollowedUsersPost(String parameter, String[] usersArray){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT _id, title_post, user_name, thanks_post, date_published, price from " + TABLE_6 + " WHERE user_name = '" + usersArray[0] + "'";
        if(usersArray.length > 1) {
            for (int i = 1; i < usersArray.length; i++) {
                query += " OR user_name = '" + usersArray[i] + "'";
            }
        }
        if(!parameter.equals("null")) {
            query += " AND category_name = '" + parameter + "'";
        }
        Cursor getFollowedUsersPostNow = db.rawQuery(query, null);
        return getFollowedUsersPostNow;
    }

    //detailed version
    public Cursor detailedVersion(String user_name, String title_post){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT user_name, title_post from " + TABLE_6 + " WHERE user_name = '" + user_name + "'" + " and title_post = '" + title_post + "'", null);
        return c;
    }

    //get details
    public Cursor getDetails(String user_name, String title_post){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT date_published, title_post, user_name, thanks_post, text_post, price, date_post, time_post from " + TABLE_6 + " WHERE user_name = '" + user_name + "'" + " and title_post = '" + title_post + "'", null);
        return c;
    }


    //get all data by categorys
    public Cursor getAllDataProfile(String parameter){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        c = db.query(true, TABLE_6, ALL_POST_DATA, COL6_10 + "=?", new String[]{parameter}, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    //get post
    public Cursor getPost(String user_name, String title_post){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT title_post, text_post, price, date_post, time_post from " + TABLE_6 + " WHERE user_name = '" + user_name + "'" + " and title_post = '" + title_post + "'", null);
        return c;
    }

    //delete post
    public Cursor deletePost(String user_name, String title_post){
        SQLiteDatabase db = getWritableDatabase();
        Cursor deletePostNow = db.rawQuery("Delete from " + TABLE_6 + " where user_name = '" + user_name + "'" + " and title_post = '" + title_post + "'", null);
        return deletePostNow;
    }
    public Cursor updatePost(String title_post, String detail, int price, String date, String time, int thanks, String spinner, String user_name, String datePublished){
        SQLiteDatabase db = getWritableDatabase();
        Cursor deletePostNow = db.rawQuery("Update " + TABLE_6 + " set title_post =  '" + title_post + "', text_post = '" + detail +
                "', price = '" + price + "', date_post = '" + date + "', time_post = '" + time + "', thanks_post = '" + thanks +
                "', category_name = '" + spinner + "', user_name = '" + user_name + "', date_published = '" + datePublished
                + "' where user_name = '" + user_name + "'" + " and title_post = '" + title_post + "'", null);
        return deletePostNow;
    }

    //number of posts
    public Cursor getNumberOfPosts(String user_name){
        SQLiteDatabase db = getWritableDatabase();
        Cursor getNumberOfPostsNow = db.rawQuery("SELECT _id from " + TABLE_6 + " where user_name = '" + user_name + "'", null);
        return getNumberOfPostsNow;
    }

    //get last post
    public Cursor getLastPost(String user_name){
        SQLiteDatabase db = getWritableDatabase();
        Cursor getLastPostNow = db.rawQuery("SELECT date_published from " + TABLE_6 + " where user_name = '" + user_name + "'" + "ORDER BY _id desc limit 1", null);
        return getLastPostNow;
    }

    //incrementThanksPost
    public void incrementThanksPost(String user_name, String title_post, int likes){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_6 + " SET thanks_post = '" + likes + "'" + " WHERE user_name = '" + user_name + "'" + " and title_post = '" + title_post + "'");
    }

    //decrementThanksPost
    public void decrementThanksPost(String user_name, String title_post, int likes){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_6 + " SET thanks_post = '" + likes + "'" + " WHERE user_name = '" + user_name + "'" + " and title_post = '" + title_post + "'");
    }

    //get thanks_total
    public Cursor getThanksTotal(String user_name){
        SQLiteDatabase db = getWritableDatabase();
        Cursor getThanksPostNow = db.rawQuery("SELECT SUM(thanks_post) from " + TABLE_6 + " WHERE user_name = '" + user_name + "'", null);
        return getThanksPostNow;
    }

    //set thanks_total
    public void incrementThanksTotal(String user_name, int likes){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_3 + " SET thanks_total = '" + likes + "'" + " WHERE user_name = '" + user_name + "'");
    }

    //see who liked
    public boolean liked(String user_name, String user_name_liked, String title_post_liked){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_2, user_name);
        contentValues.put(COL2_3, user_name_liked);
        contentValues.put(COL2_4, title_post_liked);
        long result = db.insert(TABLE_2, null, contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //check if is liked
    public Cursor isItLiked(String user_name, String user_name_liked, String title_post_liked){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor isItLikedNow = db.rawQuery("SELECT COUNT(*) from " + TABLE_2 + " WHERE user_name = '" + user_name + "'" + " and user_name_liked = '" + user_name_liked + "'" + " and title_post_liked = '" + title_post_liked + "'", null);
        return isItLikedNow;
    }

    //delete the likedPost
    public Cursor likedPostDelete(String user_name, String user_name_liked, String title_post_liked){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor deleteLikedPostNow = db.rawQuery("DELETE from " + TABLE_2 + " WHERE user_name = '" + user_name + "'" + " and user_name_liked = '" + user_name_liked + "'" + " and title_post_liked = '" + title_post_liked + "'", null);
        return deleteLikedPostNow;
    }

    //see who followed
    public boolean followedUser(String user_name, String user_name_followed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL0_2, user_name);
        contentValues.put(COL0_3, user_name_followed);
        long result = db.insert(TABLE_0, null, contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //check if user is followed
    public Cursor isUserFollowed(String user_name, String user_name_followed){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor isUserFollowedNow = db.rawQuery("SELECT COUNT(*) from " + TABLE_0 + " WHERE user_name = '" + user_name + "'" + " and user_name_followed = '" + user_name_followed + "'", null);
        return isUserFollowedNow;
    }

    //delete the followedUser
    public Cursor followedUserDelete(String user_name, String user_name_followed){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor followedUserDeleteNow = db.rawQuery("DELETE from " + TABLE_0 + " WHERE user_name = '" + user_name + "'" + " and user_name_followed = '" + user_name_followed + "'", null);
        return followedUserDeleteNow;
    }

}
