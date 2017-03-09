package com.bitbucket.heybeach.ui.imageloading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ImageDownload implements Runnable {

  private static final String LOG_TAG = ImageDownload.class.getName();

  private static final int CONNECT_TIMEOUT_MS = 15000;
  private static final int READ_TIMEOUT_MS = 15000;

  private final URL imageUrl;
  private final WeakReference<ImageView> imageView;
  private boolean canceled;

  ImageDownload(String url, ImageView imageView) throws MalformedURLException {
    this.imageUrl = new URL(url);
    this.imageView = new WeakReference<>(imageView);
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

        ImageView imageView = this.imageView.get();
        if (!canceled && imageView != null) {
          imageView.getHandler().post(() -> imageView.setImageBitmap(bitmap));
        }
      } else {
        Log.e(LOG_TAG, "Failed to load image. API returned response code != 200. Response code was " + responseCode);
      }
    } catch (IOException e) {
      Log.e(LOG_TAG, "Failed to load image. " + this, e);
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
    }
  }

  @Override
  public String toString() {
    return "ImageDownload{" + "imageUrl=" + imageUrl + ", imageView=" + imageView.get() + '}';
  }

}