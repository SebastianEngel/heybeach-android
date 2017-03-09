package com.bitbucket.heybeach;

import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.domain.RegistrationUseCase;
import com.bitbucket.heybeach.model.ImageRepository;
import com.bitbucket.heybeach.model.api.BeachesApiClient;
import com.bitbucket.heybeach.model.api.users.UsersApiClient;
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
    return new ImageRepository(provideBeachesApiClient());
  }

  private static BeachesApiClient provideBeachesApiClient() {
    return new BeachesApiClient(BuildConfig.API_BASE_URL);
  }

  public static RegistrationUseCase provideRegistrationUseCase() {
    return new RegistrationUseCase(provideUsersApiClient());
  }

  private static UsersApiClient provideUsersApiClient() {
    return new UsersApiClient(BuildConfig.API_BASE_URL, BuildConfig.API_TIMEOUT_MS);
  }

}
