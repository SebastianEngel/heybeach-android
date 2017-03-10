package com.bitbucket.heybeach.model.api.users;

import android.util.JsonWriter;
import com.bitbucket.heybeach.model.api.JsonWriterException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

class LoginRequestWriter {

  // Example request body:
  //
  // {
  //   "email": "user@example.com"
  //   "password": "test1234"
  // }

  void write(String email, String password, OutputStream outputStream) throws JsonWriterException {
    try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream))) {
      writer.beginObject();
      writer.name("email");
      writer.value(email);
      writer.name("password");
      writer.value(password);
      writer.endObject();
    } catch (IOException e) {
      throw new JsonWriterException("Failed to write request data.", e);
    }
  }

}
