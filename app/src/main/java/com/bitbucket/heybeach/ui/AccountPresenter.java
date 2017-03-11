package com.bitbucket.heybeach.ui;

import com.bitbucket.heybeach.domain.AccountManager;
import com.bitbucket.heybeach.domain.AuthenticationUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;
import com.bitbucket.heybeach.domain.User;

class AccountPresenter extends MvpPresenter<AccountPresenter.AccountView> {

  private final AuthenticationUseCase authenticationUseCase;
  private final AccountManager accountManager;
  private final ScreenNavigator screenNavigator;

  AccountPresenter(AuthenticationUseCase authenticationUseCase, AccountManager accountManager, ScreenNavigator screenNavigator) {
    this.authenticationUseCase = authenticationUseCase;
    this.accountManager = accountManager;
    this.screenNavigator = screenNavigator;
  }

  @Override
  public void onAttach(AccountView view) {
    super.onAttach(view);
    loadUserDetails();
  }

  void onLogoutAction() {
    try {
      authenticationUseCase.logout();
      screenNavigator.navigateToLoginScreen();
    } catch (UseCaseException e) {
      view.showFailureMessage();
    }
  }

  private void loadUserDetails() {
    User user = accountManager.getAuthenticatedUser();
    view.setUserDetails(user);
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view interface
  ///////////////////////////////////////////////////////////////////////////

  interface AccountView extends MvpView {
    void setUserDetails(User user);
    void showFailureMessage();
  }

}
