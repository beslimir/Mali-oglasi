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

public class Profile extends AppCompatActivity{

    TextView user_name_profile, likes_profile, followers_profile, tvNumberOfPosts, tvLastPost;
    ListView list_view_profile;
    DBHelper dbProfile;
    String myUser, myThanks, myUserFromList, myTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        //set back button on actionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbProfile = new DBHelper(this);

        //references
        user_name_profile = (TextView) findViewById(R.id.user_name_profile);
        likes_profile = (TextView) findViewById(R.id.likes_profile);
        followers_profile = (TextView) findViewById(R.id.followers_profile);
        list_view_profile = (ListView) findViewById(R.id.list_view_profile);
        tvNumberOfPosts = (TextView) findViewById(R.id.tvNumberOfPosts);
        tvLastPost = (TextView) findViewById(R.id.tvLastPost);

        registerForContextMenu(list_view_profile);

        //get and display user_name
        Cursor get_user_nameNow = dbProfile.get_user_name();
        if(get_user_nameNow.getCount() == 0){
            //Toast.makeText(Profile.this, "error user_name", //Toast.LENGTH_SHORT).show();
        }else{
            get_user_nameNow.moveToLast();
            myUser = get_user_nameNow.getString(get_user_nameNow.getColumnIndex("user_name"));
            user_name_profile.setText(myUser);
        }

        //get thanks_total
        Cursor get_thanks_totalNow = dbProfile.get_thanks_total(myUser);
        if(get_thanks_totalNow.getCount() == 0){
            //Toast.makeText(Profile.this, "error thanks_total", //Toast.LENGTH_SHORT).show();
        }else{
            get_thanks_totalNow.moveToFirst();
            myThanks = get_thanks_totalNow.getString(get_thanks_totalNow.getColumnIndex("thanks_total"));
            likes_profile.setText(myThanks);
        }

        //get followers
        Cursor get_followers_totalNow = dbProfile.get_followers_total(user_name_profile.getText().toString());
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
        list_view_profile.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                TextView txtTitle = (TextView) parent.getChildAt(position - list_view_profile.getFirstVisiblePosition()).findViewById(R.id.tvTitle);
                TextView txtUser = (TextView) parent.getChildAt(position - list_view_profile.getFirstVisiblePosition()).findViewById(R.id.tvTitleDetail2);
                myTitle = txtTitle.getText().toString();
                myUserFromList = txtUser.getText().toString();
                //Toast.makeText(Profile.this, myTitle, //Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    //Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
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
            case R.id.menu_insert:
                startActivity(new Intent(Profile.this, Insert.class));
                break;
            case R.id.menu_logout:
                //code for log out
                Cursor deleteTheNameNow = dbProfile.deleteTheName();
                if(deleteTheNameNow.getCount() == 0){
                    //Toast.makeText(Profile.this, "Log out", //Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }else{
                    //Toast.makeText(Profile.this, "Log out ERROR", //Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = Profile.this.getMenuInflater();
        inflater.inflate(R.menu.list_context_menu_profile, menu);
    }

