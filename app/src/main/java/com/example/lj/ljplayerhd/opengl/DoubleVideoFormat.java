package com.example.lj.ljplayerhd.opengl;

/**
 * Created by wh on 2017/1/15.
 */

public class DoubleVideoFormat implements VideoFormat {

    private Video video_left;
    private Video video_right;

    public DoubleVideoFormat(){
        video_left=new Video(getVideoLeftVertexData());
        video_right=new Video(getVideoRightVertexData());
    }

    @Override
    public void draw(ShaderProgram program) {
        video_left.bindData(program);
        video_left.draw();

        video_right.bindData(program);
        video_right.draw();
    }

    private float[] getVideoLeftVertexData(){
        float[] VERTEX_DATA_TOP = {
                // X, Y, Z, U, V
                -1.0f, -1.0f, 0, 0.f, 0.f,
                0.f, -1.0f, 0, 1.f, 0.f,
                -1.0f, 1.0f, 0, 0.f, 1.f,
                0.f, 1.0f, 0, 1.f, 1.f,
        };
        return VERTEX_DATA_TOP;
    }

    private float[] getVideoRightVertexData(){
        float[] VERTEX_DATA_TOP = {
                // X, Y, Z, U, V
                0.f, -1.0f, 0, 0.f, 0.f,
                1.0f, -1.0f, 0, 1.f, 0.f,
                0.f, 1.0f, 0, 0.f, 1.f,
                1.0f, 1.0f, 0, 1.f, 1.f,
        };
        return VERTEX_DATA_TOP;
    }
}
