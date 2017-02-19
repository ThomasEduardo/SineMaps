package appsine.com.br.sinemaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



import java.util.Timer;
import java.util.TimerTask;

public class Splash  extends Activity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash);

            Thread timerThread = new Thread(){
                public void run(){
                    try{
                        sleep(5000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        Intent intent = new Intent(Splash.this,MapsActivity.class);
                        startActivity(intent);
                    }
                }
            };
            timerThread.start();
        }

        @Override
        protected void onPause() {
            // TODO Auto-generated method stub
            super.onPause();
            finish();
        }

    }

