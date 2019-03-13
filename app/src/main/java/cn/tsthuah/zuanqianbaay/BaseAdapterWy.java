package cn.tsthuah.zuanqianbaay;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager.LayoutParams;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseAdapterWy<T extends Object> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_ITEM = 0;
    public final static int TYPE_FOOTER = 1;
    public final static int TYPE_HEAD = 2;
    public final static int TYPE_EMPTY = 3;
    public final static int TYPE_NONET = 4;
    private final static int TYPE_CONTENT=0;//正常内容
    public Context mContext;
    private int mLayoutId;
    public ArrayList<T> mData;
    private LayoutInflater mInflater;
    private OnLoadMoreListener onLoadMoreListener;
    private OnItemClickListener onItemClickListener;
    private boolean isFooter;
    private boolean isNoMore;
    private TextView tv_load_msg;
    private ProgressBar pb_load;
    private View mHeadView;
    private HashMap<Integer, Integer> mLayoutIds;
    private boolean isEmpty;  //是否空数据
    private boolean isNoNet;  //是否无网络
    private int totalPageCount = -1;  //总条数
    private boolean isLoaded;
    private boolean isFooterShow;
    private boolean isHideFooterOnNotMore;
    private String mBadDataTip = "";
    private String mBadDataBtnTxt = "";
    private boolean isLoadingMore;  //是否正在加载更多
    private int noDataImgId = -1;
    private RecyclerView mRecyclerView;
    private boolean isFirstLoad;  //第一次默认加载下一页，才能知道是否是最后一页

    public BaseAdapterWy(Context context, int layoutId, ArrayList<T> data) {
        mContext = context;
        mLayoutId = layoutId;
        mData = data;
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (mData.size() <= 0) {
            isEmpty = true;
        }
        mInflater = LayoutInflater.from(mContext);
    }

    public BaseAdapterWy(Context context, HashMap<Integer, Integer> layoutIds, ArrayList<T> data) {
        mContext = context;
        mLayoutIds = layoutIds;
        mData = data;
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (mData.size() <= 0) {
            isEmpty = true;
        }
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TYPE_ITEM:
                holder = new BaseViewHolder(mInflater.inflate(mLayoutId, parent, false));
                break;
            case TYPE_HEAD:
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                mHeadView.setLayoutParams(params);
                holder = new BaseViewHolder(mHeadView);
                break;
            case TYPE_FOOTER:
                holder = new BaseFooterViewHolder(mInflater.inflate(R.layout.item_footer, parent, false));
                break;
            case TYPE_NONET:
                holder = new BaseViewHolder(mInflater.inflate(R.layout.adapter_empty_data, parent, false));
                break;
            case TYPE_EMPTY:
                holder = new BaseViewHolder(mInflater.inflate(R.layout.adapter_empty_data, parent, false));
                break;
            default:
                holder = new BaseViewHolder(mInflater.inflate(mLayoutIds.get(viewType), parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        switch (getItemViewType(position)) {
            case TYPE_FOOTER:
                isFooterShow = true;
                break;
            case TYPE_EMPTY:
                ((BaseViewHolder) holder).setText(R.id.tv_tip, mBadDataTip);
                if (noDataImgId != -1) {
                    ((ImageView) ((BaseViewHolder) holder).getView(R.id.iv_icon)).setImageResource(noDataImgId);
                }
                if (TextUtils.isEmpty(mBadDataBtnTxt)) {
                    ((BaseViewHolder) holder).getView(R.id.btn_action).setVisibility(View.GONE);
                } else {
                    ((BaseViewHolder) holder).getView(R.id.btn_action).setVisibility(View.VISIBLE);
                    ((Button) ((BaseViewHolder) holder).getView(R.id.btn_action)).setText(mBadDataBtnTxt);
                }
                initNoDataView((BaseViewHolder) holder);
                break;
            case TYPE_NONET:
                ((BaseViewHolder) holder).setText(R.id.tv_tip, mBadDataTip);
                if (noDataImgId != -1) {
                    ((ImageView) ((BaseViewHolder) holder).getView(R.id.iv_icon)).setImageResource(noDataImgId);
                }
                if (TextUtils.isEmpty(mBadDataBtnTxt)) {
                    ((BaseViewHolder) holder).getView(R.id.btn_action).setVisibility(View.GONE);
                } else {
                    ((BaseViewHolder) holder).getView(R.id.btn_action).setVisibility(View.VISIBLE);
                    ((Button) ((BaseViewHolder) holder).getView(R.id.btn_action)).setText(mBadDataBtnTxt);
                }
                initNoNetView((BaseViewHolder) holder);
                break;
            case TYPE_HEAD:
            case TYPE_ITEM:
                if (position == 0 && mHeadView != null) {
                    initHeadView((BaseViewHolder) holder, position);
                } else {
                    if (onItemClickListener != null) {
                        ((BaseViewHolder) holder).rootView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mHeadView != null) {
                                    if (1==1) {
                                        onItemClickListener.onItemClick(v, mData.get(position - 1), position - 1);
                                    }
                                } else {
                                    if (1==1) {
                                        onItemClickListener.onItemClick(v, mData.get(position), position);
                                    }
                                }
                            }
                        });
                    }
                    if (mHeadView != null) {
                        initItemView((BaseViewHolder) holder, mData.get(position - 1), position - 1);
                    } else {
                        initItemView((BaseViewHolder) holder, mData.get(position), position);
                    }
                }
                break;
        }
    }

    public void initNoDataView(BaseViewHolder holder) {
    }

    public void initNoNetView(BaseViewHolder holder) {

    }

    public void initHeadView(BaseViewHolder holder, int position) {
    }


    public abstract void initItemView(BaseViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        if (!isEmpty && !isNoNet) {
            return mData.size() + (mHeadView == null ? 0 : 1) + (isFooter ? 1 : 0);
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isNoNet) {
            return TYPE_NONET;
        } else if (isEmpty) {
            return TYPE_EMPTY;
        } else if (position == 0 && mHeadView != null) {
            return TYPE_HEAD;
        } else if (position > mData.size() + (mHeadView == null ? 0 : 1) - 1) {
            return TYPE_FOOTER;
        }
        return getOnlyItemViewType(position - (mHeadView == null ? 0 : 1));
    }

    public int getOnlyItemViewType(int position) {
        return TYPE_ITEM;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View v, T item, int position);
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    public void setOnLoadMoreListener(RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        mRecyclerView = recyclerView;
        initScrollListner(recyclerView);
        isFooter = true;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void initScrollListner(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int totalItemCount = linearLayoutManager.getItemCount();
                        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                        if (!isNoMore && !isLoadingMore) {
                            if (totalPageCount < 0 || mData.size() < totalPageCount) {
                                if (totalItemCount <= lastVisibleItemPosition + 2) {  //暂时设置滑到最后才触发
                                    if (onLoadMoreListener != null) {
                                        isLoadingMore = true;
                                        onLoadMoreListener.onLoadMore();
                                    }
                                }
                            } else {
                                setLoadComplete(true);
                            }
                        }
                    }
                }
            });
        }
    }

    public void setLoadComplete(boolean flag) {
        isNoMore = flag;
        if (flag) {

            if (tv_load_msg != null) {
                isLoaded = true;

                if (isHideFooterOnNotMore) {
                    tv_load_msg.setText("已加载全部22");

                } else {
                    tv_load_msg.setText("已加载全部");

                }

                pb_load.setVisibility(View.GONE);
            }
        } else {

            if (tv_load_msg != null) {

                tv_load_msg.setText("加载中");
                pb_load.setVisibility(View.VISIBLE);
            }
        }
    }

    public View getmHeadView() {
        return mHeadView;
    }

    public void setmHeadView(View mHeadView) {
        this.mHeadView = mHeadView;
    }

    /**
     * 通用viewholder
     */
    public class BaseViewHolder extends RecyclerView.ViewHolder {

        private Map<Integer, View> viewMap;
        private View rootView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            viewMap = new HashMap<>();
            rootView = itemView;
        }

        public View getView(int viewId) {
            if (viewMap.containsKey(viewId)) {
                return viewMap.get(viewId);
            } else {
                View view = rootView.findViewById(viewId);
                viewMap.put(viewId, view);
                return view;
            }
        }

        public BaseViewHolder setText(int viewId, String str) {
            if (TextUtils.isEmpty(str)) {
                ((TextView) getView(viewId)).setText("");
            } else {
                ((TextView) getView(viewId)).setText(str);
            }
            return this;
        }

        public BaseViewHolder setTextUrl(int viewId, String url) {
            if (TextUtils.isEmpty(url)) {
                ((TextView) getView(viewId)).setText("");
            } else {
                String html = "<a href='" + url + "'>" + url + "</a>";
                ((TextView) getView(viewId)).setText(Html.fromHtml(html));
                ((TextView) getView(viewId)).setMovementMethod(LinkMovementMethod.getInstance());
            }
            return this;
        }

        public BaseViewHolder setImageWithResource(int viewId, final int resource) {
            final ImageView imageView = (ImageView) getView(viewId);

            imageView.setImageResource(resource);

//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    BigImgDialog dialog = new BigImgDialog(mContext, resource);
//                    dialog.show();
//                }
//            });

            return this;
        }
    }

    public class BaseFooterViewHolder extends RecyclerView.ViewHolder {

        public BaseFooterViewHolder(View itemView) {
            super(itemView);
            tv_load_msg = (TextView) itemView.findViewById(R.id.tv_load_msg);
            pb_load = (ProgressBar) itemView.findViewById(R.id.pb_load);
        }
    }

    public void setStatusNoNet(String tip, String btnTxt, int imgId) {
        mBadDataTip = tip;
        mBadDataBtnTxt = btnTxt;
        noDataImgId = imgId;
        notifyDataSetChanged(null);
    }

    public void setStatusNoNet() {
//        setStatusNoNet(mContext.getResources().getString(R.string.adapter_no_net),
//                mContext.getResources().getString(R.string.reload),
//                R.mipmap.adapter_no_net);
    }

    public void setStatusEmpty(String tip, String btnTxt, int imgId) {
        mBadDataTip = tip;
        mBadDataBtnTxt = btnTxt;
        noDataImgId = imgId;
        notifyDataSetChanged(new ArrayList<T>());
    }

    public void setStatusEmpty() {

    }

    public void notifyDataSetChanged(ArrayList<T> data) {
        if (data == null) {
            mData.clear();
            isNoNet = mHeadView == null ? true : false;
            isEmpty = false;
        } else if (data.size() <= 0) {
            mData.clear();
            isEmpty = mHeadView == null ? true : false;
            isNoNet = false;
        } else {
            if (totalPageCount >= 0) {
                if (isFooter && data.size() < totalPageCount) {
                    setLoadComplete(false);
                } else {
                    setLoadComplete(true);
                }
            } else {
            }

            mData = data;
            isEmpty = false;
            isNoNet = false;
        }
        isLoadingMore = false;
        notifyDataSetChanged();

        //针对当前项目接口（未返回总页数或者总条数）处理加载更多操作
        if (mRecyclerView == null || totalPageCount > 0) {
            return;
        }
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if (isFooterShow) {
                    if (totalPageCount < 0 || mData.size() < totalPageCount) {
                        int totalItemCount = linearLayoutManager.getItemCount() -
                                (isFooter ? 1 : 0);
                        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                        if (lastVisibleItemPosition == totalItemCount) {
                            if (!isFirstLoad) {
                                if (onLoadMoreListener != null) {
                                    isLoadingMore = true;
                                    onLoadMoreListener.onLoadMore();
                                }
                                isFirstLoad = true;
                            } else {
                                setLoadComplete(true);
                                isFirstLoad = false;
                            }
                        } else {
                            setLoadComplete(false);
                        }
                    } else {
                        setLoadComplete(true);
                    }
                }
            }
        }, 100);

    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public boolean isHideFooterOnNotMore() {
        return isHideFooterOnNotMore;
    }

    public void setHideFooterOnNotMore(boolean hideFooterOnNotMore) {
        isHideFooterOnNotMore = hideFooterOnNotMore;
    }
}