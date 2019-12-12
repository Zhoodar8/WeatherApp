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
import com.example.weatherapp.data.OnBoardData;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ImageView imageView;
    private TextView textView;
    ArrayList<OnBoardData> board ;
    public ViewPagerAdapter(ArrayList<OnBoardData> board) {
        this.board =board;}
    @Override
    public int getCount() {
        return board.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.view_pager, null);
        imageView = itemView.findViewById(R.id.img_board);
        textView = itemView.findViewById(R.id.txt_board);
        imageView.setImageDrawable(container.getContext().getResources().getDrawable(board.get(position).getImg()));
        textView.setText(board.get(position).getTxt());
        container.addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object); }
}
