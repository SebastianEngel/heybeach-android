package com.bitbucket.heybeach.ui.imageloading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.bitbucket.heybeach.ui.imageloading.ImageLoader.Callback;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ImageDownload implements Runnable {

  private static final String LOG_TAG = ImageDownload.class.getName();

  private static final int CONNECT_TIMEOUT_MS = 15000;
  private static final int READ_TIMEOUT_MS = 15000;

  private final URL imageUrl;
  private final Handler mainHandler = new Handler(Looper.getMainLooper());
  private Callback callback;
  private boolean canceled;

  ImageDownload(String url, Callback callback) throws MalformedURLException {
    this.imageUrl = new URL(url);
    this.callback = callback;
  }

  void cancel() {
    Log.d(LOG_TAG, "Image download canceled: " + this);
    canceled = true;
  }

  public void run() {
    HttpURLConnection urlConnection = null;
    try {
      Log.d(LOG_TAG, "Downloading image at url: " + imageUrl.toString());

      urlConnection = (HttpURLConnection) imageUrl.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
      urlConnection.setReadTimeout(READ_TIMEOUT_MS);
      urlConnection.connect();

      int responseCode = urlConnection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        Log.d(LOG_TAG, "Response " + responseCode + ". Downloading image...");

        Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
        if (!canceled && callback != null) {
          mainHandler.post(() -> callback.onDownloadCompleted(bitmap));
        }
      } else {
        Log.e(LOG_TAG, "Failed to load image. API returned response code != 200. Response code was " + responseCode);
        if (callback != null) {
          mainHandler.post(callback::onDownloadFailed);
        }
      }
    } catch (IOException e) {
      Log.e(LOG_TAG, "Failed to load image. " + this, e);
      if (callback != null) {
        mainHandler.post(callback::onDownloadFailed);
      }
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
    }
  }

  @Override
  public String toString() {
    return "ImageDownload{" + "imageUrl=" + imageUrl + ", callback=" + callback + ", canceled=" + canceled + '}';
  }

}
