package de.bujan.core;

import playn.core.Image;
import playn.core.Sound;
import playn.core.util.Callback;

import java.util.ArrayList;
import java.util.List;


/**
 * @author bob
 *
 */
public class ProgressAssetWatcher implements Callback {
	
	private int loaded;
	private int errors;
	
	private boolean started;
	private final Listener listener;
	
	private List<Image> imagesToLoad;
	private List<Sound> soundsToLoad;
	
	public ProgressAssetWatcher(Listener listener) {
		this.listener = listener;
		this.imagesToLoad = new ArrayList<Image>();
		this.soundsToLoad = new ArrayList<Sound>();
	}

	public void add(Image image) {
		imagesToLoad.add(image);
	}

	public void add(Sound sound){
		soundsToLoad.add(sound);
	}
	
	public boolean isDone() {
		return started && (loaded + errors == imagesToLoad.size() + soundsToLoad.size());
	}

	public void start() {
		started = true;
		
		for (Image image : imagesToLoad) {
			image.addCallback(this);
		}
		for(Sound sound : soundsToLoad){
			sound.addCallback(this);
		}
	}
	
	private void reportProgress() {
		if (listener != null) {
			int percentage = Math.round(((loaded + errors) / (float)(imagesToLoad.size() + soundsToLoad.size())) * 100);
			listener.progress(percentage);
		}
	}

	private void maybeDone() {
		reportProgress();
		if (isDone()) {
			if (listener != null) {
				listener.done();
			}
		}
	}

    @Override
    public void onSuccess(Object result) {
        ++loaded;
        maybeDone();
    }

    @Override
    public void onFailure(Throwable cause) {
        ++errors;
        if (listener != null)
            listener.error(cause);
        maybeDone();
    }

    public interface Listener {
		void done();
		
		void progress(final int percentageComplete);

		void error(Throwable e);
	}
	
}
