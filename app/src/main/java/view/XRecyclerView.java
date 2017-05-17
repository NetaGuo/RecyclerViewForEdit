package view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import adapter.FootViewAdapter;
import enums.EFootType;
import utils.NoAlphaItemAnimator;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public class XRecyclerView extends RecyclerView{


    private Context mcontext;
    private LinearLayoutManager mLayoutManager;
    private boolean orientation = false;

    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mcontext = context;
    }

    /**
     * 滑动到某个item的位置
     *
     * @param position
     */
    public void scrollToPosition(int position) {
        if (mLayoutManager != null) {
            mLayoutManager.scrollToPositionWithOffset(position, 0);
        }
    }

    public void setCompatible(boolean compatible) {
        this.compatible = compatible;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    private boolean compatible = false;

    public void setScollbottomposition(int scollbottomposition) {
        this.scollbottomposition = scollbottomposition;
    }

    /**
     * 滑动提前多少个position加载下一页
     */
    private int scollbottomposition = 0;

    /**
     * 上下有加载更多
     *
     * @param mAdapter
     * @param iFVA
     */
    public void init(final FootViewAdapter mAdapter, final FootViewAdapter.IFootViewAdapter iFVA) {
        if (mAdapter == null) {
            return;
        }
        mLayoutManager = new LinearLayoutManager(mcontext) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                }
            }
        };
        if (orientation) {
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        }
        setLayoutManager(mLayoutManager);
        setItemAnimator(new NoAlphaItemAnimator());
        addOnScrollListener(new OnScrollListener() {
                                private int lastVisibleItem;
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView,
                                                                 int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    boolean issupport = (mAdapter.getItemCount() > scollbottomposition && scollbottomposition != 0);
                                    int scrollposition = issupport ? mAdapter.getItemCount() - scollbottomposition : mAdapter.getItemCount();
                                    if (issupport) {
                                        if (iFVA != null && lastVisibleItem + 1 >= scrollposition) {
                                            mAdapter.setFootType(EFootType.FOOT_LOADING_MORE);
                                            iFVA.onLoadMore();
                                            return;
                                        }
                                    }
                                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == scrollposition && mAdapter.getItemCount() > 1) {
                                        if (iFVA != null) {
                                            mAdapter.setFootType(EFootType.FOOT_LOADING_MORE);
                                            iFVA.onLoadMore();
                                        }
                                    }
                                }

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                                }
                            }
        );

        setAdapter(mAdapter);
    }

    /**
     * 左右无加载更多
     *
     * @param mAdapter
     */
    public void initLeftRight(Adapter mAdapter) {
        if (mAdapter == null) {
            return;
        }
        mLayoutManager = new LinearLayoutManager(mcontext) {
            //... constructor
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (IndexOutOfBoundsException e) {
                }
            }
        };
        mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        setLayoutManager(mLayoutManager);
        setItemAnimator(new DefaultItemAnimator());
        setAdapter(mAdapter);
    }

    /**
     * 上下无加载更多
     *
     * @param mAdapter
     */
    public void init(Adapter mAdapter) {
        if (mAdapter == null) {
            return;
        }
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mLayoutManager = new LinearLayoutManager(mcontext);
        setLayoutManager(mLayoutManager);
        setItemAnimator(new DefaultItemAnimator());
        setAdapter(mAdapter);
    }
}
