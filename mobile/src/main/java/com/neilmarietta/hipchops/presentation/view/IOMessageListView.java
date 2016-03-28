package com.neilmarietta.hipchops.presentation.view;

import com.neilmarietta.hipchops.presentation.model.IOMessage;

import java.util.List;

public interface IOMessageListView extends LoadDataView {

    void renderMessageList(List<IOMessage> messages);

    void addMessage(IOMessage message);
}
