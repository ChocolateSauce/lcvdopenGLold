package com.example.lcvdopenglold;

import java.io.File;

import javax.microedition.khronos.opengles.GL10;

import com.twicecircled.spritebatcher.Drawer;
import com.twicecircled.spritebatcher.SpriteBatcher;

import android.app.Activity;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity implements Drawer{

	Rect src1 = new Rect(0,0,256,456);
	Rect src2 = new Rect(256,0,512,456);
	Rect src3 = new Rect(512,0,768,456);
	//Rect dst1 = new Rect(0,0,128,128);
	float timenow,deltatime,timelast;
	int image_num = 0;
	boolean pause = true;
	int[] resourceIds;
	
    TwoFingerDoubleTapListener multiTouchListener = new TwoFingerDoubleTapListener() {
        @Override
        public void onTwoFingerDoubleTap() {
            Toast.makeText(MainActivity.this, "Two Finger Double Tap", Toast.LENGTH_SHORT).show();
            pause = !pause;
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		GLSurfaceView glSurf = new GLSurfaceView(this);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(glSurf);
        
    	File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/animation_sprite");
    	
    	Log.v("imagePath","Found path: "+(path.toString()));
    	
    	if(path.exists() && path.isDirectory()) {
            Toast.makeText(MainActivity.this, "SD card images", Toast.LENGTH_SHORT).show();
            resourceIds = new int[] {R.string.animation_sprite};
    	} else
    		resourceIds = new int[] {R.drawable.test2};
        
    	
        
        glSurf.setRenderer(new SpriteBatcher(this, resourceIds, this));

		
	}

	@Override
	public void onDrawFrame(GL10 gl, SpriteBatcher sb) {
		timenow = System.nanoTime()/1000000.0f;  //capture the time once in miliseconds
	    deltatime = timenow-timelast;
	    if (!pause) {
			if (image_num == 0) {
				sb.draw(R.drawable.test2, src1, new Rect(0, 0,sb.getViewWidth(), sb.getViewHeight()));
				image_num++;
			} else if (image_num == 3) {
				sb.draw(R.drawable.test2, src2, new Rect(0, 0,sb.getViewWidth(), sb.getViewHeight()));
				image_num++;
			} else {
				sb.draw(R.drawable.test2, src3, new Rect(0, 0,sb.getViewWidth(), sb.getViewHeight()));
				if (image_num == 5)
					image_num = 0;
				else
					image_num++;
			}
		}
		Log.v("FrameTime","Millis between this frame and prev: "+(deltatime));
		timelast = timenow;
		
	}
	
    public boolean onTouchEvent(MotionEvent event) {
      	return multiTouchListener.onTouchEvent(event);
    }
    
}
