package com.wfe.audio;

import static org.lwjgl.openal.ALC10.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;

public class AudioMaster {

	private static List<Integer> buffers = new ArrayList<>();
	private static long device;
	private static long context;
	
	public static void init() {
		device = alcOpenDevice((ByteBuffer)null);

		int[] attributes = {0};
		context = alcCreateContext(device, attributes);
		alcMakeContextCurrent(context);
		 
		AL.createCapabilities(ALC.createCapabilities(device));
	}
	
	public static void setListenerData(final float x, final float y, final float z)
	{
		AL10.alListener3f(AL10.AL_POSITION, x, y, z);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	public static int loadSound(final String file)
	{
		final int buffer = AL10.alGenBuffers();
		buffers.add(buffer);
		WaveData wavFile = WaveData.create(file);
		AL10.alBufferData(buffer, wavFile.format, wavFile.data, wavFile.samplerate);
		wavFile.dispose();
		return buffer;
	}
	
	public static void cleanup()
	{
		for (final int buffer : buffers)
		{
			AL10.alDeleteBuffers(buffer);
		}

		//Terminate OpenAL
		alcDestroyContext(context);
		alcCloseDevice(device);
	}
	
}
