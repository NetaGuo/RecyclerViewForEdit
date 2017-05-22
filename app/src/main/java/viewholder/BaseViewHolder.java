package viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{

    protected Context context;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
    }

    /**
     * 得到当前view绑定的activity
     * @return
     */
    protected Activity getActivity(){
        return (Activity) context;
    }

    /**
     * itemView的子view 的 findViewById
     * @param viewId id
     * @return
     */
    protected <T extends View> T getView(int viewId) {
        return (T) (itemView.findViewById(viewId));
    }

    /**
     * 非itemView 的 findViewById
     * @param view 需要findViewById的父View
     * @param viewId id
     * @param <T> 指定要转换的view类型
     * @return 返回View
     */
    protected <T extends View> T getView(View view,int viewId) {
        if(view==null){
            return null;
        }
        return (T) (view.findViewById(viewId));
    }

}
