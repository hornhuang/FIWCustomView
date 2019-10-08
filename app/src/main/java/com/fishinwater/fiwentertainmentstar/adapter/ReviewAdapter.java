package com.fishinwater.fiwentertainmentstar.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.fishinwater.fiwentertainmentstar.R;
import com.fishinwater.fiwentertainmentstar.dialog.UpdateReviewDialog;
import com.fishinwater.fiwentertainmentstar.persistance.Review;
import com.fishinwater.fiwentertainmentstar.ui.ReviewViewModel;
import com.fishinwater.fiwentertainmentstar.ui.activity.ReviewActivity;
import com.fishinwater.fiwentertainmentstar.ui.activity.WebActivity;
import com.fishinwater.fiwentertainmentstar.utils.Dater;
import com.fishinwater.fiwentertainmentstar.utils.PopupWindowUtil;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 复习计划配适器
 *
 * @author fishinwater-1999
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private String TAG = "PlanAdapter";

    private int x, y;

    private PopupWindow mPopupWindow;

    private List<Review> mList;

    private Activity mContext;

    private ReviewViewModel mReviewViewModel;

    public ReviewAdapter(List<Review> mList, Activity mContext, ReviewViewModel mReviewViewModel){
        this.mList   = mList;
        this.mContext = mContext;
        this.mReviewViewModel = mReviewViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_review, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Review review = mList.get(i);
        viewHolder.mName.setText(review.getName());
        viewHolder.mCourse.setText(review.getMovie());
        viewHolder.mDate.setText(review.getDate());
        String str = "距离首次学习："+ Dater.getDiscrepantDays(Dater.getDateByYMDString(review.getDate()), new Date())+"天";
        viewHolder.mDatePoor.setText(str);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(mContext, v, review.getArticle());
            }
        });
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mPopupWindow != null && mPopupWindow.isShowing()){
                    return true;
                }
                x = (int) event.getRawX();
                y = (int) event.getRawY();
                return false;
            }
        });
        final View finalConvertView = viewHolder.itemView;
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick");
                showPopupWindow(finalConvertView, review);
                return false;
            }
        });
    }

    private void showPopupWindow(View anchorView, Review plan) {
        View contentView = getPopupWindowContentView(plan);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        int[] windowPos = PopupWindowUtil.calculatePopWindowPos(anchorView, contentView, x, y);
        mPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

    /**
     * item 弹出框
     *
     * 长按弹出，选择功能
     * 视频、修改、删除
     * @param review
     * @return
     */
    private View getPopupWindowContentView(final Review review) {
        // 一个自定义的布局，作为显示的内容
        // 布局ID
        int layoutId = R.layout.popup_content_layout;
        View contentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = v-> {
            switch (v.getId()) {
                case R.id.menu_item1:
                    showDeleteDialog(review);
                    break;
                case R.id.menu_item2:
                    Uri uri = Uri.parse(review.getMovie());
                    Intent mWebIntent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(mWebIntent);
                    break;
                case R.id.menu_item3:
                    UpdateReviewDialog.build(mContext, review, mReviewViewModel);
                    break;

                default:
            }
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
            }
        };
        contentView.findViewById(R.id.menu_item1).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.menu_item2).setOnClickListener(menuItemOnClickListener);
        contentView.findViewById(R.id.menu_item3).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    /**
     * @setIcon 设置对话框图标
     * @setTitle 设置对话框标题
     * @setMessage 设置对话框消息提示
     * setXXX方法返回Dialog对象，因此可以链式设置属性
     */
    private void showDeleteDialog(final Review plan){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        normalDialog.setIcon(R.mipmap.app_icon);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("你确定要删除本计划?");
        normalDialog.setPositiveButton("确定",
                (dialog, which) -> {
                    ((ReviewActivity) mContext).removeDBPlan(plan);
                });
        normalDialog.setNegativeButton("关闭",
                (dialog, which) -> {
                    //...To-do
                });
        // 显示
        normalDialog.show();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView mName;

        @BindView(R.id.course)
        EditText mCourse;

        @BindView(R.id.date)
        TextView mDate;

        @BindView(R.id.date_poor)
        TextView mDatePoor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
