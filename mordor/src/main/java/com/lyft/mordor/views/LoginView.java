package com.lyft.mordor.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lyft.mordor.R;
import com.lyft.mordor.screens.LoginScreen;
import com.lyft.mordor.utils.Binder;
import com.lyft.mordor.utils.PropertyChangedCallback;

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
    private static final String TAG = "LoginView";

    private final Binder binder;

    @Inject
    LoginScreen.Presenter presenter;

    @InjectView(R.id.name_edit_text)
    EditText nameEditText;

    @InjectView(R.id.password_edit_text)
    EditText passwordEditText;

    @InjectView(R.id.sign_in_button)
    Button signInButton;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);

        this.binder = new Binder();
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.takeView(this);

        ButterKnife.inject(this);

        binder.bind(presenter.inProgress(), new PropertyChangedCallback<Boolean>() {
            @Override
            public void onPropertyChanged(Boolean value) {
                Log.d(TAG, "onPropertyChanged" + value);
                nameEditText.setEnabled(!value);
                passwordEditText.setEnabled(!value);
                signInButton.setEnabled(!value);
            }
        });
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.dropView(this);

        binder.detach();
    }

    @OnClick(R.id.sign_in_button)
    public void onSignInClicked(View view) {
        presenter.login(
                nameEditText.getText().toString(),
                passwordEditText.getText().toString());
    }
}