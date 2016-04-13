package com.hou.recruitment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hou.recruitment.utils.AppUtil;
import com.hou.recruitment.utils.ResClient;
import com.hou.recruitment.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-12 15:49
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private Button mBtnLogin;
    private Button mBtnSubmitData;
    private EditText mEditUserName;
    private EditText mEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnLogin = (Button) findViewById(R.id.login);
        mBtnLogin.setOnClickListener(this);

        mBtnSubmitData = (Button) findViewById(R.id.submit_data);
        mBtnSubmitData.setOnClickListener(this);

        mEditUserName = (EditText) findViewById(R.id.user_name);
        mEditPassword = (EditText) findViewById(R.id.password);

    }


    private void login(){

        String userName = mEditUserName.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();


        if(AppUtil.isEmpty(userName)) {
            ToastUtil.shortShow(LoginActivity.this, "用户名不能为空!");
            return;
        }
        if(AppUtil.isEmpty(password)) {
            ToastUtil.shortShow(LoginActivity.this, "密码不能为空!");
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.add("user", userName);
        requestParams.add("pass", AppUtil.md5(password));
        ResClient.login(requestParams, new AsyncHttpResponseHandler(){

            /**
             * Fired when the request is started, override to handle in your own code
             */
            @Override
            public void onStart() {
                super.onStart();
                showLoading("正在登录", true, null);
            }

            /**
             * Fired when a request returns successfully, override to handle in your own code
             *
             * @param statusCode   the status code of the response
             * @param headers      return headers, if any
             * @param responseBody the body of the HTTP response from the server
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dismissLoading();
                ToastUtil.shortShow(LoginActivity.this, "登录成功!");

                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }

            /**
             * Fired when a request fails to complete, override to handle in your own code
             *
             * @param statusCode   return HTTP status code
             * @param headers      return headers, if any
             * @param responseBody the response body, if any
             * @param error        the underlying cause of the failure
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dismissLoading();
                ToastUtil.shortShow(LoginActivity.this, "登录失败!");
            }
        });
    }

    private void submit() {





        Intent intent  = new Intent(this, ScoreListActivity.class);
        startActivity(intent);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {



        switch (v.getId()) {

            case R.id.login:
                login();
                break;

            case R.id.submit_data:

                submit();
                break;
            default:
                break;
        }
    }
}
