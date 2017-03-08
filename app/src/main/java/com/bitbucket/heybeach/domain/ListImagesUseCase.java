package com.bitbucket.heybeach.domain;

import com.bitbucket.heybeach.model.ImageRepository;
import com.bitbucket.heybeach.model.RepositoryException;
import java.util.List;

public class ListImagesUseCase {

  private final ImageRepository imageRepository;

  public ListImagesUseCase(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public List<Image> getImages() throws UseCaseException {
    try {
      return imageRepository.getImages();
    } catch (RepositoryException e) {
      throw new UseCaseException("Failed to load images from repository.", e);
    }
  }

}
