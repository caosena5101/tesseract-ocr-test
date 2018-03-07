package com.example.dahuo.mytesttesstwo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TessBaseAPI mTess = null;
    private String BASE_PATH = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preinit();
        TextView tv = findViewById(R.id.tv_start);
        tv.setOnClickListener(this);
    }

    private void init() {
        BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "11";
    }

    private void preinit() {
        ArrayList<String> needPermission = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            needPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            needPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            needPermission.add(Manifest.permission.CAMERA);
        }

        if (needPermission.size() <= 0) {
            init();
        } else {
            String[] permission = new String[needPermission.size()];
            int i = 0;
            for (String per : needPermission) {
                permission[i] = per;
                i += 1;
            }
            ActivityCompat.requestPermissions(this, permission, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123) {
            int index = 0;
            String per = null;
            for (int ret : grantResults) {
                if (ret != PackageManager.PERMISSION_GRANTED) {
                    per = permissions[index];
                    break;
                }
                index += 1;
            }
            if (null == per) {
                init();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                new Thread() {
                    @Override
                    public void run() {
                        mTess = new TessBaseAPI();
                        mTess.init(BASE_PATH, "chi_sim");
                        mTess.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?");
                        mTess.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO);
                        Bitmap bmp = BitmapFactory.decodeFile(BASE_PATH + File.separator + "1.png");
                        if (bmp != null) {
                            Bitmap temp = ImageUtil.gray2Binary(bmp);
                            ImageUtil.saveBitmapFile(temp,BASE_PATH + File.separator + "t1.jpg");
                            mTess.setImage(temp);
                            final String result = mTess.getUTF8Text();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView tv = findViewById(R.id.tv_result);
                                    tv.setText(result);
                                    mTess.clear();
                                    mTess.end();
                                }
                            });
                        }
                    }
                }.start();
                break;
        }
    }
}
