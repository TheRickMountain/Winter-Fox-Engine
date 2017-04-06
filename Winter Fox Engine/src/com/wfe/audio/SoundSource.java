package com.wfe.audio;

import org.lwjgl.openal.AL10;

public class SoundSource {

	private int sourceId;
	private float volume = 1;
	
	public SoundSource() {
		sourceId = AL10.alGenSources();
	}
	
	public void play(int buffer) {
		stop();
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		continuePlaying();
	}
	
	public void delete() {
		stop();
		AL10.alDeleteSources(sourceId);
	}
	
	public void pause() {
		AL10.alSourcePause(sourceId);
	}
	
	public void continuePlaying() {
		AL10.alSourcePlay(sourceId);
	}
	
	public void stop() {
		AL10.alSourceStop(sourceId);
	}
	
	public void setVelocity(float x, float y, float z) {
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, x, y, z);
	}
	
	public void setLooping(boolean loop) {
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
	
	public void setRadius(float radius) {
		setRanges(1.0f, radius);
	}
	
	public void setRanges(float primaryRadius, float secondaryRadius) {
		if(primaryRadius<1){
			primaryRadius = 1;
		}
	    AL10.alSourcef(sourceId, AL10.AL_REFERENCE_DISTANCE, primaryRadius);
	    AL10.alSourcef(sourceId, AL10.AL_ROLLOFF_FACTOR, 1);
	    AL10.alSourcef(sourceId, AL10.AL_MAX_DISTANCE, secondaryRadius);
	}
	
	public boolean isPlaying() {
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void setVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
		this.volume = volume;
	}
	
	public void setPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	public void setPosition(float x, float y, float z) {
		AL10.alSource3f(sourceId, AL10.AL_POSITION, x, y, z);
	}
	
}