package beslimir.buckysqlite;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Insert extends AppCompatActivity implements View.OnClickListener{

    private EditText etTitle, etDetail, etPrice, etNumber, etImageUrl;
    private TextView tvTime, tvDate;
    private ImageView ivInsert, ivCancel;
    private String currentDateandTime, posCtgId, userId, datePublished;
    int pos;
    //timer
    public ImageView ivTimer;
    static final int DIALOG_ID = 0;
    private int hour, minute;
    //dater
    public ImageView ivDate;
    static final int DIALOG_ID2 = 10;
    int year, month, day;
    //spinner
    private String[] arraySpinner;
    String spinnerText;
    private DBHelper dbInsert;
    String myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);

        //set back button on actionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbInsert = new DBHelper(this);

        //add timer
        showTimeDialog();
        //add dater xD
        final Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        showDateDialog();

        /////spinner
        this.arraySpinner = new String[] {
                "Instructions", "Literature", "Living place", "Sport", "Party", "Culture", "Jobs", "Transport"
        };
        final Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        //s.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                spinnerText = s.getSelectedItem().toString();
                //id of category - skip Home and 0 index
                pos = position + 2;
                posCtgId = Integer.toString(pos);
                //Toast.makeText(getBaseContext(), spinnerText + " " + pos, //Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //Toast.makeText(getBaseContext(), "You have to select a category", //Toast.LENGTH_SHORT).show();
            }
        });
        /////
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDetail = (EditText) findViewById(R.id.etDetail);
        etPrice = (EditText) findViewById(R.id.etPrice);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        //etImageUrl = (EditText) findViewById(R.id.etImageUrl);
        ivInsert = (ImageView) findViewById(R.id.ivInsert);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        ivInsert.setOnClickListener(this);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }

    //timer
    public void showTimeDialog(){
        ivTimer = (ImageView) findViewById(R.id.ivTimer);
        ivTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID);
            }
        });
    }

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourDay, int minuteDay){
            hour = hourDay;
            minute = minuteDay;

            if(hour < 10 && minute < 10){
                ////Toast.makeText(Insert.this, "0" + hour + ":0" + minute, //Toast.LENGTH_LONG).show();
                tvTime.setText("0" + hour + ":0" + minute);
            }
            else if(hour < 10 && minute > 9){
               ////Toast.makeText(Insert.this, "0" + hour + ":" + minute, //Toast.LENGTH_LONG).show();
                tvTime.setText("0" + hour + ":" + minute);
            }
            else if(hour > 9 && minute < 10){
                ////Toast.makeText(Insert.this, hour + ":0" + minute, //Toast.LENGTH_LONG).show();
                tvTime.setText(hour + ":0" + minute);
            }
            else{
                ////Toast.makeText(Insert.this, hour + ":" + minute, //Toast.LENGTH_LONG).show();
                tvTime.setText(hour + ":" + minute);
            }
        }
    };

    //date
    public void showDateDialog(){
        ivDate = (ImageView) findViewById(R.id.ivDate);
        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_ID2);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int yearYear, int monthYear, int dayYear){
            year = yearYear;
            month = monthYear + 1;
            day = dayYear;

            if(day < 10 && month < 10){
                ////Toast.makeText(Insert.this, year + ".0" + month + ".0" + day, //Toast.LENGTH_LONG).show();
                tvDate.setText("0" + day + ".0" + month + "." + year + ".");
            }
            else if(day < 10 && month > 10){
                ////Toast.makeText(Insert.this, "0" + year + "." + month + "." + day, //Toast.LENGTH_LONG).show();
                tvDate.setText("0" + day + "." + month + "." + year + ".");
            }
            else if(day > 10 && month < 10){
                ////Toast.makeText(Insert.this, year + ".0" + month + "." + day, //Toast.LENGTH_LONG).show();
                tvDate.setText(day + ".0" + month + "." + year + ".");
            }
            else{
                ////Toast.makeText(Insert.this, year + "." + month + "." + day, //Toast.LENGTH_LONG).show();
                tvDate.setText(day + "." + month + "." + year + ".");
            }
        }
    };

    //both; timer and date
    @Override
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID){
            return new TimePickerDialog(Insert.this, kTimePickerListener, hour, minute, true);
        }
        else if(id == DIALOG_ID2){
            return new DatePickerDialog(Insert.this, dPickerListener, year, month, day);
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        //first, get user_name
        Cursor get_user_nameNow = dbInsert.get_user_name();
        if(get_user_nameNow.getCount() == 0){
            ////Toast.makeText(Insert.this, "error user_name", //Toast.LENGTH_SHORT).show();
        }
        get_user_nameNow.moveToLast();
        String myUser = get_user_nameNow.getString(get_user_nameNow.getColumnIndex("user_name"));
        ////Toast.makeText(Insert.this, "Username: " + myUser, //Toast.LENGTH_SHORT).show();

        //get system date for date_published
        datePublished = new SimpleDateFormat("dd.MM.yyyy.").format(new Date());

        //Insert a new post with all informations
        CPost post = new CPost(etTitle.getText().toString(),
                etDetail.getText().toString(),
                Integer.parseInt(etPrice.getText().toString()),
                tvDate.getText().toString(),
                tvTime.getText().toString(),
                0,
                spinnerText, //which category
                myUser, //user_name
                datePublished
        );
        dbInsert.post(post);

        ////Toast.makeText(Insert.this, "Inserted " + etTitle.getText().toString(), //Toast.LENGTH_SHORT).show();

        //send user_name to MainActivity2
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        i.putExtra("key", myUser);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                Intent intent = new Intent(this, MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
