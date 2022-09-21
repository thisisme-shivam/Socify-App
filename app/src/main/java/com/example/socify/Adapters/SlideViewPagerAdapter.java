package com.example.socify.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.socify.R;

public class SlideViewPagerAdapter extends PagerAdapter {

    Context context;

    public SlideViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        view = layoutInflater.inflate(R.layout.fragment_buddy, container, false);

        switch (position) {
            case 0:
                view = layoutInflater.inflate(R.layout.fragment_buddy, container, false);
                break;
            case 1:
                view = layoutInflater.inflate(R.layout.fragment_community, container, false);
                break;
            case 2:
                view = layoutInflater.inflate(R.layout.fragment_events, container, false);
                break;
            case 3:
                view = layoutInflater.inflate(R.layout.fragment_questions, container, false);
                break;
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
