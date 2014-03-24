package com.lyft.mordor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;

import com.lyft.mordor.screens.Main;
import com.lyft.mordor.views.MainView;

import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarContext;
import mortar.MortarScope;

/**
 * Created by zakharov on 3/23/14.
 */
public class MainActivity extends ActionBarActivity implements MortarContext {
    private MortarActivityScope activityScope;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isWrongInstance()) {
            finish();
            return;
        }

        MortarScope parentScope = getApp().getRootScope();
        activityScope = Mortar.requireActivityScope(parentScope, new Main());
        Mortar.inject(this, this);

        activityScope.onCreate(savedInstanceState);

        setContentView(R.layout.main);
    }

    private App getApp() {
        return ((App) getApplication());
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        activityScope.onSaveInstanceState(outState);
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        if (isFinishing() && activityScope != null) {
            activityScope.destroy();
            activityScope = null;
        }
    }

    @Override public MortarScope getMortarScope() {
        return activityScope;
    }

    /**
     * Dev tools and the play store (and others?) launch with a different intent, and so
     * lead to a redundant instance of this activity being spawned. <a
     * href="http://stackoverflow.com/questions/17702202/find-out-whether-the-current-activity-will-be-task-root-eventually-after-pendin"
     * >Details</a>.
     */
    private boolean isWrongInstance() {
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            boolean isMainAction = intent.getAction() != null && intent.getAction().equals(Intent.ACTION_MAIN);
            return intent.hasCategory(Intent.CATEGORY_LAUNCHER) && isMainAction;
        }
        return false;
    }

    private MainView getMainView() {
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        return (MainView) root.getChildAt(0);
    }
}
