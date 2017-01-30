package com.wfe.core;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import com.wfe.input.Keyboard;
import com.wfe.input.Mouse;
import com.wfe.utils.MathUtils;

/**
 * Created by Rick on 06.10.2016.
 */
public class Display {

	private static final int ROLLING_AVERAGE_LENGTH = 10;
	private static final float DELTA_FACTOR = 1000;
	
    private long window;
    private String title = "";
    private static boolean isResized;
    private static int width;
    private static int height;
    
    private static float delta = 0;
	private static long lastFrame = 0;
	private static List<Float> previousTimes = new ArrayList<Float>();

    public Display(String title, int width, int height) {
        this.title = title;
    	Display.width = width;
        Display.height = height;
    }
        
    public void init() {
        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFWErrorCallback.createPrint(System.err).set();
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        // Anti-aliasing
        glfwWindowHint(GLFW_SAMPLES, 4);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
        
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2);

        glfwMakeContextCurrent(window);
        
        glfwSwapInterval(1);

        glfwShowWindow(window);

        GL.createCapabilities();
        
        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                resize(width, height);
            }
        });
        
        glfwSetKeyCallback(window, new Keyboard());
        glfwSetMouseButtonCallback(window, new Mouse(this));
        glfwSetCursorPosCallback(window, new Mouse.Cursor());
        glfwSetScrollCallback(window, new Mouse.Scroll());
        
        lastFrame = getTime();
    }
    
    public void resize(int width, int height) {
    	Display.width = width;
    	Display.height = height;
    	setResized(true);
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }
    
    public void pollEvents() {
    	glfwPollEvents();
    }

    public void swapBuffers() {
    	glfwSwapBuffers(window);
    	calculateDelta();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
    
    public static float getDeltaInSeconds() {
    	return delta;
    }

    public static boolean isResized() {
        return isResized;
    }

    public void setResized(boolean resized) {
        Display.isResized = resized;
    }

    public long getWindow() {
        return window;
    }
    
    private void calculateDelta() {
    	long time = getTime();
		long difference = time - lastFrame;
		float value = ((float) difference) / DELTA_FACTOR;
		delta = updateRollingAverage(value);
		lastFrame = time;
    }
    
    private static float updateRollingAverage(float value) {
		previousTimes.add(0, value);
		if (previousTimes.size() > ROLLING_AVERAGE_LENGTH) {
			previousTimes.remove(ROLLING_AVERAGE_LENGTH);
		}
		if (previousTimes.size() < ROLLING_AVERAGE_LENGTH) {
			return value;
		}
		return MathUtils.getAverageOfList(previousTimes);
	}
    
    private long getTime() {
    	return System.nanoTime() / 1000000;
    }

    public void shutdown() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
