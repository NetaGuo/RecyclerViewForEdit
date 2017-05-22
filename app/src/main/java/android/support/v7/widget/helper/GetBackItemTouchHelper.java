package android.support.v7.widget.helper;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class GetBackItemTouchHelper extends ItemTouchHelper {
    /**
     * Creates an ItemTouchHelper that will work with the given Callback.
     * <p>
     * You can attach ItemTouchHelper to a RecyclerView via
     * an onItemTouchListener and a Child attach / detach listener to the RecyclerView.
     *
     * @param callback The Callback which controls the behavior of this touch helper.
     */
    public GetBackItemTouchHelper(Callback callback) {
        super(callback);
    }

    public Callback getCallBack(){
        return mCallback;
    }
}
