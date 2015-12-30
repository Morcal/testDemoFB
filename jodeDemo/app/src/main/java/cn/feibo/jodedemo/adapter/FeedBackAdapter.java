package cn.feibo.jodedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;
import java.util.zip.Inflater;

import cn.feibo.jodedemo.Dao.utils.UIUtil;
import cn.feibo.jodedemo.R;
import cn.feibo.jodedemo.manager.LoadListener;
import cn.feibo.jodedemo.model.Feedback;
import cn.feibo.jodedemo.widget.VImageView;

/**
 * Created by Administrator on 2015/12/28.
 */
public class FeedBackAdapter extends BaseAdapter {
    private static final int SYSTEM_TYPE = 0;
    private static final int USER_TYPE = 1;
    private Context context;
    private List<Feedback> list;
    private Feedback fb;

    public FeedBackAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<Feedback> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    public List<Feedback> getItems() {
        return list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int type = getItemViewType(position);
        int layout = type == SYSTEM_TYPE ? R.layout.item_fbl_system : R.layout.item_fbr_user;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            holder = new ViewHolder();
            holder.view = convertView.findViewById(R.id.iv_avatar);
            holder.content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            ;
            holder = (ViewHolder) convertView.getTag();
        }
        fb = (Feedback) getItem(position);
        // 设置用户头像
        if (type == USER_TYPE) {
            if (fb.author == null) {
                ((VImageView) holder.view).getImageView().setImageResource(R.drawable.default_avatar);
            } else {
                // UIL
                ImageLoader.getInstance().displayImage(fb.author.avatar, (ImageView) holder.view);
            }
        }
        holder.content.setText(fb.content);
        return convertView;
    }

    public String getUserAvatarurl() {
        if (fb.type == USER_TYPE) {
            if (fb.author.avatar != null) {
                Log.i("FeedBackAdapter", "avatar---> " + fb.author.avatar);
                return fb.author.avatar;
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return ((Feedback) getItem(position)).type == Feedback.TYPE_SYSTEM ? SYSTEM_TYPE : USER_TYPE;
    }

    public static class ViewHolder {
        private View view;
        private TextView content;
    }
}
