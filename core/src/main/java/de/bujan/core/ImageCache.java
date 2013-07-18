package de.bujan.core;

import playn.core.Assets;
import playn.core.Image;

/**
 * @author niko.schmidke
 *
 */
public class ImageCache {
	public static Image background;
    public static Image stoneWhite;
    public static Image stoneBlack;
	
	public static void loadImages(Assets assets, ProgressAssetWatcher assetWatcher) {
		background = assets.getImage("images/schachbrett.png");
		assetWatcher.add(background);
        stoneWhite= assets.getImage("images/dameWheiss.png");
        assetWatcher.add(stoneWhite);
        stoneBlack = assets.getImage("images/dameGrau.png");
        assetWatcher.add(stoneBlack);
	}
	
}
