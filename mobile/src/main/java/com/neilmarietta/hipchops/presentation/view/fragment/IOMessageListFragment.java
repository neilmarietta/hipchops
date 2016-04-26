package com.neilmarietta.hipchops.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.neilmarietta.hipchops.HipChopsApplication;
import com.neilmarietta.hipchops.R;
import com.neilmarietta.hipchops.contract.IOMessageList;
import com.neilmarietta.hipchops.internal.di.component.DaggerMessageComponent;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.neilmarietta.hipchops.presentation.presenter.IOMessageListPresenter;
import com.neilmarietta.hipchops.presentation.view.adapter.IOMessageAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IOMessageListFragment extends Fragment implements IOMessageList.View {

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

        DaggerMessageComponent.builder()
                .apiConnectionComponent(HipChopsApplication.from(getContext()).getApiConnectionComponent())
                .build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        mMessageRecycledView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageRecycledView.setAdapter(mMessageAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListPresenter.attachView(this);
        if (savedInstanceState == null)
            mListPresenter.initialize();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListPresenter.detachView();
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
    public void renderMessageList(List<IOMessage> messages) {
        mMessageAdapter.setMessages(messages);
    }

    @Override
    public void addMessage(@NonNull IOMessage message) {
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
}
