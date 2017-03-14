package com.bitbucket.heybeach.ui.imageloading;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

  private static final String LOG_TAG = ImageLoader.class.getName();

  private final ExecutorService executorService = Executors.newFixedThreadPool(5);
  private final Map<ImageView, ImageDownload> viewToDownloads = new WeakHashMap<>();

  private static ImageLoader instance;

  public static ImageLoader getInstance() {
    if (instance == null) {
      synchronized (ImageLoader.class) {
        instance = new ImageLoader();
      }
    }
    return instance;
  }

  public void load(String imageUrl, ImageView imageView, Callback callback) {
    // If there is already an image download for this ImageView, remove it.
    ImageDownload imageDownload = viewToDownloads.remove(imageView);
    if (imageDownload != null) {
      imageDownload.cancel();
    }

    try {
      imageDownload = new ImageDownload(imageUrl, callback);
      viewToDownloads.put(imageView, imageDownload);
      executorService.submit(imageDownload);
    } catch (MalformedURLException e) {
      Log.w(LOG_TAG, "Unable to download image due to invalid image url.", e);
    }
  }


  public interface Callback {
    void onDownloadCompleted(Bitmap bitmap);
    void onDownloadFailed();
  }

}
