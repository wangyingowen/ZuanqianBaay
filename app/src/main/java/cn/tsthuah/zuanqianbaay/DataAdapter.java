package cn.tsthuah.zuanqianbaay;

import android.content.Context;
import java.util.ArrayList;

public class DataAdapter extends BaseAdapterWy<DataInfo> {
  private OnProjectItemClickListener onProjectItemClickListener;

  public DataAdapter(Context context, ArrayList<DataInfo> data) {
    super(context, R.layout.layout_listmain, data);
  }

  @Override
  public void initItemView(BaseViewHolder holder, final DataInfo sendGiftSingle, final int position) {



  }

  private void doFollowOrNot(final int position) {

  }

  public OnProjectItemClickListener getOnProjectItemClickListener() {
    return onProjectItemClickListener;
  }

  public void setOnProjectItemClickListener(OnProjectItemClickListener onProjectItemClickListener) {
    this.onProjectItemClickListener = onProjectItemClickListener;
  }

  public interface OnProjectItemClickListener {
    String onGetToken();

    void onItemClick(int position);
  }
}

