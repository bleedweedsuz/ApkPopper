package com.ztcartxe.reppopkpa.apkpopper.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.activities.AppDetails_Activity;
import com.ztcartxe.reppopkpa.apkpopper.model.PackageInfoItem;
import java.util.ArrayList;

public class RecyclerAdapterApps extends RecyclerView.Adapter<RecyclerAdapterApps.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter{

    Context mContext;
    ArrayList<PackageInfoItem> mList;

    public RecyclerAdapterApps(Context mContext, ArrayList<PackageInfoItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.appName.setSelected(true);
        holder.appName.setText(mList.get(position).AppName);
        holder.appImage.setImageDrawable(mList.get(position).AppIcon);
        //RecyclerView Item Click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDetails_Activity.packageInfoItem = mList.get(position);
                Intent intent = new Intent(mContext, AppDetails_Activity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public String getSectionName(int position) {
        return mList.get(position).AppName.substring(0,1);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView appImage;
        TextView appName;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            appImage = itemView.findViewById(R.id.fragmentSystemAppIcon);
            appName = itemView.findViewById(R.id.fragmentSystemAppName);
        }
    }

    public static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}