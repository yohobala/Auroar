package hh.auroar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageAndVideo extends AppCompatActivity {
    public ImageButton edit;
    public TextView Text;
    public LinearLayout linearLayout;
    public LinearLayout linearLayout2;
    public RecyclerView RecyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);
        linearLayout=findViewById(R.id.linearLayout);
        edit=findViewById(R.id.imageButton);
        Text=findViewById(R.id.textView);
        Edit();

        Intent intent =getIntent();
        Text.setText(intent.getStringExtra("回传"));
    }
    public void Edit(){
     edit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent i=new Intent(ImageAndVideo.this, Edit.class);
             startActivity(i);
         }
     });
    }
}
