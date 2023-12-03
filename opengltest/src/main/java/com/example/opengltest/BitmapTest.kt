import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import kotlin.math.sin

class BitmapTest {
    // 每个顶点的坐标数
    private val COORDS_PER_VERTEX = 3

    // 每个纹理顶点的坐标数
    private val COORDS_PER_TEXTURE_VERTEX = 2

    // 顶点的坐标
    private var squareCoords = floatArrayOf(
        -1f, 1f, 0.0f,      // top left
        -1f, -1f, 0.0f,      // bottom left
        1f, -1f, 0.0f,      // bottom right
        1f, 1f, 0.0f       // top right
    )

    // 顶点所对应的纹理坐标
    private var texVertex = floatArrayOf(
        0f, 0f,      // top left
        0f, 1f,      // bottom left
        1f, 1f,       // bottom right
        1f, 0f     // top right
    )

//    private val vboIds = IntArray(1)
//    private val eboIds = IntArray(1)

    // 四个顶点的缓冲数组
    private val vertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(squareCoords.size * 4).order(ByteOrder.nativeOrder())
            .asFloatBuffer().apply {
                put(squareCoords)
                position(0)
            }

    // 四个顶点的绘制顺序数组
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3)

    // 四个顶点绘制顺序数组的缓冲数组
    private val drawListBuffer: ShortBuffer =
        ByteBuffer.allocateDirect(drawOrder.size * 2).order(ByteOrder.nativeOrder())
            .asShortBuffer().apply {
                put(drawOrder)
                position(0)
            }

    // 四个纹理顶点的缓冲数组
    private val texVertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(texVertex.size * 4).order(ByteOrder.nativeOrder())
            .asFloatBuffer().apply {
                put(texVertex)
                position(0)
            }

    private var alpha: Float = 0f
    private var currentTime: Long = 0L
    private val periodMillis: Long = 5000
    private var startTimeMillis: Long = 0L

    /**
     * 顶点着色器代码;
     */
    private val vertexShaderCode =
        "attribute vec4 inputTextureCoordinate;" +
                " varying vec2 textureCoordinate;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                // 把vPosition顶点经过矩阵变换后传给gl_Position
                "  gl_Position = vPosition;" +
                "textureCoordinate = inputTextureCoordinate.xy;" +
                "}"

    /**
     * 透明度片段着色器代码
     */
    private val fragmentShaderCode =
        "varying highp vec2 textureCoordinate;" +
                "precision mediump float;" +
                "uniform sampler2D inputImageTexture;" +
                "uniform float alpha;" +
                "void main() {" +
                "gl_FragColor = texture2D(inputImageTexture, textureCoordinate);" +
                "gl_FragColor = vec4(gl_FragColor.rgb, alpha * gl_FragColor.a);" +
                "}"


    /**
     * 着色器程序ID引用
     */
    private var mProgram: Int

    init {
//        GLES20.glGenBuffers(2, vboIds, 0)
//        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboIds[0])
//        GLES20.glBufferData(
//            GLES20.GL_ARRAY_BUFFER,
//            vertexBuffer.capacity() * 4,
//            vertexBuffer,
//            GLES20.GL_STATIC_DRAW
//        )
//        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0)

        // 编译顶点着色器和片段着色器
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // glCreateProgram函数创建一个着色器程序，并返回新创建程序对象的ID引用
        mProgram = GLES20.glCreateProgram().also {
            // 把顶点着色器添加到程序对象
            GLES20.glAttachShader(it, vertexShader)
            // 把片段着色器添加到程序对象
            GLES20.glAttachShader(it, fragmentShader)
            // 连接并创建一个可执行的OpenGL ES程序对象
            GLES20.glLinkProgram(it)
        }
        GLES20.glDeleteShader(vertexShader)
        GLES20.glDeleteShader(fragmentShader)

        val shaderLog: String = GLES20.glGetShaderInfoLog(fragmentShader)
        println("shaderLog: $shaderLog")

    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        // glCreateShader函数创建一个顶点着色器或者片段着色器,并返回新创建着色器的ID引用
        val shader = GLES20.glCreateShader(type)
        // 把着色器和代码关联,然后编译着色器
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }

    private val vertexStride: Int = COORDS_PER_VERTEX * 4
    private val textVertexStride: Int = COORDS_PER_TEXTURE_VERTEX * 4

    fun draw(textureID: Int) {
        // 激活着色器程序 Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram)
        // 获取顶点着色器中的vPosition变量(因为之前已经编译过着色器代码,所以可以从着色器程序中获取);用唯一ID表示
        val position = GLES20.glGetAttribLocation(mProgram, "vPosition")
        val alphaUniformLocation = GLES20.glGetUniformLocation(mProgram, "alpha")
//        alpha = setAlphaByTime(0.3f, 0.7f)
        alpha = changeAlphaByTime(0.3f, 0.7f)
        GLES20.glUniform1f(alphaUniformLocation, alpha)
        // 允许操作顶点对象position
        GLES20.glEnableVertexAttribArray(position)
        // 将顶点数据传递给position指向的vPosition变量;将顶点属性与顶点缓冲对象关联
        GLES20.glVertexAttribPointer(
            position, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
            false, vertexStride, vertexBuffer
        )
        // 激活textureID对应的纹理单元
        GLES20.glActiveTexture(textureID)
        // 绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID)
        // 获取顶点着色器中的inputTextureCoordinate变量(纹理坐标);用唯一ID表示
        val textureCoordinate = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate")
        // 允许操作纹理坐标inputTextureCoordinate变量
        GLES20.glEnableVertexAttribArray(textureCoordinate)
        // 将纹理坐标数据传递给inputTextureCoordinate变量
        GLES20.glVertexAttribPointer(
            textureCoordinate, COORDS_PER_TEXTURE_VERTEX, GLES20.GL_FLOAT,
            false, textVertexStride, texVertexBuffer
        )
        // 按drawListBuffer中指定的顺序绘制四边形
        GLES20.glDrawElements(
            GLES20.GL_TRIANGLE_STRIP, drawOrder.size,
            GLES20.GL_UNSIGNED_SHORT, drawListBuffer
        )
        // 操作完后,取消允许操作顶点对象position
        GLES20.glDisableVertexAttribArray(position)
        GLES20.glDisableVertexAttribArray(textureCoordinate)
    }

    //    private fun setAlphaByTime(start: Int,end: Int): Float {
