package com.bitbucket.heybeach;

import android.content.Context;
import com.bitbucket.heybeach.domain.AccountManager;
import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.domain.LoginUseCase;
import com.bitbucket.heybeach.domain.RegistrationUseCase;
import com.bitbucket.heybeach.model.ImageRepository;
import com.bitbucket.heybeach.model.api.beaches.BeachesApiClient;
import com.bitbucket.heybeach.model.api.users.UsersApiClient;
import com.bitbucket.heybeach.ui.ScreenNavigator;
import com.bitbucket.heybeach.ui.imageloading.ImageLoader;

public final class DependencyProvider {

  private static ImageLoader imageLoader;

  private DependencyProvider() {}

  public static ImageLoader provideImageLoader() {
    return ImageLoader.getInstance();
  }

  // Use cases

  public static ListImagesUseCase provideListImagesUseCase() {
    return new ListImagesUseCase(provideImageRepository());
  }

  public static LoginUseCase provideLoginUseCase() {
    return new LoginUseCase(provideUsersApiClient(), provideAccountManagerSingleton());
  }

  public static RegistrationUseCase provideRegistrationUseCase() {
    return new RegistrationUseCase(provideUsersApiClient(), provideAccountManagerSingleton());
  }

  // Repositories

  private static ImageRepository provideImageRepository() {
    return new ImageRepository(provideBeachesApiClient());
  }

  // API clients

  private static BeachesApiClient provideBeachesApiClient() {
    return new BeachesApiClient(BuildConfig.API_BASE_URL, BuildConfig.API_TIMEOUT_MS);
  }

  private static UsersApiClient provideUsersApiClient() {
    return new UsersApiClient(BuildConfig.API_BASE_URL, BuildConfig.API_TIMEOUT_MS);
  }

  // Other

  public static ScreenNavigator provideScreenNavigator(Context context) {
    return new ScreenNavigator(context);
  }

  public static AccountManager provideAccountManagerSingleton() {
    return AccountManager.getInstance();
  }

}
