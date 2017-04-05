package beslimir.buckysqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity{

    private List<Ctg> myCategory = new ArrayList<Ctg>();
    private String myUser, myDrawerUser, myString;
    private DBHelper dbMain;
    private TextView output, drawerProfile;
    public ListView listView, listViewDrawer;
    public DrawerLayout drawerLayout;
    public RelativeLayout drawerPane;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //set home icon (hamburger icon)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbMain = new DBHelper(this);
        output = (TextView) findViewById(R.id.output);
        drawerProfile = (TextView) findViewById(R.id.drawerProfile);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);

        //retrieve data from Insert activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myUser = extras.getString("key");
            //The key argument here must match that used in the other activity
        }

        //floating button for Insert activity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Insert.class);
                startActivity(i);
            }
        });

        //get and display user_name
        Cursor get_user_nameNow = dbMain.get_user_name();
        if(get_user_nameNow.getCount() == 0){
            //Toast.makeText(MainActivity2.this, "error user_name", //Toast.LENGTH_SHORT).show();
        }else{
            get_user_nameNow.moveToLast();
            myDrawerUser = get_user_nameNow.getString(get_user_nameNow.getColumnIndex("user_name"));
            drawerProfile.setText(myDrawerUser);
        }

        populateCategorys();
        populateNavDrawer();

        // create listener for drawer layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                // TODO Auto-generated method stub
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //start the Profile activity
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.profile_box);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, Profile.class);
                startActivity(intent);
            }
        });

        listViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment;
                switch (position) {
                    case 0:
                        myString = "Početna";
                        fragment = new FragmentTab();
                        break;
                    case 1:
                        myString = "Instrukcije";
                        fragment = new FragmentTab();
                        break;
                    case 2:
                        myString = "Literatura";
                        fragment = new FragmentTab();
                        break;
                    case 3:
                        myString = "Stanovi";
                        fragment = new FragmentTab();
                        break;
                    case 4:
                        myString = "Sport";
                        fragment = new FragmentTab();
                        break;
                    case 5:
                        myString = "Zabava";
                        fragment = new FragmentTab();
                        break;
                    case 6:
                        myString = "Kultura";
                        fragment = new FragmentTab();
                        break;
                    case 7:
                        myString = "Posao";
                        fragment = new FragmentTab();
                        break;
                    case 8:
                        myString = "Prijevoz";
                        fragment = new FragmentTab();
                        break;
                    default:
                        myString = "fragOne";
                        fragment = new FragmentTab();
                        break;
                }
                replaceFragment(fragment);
            }
        });
        myString = "Početna";
        replaceFragment(new FragmentTab());
    }

    //replace fragment
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativeLayout, fragment).commit();
        drawerLayout.closeDrawer(drawerPane); //close the drawer
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
            case R.id.menu_insert:
                Intent i = new Intent(getApplicationContext(), Insert.class);
                startActivity(i);
                break;
            case R.id.menu_logout:
                //code for log out
                Cursor deleteTheNameNow = dbMain.deleteTheName();
                if(deleteTheNameNow.getCount() == 0){
                    //Toast.makeText(MainActivity2.this, "Log out", //Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }else{
                    //Toast.makeText(MainActivity2.this, "Log out ERROR", //Toast.LENGTH_SHORT).show();
                }
                break;
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    //change the actionBar icon
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //send data to fragment
    public String getMyData(){
        return myString;
    }

    //Close the app with longPress on backButton
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //Toast.makeText(this, "bye bye", //Toast.LENGTH_LONG).show();
            super.onBackPressed();
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    //populate Navigation Drawer
    public void populateNavDrawer(){
        myCategory.add(new Ctg("Home", "from the beginning", R.drawable.icon_home));
        myCategory.add(new Ctg("Instructions", "Need some lessons?", R.drawable.icon_instructions));
        myCategory.add(new Ctg("Literature", "Some books maybe?", R.drawable.icon_literature));
        myCategory.add(new Ctg("Living place", "Find a new home", R.drawable.icon_living_place));
        myCategory.add(new Ctg("Sport", "Watch some sport events", R.drawable.icon_sport));
        myCategory.add(new Ctg("Party", "... till morning!", R.drawable.icon_party));
        myCategory.add(new Ctg("Culture", "Going to the theatre?", R.drawable.icon_culture));
        myCategory.add(new Ctg("Jobs", "Work, work...", R.drawable.icon_jobs));
        myCategory.add(new Ctg("Transport", "Need to get somewhere?", R.drawable.icon_transport));
    }

    private void populateCategorys(){
        ArrayAdapter<Ctg> myAdapter = new MyListAdapter();
        listViewDrawer = (ListView) findViewById(R.id.listViewDrawer);
        listViewDrawer.setAdapter(myAdapter);
    }

    private class MyListAdapter extends ArrayAdapter<Ctg> {

        public MyListAdapter(){
            super(MainActivity2.this, R.layout.category_list_view, myCategory); //class to access all the items; layout to use; items to use
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.category_list_view, parent, false);
            }
            //Find the movie to work with
            Ctg currentMovie = myCategory.get(position);

            //Fill the view
            ImageView imageView = (ImageView) itemView.findViewById(R.id.nav_icon);
            imageView.setImageResource(currentMovie.getIconID());

            //Title
            TextView title = (TextView) itemView.findViewById(R.id.title);
            title.setText(currentMovie.getTitle());

            //SubTitle
            TextView subTitle = (TextView) itemView.findViewById(R.id.subTitle);
            subTitle.setText(currentMovie.getSubTitle());

            return itemView;
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(), Login.class));
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
