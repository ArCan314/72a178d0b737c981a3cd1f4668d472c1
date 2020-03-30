package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;

import com.airbnb.lottie.LottieAnimationView;

import static com.airbnb.lottie.LottieDrawable.INFINITE;

public class PlaceholderFragment extends Fragment {

    private LottieAnimationView lottieAnimationView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        // return inflater.inflate(R.layout.fragment_placeholder, container, false);
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        lottieAnimationView = view.findViewById(R.id.fragment_lottie_animation_view);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(50);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入

                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(lottieAnimationView, "Alpha", 1.0f, 0.0f);
                objectAnimator1.setDuration(1000);

                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(recyclerView, "Alpha", 0.0f, 1.0f);
                objectAnimator2.setDuration(1000);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimator1, objectAnimator2);
                animatorSet.start();
            }
        }, 5000);
    }
}
