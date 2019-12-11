package com.example.weatherapp.ui.onboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.weatherapp.R;

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ImageView imageView;
    private TextView textView;
    private String[] txt;
    private int[] imgID;

    public ViewPagerAdapter(Context mContext, String[] txt, int[] imgID) {
        this.mContext = mContext;
        this.txt = txt;
        this.imgID = imgID;
    }

    @Override
    public int getCount() {
        return txt.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_pager, container, false);
        imageView = itemView.findViewById(R.id.img_board);
        textView = itemView.findViewById(R.id.txt_board);
        textView.setText(txt[position]);
        imageView.setImageResource(imgID[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
