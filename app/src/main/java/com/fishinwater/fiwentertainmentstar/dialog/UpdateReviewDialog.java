package com.fishinwater.fiwentertainmentstar.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fishinwater.fiwentertainmentstar.Injection;
import com.fishinwater.fiwentertainmentstar.R;
import com.fishinwater.fiwentertainmentstar.persistance.Review;
import com.fishinwater.fiwentertainmentstar.ui.ReviewViewModel;
import com.fishinwater.fiwentertainmentstar.ui.ViewModelFactory;
import com.fishinwater.fiwentertainmentstar.ui.activity.PushReviewActivity;
import com.fishinwater.fiwentertainmentstar.ui.activity.ReviewActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 自定义布局 Dialog
 *
 * setView()只会覆盖AlertDialog的Title与Button之间的那部分，而setContentView()则会覆盖全部，
 * setContentView()必须放在show()的后面
 *
 * @author fishinwater-1999
 */
public final class UpdateReviewDialog {

    public static void build(@NonNull Context mContext, final Review review, @NonNull ReviewViewModel mViewModel) {
        Toast.makeText(mContext, "created", Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_update_review, null);
        TextView mRevName  = v.findViewById(R.id.review_name);
        EditText mEditMovie   = v.findViewById(R.id.review_movie);
        EditText mEDitArticle = v.findViewById(R.id.review_article);
        FloatingActionButton mBtnSubmit = v.findViewById(R.id.review_submit);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分

        mRevName.setText(review.getName());
        mEditMovie.setText(review.getMovie());
        mEDitArticle.setText(review.getArticle());

        final Dialog dialog = builder.create();
        dialog.show();
        //自定义布局应该在这里添加，要在dialog.show()的后面
        dialog.getWindow().setContentView(v);
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mBtnSubmit.setEnabled(false);
                mViewModel.insertReview(new Review(review.getId(), mEditMovie.getText().toString(), mEDitArticle.getText().toString(), review.getName(), review.getDate()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mBtnSubmit.setEnabled(true);
                        Toast.makeText(mContext, "失败 Failed！原因：" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
