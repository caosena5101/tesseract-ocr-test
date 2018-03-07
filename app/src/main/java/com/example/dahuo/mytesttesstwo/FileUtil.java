package com.example.dahuo.mytesttesstwo;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by dahuo on 2018/3/7.
 */

public class FileUtil {

    public boolean copyFilesFromAssets(Context context, String oldPath, String newPath) {
        boolean ret = false;
        try {
            String fileNames[] = context.getAssets().list(oldPath);
            List<String> list = Arrays.asList(fileNames);
            if (list.contains(oldPath)) {
                File of = new File(oldPath);
                if (!of.isDirectory()) {
                    InputStream is = context.getAssets().open(oldPath);
                    FileOutputStream fos = new FileOutputStream(new File(newPath));
                    byte[] buffer = new byte[1024];
                    int byteCount;
                    while ((byteCount = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, byteCount);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                    ret = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
