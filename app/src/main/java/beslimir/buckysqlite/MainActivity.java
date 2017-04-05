package beslimir.buckysqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler, dbHandler2;
    EditText input;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.input);
        output = (TextView) findViewById(R.id.output);

        dbHandler = new DBHandler(this, null, null, 1);
        dbHandler2 = new DBHandler(this, null, null, 1);

        printDatabase();
    }

    public void addButtonClicked(View view){
        Products product = new Products(input.getText().toString());
        String a = input.getText().toString();
        dbHandler.addProduct(product);
        /////
        Cursor getIdNow = dbHandler2.getId(product);
        if(getIdNow.getCount() == 0){
            //Toast.makeText(MainActivity.this, "error", //Toast.LENGTH_SHORT).show();
        }
        getIdNow.moveToNext();
        String aaaa = getIdNow.getString(getIdNow.getColumnIndex("_id"));
        int bbbb = getIdNow.getInt(getIdNow.getColumnIndex("_id"));
        /////
        //Toast.makeText(MainActivity.this, "Added " + a + " id: " + bbbb, //Toast.LENGTH_SHORT).show();
        printDatabase();
    }

    public void deleteButtonClicked(View view){
        String inputText = input.getText().toString();
        dbHandler.deleteProduct(inputText);
        //Toast.makeText(MainActivity.this, "Deleted " + input.getText().toString(), //Toast.LENGTH_SHORT).show();
        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        output.setText(dbString);
        input.setText("");
    }
}
