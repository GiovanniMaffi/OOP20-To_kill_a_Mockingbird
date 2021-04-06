package map;

import java.util.Random;

public class Strip {
	
	public Box[] getStrip() {

		// Array to hold strip.
		Box[] boxStrip = new Box[8];

		// Number of grids wide.
		int y = boxStrip.length;

		// Selects random landscape.
		Random gen = new Random();
		int env = gen.nextInt(3);

		// Sets landscape.
		switch (env) {
		// Road.
		case 0:
			for (int i = 0; i < y; i++) {
				Box strip = new Box("/Mockingbird/resources/Road.png");
				boxStrip[i] = strip;
			}
			break;

		// Tracks.
		case 1:
			for (int i = 0; i < y; i++) {
				Box strip = new Box("/Mockingbird/resources/Tracks.png");
				boxStrip[i] = strip;
			}
			break;

		// Special Land.
		case 2:
			for (int i = 0; i < y; i++) {

				boxStrip[i] = setObstacles(i, "/Mockingbird/resources/Grass.png", "/Mockingbird/resources/Tree_One.png");
			}
			break;

		}

		return boxStrip;
	}
	
	private Box setObstacles(int i, String background, String specialBlock) {

		Box oneBlock = new Box();
		Random gen = new Random();
		int rand = gen.nextInt(4);
		// una possibilità su 4 di mettere un ostacolo  (es albero)
		if (rand == 3) { 	
			oneBlock.setImage(specialBlock);
		} else {
			oneBlock.setImage(background);
		}

		// Adds image to strip.
		return oneBlock;
	}
	
	public Box[] getSpecificStrip(String background, String specialBlock) {

		//Array to hold strip.
		Box[] boxStrip = new Box[8];

		for (int i = 0; i < 8; i++) {
			boxStrip[i] = setObstacles(i, background, specialBlock);
		}
		return boxStrip;
	}
}
