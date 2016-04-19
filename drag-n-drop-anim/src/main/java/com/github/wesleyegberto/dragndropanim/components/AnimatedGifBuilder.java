package com.github.wesleyegberto.dragndropanim.components;

public class AnimatedGifBuilder {

	private String filename;
	private double duration;
	private int cycleCount = -1;

	public AnimatedGifBuilder(String filename) {
		this.filename = filename;
	}

	public static AnimatedGifBuilder fromGifAtResources(String relativeFilename) {
		return new AnimatedGifBuilder(relativeFilename);
	}

	public AnimatedGifBuilder withDuration(double durationMs) {
		this.duration = durationMs;
		return this;
	}

	public AnimatedGif build() {
		AnimatedGif animatedGif = new AnimatedGif(getClass().getResource(filename).toExternalForm(), duration);
		if(cycleCount > 0)
			animatedGif.setCycleCount(cycleCount);
		return animatedGif;
	}

	public AnimatedGifBuilder withCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
		return this;
	}
}
