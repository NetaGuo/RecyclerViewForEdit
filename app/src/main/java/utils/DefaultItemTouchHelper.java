package utils;

import android.support.v7.widget.helper.GetBackItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class DefaultItemTouchHelper extends GetBackItemTouchHelper {

    private ItemTouchHelperCallBack itemTouchHelperCallBack;
    /**
     * Creates an ItemTouchHelper that will work with the given Callback.
     * <p>
     * You can attach ItemTouchHelper to a RecyclerView via
     * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
     *
     * @param callback The Callback which controls the behavior of this touch helper.
     */
    public DefaultItemTouchHelper(ItemTouchHelper.Callback callback) {
        super(callback);
    }

    public DefaultItemTouchHelper(ItemTouchHelperCallBack.OnItemTouchCallbackListener onItemTouchCallbackListener) {
        super(new ItemTouchHelperCallBack(onItemTouchCallbackListener));
        itemTouchHelperCallBack = (ItemTouchHelperCallBack) getCallBack();
    }

    /**
     * 设置是否可以被拖拽
     *
     * @param canDrag 是true，否false
     */
    public void setDragEnable(boolean canDrag) {
        itemTouchHelperCallBack.setDragEnable(canDrag);
    }

    /**
     * 设置是否可以被滑动
     *
     * @param canSwipe 是true，否false
     */
    public void setSwipeEnable(boolean canSwipe) {
        itemTouchHelperCallBack.setSwipeEnable(canSwipe);
    }
}
