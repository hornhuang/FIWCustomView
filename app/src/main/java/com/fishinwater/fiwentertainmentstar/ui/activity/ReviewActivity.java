package com.fishinwater.fiwentertainmentstar.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fishinwater.fiwentertainmentstar.R;
import com.fishinwater.fiwentertainmentstar.adapter.ReviewAdapter;
import com.fishinwater.fiwentertainmentstar.persistance.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        iniViews();
        iniRecyclerView();
    }

    private void iniViews(){
        toolbar.setTitle("复习");
        toolbar.setTitleTextColor(Color.WHITE);

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
        PushReviewActivity.actionStart(getActivity());
    }

    private void iniRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        mLessonLists = new ArrayList<>();
        adapter = new LessonAdapter(mLessonLists, getActivity());
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void getData(){
        BmobQuery<Lesson> query = new BmobQuery<>();
        query.order("-createdAt")
                .findObjects(new FindListener<Lesson>() {
                    @Override
                    public void done(List<Lesson> object, BmobException e) {
                        if (e == null) {
                            resetPlanLists(object);
                            mRefreshLayout.setRefreshing(false);
                        } else {
                            toast("失败！" + e.getMessage());
                        }
                    }
                });
    }

    private void resetPlanLists(List<Lesson> initialLessons){
        List<Lesson> resetList = new ArrayList<>();
        for (Lesson plan : initialLessons){
            int days = Dater.getDiscrepantDays(plan.getDate(), new Date());
            if( days == 0 || days == 1 || days == 2 || days == 4 || days == 5 || days == 15  ){
                resetList.add(plan);
            }
            if (days > 15){
                removeDBPlan(plan);
            }
        }
        mLessonLists.clear();
        mLessonLists.addAll(resetList);
        adapter.notifyDataSetChanged();
    }

    private void removeDBPlan(final Lesson expiredLesson){
        new Thread(){
            @Override
            public void run() {
                super.run();
                expiredLesson.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.d(TAG, "删除成功:" + expiredLesson.getUpdatedAt());
                        }else{
                            Log.d(TAG,"删除失败：" + e.getMessage());
                        }
                    }

                });
            }
        }.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
