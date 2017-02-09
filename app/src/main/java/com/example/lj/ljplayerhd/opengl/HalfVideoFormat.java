package com.example.lj.ljplayerhd.opengl;

/**
 * Created by wh on 2017/1/15.
 */

public class HalfVideoFormat implements VideoFormat {

    private Video video_half;

    public HalfVideoFormat(){
        video_half=new Video(getVideoHalfVertexData());
    }

    @Override
    public void draw(ShaderProgram program) {
        video_half.bindData(program);
        video_half.draw();
    }

    private float[] getVideoHalfVertexData(){
        float[] VERTEX_DATA_TOP = {
                // X, Y, Z, U, V
                -1.0f, -1.0f, 0, 0.f, 0.f,
                1.0f, -1.0f, 0, 0.5f, 0.f,
                -1.0f, 1.0f, 0, 0.f, 1.f,
                1.0f, 1.0f, 0, 0.5f, 1.f,
        };
        return VERTEX_DATA_TOP;
    }
}
