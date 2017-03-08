package com.bitbucket.heybeach;

import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.model.ImageRepository;
import com.bitbucket.heybeach.model.api.ApiClient;

public final class DependencyProvider {

  private DependencyProvider() {}

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
