package me.mrrobot97.designer.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.List;
import me.mrrobot97.designer.R;
import me.mrrobot97.designer.Utils.ScreenUtils;
import me.mrrobot97.designer.model.Attachment;

/**
 * Created by mrrobot on 16/10/25.
 */

public class AttachmentsAdapter extends RecyclerView.Adapter {
  public void setData(List<Attachment> data) {
    mData = data;
  }

  public static final int ANIM_DURATION = 500;
  private List<Attachment> mData;
  private int screenWidth;
  private Context mContext;
  private static final int offset = 8;

  public AttachmentsAdapter(List<Attachment> data, Context context) {
    mData = data;
    mContext = context;
    screenWidth = ScreenUtils.getScreenWidthAndHeight(mContext)[0];
  }

  private OnItemClickListener mListener;

  public void setListener(OnItemClickListener listener) {
    mListener = listener;
  }

  private void runAnim(View view) {
    view.setTranslationX(screenWidth);
    view.animate().translationX(0).setDuration(ANIM_DURATION).setStartDelay(300).start();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item_layout, null);
    //设置宽度
    int width = (screenWidth - offset * 4) / 3;
    int height = width * 3 / 4;
    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
    view.setLayoutParams(params);
    MyHolder holder = new MyHolder(view);
    runAnim(view);
    return holder;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    Attachment attachment = mData.get(position);
    Glide.with(mContext).load(attachment.getThumbnail_url()).into(((MyHolder) holder).mImageView);
    ((MyHolder) holder).mImageView.setOnClickListener(view -> {
      if (mListener != null) {
        mListener.onItemClicked(position);
      }
    });
  }

  @Override public int getItemCount() {
    return mData.size();
  }

  public static class MyHolder extends RecyclerView.ViewHolder {
    public ImageView getImageView() {
      return mImageView;
    }

    @BindView(R.id.image_view) ImageView mImageView;

    public MyHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public static class MyItemDecoration extends RecyclerView.ItemDecoration {
    @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
        RecyclerView.State state) {
      super.getItemOffsets(outRect, view, parent, state);
      outRect.set(offset, offset, offset, offset);
    }
  }

  public interface OnItemClickListener {
    void onItemClicked(int position);
  }
}
