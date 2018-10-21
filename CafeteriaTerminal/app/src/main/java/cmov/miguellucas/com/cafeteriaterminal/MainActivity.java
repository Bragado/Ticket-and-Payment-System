package cmov.miguellucas.com.cafeteriaterminal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnReadOrderClick(View v) {
        Toast.makeText(this,"Clicked read order",Toast.LENGTH_SHORT).show();
    }

    public void btnClientListClick(View v) {
        Toast.makeText(this,"Clicked client list",Toast.LENGTH_SHORT).show();
    }
}
