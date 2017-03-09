package com.bitbucket.heybeach;

import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.model.ImageRepository;
import com.bitbucket.heybeach.model.api.ApiClient;
import com.bitbucket.heybeach.ui.imageloading.ImageLoader;

public final class DependencyProvider {

  private static ImageLoader imageLoader;

  private DependencyProvider() {}

  public static ImageLoader provideImageLoader() {
    return ImageLoader.getInstance();
  }

  public static ListImagesUseCase provideListImagesUseCase() {
    return new ListImagesUseCase(provideImageRepository());
  }

  private static ImageRepository provideImageRepository() {
    return new ImageRepository(provideApiClient());
  }

  private static ApiClient provideApiClient() {
    return new ApiClient(BuildConfig.API_BASE_URL);
  }

}
