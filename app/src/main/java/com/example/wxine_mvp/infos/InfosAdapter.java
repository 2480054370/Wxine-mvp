package com.example.wxine_mvp.infos;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.example.wxine_mvp.data.Comment;
import com.example.wxine_mvp.data.Info;
import com.example.wxine_mvp.R;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoubing on 15/10/16.
 */
public class InfosAdapter extends RecyclerView.Adapter<InfosAdapter.ViewHolder> implements View.OnClickListener {
    private final ImageLoader imageLoader = ImageLoader.getInstance();
    private final Context context;
    private List<Info> list;

    public void setList(List<Info> list) {
        this.list = list;
        notifyDataSetChanged();
        Log.v("---------", "refresh");
    }

    public void addItems(List<Info> items) {
        final int positionStart = list.size() + 1;
        list.addAll(items);
        notifyItemRangeInserted(positionStart, items.size());
        Log.v("---------", "additems");
    }

    //新增
    public void addItem(Info info, int position) {
        list.add(position, info);
        notifyItemInserted(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView user_logo;
        public TextView user_name;
        public TextView info_content;
        public ImageView info_image;
        public TextView info_comment_num;
        public ImageView info_agree_img;
        public ImageView info_share_img;
        public ImageView info_comment;


        public ViewHolder(Context context, final View v) {
            super(v);
            user_logo = (ImageView) v.findViewById(R.id.iv_user_logo);
            user_name = (TextView) v.findViewById(R.id.tv_user_name);
            info_content = (TextView) v.findViewById(R.id.tv_info_content);
            info_image = (ImageView) v.findViewById(R.id.iv_info_image);
      //      info_comment = (ImageView) v.findViewById(R.id.iv_info_comment);
      //      info_comment_num = (TextView) v.findViewById(R.id.info_comment_num);
            info_agree_img = (ImageView) v.findViewById(R.id.iv_agree_img);
            info_share_img = (ImageView) v.findViewById(R.id.iv_share_img);




//
//            info_agree_img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onAgreeClick(info_agree_img, getLayoutPosition());   //.onButton1Click(v, getLayoutPosition());
//                }
//            });
//            info_share_img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onShareClick(v, getLayoutPosition());
//                }
//            });



        }
    }

    public InfosAdapter(Context context, List<Info> list) {
        this.context = context;
        this.list = list;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Info data);

        void onAgreeClick(ImageView view, int position);

        void onShareClick(View view, int positions);

        void onCommentClick(View view, Info data);
    }

    @Override
    public InfosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_infos_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(context, v);
        //将创建的View注册点击事件
        v.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Info info = list.get(position);
      //  imageLoader.displayImage(info.getUser().getImage(), holder.user_logo);
        imageLoader.displayImage("drawable://" + R.drawable.tou,holder.user_logo);
     //   holder.user_name.setText(info.getUser().getName());
        holder.user_name.setText(info.getTitle());
        holder.info_content.setText(info.getCleancontent(80));
     //   imageLoader.displayImage(info.getImage(), holder.info_image);
   //     holder.info_comment_num.setText(String.valueOf(info.getComments().size()));

//        if (info.getComments().size() > 0) {
//            ViewGroup.LayoutParams lp = holder.comments_recycler_view.getLayoutParams();
//            lp.height = 200;
//            holder.comments_recycler_view.setLayoutParams(lp);
//            holder.comments_recycler_view.requestLayout();
//            //Log.v("=======>0======", String.valueOf(info.getComments().size()));
//            final CommentsAdapter mAdapter = new CommentsAdapter(context, new ArrayList<Comment>(info.getComments()));
//            //Log.v("==height======", String.valueOf(0));
//            holder.comments_recycler_view.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();
//
//            //自动滚动
//            final int count = info.getComments().size();
//            if (count > 1) {//一个滚动没有意义
//                final Handler handler = new Handler();
//                Runnable runnable = new Runnable() {
//                    int to = 1;
//
//                    @Override
//                    public void run() {
//                        holder.comments_recycler_view.smoothScrollToPosition(to);
//                        if (to >= count) to = 1;
//                        else to = to + 1;//重新开始
//                        handler.postDelayed(this, 3000);
//                    }
//                };
//                handler.postDelayed(runnable, 3000);
//            }
            /*holder.comments_recycler_view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.comments_recycler_view.smoothScrollToPosition(mAdapter.getItemCount());
                }
            }, 3000);*/
//        }

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(info);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Info) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
