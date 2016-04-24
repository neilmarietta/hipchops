package com.neilmarietta.hipchops.contract;

import android.content.Context;

public interface LoadDataView {

    void showLoading();

    void hideLoading();

    void showError(String message);

    Context context();
}