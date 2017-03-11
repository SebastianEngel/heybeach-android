package com.bitbucket.heybeach;

import android.content.Context;
import com.bitbucket.heybeach.domain.AccountManager;
import com.bitbucket.heybeach.domain.AuthenticationUseCase;
import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.model.ImageRepository;
import com.bitbucket.heybeach.model.api.beaches.BeachesApiClient;
import com.bitbucket.heybeach.model.api.users.UsersApiClient;
import com.bitbucket.heybeach.ui.ScreenNavigator;
import com.bitbucket.heybeach.ui.imageloading.ImageLoader;
import java.util.concurrent.Executors;

public final class DependencyProvider {

  private static ImageLoader imageLoader;

  private DependencyProvider() {}

  public static ImageLoader provideImageLoader() {
    return ImageLoader.getInstance();
  }

  // Use cases

  public static ListImagesUseCase provideListImagesUseCase() {
    return new ListImagesUseCase(provideImageRepository(), Executors.newSingleThreadExecutor());
  }

  public static AuthenticationUseCase provideAuthenticationUseCase() {
    return new AuthenticationUseCase(provideUsersApiClient(), provideAccountManagerSingleton(), Executors.newSingleThreadExecutor());
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
