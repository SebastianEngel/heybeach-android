package com.bitbucket.heybeach.model.api.beaches;

import com.bitbucket.heybeach.model.api.ApiClientException;
import com.bitbucket.heybeach.model.api.JsonReaderException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BeachesApiClient {

  private static final String BEACHES_ENDPOINT = "beaches";

  private final String baseUrl;
  private final int timeoutMs;
  private final ImageListJsonReader imageListJsonReader;

  public BeachesApiClient(String baseUrl, int timeoutMs) {
    this.baseUrl = baseUrl;
    this.timeoutMs = timeoutMs;
    this.imageListJsonReader = new ImageListJsonReader();
  }

  public List<ImageJson> getImages() throws ApiClientException {
    HttpURLConnection urlConnection = null;

    try {
      urlConnection = (HttpURLConnection) createImagesUrl().openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.setRequestProperty("Cache-Control", "no-cache");
      urlConnection.setConnectTimeout(timeoutMs);
      urlConnection.setReadTimeout(timeoutMs);
      urlConnection.setDoInput(true);
      urlConnection.connect();

      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        List<ImageJson> imageJsons = imageListJsonReader.readImageList(inputStream);
        inputStream.close();
        return imageJsons;
      } else {
        throw new ApiClientException("API returned response code != 200. Is " + urlConnection.getResponseCode());
      }
    } catch (JsonReaderException | IOException e) {
      throw new ApiClientException(e);
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
    }
  }

  private URL createImagesUrl() throws MalformedURLException {
    String imagesUrl = baseUrl;

    if (!imagesUrl.endsWith("/")) {
      imagesUrl = imagesUrl.concat("/");
    }
    return new URL(imagesUrl.concat(BEACHES_ENDPOINT));
  }

}
