package com.neilmarietta.hipchops.presentation.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.neilmarietta.hipchops.R;
import com.neilmarietta.hipchops.presentation.view.fragment.IOMessageListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view) NavigationView mNavigationView;
    @Bind(R.id.fab_add) FloatingActionButton mFabAdd;
    @Bind(R.id.fab_send) FloatingActionButton mFabSend;
    @Bind(R.id.input_edit_text) EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                addMessageFromTestCases();
            }
        });

        mFabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                addMessageFromInput();
            }
        });
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND)
                    addMessageFromInput();
                return false;
            }
        });
    }

    private void addMessageFromTestCases() {
        IOMessageListFragment fragment = (IOMessageListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_message_list_fragment);
        if (fragment != null)
            fragment.onAddMessageButtonClick();
    }

    private void addMessageFromInput() {
        IOMessageListFragment fragment = (IOMessageListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.content_message_list_fragment);
        if (fragment != null)
            fragment.onAddMessageButtonClick(mEditText.getText().toString());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if ((drawer != null) && drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_linkedin) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://linkedin.com/in/neilmarietta")));
        } else if (id == R.id.nav_github) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/neilmarietta")));
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
