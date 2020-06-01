package com.ay.lxunhan.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BasePresenter<V> {

    private CompositeDisposable disposables = new CompositeDisposable();
    private V view;

    protected BasePresenter(V view) {
        this.view = view;
    }

    void stop() {
        disposables.clear();
    }

    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }
}
