// assignment 4
// pair 041
// Singh, Bhavneet
// singhb
// Pang, Bo
// pangbo

import java.awt.Color;

import javalib.worldimages.FromFileImage;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import javalib.worldimages.WorldImage;
import tester.Tester;

// Examples of data and tests for the vaderinvader game
public class ExamplesFundies2Game {
    // CONSTANTS:
    int width = 500;
    int height = 500;

    // two sample enemies
    Enemy enemy1 = new Enemy(new CartPt(width / 2, height / 2), 1,
            "p041-vader.png"); // an enemy
    Enemy enemy2 = new Enemy(new CartPt(width / 2, height),
            1, "p041-vader.png"); // ends game
    Enemy enemy3 = new Enemy(new CartPt(width / 3, height - 400), 1,
            "p041-vader.png");
    Enemy enemy4 = new Enemy(new CartPt(width / 3/2, height - 400), 1,
            "p041-vader.png");

    // A sample hero
    Hero hero1 = new Hero(new CartPt(width / 2, height), 8);
    Hero hero2 = new Hero(new CartPt(width / 2, height - 20), 8);

    // three sample lasers
    Laser laserin = new Laser(2, 
            new CartPt(width / 2, height - 200)); // laser in bounds
    Laser laserout = new Laser(2, 
            new CartPt(width / 2, -5)); // laser out of bounds
    Laser collidedlaser = new Laser(2,
            new CartPt(width / 2, height / 2)); // collided laser

    // sample list of lasers
    ILoL lole = new MtLoL();
    ILoL lol3 = new ConsLoL(collidedlaser, lole);
    ILoL lol2 = new ConsLoL(laserout, lol3);
    ILoL lol1 = new ConsLoL(laserin, lol2);
    ILoL lol0 = new ConsLoL(laserout, lole);

    // sample cartesian points
    CartPt cp1 = new CartPt(100, 100);
    CartPt cp2 = new CartPt(103, 104);
    CartPt cp3 = new CartPt(105, 112);
    CartPt cp4 = new CartPt(300, 0);
    CartPt cp5 = new CartPt(112, 116);
    
    //sample list of enemies
    Ie emptyie = new EmptyIe();
    Ie consie = new ConsIe(enemy1, emptyie);
    Ie consie2 = new ConsIe(enemy2, consie);
    Ie consie3 = new ConsIe(enemy3, consie);
    Ie consie4 = new ConsIe(enemy4, consie3);
    
    //a sample Prize
    Prize none = new NoPower();
    
    //a sample Boss
    Boss nob = new NoBoss();
    

    // three sample vaderinvaders
    VaderInvader myworld = new VaderInvader(consie, hero1, lol0, 0, 10, none, 1, nob);
    VaderInvader worldcollide = new VaderInvader(consie, hero1, lol1, 0, 10, none, 1, nob);
    VaderInvader worldfail = new VaderInvader(consie2, hero1, lol2, 2, 10, none, 1, nob);
    VaderInvader iworld = new VaderInvader(consie3, hero2, lole, 0, 10, none, 1, nob);
  
