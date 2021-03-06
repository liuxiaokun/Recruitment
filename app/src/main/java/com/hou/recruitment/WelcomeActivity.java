package com.hou.recruitment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hou.recruitment.utils.AppUtil;
import com.hou.recruitment.utils.ResClient;
import com.hou.recruitment.utils.ToastUtil;
import com.hou.recruitment.vo.GetStudentResp;
import com.hou.recruitment.vo.LoginResponse;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.Serializable;

import cz.msebera.android.httpclient.Header;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-12 19:55
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private TextView mWelcome;
    private TextView mKcNum;
    private TextView mSubject;

    private EditText mEditTextStudentId;

    private TextView mTvClock;
    private Button mButtonStart;
    private LoginResponse mLoginResponse;

    private String mExpertId;
    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     * {@link #managedQuery(Uri, String[], String, String[], String)} to retrieve
     * cursors for data being displayed, etc.
     * <p/>
     * <p>You can call {@link #finish} from within this function, in
     * which case onDestroy() will be immediately called without any of the rest
     * of the activity lifecycle ({@link #onStart}, {@link #onResume},
     * {@link #onPause}, etc) executing.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     * @see #onStart
     * @see #onSaveInstanceState
     * @see #onRestoreInstanceState
     * @see #onPostCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mLoginResponse = (LoginResponse) getIntent().getSerializableExtra("data");

        mExpertId = mLoginResponse.getExpert().getExpertId();
        mWelcome = (TextView) findViewById(R.id.welcome);
        mWelcome.setText(mWelcome.getText() + mLoginResponse.getExpert().getName());

        mKcNum = (TextView) findViewById(R.id.kcnum);
        mKcNum.setText(mKcNum.getText()+ mLoginResponse.getExpert().getKcNum());

        mSubject = (TextView) findViewById(R.id.subject);
        mSubject.setText(mSubject.getText() + mLoginResponse.getExpert().getSubjectName());

        mEditTextStudentId = (EditText) findViewById(R.id.student_id);


        mButtonStart = (Button) findViewById(R.id.start);
        mButtonStart.setOnClickListener(this);
        mButtonStart.setClickable(false);



        mTvClock= (TextView) findViewById(R.id.clock);
        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(10000, 1000);
        myCountDownTimer.start();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start:
                start();
                break;

            default:
                break;
        }
    }


    private void start() {

        String studentId = mEditTextStudentId.getText().toString().trim();

        if(AppUtil.isEmpty(studentId)) {
            ToastUtil.shortShow(WelcomeActivity.this, "学生考号不能为空!");
            return;
        }

        RequestParams params = new RequestParams();
        params.add("id", studentId);
        ResClient.getStudentInfo(params, new AsyncHttpResponseHandler() {


            /**
             * Fired when the request is started, override to handle in your own code
             */
            @Override
            public void onStart() {
                super.onStart();
                showLoading("正在初始化...", true, null);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                dismissLoading();

                try {
                    String response = new String(responseBody);

                    GetStudentResp resp = new Gson().fromJson(response, GetStudentResp.class);

                    Intent intent = new Intent(WelcomeActivity.this, ExamActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", resp);
                    bundle.putString("expertId", mExpertId);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtil.shortShow(WelcomeActivity.this, "服务端数据异常!");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dismissLoading();
            }
        });

    }
    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mTvClock.setText("考试已经开始！");
            mButtonStart.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTvClock.setText("离考试开始还有" + millisUntilFinished / 1000 + "秒");
        }
    }
}
