package dev.logan.game.scenes;

import dev.logan.engine.core.GameObject;
import dev.logan.engine.core.Scene;
import dev.logan.engine.input.KeyListener;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class MainMenuScene extends Scene {

    private String vertexSource =
            "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 0) in vec4 aColor;\n" +
            "out vec4 fColor;\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";

    private String fragmentSource =
            "#version 330 core\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";

    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
            // Position                 // Color
            0.5f, -0.5f, 0.0f,          1.0f, 0.0f, 0.0f, 1.0f, // Bottom right
            -0.5f, 0.5f, 0.0f,          0.0f, 1.0f, 0.0f, 1.0f, // Top left
            0.5f, 0.5f, 0.0f,           0.0f, 0.0f, 1.0f, 1.0f, // Top right
            -0.5f, -0.5f, 0.0f,         1.0f, 1.0f, 0.0f, 1.0f, // Bottom left
    };

    // IMPORTANT: MUST BE IN COUNTER-CLOCKWISE ORDER!
    private int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 1, 3 // Bottom left triangle
    };

    private int vaoID, vboID, eboID;


    public MainMenuScene() {
        super("MainMenuScene");
    }

    @Override
    public void start() {
        super.start();


        /*==========================================
        ||  COMPILE AND LINK SHADERS
        ==========================================*/

        // Load and compile vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        // Check for errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(vertexID, glGetShaderi(vertexID, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }

        // Load and compile fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        // Check for errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(fragmentID, glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }

        // Link shaders
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if(success == GL_FALSE) {
            System.out.println(glGetProgramInfoLog(shaderProgram, glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH)));
            assert false : "";
        }

        // Generate VAO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create indices
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionSize = 3;
        int colorSize = 4;
        int vertexSizeBytes = (positionSize + colorSize) * Float.BYTES;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * Float.BYTES);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(final float dt) {
        // Bind shader program
        glUseProgram(shaderProgram);

        // Bind the VAO
        glBindVertexArray(vaoID);

        // Enable vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        glUseProgram(0);
    }
}