    /*
    // TESTS FOR CartPt CLASS

    //I HAD TO EITHER CAST THE CHECKNUMRANGE, HAVE IT RETURN TRUE, OR COMMENT
    //IT OUT OTHERWISE WEBCAT WOULD GIVE ME SOME COMPILE TIME ERROR. I CHOSE 
    //TO CAST IT
    // test the method randV in the class CartPt
    Boolean testRandV(Tester t) {
        return ((boolean) t.checkNumRange(cp1.randV().x, 30, 470));
    }

    // test the method moveBy in the class CartPt
    Boolean testMoveBy(Tester t) {
        return t.checkExpect(cp1.moveBy(5, 0), new CartPt(105, 100))
                && t.checkExpect(cp1.moveBy(-5, 0), new CartPt(95, 100));
    }

    // test the method disTo in the class CartPt
    Boolean testDisTo(Tester t) {
        return t.checkExpect(cp1.disTo(cp2), 5.0)
                && t.checkExpect(cp1.disTo(cp5), 20.0);
    }

    // test the method collidePos in the class CartPt
    Boolean testCollide(Tester t) {
        return t.checkExpect(cp1.collidePos(cp2), true)
                && t.checkExpect(cp1.collidePos(cp3), true)
                && t.checkExpect(cp1.collidePos(cp5), false);
    }

    // test the method outOfBoundsp in the class CartPt
    Boolean testOutOfBoundspCartPt(Tester t) {
        return t.checkExpect(cp1.outOfBoundsp(), false)
                && t.checkExpect(cp4.outOfBoundsp(), true);
    }

    // test the method movePointUp in the class CartPt
    Boolean testMovePointUp(Tester t) {
        return t.checkExpect(cp1.movePointUp(enemy1), new CartPt(100, 99))
                && t.checkExpect(cp2.movePointUp(enemy1), new CartPt(103, 103));
    }

    // TESTS FOR Hero CLASS

    // test the method moveLeft in the class Hero
    Boolean testMoveLeft(Tester t) {
        return t.checkExpect(hero1.moveLeft(enemy1), new Hero(new CartPt(249,
                500)));
    }

    // test the method moveLeft in the class Hero
    Boolean testMoveRight(Tester t) {
        return t.checkExpect(hero1.moveRight(enemy1), new Hero(new CartPt(251,
                500)));
    }

    // test the method onKeyEvent in the class Hero
    Boolean testOnKeyEvent(Tester t) {
        return t.checkExpect(hero1.onKeyEvent("left", enemy1), new Hero(
                new CartPt(249, 500)))
                && t.checkExpect(hero1.onKeyEvent("right", enemy1), new Hero(
                        new CartPt(251, 500)))
                && t.checkExpect(hero1.onKeyEvent("x", enemy1), hero1);
    }
    
    // test the method heroImage in the class Hero
    Boolean testHeroImage(Tester t) {
        return t.checkExpect(hero1.heroImage(), new RectangleImage(
                hero1.position, 30, 20, Color.CYAN));
    }

    // TESTS FOR Enemy CLASS

    // test the method fasterVader in the class Enemy
    Boolean testFasterVader(Tester t) {
        return t.checkFail(enemy1.fasterVader(), new Enemy(cp1, 2,
                "p041-vader.png"));
    }

    // test the method fall in the class Enemy
    Boolean testFall(Tester t) {
        return t.checkExpect(enemy1.fall(), new Enemy(new CartPt(250, 251), 1,
                "p041-vader.png"));
    }

    // test the method hitGround in the class Enemy
    Boolean testHitGround(Tester t) {
        return t.checkExpect(enemy1.hitGround(height), false)
                && t.checkExpect(enemy2.hitGround(height), true);
    }

    // test the method enemyImage in the class Enemy
    Boolean testEnemyImage(Tester t) {
        return t.checkExpect(enemy1.enemyImage(), new FromFileImage(
                enemy1.position, "p041-vader.png"));
    }

    // TESTS FOR Laser CLASS

    // test the method collideV in the class Laser
    Boolean testCollideV(Tester t) {
        return t.checkExpect(laserin.collideV(enemy1), false)
                && t.checkExpect(laserout.collideV(enemy1), false)
                && t.checkExpect(collidedlaser.collideV(enemy1), true);
    }

    // test the method outOfBoundsp in the class Laser
    Boolean testOutOfBoundspLaser(Tester t) {
        return t.checkExpect(laserin.outOfBoundsp(), false)
                && t.checkExpect(laserout.outOfBoundsp(), true)
                && t.checkExpect(collidedlaser.outOfBoundsp(), false);
    }

    // test the method moveLaser in the class Laser
    Boolean testMoveLaser(Tester t) {
        return t.checkExpect(laserin.moveLaser(enemy1), new Laser(2,
                new CartPt(250, 299)))
                && t.checkExpect(laserout.moveLaser(enemy1), new Laser(2,
                        new CartPt(250, -6)));
    }

    // test the method laserImage in the class Laser
    Boolean testLaserImage(Tester t) {
        return t.checkExpect(laserin.laserImage(), new RectangleImage(
                new CartPt(250, 300), 2, 4, Color.RED));
    }

    // TESTS FOR ILoL INTERFACE

    // TESTS FOR MtLoL CLASS

    // test the method onKeyEvent in the class MtLoL
    Boolean testOnKeyEventMtLoL(Tester t) {
        return t.checkExpect(lole.onKeyEvent("up", hero1), new ConsLoL(
                new Laser(2, hero1.position), lole))
                && t.checkExpect(lole.onKeyEvent("right", hero1), lole);
    }

    // test the method collide in the class MtLoL
    Boolean testCollideMtLoL(Tester t) {
        return t.checkExpect(lole.collide(enemy1), false)
                && t.checkExpect(lole.collide(enemy2), false);
    }

    // test the method removeCollidedLasers in the class MtLoL
    Boolean testRemoveCollidedLasersMtLoL(Tester t) {
        return t.checkExpect(lole.removeCollidedLasers(enemy1), lole)
                && t.checkExpect(lole.removeCollidedLasers(enemy2), lole);
    }

    // test the method removeOutOfBoundLasers in the class MtLoL
    Boolean testRemoveOutOfBoundLasersMtLoL(Tester t) {
        return t.checkExpect(lole.removeOutOfBoundLasers(), lole)
                && t.checkExpect(lole.removeOutOfBoundLasers(), lole);
    }

    // test the method moveLasers in the class MtLoL
    Boolean testmoveLasersMtLoL(Tester t) {
        return t.checkExpect(lole.moveLasers(enemy1), lole)
                && t.checkExpect(lole.moveLasers(enemy1), lole);
    }

    // test the method lasersImage in the class MtLoL
    Boolean testLasersImageMtLoL(Tester t) {
        return t.checkExpect(lole.lasersImage(), new RectangleImage(new Posn(
                width / 2, height / 2), width, height,
                new Color(50, 150, 255)));
    }

    // TESTS FOR ConsLoL CLASS

    // test the method onKeyEvent in the class ConsLoL
    Boolean testOnKeyEventConsLoL(Tester t) {
        return t.checkExpect(lol3.onKeyEvent("up", hero1), new ConsLoL(
                new Laser(2, hero1.position), lol3))
                && t.checkExpect(lol3.onKeyEvent("x", hero1), lol3);
    }

    // test the method collide in the class ConsLoL
    Boolean testCollideConsLoL(Tester t) {
        return t.checkExpect(lol3.collide(enemy1), true)
                && t.checkExpect(lol3.collide(enemy2), false);
    }

    // test the method removeCollidedLasers in the class ConsLoL
    Boolean testRemoveCollidedLasersConsLoL(Tester t) {
        return t.checkExpect(lol1.removeCollidedLasers(enemy1), new ConsLoL(
                laserin, lol0))
                && t.checkExpect(lol3.removeCollidedLasers(enemy2), lol3);
    }

    // test the method removeOutOfBoundLasers in the class ConsLoL
    Boolean testRemoveOutOfBoundLasersConsLoL(Tester t) {
        return t.checkExpect(lol1.removeOutOfBoundLasers(), new ConsLoL(
                laserin, lol3))
                && t.checkExpect(lol3.removeOutOfBoundLasers(), lol3);
    }

    // test the method moveLasers in the class ConsLoL
    Boolean testmoveLasersConsLoL(Tester t) {
        return t.checkExpect(lol1.moveLasers(enemy1), new ConsLoL(new Laser(2,
                new CartPt(250, 299)), new ConsLoL(new Laser(2, new CartPt(250,
                -6)), new ConsLoL(new Laser(2, new CartPt(250, 249)), lole))));
    }

    // test the method lasersImage in the class ConsLoL
    Boolean testLasersImageConsLoL(Tester t) {
        return t.checkExpect(lol0.lasersImage(), new RectangleImage(new Posn(
                width / 2, height / 2), width, height, new Color(50, 150, 255))
                .overlayImages(new RectangleImage(laserout.position, 2, 4,
                        Color.RED)));
    }

    // TESTS FOR VaderInvader CLASS

    // test the method onTick in the class VaderInvader
    Boolean testOnTick(Tester t) {
        return t.checkExpect(myworld.onTick(), new VaderInvader(new Enemy(
                new CartPt(width / 2, 251), 1, "p041-vader.png"), hero1, lole,
                0))
                && t.checkExpect(worldcollide.onTick().score, 1);
    }

    // test the method onKeyEvent in the class VaderInvader
    Boolean testOnKey(Tester t) {
        return t.checkExpect(myworld.onKeyEvent("left"), new VaderInvader(
                enemy1, new Hero(new CartPt(249, 500)), lol0, 0))
                && t.checkExpect(myworld.onKeyEvent("right"), new VaderInvader(
                        enemy1, new Hero(new CartPt(251, 500)), lol0, 0))
                && t.checkExpect(myworld.onKeyEvent("up"), new VaderInvader(
                        enemy1, hero1, new ConsLoL(
                                new Laser(2, hero1.position), lol0), 0))
                && t.checkExpect(myworld.onKeyEvent("x"), new VaderInvader(
                        enemy1, hero1, lol0, 0));
    }

    // test the method makeImage in the class VaderInvader
    boolean testmakeImage(Tester t) {
        return t.checkExpect(
                myworld.makeImage(),
                lol0.lasersImage().overlayImages(
                        enemy1.enemyImage(),
                        hero1.heroImage(),
                        new TextImage(new Posn(width - 30, 10), "Score: 0",
                                Color.green)));
    }

    // test the method worldEnds in the class VaderInvader
    Boolean testWorldEnds(Tester t) {
        return t.checkExpect(myworld.worldEnds(),
                new WorldEnd(false, myworld.makeImage()))
                && t.checkExpect(
                        worldfail.worldEnds(),
                        new WorldEnd(true, worldfail.makeImage()
                                .overlayImages(
                                        new TextImage(new Posn(width / 2,
                                                height / 2),
                                                "You Lose, Final Score: 2",
                                                Color.black))));
    }

    // test the method lastImage in the class VaderInvader
    boolean testlastImage(Tester t) {
        return t.checkExpect(
                worldfail.lastImage("You Lose, Final Score: 2"),
                worldfail.makeImage().overlayImages(
                        new TextImage(new Posn(width / 2, height / 2),
                                "You Lose, Final Score: 2", Color.black)));
    }
    
    
    //test countHits()
    Boolean testCount(Tester t) {
        return t.checkExpect(new ConsIe(enemy2, consie2).countHits(height), 2);
    }
    */
}