    //Update or remove
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Cursor get_user_nameNow = dbProfile.get_user_name();
        if(get_user_nameNow.getCount() == 0){
            //Toast.makeText(Profile.this, "error user_name", //Toast.LENGTH_SHORT).show();
        }else{
            get_user_nameNow.moveToLast();
            myUser = get_user_nameNow.getString(get_user_nameNow.getColumnIndex("user_name"));
            //Toast.makeText(Profile.this, "Username: " + myUser, //Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.menuUpdate){
            //check if it's my post
            if(isItMine(myUser) == true){
                //you can update your post
                //Toast.makeText(Profile.this, "It's your post", //Toast.LENGTH_SHORT).show();
                Cursor getPostNow = dbProfile.getPost(myUser, myTitle);
                if(getPostNow.getCount() == 0){
                    //Toast.makeText(Profile.this, "No data found.", //Toast.LENGTH_SHORT).show();
                }else {
                    Intent in = new Intent(Profile.this, Update.class);
                    in.putExtra("user_name", myUser);
                    in.putExtra("title_post", myTitle);
                    startActivity(in);
                }
            }else{
                //you can't update a post from other users
                //Toast.makeText(Profile.this, "It's NOT your post", //Toast.LENGTH_SHORT).show();
            }
        }else if(item.getItemId() == R.id.menuRemove){
            if(isItMine(myUser) == true){
                //you can remove your post
                //Toast.makeText(Profile.this, "It's your post", //Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alert =  new AlertDialog.Builder(Profile.this);
                alert.setMessage("Do you want to remove " + myTitle + "?");
                alert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cursor deletePostNow = dbProfile.deletePost(myUser, myTitle);
                        if(deletePostNow.getCount() == 0){
                            //Toast.makeText(Profile.this, "Post deleted: " + myTitle, //Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            deletePostNow.moveToNext();
                            //Toast.makeText(Profile.this, "Post not deleted...", //Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("Cancel", null);
                alert.show();
            }else{
                //you can't remove a post from other users
                //Toast.makeText(Profile.this, "It's NOT your post", //Toast.LENGTH_SHORT).show();
            }
        }
        return super.onContextItemSelected(item);
    }

    //populate list view
    private void populateListView(String parameter){
        Cursor cursor = dbProfile.getAllDataProfile(parameter);
        //get data from SQLite into arrays
        String[] fromData = new String[] {DBHelper.COL6_2, DBHelper.COL6_10, DBHelper.COL6_8, DBHelper.COL6_11, DBHelper.COL6_4};
        int[] ids = new int[] {R.id.tvTitle, R.id.tvTitleDetail2, R.id.tvLikes, R.id.tvDate, R.id.tvPrice};
        //define SimpleCursorAdapter
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(Profile.this, R.layout.item_layout, cursor, fromData, ids, 0);
        //reference the listView
        list_view_profile.setAdapter(myCursorAdapter);
    }

    //display all data in a list view
    private void displayEverything(String parameter){
        Cursor getAllDataNow = dbProfile.getAllDataProfile(parameter);
        if(getAllDataNow.getCount() == 0){
            //Toast.makeText(Profile.this, "No data found.", //Toast.LENGTH_SHORT).show();
        }else{
            getAllDataNow.moveToFirst();
            populateListView(parameter);
        }
        getAllDataNow.moveToLast();
    }

    private boolean isItMine(String myUser){
        if(myUser.equals(myUserFromList)){
            return true;
        }else{
            return false;
        }
    }

    private void detailedVersion(String user_name, String title_post){
        Cursor getDetailsNow = dbProfile.detailedVersion(user_name, title_post);
        if(getDetailsNow.getCount() == 0){
            //Toast.makeText(Profile.this, "error details", //Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(Profile.this, "Opening...", //Toast.LENGTH_SHORT).show();
            Intent in = new Intent(Profile.this, Details.class);
            in.putExtra("user_name", user_name);
            in.putExtra("title_post", title_post);
            in.putExtra("FROM_ACTIVITY", "Profile");
            startActivity(in);
        }
    }

    private void getNumberOfPosts(String myUser){
        Cursor getNumberOfPostsNow = dbProfile.getNumberOfPosts(myUser);
        tvNumberOfPosts.setText(Integer.toString(getNumberOfPostsNow.getCount()));
    }

    private void getLastPost(String myUser){
        Cursor getLastPostNow = dbProfile.getLastPost(myUser);
        if(getLastPostNow.getCount() == 0){
            tvLastPost.setText("bez objava");
        }else {
            getLastPostNow.moveToFirst();
            tvLastPost.setText(getLastPostNow.getString(getLastPostNow.getColumnIndex("date_published")));
        }
    }

    private void getThanksTotal(String myUser){
        Cursor getThanksTotalNow = dbProfile.getThanksTotal(myUser);
        getThanksTotalNow.moveToFirst();
        likes_profile.setText(Integer.toString(getThanksTotalNow.getInt(0)));
        dbProfile.incrementThanksTotal(myUser, Integer.parseInt(likes_profile.getText().toString()));
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Profile.this, MainActivity2.class));
    }
}
