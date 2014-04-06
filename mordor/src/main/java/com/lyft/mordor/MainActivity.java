package com.lyft.mordor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lyft.mordor.android.ActionBarOwner;
import com.lyft.mordor.screens.Main;
import com.lyft.mordor.views.MainView;

import javax.inject.Inject;

import flow.Flow;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarContext;
import mortar.MortarScope;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_LAUNCHER;
import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;

/**
 * Created by zakharov on 3/23/14.
 */
public class MainActivity extends ActionBarActivity implements MortarContext, ActionBarOwner.View {
    private MortarActivityScope activityScope;
    private ActionBarOwner.MenuAction actionBarMenuAction;

    @Inject ActionBarOwner actionBarOwner;
    private Flow mainFlow;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isWrongInstance()) {
            finish();
            return;
        }

        MortarScope parentScope = ((App) getApplication()).getRootScope();
        activityScope = Mortar.requireActivityScope(parentScope, new Main());
        Mortar.inject(this, this);

        activityScope.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MainView mainView = (MainView) findViewById(R.id.container);
        mainFlow = mainView.getFlow();

        actionBarOwner.takeView(this);
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        activityScope.onSaveInstanceState(outState);
    }

    /** Inform the view about back events. */
    @Override public void onBackPressed() {
        // Give the view a chance to handle going back. If it declines the honor, let super do its thing.
        if (!mainFlow.goBack()) super.onBackPressed();
    }

    /** Inform the view about up events. */
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return mainFlow.goUp();
        }

        return super.onOptionsItemSelected(item);
    }

    /** Configure the action bar menu as required by {@link ActionBarOwner.View}. */
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        if (actionBarMenuAction != null) {
            menu.add(actionBarMenuAction.title)
                    .setShowAsActionFlags(SHOW_AS_ACTION_ALWAYS)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override public boolean onMenuItemClick(MenuItem menuItem) {
                            actionBarMenuAction.action.call();
                            return true;
                        }
                    });
        }
        return true;
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        actionBarOwner.dropView(this);

        // activityScope may be null in case isWrongInstance() returned true in onCreate()
        if (isFinishing() && activityScope != null) {
            activityScope.destroy();
            activityScope = null;
        }
    }

    @Override public MortarScope getMortarScope() {
        return activityScope;
    }

    @Override public void setShowHomeEnabled(boolean enabled) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override public void setUpButtonEnabled(boolean enabled) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(enabled);
        actionBar.setHomeButtonEnabled(enabled);
    }

    @Override public void setMenu(ActionBarOwner.MenuAction action) {
        if (action != actionBarMenuAction) {
            actionBarMenuAction = action;
            invalidateOptionsMenu();
        }
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
            boolean isMainAction = intent.getAction() != null && intent.getAction().equals(ACTION_MAIN);
            return intent.hasCategory(CATEGORY_LAUNCHER) && isMainAction;
        }
        return false;
    }
}