package com.example.opengltest

import android.opengl.GLES20
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.math.sin

// 定义顶点数量
const val COORDS_COUNT = 3

// 定义star坐标点
val starCoords = floatArrayOf(
    // 逆时针坐标点，从最下面的角开始
    0.0f, -0.5f, 0.0f,
    0.13f, -0.12f, 0.0f,
    0.5f, -0.12f, 0.0f,
    0.19f, 0.09f, 0.0f,
    0.32f, 0.5f, 0.0f,
    0.0f, 0.25f, 0.0f,
    -0.32f, 0.5f, 0.0f,
    -0.19f, 0.09f, 0.0f,
    -0.5f, -0.12f, 0.0f,
    -0.18f, -0.12f, 0.0f
)


class Star {
    private var mProgramHandle: Int = 0
    private var colorHandle: Int = 0
    private var vPMatrixHandle: Int = 0
    private var alphaHandle: Int = 0

    private var alpha: Float = 0f

    private val color = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)

    // 顶点数量
    private val vertexCount: Int = starCoords.size / COORDS_COUNT

    // 从一个顶点移动到下一个顶点时需要跳过多少字节
    private val vertexStride: Int = COORDS_COUNT * 4

    // 顶点着色器
    private var vertexShaderCode =
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                " gl_Position = uMVPMatrix * vPosition;" +
                "}"

    // 片元着色器
    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "uniform float alpha;" +
                "void main() {" +
                "  gl_FragColor = vec4(vColor.rgb, alpha * vColor.a);" +
                "}"

    private var vertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(starCoords.size * 4).run {
            order(ByteOrder.nativeOrder())

            asFloatBuffer().apply {
                put(starCoords)
                position(0)
            }
        }

    init {
        mProgramHandle = createAndLinkProgram(vertexShaderCode, fragmentShaderCode)
    }

    // 编译shader
    private fun compileShader(shaderType: Int, shaderSource: String): Int {
        // 创建shader句柄
        var shaderHandle: Int = GLES20.glCreateShader(shaderType)

        if (shaderHandle != 0) {
            GLES20.glShaderSource(shaderHandle, shaderSource)
            GLES20.glCompileShader(shaderHandle)
            val compileStatus = IntArray(1)
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0)

            if (compileStatus[0] == 0) {
                println("Error compile shader: ${GLES20.glGetShaderInfoLog(shaderHandle)}")
                GLES20.glDeleteShader(shaderHandle)
                shaderHandle = 0
            }

            if (shaderHandle == 0) {
                println("Error create shader")
            }
        }
        return shaderHandle
    }

    // 创建并链接到着色器程序
    private fun createAndLinkProgram(vertexShaderCode: String, fragmentShaderCode: String): Int {
        // 创建着色器句柄
        var programHandle = GLES20.glCreateProgram()

        if (programHandle != 0) {
            // 编译顶点shader和片段shader
//            val vertexShaderHandle = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
//            val fragmentShaderHandle = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
            val vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
            val fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

            // 绑定shader和program
            GLES20.glAttachShader(programHandle, vertexShaderHandle)
            GLES20.glAttachShader(programHandle, fragmentShaderHandle)

            // 链接program
            GLES20.glLinkProgram(programHandle)

            println("Error Error Error")

            var linkStatus = IntArray(1)
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0)

            if (linkStatus[0] == 0) {
                println("Error compile program: ${GLES20.glGetProgramInfoLog(programHandle)}")
                GLES20.glDeleteProgram(programHandle)
                programHandle = 0
            }

            if (programHandle == 0) {
                println("Error create program")
            }
        }
        return programHandle
    }

    fun draw(mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgramHandle)

        // 获取顶点坐标句柄
        val positionHandle = GLES20.glGetAttribLocation(mProgramHandle, "vPosition")
        // 允许操作顶点
        GLES20.glEnableVertexAttribArray(positionHandle)
        // 将顶点数据传递给positionHandle指向的vPosition变量;将顶点属性与顶点缓冲对象关联
        // false代表不需要被顶点数据不需要被标准化
        GLES20.glVertexAttribPointer(
            positionHandle, COORDS_COUNT, GLES20.GL_FLOAT,
            false, vertexStride, vertexBuffer
        )

        colorHandle = GLES20.glGetUniformLocation(mProgramHandle, "vColor")
        GLES20.glUniform4fv(colorHandle, 1, color, 0)

        alphaHandle = GLES20.glGetUniformLocation(mProgramHandle, "alpha")
        alpha = changeAlphaByTime(0.3f, 0.7f)
        GLES20.glUniform1f(alphaHandle, alpha)

        // false代表是否转置矩阵，最后一个参数代表偏移量为0
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgramHandle, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)
        /*
        * glDrawArrays方法是绘制图形
        * 1. 第一个参数是mode，表示绘制的方式，可选值有: GL_POINTS、GL_LINES、GL_LINE_LOOP、
        * GL_LINE_STRIP、GL_TRIANGLES、GL_TRIANGLE_STRIP、GL_TRIANGLE_FAN
        * 2. 第二个参数表示从数组缓存中的哪一位开始绘制，一般为0
        * 3. 第三个参数表述绘制顶点的数量
        * */
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vertexCount)
        // 添加错误检查
        checkGLError("draw")
        // 绘制完后取消允许操作顶点坐标
        GLES20.glDisableVertexAttribArray(positionHandle)
    }

    private fun checkGLError(glOperation: String) {
        val error = GLES20.glGetError()
        if (error != GLES20.GL_NO_ERROR) {
            Log.e("OpenGL", "$glOperation - GL错误: $error")
        }
    }

    private fun changeAlphaByTime(start: Float, end: Float): Float {
        var startTime: Long = System.currentTimeMillis() % 2000
        var percentage: Double = startTime.toDouble() / 2000.0
        var currentAngle: Float = (percentage * 2 * Math.PI).toFloat()
        return (sin(currentAngle) * ((end - start) / 2)) + (end + start) / 2
    }


}