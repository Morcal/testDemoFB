package cn.feibo.jodedemo.Dao.utils;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.feibo.jodedemo.AppContext;
import cn.feibo.jodedemo.R;
import cn.feibo.jodedemo.cache.DataProvider;
import cn.feibo.jodedemo.widget.VImageView;
import pl.droidsonroids.gif.GifDrawable;

import fbcore.cache.image.ImageLoader;
import fbcore.cache.image.ImageLoader.DefaultLoadListener;
import fbcore.cache.image.ImageLoader.ImageContainer;
import fbcore.log.LogUtil;


public class UIUtil {

    private static final float SCALE = 16f / 9;

    private UIUtil() {
    }

    public static void setVAvatar(String url, boolean isSensation, VImageView img) {
        UIUtil.setVAvatar(url, isSensation, img, R.drawable.default_avatar, R.drawable.default_avatar);
    }

    public static void setVAvatar(String url, boolean isSensation, VImageView img, int loading, int fail) {
        img.showSensation(isSensation);
        UIUtil.setImage(url, img.getImageView(), loading, fail);
    }

    public static void setImage(String url, ImageView img, int loading, int fail) {
        img.setImageResource(loading);
        Object tag = img.getTag();
        boolean validTag = tag == null || tag instanceof ImageContainer;
        if (validTag && tag != null) {
            ImageContainer lastContainer = (ImageContainer) tag;
          /*  if (lastContainer.getUrl().equals(url)) {
                return;
            }*/
            lastContainer.cancel();
        }
        if (isEmpty(url)) {
            img.setImageResource(fail);
            img.setTag(null);
            return;
        }
        ImageContainer container = DataProvider.getInstance().getImageLoader()
                .loadImage(url, new ImageLoadListener(img, loading, fail));
        if (validTag) {
            img.setTag(container);
        }
    }

    public static void setImage(Uri uri, ImageView img, int loading, int fail, int width, int height) {
        img.setImageResource(loading);
        Object tag = img.getTag();
        boolean validTag = tag == null || tag instanceof ImageContainer;
        if (validTag && tag != null) {
            ImageContainer lastContainer = (ImageContainer) tag;
          /*  if (lastContainer.getUrl().equals(url)) {
                return;
            }*/
            lastContainer.cancel();
        }
        if (isEmpty(uri.toString())) {
            img.setImageResource(fail);
            img.setTag(null);
            return;
        }
        ImageContainer container = DataProvider.getInstance().getImageLoader()
                .loadImage(uri, new ImageLoadListener(img, loading, fail), width, height);
        if (validTag) {
            img.setTag(container);
        }
    }


    public static void setImageFromFile(String filePath, ImageView img, int loading, int fail, int width, int height) {
        img.setImageResource(loading);
        Object tag = img.getTag();
        boolean validTag = tag == null || tag instanceof ImageContainer;
        if (validTag && tag != null) {
            ImageContainer lastContainer = (ImageContainer) tag;
          /*  if (lastContainer.getUrl().equals(url)) {
                return;
            }*/
            lastContainer.cancel();
        }
        if (isEmpty(filePath)) {
            img.setImageResource(fail);
            img.setTag(null);
            return;
        }
        File file = new File(filePath);
        ImageContainer container = DataProvider.getInstance().getImageLoader()
                .loadImage(Uri.fromFile(file), new ImageLoadListener(img, loading, fail), width, height, ImageLoader.FLAG_MEM_EXCEPT);
        if (validTag) {
            img.setTag(container);
        }
    }


    public static class ImageLoadListener extends DefaultLoadListener {
        private ImageView imageView;
        private int defaultResId;

        public ImageLoadListener(ImageView imageView, int defaultResId, int errorResId) {
            super(imageView, defaultResId, errorResId);
            this.imageView = imageView;
            this.defaultResId = defaultResId;
        }

        @Override
        public void onFail() {
            super.onFail();
            imageView.setTag(null);
        }

        @Override
        public void onSuccess(Drawable drawable, boolean immediate) {
            if (drawable == null) {
                LogUtil.i("ImageLoader", "drawable is null");

                return;
            }
            if (drawable instanceof GifDrawable) {
                //不支持GIF格式图片
                imageView.setImageResource(defaultResId);
            } else {
                super.onSuccess(drawable, immediate);
            }
            if (!immediate && imageView != null) {
                Object tag = imageView.getTag();
                if (tag instanceof ImageContainer) {
                    imageView.setTag(null);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static int dp2Px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, AppContext.getContext().getResources()
                .getDisplayMetrics());
    }

    public static int px2Dp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px * density);
    }

    public static String addSpace(String classifyName) {
        if (classifyName.length() > 2) {
            return classifyName;
        }
        return classifyName.substring(0, 1) + " " + classifyName.substring(1);
    }

    public static Bitmap comPoseBitmap(Bitmap secondBitmap) {
        int width = getScreenWidth();
        int height = (int) (SCALE * width);
        Bitmap firstBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(firstBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(secondBitmap, 0, (height - secondBitmap.getHeight()) / 2, new Paint());

        // 这里只是一个经验的数字，代表的是分辨率。似乎微信对分辨率太大的图片会自动压缩，但其质量又很糟糕，所以自己来处理
        if (width >= 1500 || height >= 1500) {
            firstBitmap = resize(firstBitmap, 0.5f);
        }
        return firstBitmap;
    }

    public static int getScreenWidth() {
        Context context = AppContext.getContext();
        WindowManager wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wManager.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        Context context = AppContext.getContext();
        WindowManager wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wManager.getDefaultDisplay().getHeight();
    }

    private static Bitmap resize(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}