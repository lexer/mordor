package com.lyft.mordor.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyft.mordor.R;
import com.lyft.mordor.screens.Main;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mortar.Mortar;


/**
 * Created by zakharov on 3/23/14.
 */
public class MainView extends LinearLayout {
    @Inject
    Main.Presenter presenter;

    @InjectView(R.id.hello_text_view)
    TextView textView;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.inject(this);

        presenter.takeView(this);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    public void show(CharSequence stuff) {
        textView.setText(stuff);
    }
}