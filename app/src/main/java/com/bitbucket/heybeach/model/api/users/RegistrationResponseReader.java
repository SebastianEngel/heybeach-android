package com.bitbucket.heybeach.model.api.users;

import android.util.JsonReader;
import com.bitbucket.heybeach.model.api.JsonReaderException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Parser that reads the {@link UserJson response} coming back for a registration request.
 * The parser is strict.
 */
class RegistrationResponseReader {

  // Example response body:
  //
  // {
  //   "_id": "58c15c8dec1fe2000ef290b1",
  //   "email": "user@example.com"
  // }

  UserJson read(InputStream inputStream) throws JsonReaderException {
    UserJson userJson = new UserJson();

    try (JsonReader reader = new JsonReader(new InputStreamReader(inputStream))) {
      reader.beginObject();

      while (reader.hasNext()) {
        String name = reader.nextName();
        switch (name) {
          case "_id":
            userJson.id = reader.nextString();
            break;
          case "email":
            userJson.email = reader.nextString();
            break;
          default:
            throw new JsonReaderException("Unknown name: " + name);
        }
      }
      reader.endObject();
      return userJson;
    } catch (IOException e) {
      throw new JsonReaderException("Failed to read user from registration response.", e);
    }
  }

}
