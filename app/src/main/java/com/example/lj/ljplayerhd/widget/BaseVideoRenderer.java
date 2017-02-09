package com.example.lj.ljplayerhd.widget;

import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wh on 2017/1/14.
 */

public class BaseVideoRenderer implements GLSurfaceView.Renderer ,SurfaceTexture.OnFrameAvailableListener{

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
    }
}
