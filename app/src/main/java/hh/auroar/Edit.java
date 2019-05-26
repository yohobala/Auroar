package hh.auroar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Edit extends AppCompatActivity {
    public EditText editText;
public Button button;
    public Text text;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        editText = findViewById(R.id.edit);
        button=findViewById(R.id.button);
        test();
    }
    public  void  test() {
        button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent i = new Intent(Edit.this, ImageAndVideo.class);
            i.putExtra("回传",editText.getText().toString());
            startActivity(i);
        }
    });

    }
}
