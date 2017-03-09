package com.bitbucket.heybeach.model.api.users;

import com.bitbucket.heybeach.model.api.ApiClientException;
import com.bitbucket.heybeach.model.api.JsonReaderException;
import com.bitbucket.heybeach.model.api.JsonWriterException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UsersApiClient {

  private static final String REGISTRATION_ENDPOINT = "user/register";

  private final String baseUrl;
  private final int timeoutMs;
  private final RegistrationRequestWriter registrationRequestWriter;
  private final RegistrationResponseReader registrationResponseReader;

  public UsersApiClient(String baseUrl, int timeoutMs) {
    this.baseUrl = baseUrl;
    this.timeoutMs = timeoutMs;
    this.registrationResponseReader = new RegistrationResponseReader();
    registrationRequestWriter = new RegistrationRequestWriter();
  }

  public UserJson register(String email, String password) throws ApiClientException {
    HttpURLConnection urlConnection = null;

    try {
      urlConnection = (HttpURLConnection) createFullRegistrationUrl().openConnection();
      urlConnection.setRequestMethod("POST");
      urlConnection.setRequestProperty("Cache-Control", "no-cache");
      urlConnection.setRequestProperty("Content-Type", "application/json");
      urlConnection.setConnectTimeout(timeoutMs);
      urlConnection.setReadTimeout(timeoutMs);
      urlConnection.setDoOutput(true);
      urlConnection.setDoInput(true);

      OutputStream outputStream = urlConnection.getOutputStream();
      registrationRequestWriter.write(email, password, outputStream);
      outputStream.close();

      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        return registrationResponseReader.read(new BufferedInputStream(urlConnection.getInputStream()));
      } else {
        throw new ApiClientException("API returned response code != 200. Is " + urlConnection.getResponseCode());
      }
    } catch (JsonWriterException | JsonReaderException | IOException e) {
      throw new ApiClientException(e);
    } finally {
      if (urlConnection != null) {
        urlConnection.disconnect();
      }
    }
  }

  private URL createFullRegistrationUrl() throws MalformedURLException {
    String registrationUrl = baseUrl;
    if (!registrationUrl.endsWith("/")) {
      registrationUrl = registrationUrl.concat("/");
    }
    return new URL(registrationUrl.concat(REGISTRATION_ENDPOINT));
  }

}