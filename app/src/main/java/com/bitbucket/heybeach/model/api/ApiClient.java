package com.bitbucket.heybeach.model.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ApiClient {

  private static final String IMAGES_PATH = "beaches";
  private static final int CONNECT_TIMEOUT_MS = 15000;
  private static final int READ_TIMEOUT_MS = 15000;

  private final String imagesUrl;
  private final ImageListJsonReader imageListJsonReader;

  public ApiClient(String baseUrl) {
    this.imagesUrl = createImagesUrl(baseUrl);
    this.imageListJsonReader = new ImageListJsonReader();
  }

  public List<ImageJson> getImages() throws ApiClientException {
    HttpURLConnection urlConnection = null;

    try {
      urlConnection = (HttpURLConnection) new URL(imagesUrl).openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.setConnectTimeout(CONNECT_TIMEOUT_MS);
      urlConnection.setReadTimeout(READ_TIMEOUT_MS);
      urlConnection.connect();

      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        return imageListJsonReader.readImageList(new BufferedInputStream(urlConnection.getInputStream()));
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

  private String createImagesUrl(String baseUrl) {
    String imagesUrl = baseUrl;

    if (!imagesUrl.endsWith("/")) {
      imagesUrl = imagesUrl.concat("/");
    }
    return imagesUrl.concat(IMAGES_PATH);
  }

}
