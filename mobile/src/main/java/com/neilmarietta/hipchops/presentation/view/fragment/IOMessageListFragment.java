package com.neilmarietta.hipchops.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.neilmarietta.hipchops.R;
import com.neilmarietta.hipchops.internal.di.component.DaggerMessageListComponent;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.presentation.presenter.IOMessageListPresenter;
import com.neilmarietta.hipchops.presentation.view.IOMessageListView;
import com.neilmarietta.hipchops.presentation.view.adapter.IOMessageAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IOMessageListFragment extends Fragment implements IOMessageListView {

    @Inject IOMessageListPresenter mListPresenter;

    private IOMessageAdapter mMessageAdapter;

    @Bind(R.id.rv_messages) RecyclerView mMessageRecycledView;
    @Bind(R.id.rl_progress) RelativeLayout mProgressRelativeLayout;

    public IOMessageListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMessageAdapter = new IOMessageAdapter(getContext());

        DaggerMessageListComponent.builder().build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        mMessageRecycledView.setLayoutManager(new LinearLayoutManager(context()));
        mMessageRecycledView.setAdapter(mMessageAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListPresenter.setView(this);
        if (savedInstanceState == null)
            mListPresenter.initialize();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_messages_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_refresh) {
            mListPresenter.loadMessageList();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mListPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListPresenter.destroy();
    }

    @Override
    public void renderMessageList(List<IOMessage> messages) {
        if (messages != null)
            mMessageAdapter.setMessages(messages);
    }

    @Override
    public void addMessage(IOMessage message) {
        mMessageAdapter.addMessage(message);
        mMessageRecycledView.scrollToPosition(0);
    }

    public void onAddMessageButtonClick() {
        mListPresenter.addMessage();
    }

    public void onAddMessageButtonClick(String input) {
        mListPresenter.addMessage(input);
    }

    @Override
    public void showLoading() {
        mProgressRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mMessageRecycledView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }
}
