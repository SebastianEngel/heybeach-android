package com.bitbucket.heybeach.model;

import com.bitbucket.heybeach.domain.Image;
import com.bitbucket.heybeach.model.api.ImageJson;

final class ImageJsonModelConverter {

  private ImageJsonModelConverter() {}

  static Image convert(ImageJson imageJson) {
    return new Image(imageJson.id, imageJson.name, imageJson.url, Integer.parseInt(imageJson.width), Integer.parseInt(imageJson.height));
  }

}
