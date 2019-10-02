package com.fishinwater.fiwentertainmentstar.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fishinwater.fiwentertainmentstar.R;
import com.fishinwater.fiwentertainmentstar.persistance.Review;
import com.fishinwater.fiwentertainmentstar.ui.activity.WebActivity;
import com.fishinwater.fiwentertainmentstar.utils.Dater;

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

    private List<Review> mList;

    private Activity context;

    public ReviewAdapter(List<Review> mList, Activity context){
        this.mList   = mList;
        this.context = context;
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
        // String str = "距离首次学习："+ Dater.getDiscrepantDays(Dater.getDateByYMDString(review.getDate()), new Date())+"天";
        // viewHolder.mDatePoor.setText(str);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.actionStart(context, review.getArticle());
            }
        });
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
