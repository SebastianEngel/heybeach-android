package com.bitbucket.heybeach.ui.imageloading;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

  private static final String LOG_TAG = ImageLoader.class.getName();

  private final ExecutorService executorService = Executors.newCachedThreadPool();
  private final Map<ImageView, ImageDownload> viewToDownloads = new WeakHashMap<>();
  private final ImageCache imageCache = new ImageCache();

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
    WeakReference<Callback> callbackRef = new WeakReference<>(callback);

    Bitmap bitmap = imageCache.get(imageUrl);
    if (bitmap != null) {
      Log.d(LOG_TAG, "Taking image from cache for URL =  " + imageUrl);
      if (callbackRef.get() != null) {
        callbackRef.get().onDownloadCompleted(bitmap);
      }
    } else {

      // If there is already an image download for this ImageView, remove it.
      ImageDownload imageDownload = viewToDownloads.remove(imageView);
      if (imageDownload != null) {
        imageDownload.cancel();
      }

      try {
        imageDownload = new ImageDownload(imageUrl, new Callback() {
          @Override public void onDownloadCompleted(Bitmap bitmap) {
            synchronized (imageCache) {
              imageCache.put(imageUrl, bitmap);
            }
            if (callbackRef.get() != null) {
              callbackRef.get().onDownloadCompleted(bitmap);
            }
          }

          @Override
          public void onDownloadFailed() {
            if (callbackRef.get() != null) {
              callbackRef.get().onDownloadFailed();
            }
          }
        });
        viewToDownloads.put(imageView, imageDownload);
        executorService.submit(imageDownload);
      } catch (MalformedURLException e) {
        Log.w(LOG_TAG, "Unable to download image due to invalid image url.", e);
      }
    }
  }

  public interface Callback {
    void onDownloadCompleted(Bitmap bitmap);
    void onDownloadFailed();
  }

  static class ImageCache extends LruCache<String, Bitmap> {

    private static final int CACHE_SIZE_MB = 50 * 1024 * 1024; // 50 MB

    ImageCache() {
      super(CACHE_SIZE_MB);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
      return bitmap.getByteCount();
    }
  }

}
