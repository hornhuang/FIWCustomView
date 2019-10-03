package com.fishinwater.fiwentertainmentstar.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fishinwater.fiwentertainmentstar.Injection;
import com.fishinwater.fiwentertainmentstar.R;
import com.fishinwater.fiwentertainmentstar.adapter.ReviewAdapter;
import com.fishinwater.fiwentertainmentstar.persistance.Review;
import com.fishinwater.fiwentertainmentstar.ui.ReviewViewModel;
import com.fishinwater.fiwentertainmentstar.ui.ViewModelFactory;
import com.fishinwater.fiwentertainmentstar.utils.Dater;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 主活动
 * 显示复习内容
 *
 * @author fishinwater-1999
 */
public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = ReviewActivity.class.getSimpleName();

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<Review> mLessonLists;

    private ReviewAdapter adapter;

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
        setContentView(R.layout.activity_review);

        ButterKnife.bind(this);
        iniViews();
        iniRecyclerView();
    }

    private void iniViews(){
        toolbar.setTitle("复习");
        toolbar.setTitleTextColor(Color.WHITE);

        // 实例化 ViewModelFactory 对象，准备实例化 ViewModel
        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(ReviewViewModel.class);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                getData();
            }
        });
    }

    @OnClick(R.id.fab)
    public void fabOnClick(View view){
        PushReviewActivity.actionStart(this);
    }

    private void iniRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        if (mLessonLists == null){
            mLessonLists = new ArrayList<>();
        }
        adapter = new ReviewAdapter(mLessonLists, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 取消订阅。防止在 activity 或者 fragment 销毁后仍然占用着内存，无法释放。
        mDisposable.clear();
    }

    private void getData(){
        if (mLessonLists == null){
            mLessonLists = new ArrayList<>();
        }else {
            mLessonLists.clear();
        }
        mDisposable.add(mViewModel.getReviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Review>>() {
                               @Override
                               public void accept(List<Review> reviews) throws Exception {
                                   mLessonLists.addAll(reviews);
                                   adapter.notifyDataSetChanged();
                               }
                           }
                ));
        resetPlanLists(mLessonLists);
        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    private void resetPlanLists(List<Review> initialLessons){
        List<Review> resetList = new ArrayList<>();
        for (Review review : initialLessons){
            int days = Dater.getDiscrepantDays(Dater.getDateByYMDString(review.getDate()), new Date());
            if( days == 0 || days == 1 || days == 2 || days == 4 || days == 5 || days == 15  ){
                resetList.add(review);
            }
            if (days > 15){
                removeDBPlan(review);
            }
        }
        mLessonLists.clear();
        mLessonLists.addAll(resetList);
        adapter.notifyDataSetChanged();
    }

    public void removeDBPlan(final Review expiredReview){
        mDisposable.add(mViewModel.deleteReview(expiredReview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(ReviewActivity.this, "删除一条数据", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(ReviewActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
