package com.cubicl.buzzclicker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity implements View.OnClickListener {
    EditText etUsername;
    EditText etPassword;
    UserLocalStore userLocalStore;
    User tryToLoginUser;
    Button btnLogin;
    private ProgressDialog dialog;
    String user;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.gtid);
        etPassword = (EditText) findViewById(R.id.pass);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        user = etUsername.getText().toString();
        pass = etPassword.getText().toString();
        userLocalStore = new UserLocalStore(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                new TryToLogin().execute();
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }



    private class TryToLogin extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        boolean result;
        String username;
        String password;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("logging in");
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();
            showDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServerRequest serverRequest = new ServerRequest();
            try {
                if (serverRequest.login(username, password)) {
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void r) {
            hideDialog();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            if (result) {
                startActivity(intent);
                LoginActivity.this.finish();
            } else {
                Toast.makeText(LoginActivity.this, "There was an error logging in to your account", Toast.LENGTH_LONG).show();
            }


        }

        private void showDialog() {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        private void hideDialog() {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


}