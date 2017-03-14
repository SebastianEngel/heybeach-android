package com.bitbucket.heybeach.domain;

import com.bitbucket.heybeach.model.ImageRepository;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ListImagesUseCase {

  private final ImageRepository imageRepository;
  private final ExecutorService executorService;

  public ListImagesUseCase(ImageRepository imageRepository, ExecutorService executorService) {
    this.imageRepository = imageRepository;
    this.executorService = executorService;
  }

  public List<Image> getImagesForPage(int page) throws UseCaseException {
    try {
      return executorService.submit(() -> imageRepository.getImagesForPage(page)).get();
    } catch (Exception e) {
      throw new UseCaseException("Failed to load images from repository.", e);
    }
  }

}
