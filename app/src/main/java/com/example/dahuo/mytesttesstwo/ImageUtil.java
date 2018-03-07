package com.example.dahuo.mytesttesstwo;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Created by dahuo on 2018/3/7.
 */

public class ImageUtil {

    public static Bitmap gray2Binary(Bitmap bm) {
        //得到图形的宽度和长度
        int width = bm.getWidth();
        int height = bm.getHeight();
        //创建二值化图像
        Bitmap binaryMap;
        binaryMap = bm.copy(Bitmap.Config.ARGB_8888, true);
        //依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //得到当前像素的值
                int col = binaryMap.getPixel(i, j);
                //得到alpha通道的值
                int alpha = col & 0xFF000000;
                //得到图像的像素RGB的值
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //对图像进行二值化处理
                if (gray <= 90) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // 新的ARGB
                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                //设置新图像的当前像素值
                binaryMap.setPixel(i, j, newColor);
            }
        }
        return binaryMap;
    }

    public static void saveBitmapFile(Bitmap bitmap, String filename) {
        File file = new File(filename);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public Bitmap bitmap2Gray(Bitmap bmSrc) {
//        // 得到图片的长和宽
//        int width = bmSrc.getWidth();
//        int height = bmSrc.getHeight();
//        // 创建目标灰度图像
//        Bitmap bmpGray;
//        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        // 创建画布
//        Canvas c = new Canvas(bmpGray);
//        Paint paint = new Paint();
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0);
//        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
//        paint.setColorFilter(f);
//        c.drawBitmap(bmSrc, 0, 0, paint);
//        return bmpGray;
//    }
//
//    public Bitmap lineGrey(Bitmap image) {
//        //得到图像的宽度和长度
//        int width = image.getWidth();
//        int height = image.getHeight();
//        //创建线性拉升灰度图像
//        Bitmap lineGray;
//        lineGray = image.copy(Bitmap.Config.ARGB_8888, true);
//        //依次循环对图像的像素进行处理
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                //得到每点的像素值
//                int col = image.getPixel(i, j);
//                int alpha = col & 0xFF000000;
//                int red = (col & 0x00FF0000) >> 16;
//                int green = (col & 0x0000FF00) >> 8;
//                int blue = (col & 0x000000FF);
//                // 增加了图像的亮度
//                red = (int) (1.1 * red + 30);
//                green = (int) (1.1 * green + 30);
//                blue = (int) (1.1 * blue + 30);
//                //对图像像素越界进行处理
//                if (red >= 255) {
//                    red = 255;
//                }
//
//                if (green >= 255) {
//                    green = 255;
//                }
//
//                if (blue >= 255) {
//                    blue = 255;
//                }
//                // 新的ARGB
//                int newColor = alpha | (red << 16) | (green << 8) | blue;
//                //设置新图像的RGB值
//                lineGray.setPixel(i, j, newColor);
//            }
//        }
//        return lineGray;
//    }
}
