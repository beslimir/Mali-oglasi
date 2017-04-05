package beslimir.buckysqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView forgot_password;
    private EditText user_name, password;
    private Button login, registration;
    private String currentDateandTime;
    private CheckBox chk;
    private DBHelper dbLogin;
    int myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbLogin = new DBHelper(this);

        anyoneRemembered();

        registerScreen();
        forgotPasswordScreen();

        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        chk = (CheckBox) findViewById(R.id.chk);

        //Set date and time of login
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        currentDateandTime = sdf.format(new Date());

        //checkbox - stay logged in
        chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if (isChecked) {
                    //Toast.makeText(Login.this, "Cekirano", //Toast.LENGTH_SHORT).show();
                    //code for stay logged in
                    boolean remember = dbLogin.rememberTheName(user_name.getText().toString(),
                            password.getText().toString()
                    );
                    if (remember) {
                        //Toast.makeText(Login.this, "User remembered: " + user_name.getText().toString(), //Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(Login.this, "User not remembered", //Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Cursor deleteTheNameNow = dbLogin.deleteTheName();
                    if (deleteTheNameNow.getCount() == 0) {
                        //Toast.makeText(Login.this, "User not remembered, deleted: " + user_name.getText().toString(), //Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        deleteTheNameNow.moveToNext();
                        //Toast.makeText(Login.this, "ne valja nesto xD", //Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(Login.this, "Nije cekirano", //Toast.LENGTH_SHORT).show();
                }
            }
        });
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //check if user_name and password are correct
        Cursor isLoginValidNow = dbLogin.isLoginValid(user_name.getText().toString(), password.getText().toString());
        if(isLoginValidNow.getCount() == 1){
            boolean isInserted = dbLogin.login(currentDateandTime,
                    user_name.getText().toString()
            );
            if(isInserted){
                //Toast.makeText(Login.this, "Logged in: " + user_name.getText().toString(), //Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(i);
            }else{
                //Toast.makeText(Login.this, "Error by logging in...", //Toast.LENGTH_SHORT).show();
            }
        }else {
            //Toast.makeText(Login.this, "Not valid", //Toast.LENGTH_SHORT).show();
        }
    }

    public void registerScreen(){
        registration = (Button) findViewById(R.id.registration);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

    public void forgotPasswordScreen(){
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });
    }

    public void anyoneRemembered(){
        Cursor heIsRemembered = dbLogin.isRemembered();
        heIsRemembered.moveToFirst();
        if(heIsRemembered.getInt(0) > 0){
            //first, get user_name
            Cursor getRememberedUserNow = dbLogin.getRememberedUser();
            if(getRememberedUserNow.getCount() == 0){
                //Toast.makeText(Login.this, "error user_name", //Toast.LENGTH_SHORT).show();
            }
            getRememberedUserNow.moveToLast();
            String myUser = getRememberedUserNow.getString(getRememberedUserNow.getColumnIndex("user_name"));
            //Toast.makeText(Login.this, "User already remembered: " + myUser, //Toast.LENGTH_SHORT).show();
            //Start Insert activity
            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(i);
        }else{
            //Toast.makeText(Login.this, "Go on...", //Toast.LENGTH_SHORT).show();
        }
    }
}

