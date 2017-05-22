package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by guoying on 2017/2/24.
 * Description：时刻获取EditText的光标位置
 */

public class MonitorSelectionEditText extends EditText{

    private int selection;

    public int getSelection() {
        return selection;
    }

    public MonitorSelectionEditText(Context context) {
        super(context);
    }

    public MonitorSelectionEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorSelectionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        selection=selStart;
    }
}
