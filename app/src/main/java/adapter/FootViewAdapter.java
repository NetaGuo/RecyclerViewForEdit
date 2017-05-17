package adapter;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.guoying.editrecyclerview.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import enums.EFootType;
import utils.ListItem;
import viewholder.FootViewHolder;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public abstract class FootViewAdapter<T extends ListItem> extends RecyclerView.Adapter{

    protected Activity mActivity;
    protected List<T> data;
    private boolean create=true;
    private int headerHeight;

    public List<T> getData() {
        return data;
    }
    /**
     * 开启关闭Foot
     *
     * @param enabledFoot
     */
    public void setEnabledFoot(boolean enabledFoot) {
        if (enabledFoot != isEnabledFoot) {
            this.isEnabledFoot = enabledFoot;
            notifyDataSetChanged();
        }
    }

    /**
     * 开启关闭显示没有更多数据
     *
     * @param enabledFoot
     */
    public void isShwoNoDataFoot(boolean enabledFoot) {
        if (enabledFoot != isShwoNoDataFoot) {
            this.isShwoNoDataFoot = enabledFoot;
            if (!isShwoNoDataFoot) {
                isCloseShwoNoDataFoot = true;
            }
        }
    }

    public T getDataT(int p) {
        if(p>data.size() || p<0){
            return null;
        }
        return data.get(p);
    }

    public void setDataT(int p, T t) {
        data.set(p, t);
        notifyItemChanged(p);
    }

    private boolean isEnabledFoot = true, isShwoNoDataFoot = true, isCloseShwoNoDataFoot = false;

    public void setGONEFiristAddViewHolder(boolean isGONEFiristAddViewHolder) {
        isGONEFiristAddViewHolder = isGONEFiristAddViewHolder;
    }

    private boolean isGONEFiristAddViewHolder = false;
    protected IFootViewAdapter mIFVA;

    public FootViewAdapter(Activity activity, IFootViewAdapter iFVA) {
        this.mActivity = activity;
        this.mIFVA = iFVA;
        this.foottext = mActivity.getResources().getString(R.string.loading_add);
        this.data = new ArrayList<>();
    }

    @IntDef({EFootType.FOOT_LOADING_ADD,EFootType.FOOT_ERROR_LOADDATA,EFootType.FOOT_LOADING_MORE,EFootType.FOOT_NO_LOADDATA})
    @Retention(RetentionPolicy.SOURCE)
    private @interface FootType{}
    /**
     * FOOT_LOADING_ADD 点击加载更多
     * FOOT_LOADING_MORE 正在加载更多
     * FOOT_NO_LOADDATA 没有更多数据
     * FOOT_ERROR_LOADDATA 加载数据失败
     */
    public void setFootType(@FootType int type) {
        if (!isEnabledFoot) {
            return;
        }
        if (this.type == EFootType.FOOT_NO_LOADDATA && type == EFootType.FOOT_LOADING_MORE) {//如果是没有更多数据就不能显示正在加载了
            return;
        }
        if (isCloseShwoNoDataFoot) {
            isShwoNoDataFoot(true);
        }
        this.type = type;
        switch (type) {
            case EFootType.FOOT_LOADING_ADD:
                this.foottext = mActivity.getResources().getString(R.string.loading_add);
                break;
            case EFootType.FOOT_LOADING_MORE:
                this.foottext = mActivity.getResources().getString(R.string.loadingmore);
                break;
            case EFootType.FOOT_NO_LOADDATA:
                this.foottext = mActivity.getResources().getString(R.string.no_loaddata);
                if (isCloseShwoNoDataFoot) {
                    isShwoNoDataFoot(false);
                }
                break;
            case EFootType.FOOT_ERROR_LOADDATA:
                this.foottext = mActivity.getResources().getString(R.string.error_loaddata);
                break;
        }
        notifyItemChanged(getItemCount() - 1);
    }

    /**
     * 是否显示进度条 0 点击加载更多 1 正在加载更多 2 没有更多数据 3 加载数据失败
     */
    @FootType
    protected int type = EFootType.FOOT_LOADING_ADD;
    /**
     * foot的文字
     */
    protected String foottext;

    public int getItemCount() {
        return getNewItemCount() + (isEnabledFoot ? 1 : 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == 9999) {
            vh = new FootViewHolder(parent);
        } else {
            vh = onNewCreateViewHolder(parent, viewType);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FootViewHolder) {
            FootViewHolder footer = ((FootViewHolder) holder);
            footer.bindDataandListener(this, isShwoNoDataFoot ?type==EFootType.FOOT_LOADING_MORE?false:getItemCount() == 1 : true, mIFVA, type, foottext);
        } else {
            onNewBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int footerPosition = getItemCount() - 1;
        if (position == footerPosition && isEnabledFoot) {
            return 9999;
        }
        int itemtype_result = getNewItemViewType(position);
        if (itemtype_result == 9999) {
            throw new RuntimeException("该 itemType 已经存在值为 9999 的情况，不能再返回9999");
        }
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
        //        void onItemClick(int position);
        void onLoadMore();
    }

    public void clearNotNotify() {
        if (data != null) {
            data.clear();
        }
    }

    private boolean off = false;
    public void clearNotNotify(boolean off) {
        this.off = off;
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> datas) {
        if (datas != null) {
            if(off){
                data.clear();
                off = false;
            }
            data.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void addData(T mData) {
        if (data != null) {
            data.add(mData);
        }
    }

    public void addDataNotNotify(List<T> datas) {
        if (datas != null) {
            data.addAll(datas);
        }
    }

    public void setData(List<T> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data.clear();
        }
        if(off){
            off = false;
        }
        notifyDataSetChanged();
    }

}
