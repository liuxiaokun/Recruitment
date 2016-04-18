package com.hou.recruitment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hou.recruitment.bean.Score;
import com.hou.recruitment.common.Constant;
import com.hou.recruitment.db.DatabaseHelper;
import com.hou.recruitment.utils.ResClient;
import com.hou.recruitment.utils.ToastUtil;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.sql.SQLException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-13 13:35
 */
public class ScoreListActivity extends BaseActivity implements View.OnClickListener{

    private ScoreAdapter mScoreAdapter;
    private ListView mScoreList;


    private Dao<Score, Integer> mScoreDao;
    private Button mBtnUploadData;

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
        setContentView(R.layout.activity_score_list);

        mScoreList = (ListView) findViewById(R.id.score_list);


        DatabaseHelper helper = OpenHelperManager.getHelper(ScoreListActivity.this, DatabaseHelper.class);
        mScoreDao = helper.getScoreDao();

        mBtnUploadData = (Button) findViewById(R.id.upload_data);
        mBtnUploadData.setOnClickListener(this);
    }


    /**
     * Called after {@link #onRestoreInstanceState}, {@link #onRestart}, or
     * {@link #onPause}, for your activity to start interacting with the user.
     * This is a good place to begin animations, open exclusive-access devices
     * (such as the camera), etc.
     * <p/>
     * <p>Keep in mind that onResume is not the best indicator that your activity
     * is visible to the user; a system window such as the keyguard may be in
     * front.  Use {@link #onWindowFocusChanged} to know for certain that your
     * activity is visible to the user (for example, to resume a game).
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onRestoreInstanceState
     * @see #onRestart
     * @see #onPostResume
     * @see #onPause
     */
    @Override
    protected void onResume() {
        super.onResume();


        try {
            mScoreAdapter = new ScoreAdapter(mScoreDao.queryForEq("sync", 0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mScoreList.setAdapter(mScoreAdapter);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.upload_data:

                try {
                    List<Score> scores = mScoreDao.queryForEq("sync", 0);

                    if(scores == null || scores.size() == 0) {
                        ToastUtil.shortShow(ScoreListActivity.this, "没有需要同步的数据!");
                        return;
                    }
                    for (Score score : scores) {


                        final Score s = score;
                        String url = Constant.SUBMIT_SCORE + score.getStudentId() + "/" + score.getExpertId() + "/" + score.getFirstScore() + "/" + score.getFinalScore();

                        ResClient.submitScore(new RequestParams(), url, new AsyncHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                s.setSync(1);
                                try {
                                    int update = mScoreDao.update(s);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                System.out.println(new String(responseBody));
                            }

                        });
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }
    }

    private class ScoreAdapter extends ArrayAdapter<Score> {

        public ScoreAdapter(List<Score> scores) {
            super(ScoreListActivity.this, 0, scores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = ScoreListActivity.this.getLayoutInflater().inflate(R.layout.score_item, null);
                viewHolder = new ViewHolder();

                viewHolder.mTextViewName = (TextView) convertView.findViewById(R.id.name);
                viewHolder.mTextViewFirstScore = (TextView) convertView.findViewById(R.id.first_score);
                viewHolder.mTextViewFinalScore = (TextView) convertView.findViewById(R.id.final_score);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Score score = getItem(position);

            viewHolder.mTextViewName.setText(score.getStudentId());
            viewHolder.mTextViewFirstScore.setText(String.valueOf(score.getFirstScore()));
            viewHolder.mTextViewFinalScore.setText(String.valueOf(score.getFinalScore()));

            return convertView;
        }
    }

    static class ViewHolder {
        private TextView mTextViewName;
        private TextView mTextViewFirstScore;
        private TextView mTextViewFinalScore;
    }
}
