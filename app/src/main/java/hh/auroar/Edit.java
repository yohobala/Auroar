package hh.auroar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

public class Edit extends AppCompatActivity {
    public EditText editText;
    public Button test;
    public Button button;
    public ImageView imageView;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private File output;
    private Uri imageUri;
    private static final int CROP_PHOTO = 2;
    private String str=null;
    private  SpannableString ss;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        editText = findViewById(R.id.edit);
        button = findViewById(R.id.button);
        test = findViewById(R.id.button2);
        imageView=findViewById(R.id.imageView);
        CompleteEdit();
        test();
    }

    public void CompleteEdit() {
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Edit.this, ImageAndVideo.class);
                i.putExtra("回传", editText.getText().toString());
                startActivity(i);
            }
        });

    }

    public void test() {
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Edit.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Edit.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

                    //权限还没有授予，需要在这里写申请权限的代码
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    choosePhoto();

                }
            }
        });
    }

    void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            /**
             * 从相册中选取图片的请求标志
             */

            case REQUEST_CODE_PICK_IMAGE:
                //if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri是上一个Activity返回的
                         */
                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        ImageSpan imgSpan = new ImageSpan(Edit.this,bit);
                        ss.setSpan(imgSpan,1,20, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                        editText.setText(ss);
                        imageView.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                       // Log.d("tag", e.getMessage());
                        //Toast.makeText(this, "程序崩溃", Toast.LENGTH_SHORT).show();
                    }
                //} else {
                    //Log.i("liang", "失败");
                //}

                break;

            default:
                break;
        }
    }
}
