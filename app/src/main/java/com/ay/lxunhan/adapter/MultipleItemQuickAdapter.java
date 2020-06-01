package com.ay.lxunhan.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;


public class MultipleItemQuickAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {

    public MultipleItemQuickAdapter(List<T> data) {
        super(data);
        addItemTypes();
    }

    protected void addItemTypes(){
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {

    }

}


