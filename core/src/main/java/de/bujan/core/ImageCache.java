package de.bujan.core;

import playn.core.Image;

import static playn.core.PlayN.assets;

/**
 * @author niko.schmidke
 *
 */
public class ImageCache {
	public static Image background;
    public static Image stoneWhite;
    public static Image stoneBlack;
	
	public static void loadImages(ProgressAssetWatcher assetWatcher) {
		background = assets().getImage("images/schachbrett.png");
		assetWatcher.add(background);
        stoneWhite= assets().getImageSync("images/dameWheiss.png");
        assetWatcher.add(stoneWhite);
        stoneBlack = assets().getImageSync("images/dameGrau.png");
        assetWatcher.add(stoneBlack);
	}
	
}
