package com.example.lj.ljplayerhd.opengl;

/**
 * Created by wh on 2017/1/15.
 */

public class DefaultVideoFormat implements VideoFormat{

    private Video video_full;

    public DefaultVideoFormat(){
        video_full=new Video(getVideoFullVertexData());
    }

    @Override
    public void draw(ShaderProgram program) {
        video_full.bindData(program);
        video_full.draw();
    }

    private float[] getVideoFullVertexData(){
        float[] VERTEX_DATA_TOP = {
                // X, Y, Z, U, V
                -1.0f, -1.0f, 0, 0.f, 0.f,
                1.0f, -1.0f, 0, 1.f, 0.f,
                -1.0f, 1.0f, 0, 0.f, 1.f,
                1.0f, 1.0f, 0, 1.f, 1.f,
        };
        return VERTEX_DATA_TOP;
    }
}
