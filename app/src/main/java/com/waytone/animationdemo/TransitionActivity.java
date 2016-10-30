package com.waytone.animationdemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transitionseverywhere.ArcMotion;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class TransitionActivity extends AppCompatActivity {

    private boolean visible;
    private boolean isRotated;
    private boolean isFirstText;
    private boolean isReturnAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);


        // 默认效果
        final LinearLayout transitionsContainer = (LinearLayout) findViewById(R.id.ll_container_one);
        final TextView text = (TextView) findViewById(R.id.tv_one);
        final Button button = (Button) findViewById(R.id.btn_one);

        button.setOnClickListener(v -> Snackbar.make(transitionsContainer, "haha", Snackbar.LENGTH_SHORT).show());

        button.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(transitionsContainer);

            if (text.getVisibility() == View.VISIBLE) {
                text.setVisibility(View.GONE);
            } else {
                text.setVisibility(View.VISIBLE);
            }
        });


        // Fade
        final ViewGroup transitionsContainerTwo = (ViewGroup) findViewById(R.id.ll_container_two);
        final TextView textTwo = (TextView) findViewById(R.id.tv_two);
        final Button buttonTwo = (Button) findViewById(R.id.btn_two);

        buttonTwo.setOnClickListener(v -> {

            Fade fade = new Fade();
            TransitionManager.beginDelayedTransition(transitionsContainerTwo, fade);

            if (textTwo.getVisibility() == View.VISIBLE) {
                textTwo.setVisibility(View.GONE);
            } else {
                textTwo.setVisibility(View.VISIBLE);
            }

        });


        // slide
        final ViewGroup transitionsContainerSlide = (ViewGroup) findViewById(R.id.ll_container_slide);
        final TextView textSlide = (TextView) findViewById(R.id.tv_slide);
        final Button buttonSlide = (Button) findViewById(R.id.btn_slide);

        buttonSlide.setOnClickListener(v -> {

//            Slide slide = new Slide();
            Slide slide = new Slide(Gravity.RIGHT);
            TransitionManager.beginDelayedTransition(transitionsContainerSlide, slide);

            if (textSlide.getVisibility() == View.VISIBLE) {
                textSlide.setVisibility(View.GONE);
            } else {
                textSlide.setVisibility(View.VISIBLE);
            }

        });

        // Scale
        final ViewGroup transitionsContainerScale = (ViewGroup) findViewById(R.id.ll_container_scale);
        final TextView textScale = (TextView) findViewById(R.id.tv_scale);
        final Button buttonScale = (Button) findViewById(R.id.btn_scale);

        buttonScale.setOnClickListener(v -> {

            Scale scale = new Scale();
            TransitionManager.beginDelayedTransition(transitionsContainerScale, scale);

            if (textScale.getVisibility() == View.VISIBLE) {
                textScale.setVisibility(View.GONE);
            } else {
                textScale.setVisibility(View.VISIBLE);
            }

        });

        // TransitionSet
        final ViewGroup transitionsContainerSet = (ViewGroup) findViewById(R.id.ll_container_set);
        final TextView textSet = (TextView) findViewById(R.id.tv_set);
        final Button buttonSet = (Button) findViewById(R.id.btn_set);

        TransitionSet set = new TransitionSet()
                .addTransition(new Scale(0.7f))
                .addTransition(new Fade())
                .setInterpolator(visible ? new LinearOutSlowInInterpolator() :
                        new FastOutLinearInInterpolator());


        buttonSet.setOnClickListener(v -> {

            TransitionManager.beginDelayedTransition(transitionsContainerSet, set);

            if (textSet.getVisibility() == View.VISIBLE) {
                textSet.setVisibility(View.GONE);
                visible = true;

            } else {
                visible = false;
                textSet.setVisibility(View.VISIBLE);
            }

        });


        // ReColor
        final ViewGroup transitionsContainerRecolor = (ViewGroup) findViewById(R.id.ll_container_recolor);
        final Button btnRecolor = (Button) findViewById(R.id.btn_recolor);
        final Button btnNormal = (Button) findViewById(R.id.btn_normal);

        int green = getResources().getColor(R.color.green);
        int white = getResources().getColor(R.color.white);
        int grey = getResources().getColor(R.color.grey);

        btnRecolor.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(transitionsContainerRecolor, new Recolor());
