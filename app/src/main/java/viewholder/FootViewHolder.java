package viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.guoying.editrecyclerview.R;

import adapter.FootViewAdapter;
import enums.EFootType;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class FootViewHolder extends BaseViewHolder{

    private LinearLayout layout_footer;
    private ProgressBar pro_footer;
    private TextView text_footer;

    public FootViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_footview, parent, false));
        layout_footer = getView(R.id.layout_footer);
        text_footer = getView(R.id.text_footer);
        pro_footer = getView(R.id.pro_footer);
    }

    public void bindDataandListener(final FootViewAdapter footViewAdapter, boolean isFirst, final FootViewAdapter.IFootViewAdapter iFVA, int type, String foottext) {
        isShow(isFirst);
        boolean isShow = false;
        layout_footer.setOnClickListener(null);
        if (type == EFootType.FOOT_LOADING_ADD || type == EFootType.FOOT_ERROR_LOADDATA) {
            layout_footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iFVA != null) {
                        footViewAdapter.setFootType(EFootType.FOOT_LOADING_MORE);
                        iFVA.onLoadMore();
                    }
                }
            });
        }
        if (type == EFootType.FOOT_LOADING_MORE) {
            isShow = true;
        }
        pro_footer.setVisibility(isShow ? View.VISIBLE : View.GONE);
        text_footer.setText(foottext);
    }

    public void isShow(boolean isFirst) {
        if (isFirst) {
            layout_footer.setVisibility(View.GONE);
        } else {
            layout_footer.setVisibility(View.VISIBLE);
        }
    }

}
