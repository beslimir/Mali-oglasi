package beslimir.buckysqlite;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity{

    TextView tvTitleDetail, tvTitleDetail2, tvUserDetail, tvDatePublishedDetail, tvLikesDetail, tvDetailDetail, tvPriceDetail, tvDateDetail, tvTimeDetail, tvPhoneDetail, tvUserDetail2, tvClickHere, tvLikeText, tvFollowUserText;
    DBHelper dbDetail;
    LinearLayout ll_likePost, ll_followUser;
    int counterLikes;
    ColorStateList oldColors;
    String myUser, previousActivity;
    boolean isLiked, isPostFollowing, isUserFollowing;
    Cursor heLiked, heFollowedPost, heFollowedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //references
        tvTitleDetail = (TextView) findViewById(R.id.tvTitleDetail);
        tvTitleDetail2 = (TextView) findViewById(R.id.tvTitleDetail2);
        tvUserDetail = (TextView) findViewById(R.id.tvUserDetail);
        tvUserDetail2 = (TextView) findViewById(R.id.tvUserDetail2);
        tvDatePublishedDetail = (TextView) findViewById(R.id.tvDatePublishedDetail);
        tvLikesDetail = (TextView) findViewById(R.id.tvLikesDetail);
        tvDetailDetail = (TextView) findViewById(R.id.tvDetailDetail);
        tvPriceDetail = (TextView) findViewById(R.id.tvPriceDetail);
        tvDateDetail = (TextView) findViewById(R.id.tvDateDetail);
        tvTimeDetail = (TextView) findViewById(R.id.tvTimeDetail);
        tvClickHere = (TextView) findViewById(R.id.tvClickHere);
        tvLikeText = (TextView) findViewById(R.id.tvLikeText);
        tvFollowUserText = (TextView) findViewById(R.id.tvFollowUserText);
        ll_likePost = (LinearLayout) findViewById(R.id.ll_likePost);
        ll_followUser = (LinearLayout) findViewById(R.id.ll_followUser);

        dbDetail = new DBHelper(this);

        Intent in = getIntent();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvUserDetail.setText(extras.getString("user_name"));
            tvTitleDetail.setText(extras.getString("title_post"));
            previousActivity = in.getStringExtra("FROM_ACTIVITY");
        }

        displayDetails(tvUserDetail.getText().toString(), tvTitleDetail.getText().toString());

        //onNumberClick, call
        /*tvPhoneDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String phone_no = tvPhoneDetail.getText().toString().replaceAll("-", "");
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phone_no));
                startActivity(callIntent);
                return false;
            }
        });*/

        //enter profile of the user who posted
        tvClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Details.this, ProfileNotMe.class);
                in.putExtra("user_name_not_me", tvUserDetail.getText().toString());
                startActivity(in);
            }
        });

        //get user_name
        Cursor get_user_nameNow = dbDetail.get_user_name();
        if(get_user_nameNow.getCount() == 0){
            //Toast.makeText(Details.this, "error user_name", //Toast.LENGTH_SHORT).show();
        }else{
            get_user_nameNow.moveToLast();
            myUser = get_user_nameNow.getString(get_user_nameNow.getColumnIndex("user_name"));
        }

        oldColors = tvLikeText.getTextColors(); //save original color

        //check if I liked that post
        heLiked = dbDetail.isItLiked(myUser, tvUserDetail.getText().toString(), tvTitleDetail.getText().toString());
        heLiked.moveToFirst();
        //if didn't liked, set as usual
        if(heLiked.getInt(0) == 0) {
            tvLikeText.setTextColor(oldColors);//restore original color
            tvLikeText.setText("Like this post");
            ll_likePost.setBackgroundColor(Color.parseColor("#D6EFBF"));
        }else{
            //if liked, set liked situation
            tvLikeText.setTextColor(oldColors);//restore original color
            tvLikeText.setText("Liked!");
            ll_likePost.setBackgroundColor(Color.parseColor("#C3F099"));
        }

        //like
        ll_likePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String likes = tvLikesDetail.getText().toString();
                int likesPost = Integer.parseInt(likes);
                //check if I liked that post
                heLiked = dbDetail.isItLiked(myUser, tvUserDetail.getText().toString(), tvTitleDetail.getText().toString());
                heLiked.moveToFirst();
                //if I didn't like, like it now...
                if(heLiked.getInt(0) == 0) {
                    isLiked = dbDetail.liked(myUser,
                            tvUserDetail.getText().toString(),
                            tvTitleDetail.getText().toString()
                    );
                    tvLikesDetail.setText(Integer.toString(likesPost + 1));
                    //like
                    //save likes to SQLite
                    dbDetail.incrementThanksPost(tvUserDetail.getText().toString(), tvTitleDetail.getText().toString(), likesPost + 1);
                    oldColors =  tvLikeText.getTextColors(); //save original color
                    tvLikeText.setTextColor(oldColors);//restore original color
                    tvLikeText.setText("Liked!");
                    ll_likePost.setBackgroundColor(Color.parseColor("#C3F099"));
                    //Toast.makeText(Details.this, "Liked", //Toast.LENGTH_SHORT).show();
                }else{
                    //if I want to dislike the post...
                    Cursor likedPostDeleteNow = dbDetail.likedPostDelete(myUser, tvUserDetail.getText().toString(), tvTitleDetail.getText().toString());
                    if (likedPostDeleteNow.getCount() == 0) {
                        //Toast.makeText(Details.this, "like deleted", //Toast.LENGTH_SHORT).show();
                        //dislike
                        tvLikesDetail.setText(Integer.toString(likesPost - 1));
                        dbDetail.decrementThanksPost(tvUserDetail.getText().toString(), tvTitleDetail.getText().toString(), likesPost - 1);
                        tvLikeText.setTextColor(oldColors);//restore original color
                        tvLikeText.setText("Like this post");
                        ll_likePost.setBackgroundColor(Color.parseColor("#D6EFBF"));
                        //Toast.makeText(Details.this, "Disliked", //Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        likedPostDeleteNow.moveToNext();
                        //Toast.makeText(Details.this, "like NOT deleted", //Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //check if I'm following that user
        heFollowedUser = dbDetail.isUserFollowed(myUser, tvUserDetail.getText().toString());
        heFollowedUser.moveToFirst();
        //if I'm not following, set as usual
        if(heFollowedUser.getInt(0) == 0) {
            tvFollowUserText.setTextColor(oldColors);//restore original color
            tvFollowUserText.setText("Follow this user");
            ll_followUser.setBackgroundColor(Color.parseColor("#D6EFBF"));
        }else{
            //if following, set follow situation
            tvFollowUserText.setTextColor(oldColors);//restore original color
            tvFollowUserText.setText("Following!");
            ll_followUser.setBackgroundColor(Color.parseColor("#C3F099"));
        }

        //follow user
        ll_followUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if I'm following that post
                heFollowedUser = dbDetail.isUserFollowed(myUser, tvUserDetail.getText().toString());
                heFollowedUser.moveToFirst();
                //if I'm not following, follow it now...
                if(heFollowedUser.getInt(0) == 0) {
                    isUserFollowing = dbDetail.followedUser(myUser,
                            tvUserDetail.getText().toString()
                    );
                    //save that you're following this post to SQLite
                    oldColors =  tvLikeText.getTextColors(); //save original color
                    tvFollowUserText.setTextColor(oldColors);//restore original color
                    tvFollowUserText.setText("Following!");
                    ll_followUser.setBackgroundColor(Color.parseColor("#C3F099"));
                    //Toast.makeText(Details.this, "Following", //Toast.LENGTH_SHORT).show();
                }else{
                    //if I want to stop following the post...
                    Cursor followedUserDeleteNow = dbDetail.followedUserDelete(myUser, tvUserDetail.getText().toString());
                    if (followedUserDeleteNow.getCount() == 0) {
                        //Toast.makeText(Details.this, "following deleted", //Toast.LENGTH_SHORT).show();
                        tvFollowUserText.setTextColor(oldColors);//restore original color
                        tvFollowUserText.setText("Follow this user");
                        ll_followUser.setBackgroundColor(Color.parseColor("#D6EFBF"));
                        //Toast.makeText(Details.this, "Stopped following", //Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        followedUserDeleteNow.moveToNext();
                        //Toast.makeText(Details.this, "following NOT stopped", //Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    //display all data
    private void displayDetails(String title_post, String user_name){
        Cursor getDetailsNow = dbDetail.getDetails(title_post, user_name);
        if(getDetailsNow.getCount() == 0){
            //Toast.makeText(Details.this, "No data found.", //Toast.LENGTH_SHORT).show();
        }else{
            getDetailsNow.moveToFirst();
            tvTitleDetail.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("title_post")));
            tvTitleDetail2.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("title_post")));
            tvUserDetail.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("user_name")));
            tvUserDetail2.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("user_name")));
            tvDatePublishedDetail.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("date_published")));
            tvLikesDetail.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("thanks_post")));
            tvDetailDetail.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("text_post")));
            tvPriceDetail.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("price")));
            tvDateDetail.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("date_post")));
            tvTimeDetail.setText(getDetailsNow.getString(getDetailsNow.getColumnIndex("time_post")));
        }
        getDetailsNow.moveToLast();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed(previousActivity);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onBackPressed(String previousActivity) {
        //check from which activity we came, so the likes will automatically update in the listView
        if(previousActivity.equals("Main")){
            startActivity(new Intent(Details.this, MainActivity2.class));
        }else if(previousActivity.equals("Profile")){
            startActivity(new Intent(Details.this, Profile.class));
        }else if(previousActivity.equals("ProfileNotMe")){
            startActivity(new Intent(Details.this, ProfileNotMe.class));
        }
    }
}
