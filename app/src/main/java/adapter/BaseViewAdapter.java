package adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import utils.ListItem;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public abstract class BaseViewAdapter <T extends ListItem> extends RecyclerView.Adapter{


    protected Activity mActivity;
    protected List<T> data;

    public T getDataT(int p) {
        return data.get(p);
    }

    public void setDataT(int p, T t) {
        data.set(p, t);
        notifyItemChanged(p);
    }

    public BaseViewAdapter(Activity activity) {
        this.mActivity = activity;
        this.data = new ArrayList<>();
    }

    public int getItemCount() {
        return getNewItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = onNewCreateViewHolder(parent, viewType);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        onNewBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        int itemtype_result = getNewItemViewType(position);
        return itemtype_result;
    }


    /**
     * 要显示的item
     *
     * @param parent
     * @param viewType 对应的viewType
     * @return
     */
    public abstract RecyclerView.ViewHolder onNewCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定item
     *
     * @param holder   对应viewType的holder
     * @param position list的下标
     */
    public abstract void onNewBindViewHolder(RecyclerView.ViewHolder holder, final int position);

    /**
     * 要展示的item个数
     *
     * @return
     */
    public abstract int getNewItemCount();

    /**
     * 在需要foot时要展示的itemtype 不可返回9999
     *
     * @param position
     * @return
     */
    public abstract int getNewItemViewType(int position);

    public interface IFootViewAdapter {
        void onLoadMore();
    }

    public List<T> getData() {
        return data;
    }


    public void clearNotNotify() {
        if (data != null) {
            data.clear();
        }
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> datas) {
        if (datas != null) {
            data.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void setData(List<T> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data.clear();
        }
        notifyDataSetChanged();
    }
}
