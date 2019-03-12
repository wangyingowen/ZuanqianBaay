package cn.tsthuah.zuanqianbaay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

/**
 * @author wy
 */
public class LoadingActvity extends Activity{

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.loading_actvity);
    View topView = findViewById(R.id.iii);
             ImmersedStatusbarUtils.initAfterSetContentView(this, topView);

             Thread thread=new Thread(new Runnable() {
               @Override
               public void run() {
                 try {
                   Thread.sleep(2000);
                   Intent intent=new Intent(LoadingActvity.this,MainActivity.class);
                   startActivity(intent);
                 } catch (InterruptedException e) {
                   e.printStackTrace();
                 }

               }
             });

             thread.start();
  }
}
