package com.ay.lxunhan.contract;

import java.util.List;

import okhttp3.MultipartBody;

public interface IssueContract {
    interface IssuePresenter{
        void issue( String title, List<MultipartBody.Part> partList);
    }
    interface IssueView{
        void issueFinish();
        void showProgress();
        void hideProgress();
    }
}
