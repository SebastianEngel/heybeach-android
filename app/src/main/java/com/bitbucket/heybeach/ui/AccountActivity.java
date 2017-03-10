package com.bitbucket.heybeach.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.bitbucket.heybeach.R;

public class AccountActivity extends AppCompatActivity {

  public static void start(Context context) {
    context.startActivity(new Intent(context, AccountActivity.class));
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_account);
  }

}
