package com.jnu.booklistmainapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

   private void initView(){
        surfaceHolder=getHolder();
        //Callback和Surface联系起来，以便surface实现以下三个方法
        this.getHolder().addCallback(this);
   }
    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread=null;
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        //Callback和Surface联系起来
        this.getHolder().addCallback(this);
        drawThread=new DrawThread();
        drawThread.start();
    }
    //屏幕变大变小
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.stopThread();
    }
    //继承线程,进行绘图
    class DrawThread extends Thread{
        private boolean isDrawing=true;
        public void stopThread(){
            isDrawing=false;
            try {
                join();//线程结束的时候，一定要把它加到主线程去记录一下
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while (isDrawing)
            {
                Canvas canvas=null;
                try {
                    canvas=surfaceHolder.lockCanvas();//画布
                    canvas.drawColor(Color.GRAY);//画布背景
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                   if(null!=canvas) surfaceHolder.unlockCanvasAndPost(canvas);//释放canvas对象，并提交画布
                }

                //drawing
                try {
                    sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
