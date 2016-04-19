package com.github.wesleyegberto.dragndropanim.components;

public class AnimatedGifBuilder {

	private String filename;
	private double duration;
	private int cycleCount;

	public AnimatedGifBuilder(String filename) {
		this.filename = filename;
	}

	public AnimatedGifBuilder withDuration(double durationMs) {
		this.duration = durationMs;
		return this;
	}

	public AnimatedGif build() {
		return new AnimatedGif(getClass().getResource(filename).toExternalForm(), duration);
	}

	public static AnimatedGifBuilder fromGifAtResources(String filename) {
		return new AnimatedGifBuilder(filename);
	}

	public AnimatedGifBuilder withCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
		return this;
	}
}
