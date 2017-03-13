package com.bitbucket.heybeach.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.AccountManager;
import com.bitbucket.heybeach.domain.AuthenticationUseCase;
import com.bitbucket.heybeach.domain.User;

public class AccountFragment extends Fragment implements AccountPresenter.AccountView {

  private Toolbar toolbar;
  private TextView userIdText;
  private TextView emailText;
  private Button logoutButton;
  private AccountPresenter presenter;

  static AccountFragment newInstance() {
    return new AccountFragment();
  }

  ///////////////////////////////////////////////////////////////////////////
  // Fragment lifecycle
  ///////////////////////////////////////////////////////////////////////////

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_account, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bindViews();
    setupToolbar();
    setupLogoutButton();
    createPresenter();
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.onAttach(this);
  }

  @Override
  public void onStop() {
    presenter.onDetach();
    super.onStop();
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view implementation
  ///////////////////////////////////////////////////////////////////////////

  @Override
  public void setUserDetails(User user) {
    userIdText.setText(user.getId());
    emailText.setText(user.getEmail());
  }

  @Override
  public void close() {
    getActivity().finish();
  }

  @Override
  public void showFailureMessage() {
    Toast.makeText(getActivity(), R.string.account_logout_failure_message, Toast.LENGTH_SHORT).show();
  }

  ///////////////////////////////////////////////////////////////////////////
  // Private functionality
  ///////////////////////////////////////////////////////////////////////////

  private void bindViews() {
    toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
    userIdText = (TextView) getView().findViewById(R.id.user_id);
    emailText = (TextView) getView().findViewById(R.id.email);
    logoutButton = (Button) getView().findViewById(R.id.logout_button);
  }

  private void setupToolbar() {
    toolbar.setTitle(R.string.account_toolbar_title);
  }

  private void setupLogoutButton() {
    logoutButton.setOnClickListener(view -> presenter.onLogoutAction());
  }

  private void createPresenter() {
    AuthenticationUseCase authenticationUseCase = DependencyProvider.provideAuthenticationUseCase();
    AccountManager accountManager = DependencyProvider.provideAccountManagerSingleton();
    ScreenNavigator screenNavigator = DependencyProvider.provideScreenNavigator(getActivity());
    presenter = new AccountPresenter(authenticationUseCase, accountManager, screenNavigator);
  }

}