//        currentTime = System.currentTimeMillis()
//        if (startTimeMillis == 0L) {
//            startTimeMillis = currentTime
//        }
//        val elapsedTimeMillis = (currentTime - startTimeMillis) % periodMillis
////        val elapsedCnt = (currentTime - startTimeMillis) / periodMillis
//        println("elapsedTimeMillis: $elapsedTimeMillis")
////        println("elapsedCnt: $elapsedCnt")
//        // 计算 alpha 的值，使用正弦函数映射到[0,2] -> [0,1]
//        val alpha = (sin(2 * Math.PI * elapsedTimeMillis / periodMillis) / 2/(end-start)) + (end-start)
//        return alpha.toFloat()
//    }
    private fun setAlphaByTime(start: Float, end: Float): Float {
        currentTime = System.currentTimeMillis()
        if (startTimeMillis == 0L) {
            startTimeMillis = currentTime
        }

        val elapsedTimeMillis = (currentTime - startTimeMillis) % periodMillis
        val alpha = sin(2 * Math.PI * elapsedTimeMillis / periodMillis).toFloat() * 0.5f + 0.5f
        return start + alpha * (end - start)
    }

    private fun changeAlphaByTime(start: Float, end: Float): Float {
        var startTime: Long = System.currentTimeMillis() % 2000
        var percentage: Double = startTime.toDouble() / 2000.0
        var currentAngle: Float = (percentage * 2 * Math.PI).toFloat()
        return (sin(currentAngle) * ((end - start) / 2)) + (end + start) / 2
    }

}
