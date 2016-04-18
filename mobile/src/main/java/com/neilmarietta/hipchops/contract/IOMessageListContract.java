package com.neilmarietta.hipchops.contract;

import android.support.annotation.NonNull;

import com.neilmarietta.hipchops.presentation.model.IOMessage;

import java.util.List;

public class IOMessageListContract {

    public interface View extends LoadDataView {

        void renderMessageList(List<IOMessage> messages);

        void addMessage(@NonNull IOMessage message);
    }

    public interface UserActionListener {

        void loadMessageList();

        void addMessage();

        void addMessage(@NonNull String input);
    }
}
