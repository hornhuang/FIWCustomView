package com.fishinwater.fiwentertainmentstar.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fishinwater.fiwentertainmentstar.Injection;
import com.fishinwater.fiwentertainmentstar.R;
import com.fishinwater.fiwentertainmentstar.persistance.Review;
import com.fishinwater.fiwentertainmentstar.ui.ReviewViewModel;
import com.fishinwater.fiwentertainmentstar.ui.ViewModelFactory;
import com.fishinwater.fiwentertainmentstar.utils.Dater;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 将复习存入数据库中
 *
 * @author fishinwater-1999
 */
public class PushReviewActivity extends AppCompatActivity {

    private final String TAG = PushReviewActivity.class.getCanonicalName();

    private Toolbar toolbar;

    private EditText mCourseEdit;

    private TextView mDateText;

    private EditText mMovieUriLink;

    private EditText mArticleUriLink;

    private Button mPushBtn;

    /**
     * 一个 ViewModel 用于获得 Activity & Fragment 实例
     */
    private ViewModelFactory mViewModelFactory;

    /**
     * 用于访问数据库
     */
    private ReviewViewModel mViewModel;

    /**
     * disposable 是订阅事件，可以用来取消订阅。防止在 activity 或者 fragment 销毁后仍然占用着内存，无法释放。
     */
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_review);

        iniViews();
    }

    private void iniViews(){
        toolbar         = findViewById(R.id.toolbar);
        mCourseEdit     = findViewById(R.id.course);
        mDateText       = findViewById(R.id.date);
        mMovieUriLink   = findViewById(R.id.movie_url);
        mArticleUriLink = findViewById(R.id.article_url);
        mPushBtn        = findViewById(R.id.push);

        setSupportActionBar(toolbar);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        mDateText.setText(date);

        // 实例化 ViewModelFactory 对象，准备实例化 ViewModel
        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(ReviewViewModel.class);
        mPushBtn.setOnClickListener( v -> {
                pushPlan();
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 取消订阅。防止在 activity 或者 fragment 销毁后仍然占用着内存，无法释放。
        mDisposable.clear();
    }

    private void pushPlan(){
        Review review = new Review(mMovieUriLink.getText().toString(),
                mArticleUriLink.getText().toString(),
                mCourseEdit.getText().toString(),
                Dater.getYMDString(new Date()));
        // 在完成用户名更新之前禁用“更新”按钮
        mPushBtn.setEnabled(false);
        // 开启观察者模式
        // 更新用户信息，结束后重新开启按钮
        mDisposable.add(mViewModel.insertReview(review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        mPushBtn.setEnabled(true);
                        Toast.makeText(PushReviewActivity.this, "提交一条数据", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: Unable to update username");
                        Toast.makeText(PushReviewActivity.this, "失败一条数据", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    public static void actionStart(Activity activity){
        Intent intent = new Intent(activity, PushReviewActivity.class);
        activity.startActivity(intent);
    }
}
