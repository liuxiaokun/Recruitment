package com.hou.recruitment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hou.recruitment.bean.Score;
import com.hou.recruitment.common.Constant;
import com.hou.recruitment.db.DatabaseHelper;
import com.hou.recruitment.utils.AppUtil;
import com.hou.recruitment.utils.ResClient;
import com.hou.recruitment.utils.ToastUtil;
import com.hou.recruitment.vo.GetStudentResp;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.sql.SQLException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.config.SocketConfig;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-13 10:37
 */
public class ExamActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvId;

    private TextView mTvName;

    private EditText mEditFirstScore;

    private EditText mEditFinalScore;


    private String mStudentId;
    private String mExpertId;

    private Button mBtnSubmit;

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
        setContentView(R.layout.activity_exam);

        GetStudentResp data = (GetStudentResp) getIntent().getSerializableExtra("data");

        mStudentId = data.getStudent().getStudentId();

        mExpertId = getIntent().getStringExtra("expertId");

        mTvId = (TextView) findViewById(R.id.student_id);
        mTvId.setText(mTvId.getText() + data.getStudent().getStudentId());

        mTvName = (TextView) findViewById(R.id.student_name);
        mTvName.setText(mTvName.getText() + data.getStudent().getName());

        mBtnSubmit = (Button) findViewById(R.id.submit);
        mBtnSubmit.setOnClickListener(this);

        mEditFirstScore = (EditText) findViewById(R.id.first_score);
        mEditFinalScore = (EditText) findViewById(R.id.final_score);
    }

    private void submit() {

        final String firstScore = mEditFirstScore.getText().toString().trim();
        final String finalScore = mEditFinalScore.getText().toString().trim();


        if (AppUtil.isEmpty(firstScore)) {
            Toast.makeText(ExamActivity.this, "印象分不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (AppUtil.isEmpty(finalScore)) {
            Toast.makeText(ExamActivity.this, "最终分不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Constant.SUBMIT_SCORE + mStudentId + "/" + mExpertId + "/" + firstScore + "/" + finalScore;

        Log.d("submit + url: ", url);

        ResClient.submitScore(new RequestParams(), url, new AsyncHttpResponseHandler() {

            /**
             * Fired when the request is started, override to handle in your own code
             */
            @Override
            public void onStart() {
                super.onStart();
                showLoading("正在提交...", true, null);
                finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dismissLoading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dismissLoading();
                ToastUtil.shortShow(ExamActivity.this, "数据未提交成功!");

                Score score = new Score();
                score.setExpertId(mExpertId);
                score.setStudentId(mStudentId);
                score.setFirstScore(Integer.parseInt(firstScore));
                score.setFinalScore(Integer.parseInt(finalScore));
                DatabaseHelper helper = OpenHelperManager.getHelper(ExamActivity.this, DatabaseHelper.class);
                try {
                    helper.getScoreDao().create(score);
                } catch (SQLException e) {
                    ToastUtil.shortShow(ExamActivity.this, "本地数据保存失败!");
                }


            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.submit:

                submit();
                break;

            default:
                break;
        }
    }
}
