package beslimir.buckysqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentTab extends Fragment {

    TabHost tabHost;
    ListView lvProduct, lvFavorites;
    DBHelper dbTab;
    String myUser, myTitle, myUserFromList, myUserName;
    private String[] usersArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        //get data from another activity
        MainActivity2 activity = (MainActivity2) getActivity();
        final String myData = activity.getMyData();

        //references
        tabHost = (TabHost) view.findViewById(R.id.tabHost);
        lvProduct = (ListView) view.findViewById(R.id.lvProduct);
        lvFavorites = (ListView) view.findViewById(R.id.lvFavorites);

        dbTab = new DBHelper(getContext());

        //get user_name
        Cursor get_user_nameNow = dbTab.get_user_name();
        if(get_user_nameNow.getCount() == 0){
            //Toast.makeText(getContext(), "error user_name", //Toast.LENGTH_SHORT).show();
        }else{
            get_user_nameNow.moveToLast();
            myUserName = get_user_nameNow.getString(get_user_nameNow.getColumnIndex("user_name"));
        }

        registerForContextMenu(lvProduct);
        registerForContextMenu(lvFavorites);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("All posts");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tab2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Favorites");
        tabHost.addTab(tabSpec);

        switch(myData){
            case "Početna":
                //fill the listView
                displayEverything("null");
                break;
            case "Instrukcije":
                displayEverything("Instructions");
                break;
            case "Literatura":
                displayEverything("Literature");
                break;
            case "Stanovi":
                displayEverything("Living place");
                break;
            case "Sport":
                displayEverything("Sport");
                break;
            case "Zabava":
                displayEverything("Party");
                break;
            case "Kultura":
                displayEverything("Culture");
                break;
            case "Posao":
                displayEverything("Jobs");
                break;
            case "Prijevoz":
                displayEverything("Transport");
                break;
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (myData) {
                    case "Početna":
                        if ("tab1".equals(tabId)){
                            displayEverything("null");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("null");
                        }
                        break;
                    case "Instrukcije":
                        if ("tab1".equals(tabId)){
                            displayEverything("Instructions");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("Instructions");
                        }
                        break;
                    case "Literatura":
                        if ("tab1".equals(tabId)){
                            displayEverything("Literature");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("Literature");
                        }
                        break;
                    case "Stanovi":
                        if ("tab1".equals(tabId)){
                            displayEverything("Living place");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("Living place");
                        }
                        break;
                    case "Sport":
                        if ("tab1".equals(tabId)){
                            displayEverything("Sport");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("Sport");
                        }
                        break;
                    case "Zabava":
                        if ("tab1".equals(tabId)){
                            displayEverything("Party");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("Party");
                        }
                        break;
                    case "Kultura":
                        if ("tab1".equals(tabId)){
                            displayEverything("Culture");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("Culture");
                        }
                        break;
                    case "Posao":
                        if ("tab1".equals(tabId)){
                            displayEverything("Jobs");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("Jobs");
                        }
                        break;
                    case "Prijevoz":
                        if ("tab1".equals(tabId)){
                            displayEverything("Transport");
                        }else if("tab2".equals(tabId)){
                            displayEverything2("Transport");
                        }
                        break;
                }
            }
        });

        //on item click
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtTitle = (TextView) parent.getChildAt(position - lvProduct.getFirstVisiblePosition()).findViewById(R.id.tvTitle);
                TextView txtUser = (TextView) parent.getChildAt(position - lvProduct.getFirstVisiblePosition()).findViewById(R.id.tvTitleDetail2);
                String myTitle = txtTitle.getText().toString();
                String myUser = txtUser.getText().toString();
                ////Toast.makeText(getContext(), myTitle + " " + myUser, //Toast.LENGTH_SHORT).show();
                detailedVersion(myUser, myTitle);
            }
        });
        lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                TextView txtTitle = (TextView) parent.getChildAt(position - lvProduct.getFirstVisiblePosition()).findViewById(R.id.tvTitle);
                TextView txtUser = (TextView) parent.getChildAt(position - lvProduct.getFirstVisiblePosition()).findViewById(R.id.tvTitleDetail2);
                myTitle = txtTitle.getText().toString();
                myUserFromList = txtUser.getText().toString();
                ////Toast.makeText(getContext(), myTitle, //Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //on item click
        lvFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtTitle = (TextView) parent.getChildAt(position - lvFavorites.getFirstVisiblePosition()).findViewById(R.id.tvTitle);
                TextView txtUser = (TextView) parent.getChildAt(position - lvFavorites.getFirstVisiblePosition()).findViewById(R.id.tvTitleDetail2);
                String myTitle = txtTitle.getText().toString();
                String myUser = txtUser.getText().toString();
                //Toast.makeText(getContext(), myTitle + " " + myUser, //Toast.LENGTH_SHORT).show();
                detailedVersion(myUser, myTitle);
            }
        });
        lvFavorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                TextView txtTitle = (TextView) parent.getChildAt(position - lvFavorites.getFirstVisiblePosition()).findViewById(R.id.tvTitle);
                TextView txtUser = (TextView) parent.getChildAt(position - lvFavorites.getFirstVisiblePosition()).findViewById(R.id.tvTitleDetail2);
                myTitle = txtTitle.getText().toString();
                myUserFromList = txtUser.getText().toString();
                //Toast.makeText(getContext(), myTitle, //Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.list_context_menu, menu);
    }

    //Update or remove
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Cursor get_user_nameNow = dbTab.get_user_name();
        if(get_user_nameNow.getCount() == 0){
            //Toast.makeText(getContext(), "error user_name", //Toast.LENGTH_SHORT).show();
        }else{
            get_user_nameNow.moveToLast();
            myUser = get_user_nameNow.getString(get_user_nameNow.getColumnIndex("user_name"));
            //Toast.makeText(getContext(), "Username: " + myUser, //Toast.LENGTH_SHORT).show();
        }

        if(item.getItemId() == R.id.menuUpdate){
            //check if it's my post
            if(isItMine(myUserName) == true){
                //you can update your post
                //Toast.makeText(getContext(), "It's your post", //Toast.LENGTH_SHORT).show();
                Cursor getPostNow = dbTab.getPost(myUser, myTitle);
                if(getPostNow.getCount() == 0){
                    //Toast.makeText(getContext(), "No data found.", //Toast.LENGTH_SHORT).show();
                }else {
                    Intent in = new Intent(getContext(), Update.class);
                    in.putExtra("user_name", myUser);
                    in.putExtra("title_post", myTitle);
                    startActivity(in);
                }
            }else{
                //you can't update a post from other users
                //Toast.makeText(getContext(), "It's NOT your post", //Toast.LENGTH_SHORT).show();
            }
        }else if(item.getItemId() == R.id.menuOpenProfile){
            //is it you?
            if(isItMine(myUserName) == true){
                startActivity(new Intent(getContext(), Profile.class));
            }else{
                //other user
                //Toast.makeText(getContext(), "It's NOT your profile", //Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getContext(), ProfileNotMe.class);
                in.putExtra("user_name_not_me", myUserFromList);
                startActivity(in);
            }
        }else if(item.getItemId() == R.id.menuRemove){
            if(isItMine(myUserName) == true){
                //you can remove your post
                //Toast.makeText(getContext(), "It's your post", //Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alert =  new AlertDialog.Builder(getContext());
                alert.setMessage("Do you want to remove " + myTitle + "?");
                alert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cursor deletePostNow = dbTab.deletePost(myUser, myTitle);
                        if(deletePostNow.getCount() == 0){
                            //Toast.makeText(getContext(), "Post deleted: " + myTitle, //Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            deletePostNow.moveToNext();
                            //Toast.makeText(getContext(), "Post not deleted...", //Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.setNegativeButton("Cancel", null);
                alert.show();
            }else{
                //you can't remove a post from other users
                //Toast.makeText(getContext(), "It's NOT your post", //Toast.LENGTH_SHORT).show();
            }
        }
        return super.onContextItemSelected(item);
    }

    //populate list view
    private void populateListView(String parameter){
        Cursor cursor = dbTab.getAllDataCategory(parameter);
        //get data from SQLite into arrays
        String[] fromData = new String[] {DBHelper.COL6_2, DBHelper.COL6_10, DBHelper.COL6_8, DBHelper.COL6_11, DBHelper.COL6_4};
        int[] ids = new int[] {R.id.tvTitle, R.id.tvTitleDetail2, R.id.tvLikes, R.id.tvDate, R.id.tvPrice};
        //define SimpleCursorAdapter
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.item_layout, cursor, fromData, ids, 0);
        //reference the listView
        lvProduct.setAdapter(myCursorAdapter);
    }

    //display all data in a list view
    private void displayEverything(String parameter){
        Cursor getAllDataNow = dbTab.getAllDataCategory(parameter);
        if(getAllDataNow.getCount() == 0){
            //Toast.makeText(getActivity(), "No data found.", //Toast.LENGTH_SHORT).show();
        }else{
            getAllDataNow.moveToFirst();
            populateListView(parameter);
        }
        getAllDataNow.moveToLast();
    }

    //populate list view2
    private void populateListView2(String parameter, String[] usersArray){
        Cursor cursor2 = dbTab.getFollowedUsersPost(parameter, usersArray);
        //get data from SQLite into arrays
        String[] fromData2 = new String[] {DBHelper.COL6_2, DBHelper.COL6_10, DBHelper.COL6_8, DBHelper.COL6_11, DBHelper.COL6_4};
        int[] ids2 = new int[] {R.id.tvTitle, R.id.tvTitleDetail2, R.id.tvLikes, R.id.tvDate, R.id.tvPrice};
        //define SimpleCursorAdapter
        SimpleCursorAdapter myCursorAdapter2;
        myCursorAdapter2 = new SimpleCursorAdapter(getContext(), R.layout.item_layout, cursor2, fromData2, ids2, 0);
        //reference the listView
        lvFavorites.setAdapter(myCursorAdapter2);
    }

    //display all data in a list view2
    private void displayEverything2(String parameter){
        //get array of followed users
        Cursor getFollowedUsersNow = dbTab.getFollowedUsers(myUserName);
        if(getFollowedUsersNow.getCount() == 0){
            //Toast.makeText(getActivity(), "No users followed.", //Toast.LENGTH_SHORT).show();
        }else{
            int i = 0;
            String followedUser;
            usersArray = new String[getFollowedUsersNow.getCount()];
            while(getFollowedUsersNow.moveToNext()){
                followedUser = getFollowedUsersNow.getString(getFollowedUsersNow.getColumnIndex("user_name_followed"));
                usersArray[i] = followedUser;
                i++;
            }
            populateListView2(parameter, usersArray);
        }
        getFollowedUsersNow.moveToLast();
    }

    private boolean isItMine(String myUserName){
        /*Cursor isItMineNow = dbTab.isItMine(user_name);
        if(isItMineNow.getCount() == 0){
            //Toast.makeText(getContext(), "error my_user_name", //Toast.LENGTH_SHORT).show();
            return false;
        }else{
            isItMineNow.moveToLast();
            //Toast.makeText(getContext(), "It's my post: " + user_name, //Toast.LENGTH_SHORT).show();
            return true;
        }*/
        if(myUserName.equals(myUserFromList)){
            return true;
        }else{
            return false;
        }
    }

    private void detailedVersion(String user_name, String title_post){
        Cursor getDetailsNow = dbTab.detailedVersion(user_name, title_post);
        if(getDetailsNow.getCount() == 0){
            //Toast.makeText(getContext(), "error details", //Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(getContext(), "Opening...", //Toast.LENGTH_SHORT).show();
            Intent in = new Intent(getActivity(), Details.class);
            in.putExtra("user_name", user_name);
            in.putExtra("title_post", title_post);
            in.putExtra("FROM_ACTIVITY", "Main");
            startActivity(in);
        }
    }

}
