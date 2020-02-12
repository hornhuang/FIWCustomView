package com.example.customview.view_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.customview.R;

/**
 * @author fishinwater-1999
 * @version 2020-02-12
 */
public class ItemFragment extends Fragment {

    public static ItemFragment newInstance(String item) {
        Bundle args = new Bundle();
        args.putString("title", item);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, null);
        TextView textView = view.findViewById(R.id.text);
        Bundle bundle = getArguments();
        textView.setText(bundle.getShort("title"));
        return view;
    }
}