//            btnRecolor.setBackgroundColor(visible ? green : white); // 无动画效果
            btnRecolor.setTextColor(visible ? white : grey);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                btnRecolor.setBackground(new ColorDrawable(visible ? green : white));
//            } else {
            btnRecolor.setBackgroundDrawable(new ColorDrawable(visible ? green : white));
//            }
            visible = !visible;
        });

        btnNormal.setOnClickListener(v -> {
            btnNormal.setBackgroundColor(visible ? green : white);
            btnNormal.setTextColor(visible ? white : green);
            visible = !visible;
        });


        // rotate
        final ViewGroup transitionsContainerRotate = (ViewGroup) findViewById(R.id.ll_container_rotate);
        final Button btnRotate = (Button) findViewById(R.id.btn_rotate);
        ImageView ivRotate = (ImageView) findViewById(R.id.iv_rotate);

        btnRotate.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(transitionsContainerRotate, new Rotate());
            ivRotate.setRotation(isRotated ? 0 : 135);
            isRotated = !isRotated;
        });


        // ChangeText
        final LinearLayout transitionsContainerChangeText = (LinearLayout) findViewById(R.id.ll_container_change_text);
        final TextView tvText = (TextView) findViewById(R.id.tv_text);
        final Button btnChangeText = (Button) findViewById(R.id.btn_change_text);
        String secText = " Second Text";
        String firstText = "First Text";

        tvText.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(transitionsContainerChangeText,
                    new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN));


            tvText.setText(isFirstText ? secText : firstText);
            isFirstText = !isFirstText;

        });

        // Targets 设置动画的目标对象
        final LinearLayout transitionsContainerTarget = (LinearLayout) findViewById(R.id.ll_container_target);
        final Button btnTarget = (Button) findViewById(R.id.btn_target);
        final TextView tvFade = (TextView) findViewById(R.id.tv_target_fade);
        final TextView tvSlide = (TextView) findViewById(R.id.tv_target_slide);


        btnTarget.setOnClickListener(v -> {
            Slide slide = new Slide(Gravity.RIGHT);
            slide.excludeTarget(tvFade, true);

            Fade fade = new Fade();
            fade.excludeTarget(tvSlide, true);

            TransitionSet transitionSet = new TransitionSet()
                    .addTransition(slide)
                    .addTransition(fade);

            TransitionManager.beginDelayedTransition(transitionsContainerTarget, transitionSet);

            if (tvFade.getVisibility() == View.VISIBLE) {
                tvFade.setVisibility(View.GONE);
                tvSlide.setVisibility(View.GONE);
            } else {
                tvFade.setVisibility(View.VISIBLE);
                tvSlide.setVisibility(View.VISIBLE);
            }

        });

        // TransitionName 做打乱动画
        final LinearLayout transitionsContainerTransitionName = (LinearLayout) findViewById(R.id.ll_container_transition_name);
        final Button btnTransitionName = (Button) findViewById(R.id.btn_transition_name);

        LayoutInflater inflater = LayoutInflater.from(this);
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            titles.add(String.format(Locale.CHINA, "Item %d", i));
        }
        createViews(inflater, transitionsContainerTransitionName, titles);

        btnTransitionName.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(transitionsContainerTransitionName, new ChangeBounds());
            Collections.shuffle(titles);
            createViews(inflater, transitionsContainerTransitionName, titles);
        });


        // path motion 路径过渡动画
        final FrameLayout transitionsContainerPathMotion = (FrameLayout) findViewById(R.id.fl_container_path_motion);
        Button btnPathMotion = (Button) findViewById(R.id.btn_path_motion);

        btnPathMotion.setOnClickListener(v -> {

            TransitionManager.beginDelayedTransition(transitionsContainerPathMotion,
                    new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) btnPathMotion.getLayoutParams();
            params.gravity = isReturnAnimation ? (Gravity.LEFT | Gravity.TOP) :
                    (Gravity.BOTTOM | Gravity.RIGHT);
            btnPathMotion.setLayoutParams(params);
            isReturnAnimation = !isReturnAnimation;

        });




    }


    // In createViews we should provide transition name for every view.

    private static void createViews(LayoutInflater inflater, ViewGroup layout, List<String> titles) {
        layout.removeAllViews();
        for (String title : titles) {
            TextView textView = (TextView) inflater.inflate(R.layout.text_view, layout, false);
            textView.setText(title);
            TransitionManager.setTransitionName(textView, title);
            layout.addView(textView);
        }
    }


}
