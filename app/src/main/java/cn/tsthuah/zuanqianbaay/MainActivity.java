package cn.tsthuah.zuanqianbaay;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * wangying
 */
public class MainActivity extends AppCompatActivity {


  ArrayList<DataInfo> datas=new ArrayList<>();
  MyAdapterData myAdapterData;
  RecyclerView rcv;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    hideStatusBar();
    initData();
    bindData();

  }

  private void bindData() {
    myAdapterData=new MyAdapterData(MainActivity.this,datas);
    rcv.setAdapter(myAdapterData);
  }

  private void initData() {
    rcv=(RecyclerView)findViewById(R.id.rcv);
    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
    //设置为横向滑动
    manager.setOrientation(LinearLayoutManager.VERTICAL);
    //设置
    rcv.setLayoutManager(manager);

    DataInfo dataInfo1=new DataInfo();
    dataInfo1.setPtlogo(R.mipmap.img1);
    dataInfo1.setPtName("鑫划算");
    dataInfo1.setPtms("吃货集中营，畅享美食，永久锁粉，轻松做团队");
    DataInfo dataInfo2=new DataInfo();
    dataInfo2.setPtlogo(R.mipmap.img2);
    dataInfo2.setPtName("黔票票");
    dataInfo2.setPtms("贵州本地化分销平台，返利高高");
    DataInfo dataInfo3=new DataInfo();
    dataInfo3.setPtlogo(R.mipmap.img3);
    dataInfo3.setPtName("超便宜周边游");
    dataInfo3.setPtms("注册就有奖励，立刻到账");
    DataInfo dataInfo4=new DataInfo();
    dataInfo4.setPtlogo(R.mipmap.img4);
    dataInfo4.setPtName("联联周边游");
    dataInfo4.setPtms("覆盖全国，自用省钱，分享赚钱");
    DataInfo dataInfo5=new DataInfo();
    dataInfo5.setPtlogo(R.mipmap.img5);
    dataInfo5.setPtName("成都严选");
    dataInfo5.setPtms("优质靠谱，自造流量，永久收益");
    DataInfo dataInfo6=new DataInfo();
    dataInfo6.setPtlogo(R.mipmap.img6);
    dataInfo6.setPtName("乐玩");
    dataInfo6.setPtms("吃喝玩乐，除了佣金还有奖励等着你");
    DataInfo dataInfo7=new DataInfo();
    dataInfo7.setPtlogo(R.mipmap.img7);
    dataInfo7.setPtName("迈迈周边游");
    dataInfo7.setPtms("优选爆款，超高佣金，简单易学");
    DataInfo dataInfo8=new DataInfo();
    dataInfo8.setPtlogo(R.mipmap.img8);
    dataInfo8.setPtName("好耍的很");
    dataInfo8.setPtms("上到高端大气上档次，下到餐饮关注鬼饮食");
    datas.add(dataInfo1);
    datas.add(dataInfo2);
    datas.add(dataInfo3);
    datas.add(dataInfo4);
    datas.add(dataInfo5);
    datas.add(dataInfo6);
    datas.add(dataInfo7);
    datas.add(dataInfo8);

  }


  //只透明状态栏
  private void hideStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
      return;
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }

  //状态栏、导航栏都透明
  private void hideStatusBarNavigationBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
          | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      window.getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
      window.setNavigationBarColor(Color.TRANSPARENT);
      return;
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }
  }
  private class MyAdapterData extends DataAdapter{

    public MyAdapterData(Context context,
        ArrayList<DataInfo> data) {
      super(context, data);
    }
    @Override
    public int getItemCount() {
      return datas.size();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position,
        @NonNull List<Object> payloads) {
      super.onBindViewHolder(holder, position, payloads);

    }
    @Override
    public void initItemView(BaseViewHolder holder, DataInfo dtContent, int position){
      TextView tx_name=(TextView) holder.getView(R.id.tx_name);
      TextView tx_ms=(TextView) holder.getView(R.id.tx_ms);
      ImageView im_log=(ImageView) holder.getView(R.id.im_logo);
      tx_name.setText(dtContent.getPtName());
      tx_ms.setText(dtContent.getPtms());
      im_log.setBackgroundResource(dtContent.getPtlogo());
    }

  }
}
