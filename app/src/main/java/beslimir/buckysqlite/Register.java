package beslimir.buckysqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText user_name, password, email, first_name, last_name;
    private ImageView registration, ivCancel;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private String currentDateandTime;
    private DBHelper dbRegister, dbId, dbCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        dbRegister = new DBHelper(this);
        dbId = new DBHelper(this);
        dbCheck = new DBHelper(this);

        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        registration = (ImageView) findViewById(R.id.registration);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

        //Set date and time of registration
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
        currentDateandTime = sdf.format(new Date());

        registration.setOnClickListener(this);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(Register.this, "Clicked", //Toast.LENGTH_SHORT).show();

        // get selected radio button from radioGroup
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        // find the radio button by returned id
        radioSexButton = (RadioButton) findViewById(selectedId);

        String checkUserName = user_name.getText().toString();
        String checkEMail = email.getText().toString();

        //User validation
        Cursor checkUsername = dbCheck.checkRegisterUsername(checkUserName);
        if(checkUsername.getCount() == 0){
            Cursor checkEmail = dbCheck.checkRegisterEmail(checkEMail);
            if(checkEmail.getCount() == 0){
                String emailInput = email.getText().toString().trim();
                String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                //validate email address
                if (emailInput.matches(emailPattern)) {
                    //Toast.makeText(Register.this, "valid email address", //Toast.LENGTH_SHORT).show();
                    //Insert a new user with his informations
                    CUser user = new CUser(user_name.getText().toString(),
                            password.getText().toString(),
                            email.getText().toString(),
                            first_name.getText().toString(),
                            last_name.getText().toString(),
                            radioSexButton.getText().toString(),
                            currentDateandTime,
                            0,
                            0,
                            "Mostar"
                    );
                    dbCheck.register(user);

                    ///// Get the id from this user and send it to Insert activity and then to MainActivity2
                    Cursor getIdNow = dbCheck.getId(user);
                    if(getIdNow.getCount() == 0){
                        //Toast.makeText(Register.this, "error id", //Toast.LENGTH_SHORT).show();
                    }
                    getIdNow.moveToLast();
                    int bbbb = getIdNow.getInt(getIdNow.getColumnIndex("user_id"));
                    /////

                    //Toast.makeText(Register.this, "Added " + checkUserName + " id: " + bbbb, //Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(), Login.class));
                }else{
                    //Toast.makeText(Register.this, "Invalid email address", //Toast.LENGTH_SHORT).show();
                }
            }else{
                //Toast.makeText(Register.this, "Error email", //Toast.LENGTH_SHORT).show();
            }
        }else{
            //Toast.makeText(Register.this, "Error user_name", //Toast.LENGTH_SHORT).show();
        }
    }
}
