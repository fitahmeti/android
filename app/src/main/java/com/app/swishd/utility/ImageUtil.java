package com.app.swishd.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageUtil {

    private static DisplayImageOptions imageOptions = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).build();
    private static DisplayImageOptions.Builder imageOptionsLoaderBuilder = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true);

    public static void init(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .displayer(new FadeInBitmapDisplayer(300)).build(); //.imageScaleType(ImageScaleType.EXACTLY)

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }

    public static void load(final String mAvatarUrl, final Target target) {
        if (mAvatarUrl == null || mAvatarUrl.isEmpty())
            return;
        final String avatarUrl;
        if (!mAvatarUrl.startsWith("http") && !mAvatarUrl.startsWith("content://"))
            avatarUrl = "File://" + mAvatarUrl;
        else
            avatarUrl = mAvatarUrl;

        try {
            DisplayImageOptions options = getOption(-1);
            ImageLoader.getInstance().loadImage(avatarUrl, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    target.onFail(new Exception(failReason.getCause()));
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    target.onSuccess(loadedImage);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });
        } catch (Exception e) {
            target.onFail(new Exception(e.getMessage()));
        }
    }

    public interface Target {
        void onSuccess(Bitmap bitmap);

        void onFail(Exception e);
    }

    public static void load(String avatarUrl, final ImageView userAvatar) {
        load(avatarUrl, userAvatar, -1);
    }

    public static void load(final String mAvatarUrl, final ImageView userAvatar, final @DrawableRes int loadingRes) {
        Handler handler = userAvatar.getHandler();
        if (handler == null)
            handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mAvatarUrl == null || mAvatarUrl.isEmpty())
                    return;
                String avatarUrl = mAvatarUrl;
                if (!mAvatarUrl.startsWith("http")) {
                    if (!mAvatarUrl.startsWith("content://"))
                        avatarUrl = "File://" + mAvatarUrl;
                }

                try {
                    DisplayImageOptions options = getOption(loadingRes);
                    ImageLoader.getInstance().displayImage(avatarUrl, userAvatar, options);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    private static DisplayImageOptions getOption(int loadingRes) {
        if (loadingRes == -1)
            return imageOptions;
        DisplayImageOptions imageOptionsLoader = imageOptionsLoaderBuilder
                .showImageOnLoading(loadingRes)
                .showImageForEmptyUri(loadingRes)
                .showImageOnFail(loadingRes)
                .build();
        return imageOptionsLoader;
    }
}
