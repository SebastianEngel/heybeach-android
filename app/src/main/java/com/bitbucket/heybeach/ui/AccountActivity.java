package com.bitbucket.heybeach.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.AccountManager;
import com.bitbucket.heybeach.domain.AuthenticationUseCase;
import com.bitbucket.heybeach.domain.User;

public class AccountActivity extends AppCompatActivity implements AccountPresenter.AccountView {

  private Toolbar toolbar;
  private TextView userIdText;
  private TextView emailText;
  private Button logoutButton;
  private AccountPresenter presenter;

  public static void start(Context context) {
    context.startActivity(new Intent(context, AccountActivity.class));
  }

  ///////////////////////////////////////////////////////////////////////////
  // Activity lifecycle
  ///////////////////////////////////////////////////////////////////////////

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_account);
    bindViews();
    setupToolbar();
    setupLogoutButton();
    createPresenter();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.onAttach(this);
  }

  @Override
  protected void onStop() {
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
  public void showFailureMessage() {
    Toast.makeText(this, R.string.account_logout_failure_message, Toast.LENGTH_SHORT).show();
  }

  ///////////////////////////////////////////////////////////////////////////
  // Private functionality
  ///////////////////////////////////////////////////////////////////////////

  private void bindViews() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    userIdText = (TextView) findViewById(R.id.user_id);
    emailText = (TextView) findViewById(R.id.email);
    logoutButton = (Button) findViewById(R.id.logout_button);
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
    ScreenNavigator screenNavigator = DependencyProvider.provideScreenNavigator(this);
    presenter = new AccountPresenter(authenticationUseCase, accountManager, screenNavigator);
  }

}
