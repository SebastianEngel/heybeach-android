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
  private static final String LOGIN_ENDPOINT = "user/login";
  private static final String LOGOUT_ENDPOINT = "user/logout";

  private final String baseUrl;
  private final int timeoutMs;

  public UsersApiClient(String baseUrl, int timeoutMs) {
    this.baseUrl = baseUrl;
    this.timeoutMs = timeoutMs;
  }

  public RegistrationResult register(String email, String password) throws ApiClientException {
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
      RegistrationRequestWriter registrationRequestWriter = new RegistrationRequestWriter();
      registrationRequestWriter.write(email, password, outputStream);
      outputStream.close();

      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        String authToken = urlConnection.getHeaderField("x-auth");

        BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        RegistrationResponseReader registrationResponseReader = new RegistrationResponseReader();
        UserJson userJson = registrationResponseReader.read(inputStream);
        inputStream.close();

        return new RegistrationResult(authToken, userJson);
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

  public LoginResult login(String email, String password) throws ApiClientException {
    HttpURLConnection urlConnection = null;

    try {
      urlConnection = (HttpURLConnection) createFullLoginUrl().openConnection();
      urlConnection.setRequestMethod("POST");
      urlConnection.setRequestProperty("Cache-Control", "no-cache");
      urlConnection.setRequestProperty("Content-Type", "application/json");
      urlConnection.setConnectTimeout(timeoutMs);
      urlConnection.setReadTimeout(timeoutMs);
      urlConnection.setDoOutput(true);
      urlConnection.setDoInput(true);

      OutputStream outputStream = urlConnection.getOutputStream();
      LoginRequestWriter loginRequestWriter = new LoginRequestWriter();
      loginRequestWriter.write(email, password, outputStream);
      outputStream.close();

      if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        String authToken = urlConnection.getHeaderField("x-auth");

        BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        LoginResponseReader loginResponseReader = new LoginResponseReader();
        UserJson userJson = loginResponseReader.read(inputStream);
        inputStream.close();

        return new LoginResult(authToken, userJson);
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

  public void logout(String authToken) throws ApiClientException {
    HttpURLConnection urlConnection = null;

    try {
      urlConnection = (HttpURLConnection) createFullLogoutUrl().openConnection();
      urlConnection.setRequestMethod("DELETE");
      urlConnection.setRequestProperty("Cache-Control", "no-cache");
      urlConnection.setRequestProperty("x-auth", authToken);
      urlConnection.setConnectTimeout(timeoutMs);
      urlConnection.setReadTimeout(timeoutMs);
      urlConnection.connect();

      if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
        throw new ApiClientException("API returned response code != 200. Is " + urlConnection.getResponseCode());
      }
    } catch (IOException e) {
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

  private URL createFullLoginUrl() throws MalformedURLException {
    String loginUrl = baseUrl;
    if (!loginUrl.endsWith("/")) {
      loginUrl = loginUrl.concat("/");
    }
    return new URL(loginUrl.concat(LOGIN_ENDPOINT));
  }

  private URL createFullLogoutUrl() throws MalformedURLException {
    String logoutUrl = baseUrl;
    if (!logoutUrl.endsWith("/")) {
      logoutUrl = logoutUrl.concat("/");
    }
    return new URL(logoutUrl.concat(LOGOUT_ENDPOINT));
  }

}
