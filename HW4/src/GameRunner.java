// assignment 4
// pair 041
// Singh, Bhavneet
// singhb
// Pang, Bo
// pangbo

//THE RUNNER IS COMMENTED OUT SO AS TO PREVENT POINT DEDUCTION
//FROM THE WEBCAT SUBMISSION SERVER


import javalib.worldimages.*;
import javalib.worldcanvas.*;
import javalib.funworld.*;
import tester.Tester;

// test the display of images for the vaderinvader game and run the game
public class GameRunner {

    ExamplesFundies2Game evi = new ExamplesFundies2Game();

    WorldCanvas c1 = new WorldCanvas(500, 500);
    WorldCanvas c2 = new WorldCanvas(500, 500);

    WorldImage enemyImage = this.evi.consie.enemyImage();
    WorldImage heroImage = this.evi.hero2.heroImage();
    WorldImage lasersImage = this.evi.lole.lasersImage();
    WorldImage prizeImage = this.evi.none.prizeImage();
    WorldImage bossImage = this.evi.nob.bossImage();

    WorldImage worldImage = this.evi.iworld.makeImage();

    // show the enemy, the hero, and the lasers on one canvas
    boolean makeDrawing1 = c1.show() && c1.drawImage(this.lasersImage)
            && c1.drawImage(this.enemyImage) && c1.drawImage(this.heroImage)
            && c1.drawImage(this.prizeImage) && c1.drawImage(this.bossImage);

    // show the world on one canvas
    boolean makeDrawing2 = c2.show() && c2.drawImage(this.worldImage);

    
    // run the game
    boolean run() {
        return this.evi.iworld.bigBang(500, 500, 0.05);
    }

    // invoke this method to run the vaderinvader game
    public static void main(String[] argv) {
        GameRunner gr = new GameRunner();
        gr.run();
    }
    
}