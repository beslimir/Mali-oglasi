package beslimir.buckysqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        Toast.makeText(ForgotPassword.this, "Can't access the internet", Toast.LENGTH_SHORT).show();
    }
}
