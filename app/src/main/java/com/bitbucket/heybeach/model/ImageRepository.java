package com.bitbucket.heybeach.model;

import com.bitbucket.heybeach.domain.Image;
import com.bitbucket.heybeach.model.api.beaches.BeachesApiClient;
import com.bitbucket.heybeach.model.api.ApiClientException;
import com.bitbucket.heybeach.model.api.beaches.ImageJson;
import java.util.ArrayList;
import java.util.List;

public class ImageRepository {

  private final BeachesApiClient apiClient;

  public ImageRepository(BeachesApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public List<Image> getImagesForPage(int page) throws RepositoryException {
    try {
      List<ImageJson> imageJsonList = apiClient.getImagesForPage(page);
      List<Image> images = new ArrayList<>(imageJsonList.size());

      for (ImageJson imageJson : imageJsonList) {
        images.add(ImageJsonModelConverter.convert(imageJson));
      }

      return images;
    } catch (ApiClientException e) {
      throw new RepositoryException("Failed to load images from API.", e);
    }
  }

}
