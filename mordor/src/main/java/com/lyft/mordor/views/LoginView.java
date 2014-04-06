package com.lyft.mordor.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lyft.mordor.R;
import com.lyft.mordor.screens.LoginScreen;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import flow.Layout;
import mortar.Mortar;

/**
 * Created by zakharov on 4/6/14.
 */

@Layout(R.layout.login)
public class LoginView extends LinearLayout {
    @Inject
    LoginScreen.Presenter presenter;

    @InjectView(R.id.name_edit_text)
    EditText nameEditText;

    @InjectView(R.id.password_edit_text)
    EditText passwordEditText;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.takeView(this);

        ButterKnife.inject(this);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);
    }

    @OnClick(R.id.sign_in_button)
    public void onSignInClicked(View view) {
        presenter.login(
                nameEditText.getText().toString(),
                passwordEditText.getText().toString());
    }
}