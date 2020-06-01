package com.ay.lxunhan.utils;

import android.widget.TextView;

import com.ay.lxunhan.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.ay.lxunhan.utils.StringUtil.getString;


/**
 * author: yu.jl
 * e-mail: bbfx89625@gmail.com
 * time  : 2019/2/22
 * desc  :
 */
public class RxHelper {
    public static int count = 60;

    public static void getCode(final TextView tv) {
        Observable.interval(0, 1, TimeUnit.SECONDS).take(count + 1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                tv.setEnabled(false);
            }

            @Override
            public void onNext(Long aLong) {
                tv.setText(String.format(getString(R.string.format_get_code), String.valueOf(count - aLong)));
            }

            @Override
            public void onError(Throwable e) {
                tv.setEnabled(true);
                tv.setText(getString(R.string.get_code));
            }

            @Override
            public void onComplete() {
                tv.setEnabled(true);
                tv.setText(getString(R.string.get_code));
            }
        });
    }
}
