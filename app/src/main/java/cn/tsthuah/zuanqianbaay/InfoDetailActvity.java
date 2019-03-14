package cn.tsthuah.zuanqianbaay;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class InfoDetailActvity extends Activity {
  ImageView im_erweima;
  ImageView im_xct;
  Button btn_save;
  RelativeLayout rl_back;
  DataInfo dataInfo;
  TextView tx_title;
  private static String[] PERMISSIONS_STORAGE = {
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE};

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_infodetail);
    hideStatusBar();
    initView();
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 123);
      }
    }

  }

  private void initView() {
    dataInfo=(DataInfo) getIntent().getSerializableExtra("data");
    im_erweima=(ImageView)findViewById(R.id.im_erweima);
    im_xct=(ImageView)findViewById(R.id.iconcc);
    btn_save=(Button)findViewById(R.id.btn_save);
    rl_back=(RelativeLayout)findViewById(R.id.rl_back);
    tx_title=(TextView)findViewById(R.id.tx_title);
    tx_title.setText(dataInfo.getPtName());
    im_erweima.setBackgroundResource(dataInfo.getPtewm());
    rl_back.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
   LayoutParams para = (LayoutParams) im_xct.getLayoutParams();
   ;
    DisplayMetrics dm = getResources().getDisplayMetrics();
    int heigth = dm.heightPixels;
    int width = dm.widthPixels;

    para.height=width-60;
    para.width=width-60;
    im_xct.setLayoutParams(para);


btn_save.setOnClickListener(new OnClickListener() {
  @Override
  public void onClick(View v) {
    Bitmap  bitmap = BitmapFactory.decodeResource(InfoDetailActvity.this.getResources(), dataInfo.getPtewm());
    Log.e("wangying","bbb=="+bitmap.getHeight());
    try {
      saveFile(bitmap);
    } catch (IOException e) {
      e.printStackTrace();
      Toast.makeText(InfoDetailActvity.this,"保存失败，请联系客服！",Toast.LENGTH_LONG).show();

    }

  }
});
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

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 123) {
      for (int i = 0; i < permissions.length; i++) {
        Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
      }
    }
  }
  /**
   * 保存图片
   * @param bm
   * @throws IOException
   */
  public  void saveFile(Bitmap bm ) throws IOException {
    File dirFile = new File(Environment.getExternalStorageDirectory().getPath());
    if (!dirFile.exists()) {
      dirFile.mkdir();
    }
    String fileName = UUID.randomUUID().toString() + ".jpg";
    File myCaptureFile = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + fileName);
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
    bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
    bos.flush();
    bos.close();
    //把图片保存后声明这个广播事件通知系统相册有新图片到来
    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    Uri uri = Uri.fromFile(myCaptureFile);
    intent.setData(uri);
    InfoDetailActvity.this.sendBroadcast(intent);
    Toast.makeText(InfoDetailActvity.this,"保存成功！请打开微信扫一扫选择相册进行扫描！",Toast.LENGTH_LONG).show();

  }


  /**
   * @param bmp 获取的bitmap数据
   * @param picName 自定义的图片名
   */
//  public  void saveBmp2Gallery(Bitmap bmp, String picName) {
//
//    String fileName = null;
//    //系统相册目录
//    String galleryPath= Environment.getExternalStorageDirectory()
//        + File.separator + Environment.DIRECTORY_DCIM
//        +File.separator+"Camera"+File.separator;
//
//
//    // 声明文件对象
//    File file = null;
//    // 声明输出流
//    FileOutputStream outStream = null;
//
//    try {
//      // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
//      file = new File(galleryPath, picName+ ".jpg");
//
//      // 获得文件相对路径
//      fileName = file.toString();
//      // 获得输出流，如果文件中有内容，追加内容
//      outStream = new FileOutputStream(fileName);
//      if (null != outStream) {
//        bmp.compress(bmp_format, 90, outStream);
//      }
//
//    } catch (Exception e) {
//      e.getStackTrace();
//    }finally {
//      try {
//        if (outStream != null) {
//          outStream.close();
//        }
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//}
////通知相册更新
//MediaStore.Images.Media.insertImage(this.getContentResolver(),
//        bmp, fileName, null);
//    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//    Uri uri = Uri.fromFile(file);
//    intent.setData(uri);
//    this.sendBroadcast(intent);
//
//    Toast.makeText(InfoDetailActvity.this,"保存成功！请打开微信扫一扫选择相册进行扫描！",Toast.LENGTH_LONG).show();
//
//  }
}
