package com.example.classup.classup.classup.mycollege.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.classup.classup.classup.mycollege.R;

import java.util.List;

public class BranchAdapter extends PagerAdapter {
    private Context context;
    private List<BranchModel> list;

    public BranchAdapter(Context context, List<BranchModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.branch_item_layout, container, false);

        ImageView brIcon;
        TextView brTitle, brDesc;

        brIcon = view.findViewById(R.id.brIcon);
        brTitle = view.findViewById(R.id.brTitle);
        brDesc = view.findViewById(R.id.brDes); // Ensure correct ID

        brIcon.setImageResource(list.get(position).getImg());
        brTitle.setText(list.get(position).getTitle());
        brDesc.setText(list.get(position).getDescription()); // Ensure correct data binding

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
