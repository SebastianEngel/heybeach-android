package com.bitbucket.heybeach.model.api;

import android.util.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser that reads a list of {@link ImageJson} objects from an InputStream.
 * The parser is strict.
 */
class ImageListJsonReader {

  // [
  //   {
  //     "_id":"58b5784dde02e92969c8bf3b",
  //     "name":"63f08c32-9ba2-49af-a385-39fc0f428d1d.png",
  //     "url":"images/63f08c32-9ba2-49af-a385-39fc0f428d1d.png",
  //     "width":"410",
  //     "height":"560"
  //   },
  //   ...
  // ]

  List<ImageJson> readImageList(InputStream inputStream) throws JsonReaderException {
    List<ImageJson> imageJsonObjects = new ArrayList<>();

    try (JsonReader reader = new JsonReader(new InputStreamReader(inputStream))) {
      reader.beginArray();
      while (reader.hasNext()) {
        imageJsonObjects.add(readImageObject(reader));
      }
      reader.endArray();
    } catch (IOException e) {
      throw new JsonReaderException("Failed to read image list.", e);
    }
    return imageJsonObjects;
  }

  private ImageJson readImageObject(JsonReader reader) throws JsonReaderException, IOException {
    ImageJson imageJson = new ImageJson();

    reader.beginObject();
    while (reader.hasNext()) {
      String name = reader.nextName();
      switch (name) {
        case "_id":
          imageJson.id = reader.nextString();
          break;
        case "name":
          imageJson.name = reader.nextString();
          break;
        case "url":
          imageJson.url = reader.nextString();
          break;
        case "width":
          imageJson.width = reader.nextString();
          break;
        case "height":
          imageJson.height = reader.nextString();
          break;
        default:
          throw new JsonReaderException("Unknown name: " + name);
      }
    }
    reader.endObject();
    return imageJson;
  }

}
