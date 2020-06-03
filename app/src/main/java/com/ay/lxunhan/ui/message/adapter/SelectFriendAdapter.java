package com.ay.lxunhan.ui.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ay.lxunhan.R;
import com.ay.lxunhan.bean.FriendBean;
import com.ay.lxunhan.utils.glide.GlideUtil;
import com.ay.lxunhan.widget.azlist.AZBaseAdapter;
import com.ay.lxunhan.widget.azlist.AZItemEntity;

import java.util.List;

/**
 * @author ${jlj}
 * @package com.ay.lxunhan.ui.message.adapter
 * @date 2020/6/2
 */
public class SelectFriendAdapter extends AZBaseAdapter<FriendBean, SelectFriendAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private Context context;
    private List<AZItemEntity<FriendBean>> dataList;

    public SelectFriendAdapter(Context context,List<AZItemEntity<FriendBean>> dataList) {
        super(dataList);
        this.context=context;
        this.dataList=dataList;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friend_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FriendBean friendBean=dataList.get(i).getValue();
        GlideUtil.loadCircleImgForHead(context,viewHolder.ivHeader,friendBean.getAvatar());
        viewHolder.tvName.setText(friendBean.getNickname());
        viewHolder.ivV.setVisibility(friendBean.getIs_media()?View.VISIBLE:View.GONE);
        viewHolder.ivSex.setImageDrawable(context.getResources().getDrawable(friendBean.getSex()?R.drawable.ic_man:R.drawable.ic_woman));
        viewHolder.llItem.setOnClickListener(v -> {
            if (onItemClickListener!=null){
                onItemClickListener.onItemClickListener(friendBean);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHeader;
        ImageView ivV;
        TextView tvName;
        ImageView ivSex;
        LinearLayout llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llItem=itemView.findViewById(R.id.ll_item);
            ivHeader=itemView.findViewById(R.id.iv_header);
            ivV=itemView.findViewById(R.id.iv_v);
            tvName=itemView.findViewById(R.id.tv_name);
            ivSex=itemView.findViewById(R.id.iv_sex);
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(FriendBean friendBean);
    }
}
