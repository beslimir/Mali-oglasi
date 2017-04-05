package beslimir.buckysqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileNotMe extends AppCompatActivity{

    TextView user_name_profile, likes_profile, followers_profile, tvNumberOfPosts, tvLastPost;
    ListView list_view_profile;
    DBHelper dbProfileNotMe;
    String myUser, myThanks, myFollowers, myTitle, previousActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        //set back button on actionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbProfileNotMe = new DBHelper(this);

        //references
        user_name_profile = (TextView) findViewById(R.id.user_name_profile);
        likes_profile = (TextView) findViewById(R.id.likes_profile);
        followers_profile = (TextView) findViewById(R.id.followers_profile);
        tvNumberOfPosts = (TextView) findViewById(R.id.tvNumberOfPosts);
        tvLastPost = (TextView) findViewById(R.id.tvLastPost);
        list_view_profile = (ListView) findViewById(R.id.list_view_profile);

        //get and set user_name
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_name_profile.setText(extras.getString("user_name_not_me"));
        }else{
            user_name_profile.setText("No username");
        }

        myUser = user_name_profile.getText().toString();

        //get thanks_total
        Cursor get_thanks_totalNow = dbProfileNotMe.get_thanks_total(myUser);
        if(get_thanks_totalNow.getCount() == 0){
            //Toast.makeText(ProfileNotMe.this, "error thanks_total", //Toast.LENGTH_SHORT).show();
        }else{
            get_thanks_totalNow.moveToFirst();
            myThanks = get_thanks_totalNow.getString(get_thanks_totalNow.getColumnIndex("thanks_total"));
            likes_profile.setText(myThanks);
        }

        //get followers
        Cursor get_followers_totalNow = dbProfileNotMe.get_followers_total(user_name_profile.getText().toString());
        followers_profile.setText(String.valueOf(get_followers_totalNow.getCount()));

        displayEverything(myUser);
        getNumberOfPosts(myUser);
        getLastPost(myUser);
        getThanksTotal(myUser);

        //get title_post
        list_view_profile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtTitle = (TextView) parent.getChildAt(position - list_view_profile.getFirstVisiblePosition()).findViewById(R.id.tvTitle);
                myTitle = txtTitle.getText().toString();
                detailedVersion(myUser, myTitle);
            }
        });

    }

    //Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_not_me, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        //ContextMenu
        String msg = "" + item.getTitle();
        ////Toast.makeText(this, msg, //Toast.LENGTH_LONG).show();
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_logout:
                //code for log out
                Cursor deleteTheNameNow = dbProfileNotMe.deleteTheName();
                if(deleteTheNameNow.getCount() == 0){
                    //Toast.makeText(ProfileNotMe.this, "Log out", //Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }else{
                    //Toast.makeText(ProfileNotMe.this, "Log out ERROR", //Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //populate list view
    private void populateListView(String parameter){
        Cursor cursor = dbProfileNotMe.getAllDataProfile(parameter);
        //get data from SQLite into arrays
        String[] fromData = new String[] {DBHelper.COL6_2, DBHelper.COL6_10, DBHelper.COL6_8, DBHelper.COL6_11, DBHelper.COL6_4};
        int[] ids = new int[] {R.id.tvTitle, R.id.tvTitleDetail2, R.id.tvLikes, R.id.tvDate, R.id.tvPrice};
        //define SimpleCursorAdapter
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(ProfileNotMe.this, R.layout.item_layout, cursor, fromData, ids, 0);
        //reference the listView
        list_view_profile.setAdapter(myCursorAdapter);
    }

    //display all data in a list view
    private void displayEverything(String parameter){
        Cursor getAllDataNow = dbProfileNotMe.getAllDataProfile(parameter);
        if(getAllDataNow.getCount() == 0){
            //Toast.makeText(ProfileNotMe.this, "No data found.", //Toast.LENGTH_SHORT).show();
        }else{
            getAllDataNow.moveToFirst();
            populateListView(parameter);
        }
        getAllDataNow.moveToLast();
    }

    private void detailedVersion(String user_name, String title_post){
        Cursor getDetailsNow = dbProfileNotMe.detailedVersion(user_name, title_post);
        if(getDetailsNow.getCount() == 0){
            //Toast.makeText(ProfileNotMe.this, "error details", //Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(ProfileNotMe.this, "Opening...", //Toast.LENGTH_SHORT).show();
            Intent in = new Intent(ProfileNotMe.this, Details.class);
            in.putExtra("user_name", user_name);
            in.putExtra("title_post", title_post);
            in.putExtra("FROM_ACTIVITY", "ProfileNotMe");
            startActivity(in);
        }
    }

    private void getNumberOfPosts(String myUser){
        Cursor getNumberOfPostsNow = dbProfileNotMe.getNumberOfPosts(myUser);
        tvNumberOfPosts.setText(Integer.toString(getNumberOfPostsNow.getCount()));
    }

    private void getLastPost(String myUser){
        Cursor getLastPostNow = dbProfileNotMe.getLastPost(myUser);
        //getLastPostNow.moveToNext();
        getLastPostNow.moveToFirst();
        tvLastPost.setText(getLastPostNow.getString(getLastPostNow.getColumnIndex("date_published")));
    }

    private void getThanksTotal(String myUser){
        Cursor getThanksTotalNow = dbProfileNotMe.getThanksTotal(myUser);
        getThanksTotalNow.moveToFirst();
        likes_profile.setText(Integer.toString(getThanksTotalNow.getInt(0)));
        dbProfileNotMe.incrementThanksTotal(myUser, Integer.parseInt(likes_profile.getText().toString()));
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(ProfileNotMe.this, MainActivity2.class));
    }
}
