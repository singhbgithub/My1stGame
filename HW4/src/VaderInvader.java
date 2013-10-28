// assignment 4
// pair 041
// Singh, Bhavneet
// singhb
// Pang, Bo
// pangbo

import javalib.funworld.*;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.*;

//this class represents the vaderinvader world/game
class VaderInvader extends World {
    // darth vader (the enemy) falling from the sky
    Ie darthvader;

    // the hero, bo, who can move across the screen and
    // fire lasers to "kill" the enemy
    Hero bo;

    // Deadly beams that are fire   the screen from
    // the position of the hero
    ILoL lasers;

    // An integer that represents how many darthvaders you killed
    int score;
    
    // An integer that represents health
    int health;
    
    //A Prize representing a power up
    Prize powerup;
    
    // destroy all blast: counter
    int finalflash;
    
    // blasters
    int blasters;
    
    //the boss of the game
    Boss boss;

    // CONSTANTS:
    int width = 500;
    int height = 500;

    VaderInvader(Ie darthvader, Hero bo, ILoL lasers,
            int score, int health, Prize powerup, int finalflash, Boss boss) {
        this.darthvader = darthvader;
        this.bo = bo;
        this.lasers = lasers;
        this.score = score;
        this.health = health;
        this.powerup = powerup;
        this.finalflash = finalflash;
        this.boss = boss;
    }
    
     /*
     * TEMPLATE: 
     * FIELDS: 
     * ...this.darthvader...                         -- Enemy 
     * ...this.bo...                                 -- Hero
     * ...this.lasers...                             -- ILoL
     * ...this.score...                              -- int
     * 
     * METHODS: 
     * ...this.onTick()...                           -- World 
     * ...this.onKeyEvent(String)...                 -- World 
     * ...this.makeImage()...                        -- WorldImage 
     * ...this.lastImage(String)...                  -- WorldImage 
     * ...this.worldEnds()...                        -- WorldEnd
     * 
     * METHODS FOR FIELDS:
     * ...this.darthvader.fasterVader()...           -- Enemy
     * ...this.darthvader.fall()...                  -- Enemy
     * ...this.darthvader.hitGround(int)...          -- boolean
     * ...this.darthvader.vaderImage()...            -- WorldImage
     * 
     * ...this.bo.onKeyEvent()...                    -- Hero
     * ...this.bo.moveLeft()...                      -- Hero
     * ...this.bo.moveRight()...                     -- Hero
     * ...this.bo.heroImage()...                     -- WorldImage
     * 
     * ...this.lasers.onKeyEvent(String)...          -- ILoL
     * ...this.lasers.collide(Enemy)...              -- boolean
     * ...this.lasers.removeCollidedLasers(Enemy)... -- ILoL
     * ...this.lasers.removeOutOfBoundLasers()...    -- ILoL
     * ...this.lasers.moveLasers()...                -- ILoL
     * ...this.lasers.lasersImage()...               -- WorldImage
     */


    // produce a new vaderinvader out of this one after one tick has passed
    // this vaderinvader's enemy moves down the screen,this vaderinvader's
    // ILoL, advance   the screen and are culled off when they reach the
    // top, or if they hit this vaderinvader's enemy
    // this vaderinvader's hero does not move
    // if this vaderinvader's ILoL collides with this vaderinvader's enemy
    // then it will produce a new vaderinvader with a faster enemy and 1
    // added to the score
    public VaderInvader onTick() {   
        if (this.darthvader.collideAll(this.lasers))
            return new VaderInvader(this.darthvader.addVader()
                    .newVaders(lasers)
                    .spawnVaders(height)
                    .removeCollideAllIE(this.lasers)
                    .removeHits(height)
                    .fall(),
                  this.bo,
                  this.lasers
                    .removeAllCollidedLasers(this.darthvader)
                    .removeOutOfBoundLasers()
                    .removeLaserPrize(this.powerup)
                    .removeLaserBoss(this.boss)
                    .moveLasers(),
                  this.score + this.darthvader.countCollide(lasers), 
                  this.health 
                    - this.darthvader.countHits(height)
                    + this.powerup.addHealth(this.lasers),
                  this.powerup.addPrize(this.score).removePrize(this.lasers),
                  this.finalflash,
                  this.boss.addBoss(this.score).subtractHealth(this.lasers).moveBoss());
        else                                                                    
            return new VaderInvader(this.darthvader
                    .spawnVaders(height)
                    .removeHits(height)
                    .fall(),
                  this.bo,
                  this.lasers
                    .removeOutOfBoundLasers()
                    .removeLaserPrize(this.powerup)
                    .removeLaserBoss(this.boss)
                    .moveLasers(),
                  this.score,
                  this.health 
                    - this.darthvader.countHits(height)
                    + this.powerup.addHealth(this.lasers),
                  this.powerup.addPrize(this.score).removePrize(this.lasers),
                  this.finalflash,
                  this.boss.subtractHealth(this.lasers).moveBoss()); //don't need to add boss here, b/c no new kills
        //Constraint: Initial world must have one enemy in the list
    }
    
    
    // produce a new vaderinvader out of this one in response to a key event,
    // this vaderinvader's enemy does not move, this vaderinvader's ILoL,
    // is unaffected unless, the given key is spacebar, then one laser will
    // be added to the this vaderinvader's ILoL and this vaderinvader's hero,
    // may move left or right, if the given key is "left" or "right"
    // respectively
    public World onKeyEvent(String ke) {
        if ((ke == "up") && (this.finalflash > 0))
            return new VaderInvader(
                    new ConsIe(
                            new Enemy(new CartPt(0, 0).randV(),
                                    1,
                                    this.darthvader.enemify().name), 
                                    new ConsIe(
                            new Enemy(new CartPt(0, 0).randV(),
                                    1,
                                    this.darthvader.enemify().name),
                            new EmptyIe())),
                            this.bo,
                            this.lasers,
                            this.score + this.darthvader.countVaders(),
                            this.health,
                            this.powerup,
                            this.finalflash - 1,
                            this.boss.flash());
        else return new VaderInvader(this.darthvader,
                this.bo.onKeyEvent(ke),
                this.lasers.onKeyEvent(ke, this.bo),
                this.score,
                this.health,
                this.powerup,
                this.finalflash,
                this.boss);
    }

    // produce the image that represents this vaderinvader
    public WorldImage makeImage() {
        return this.lasers.lasersImage().overlayImages(
                this.powerup.prizeImage(),
                this.darthvader.enemyImage(),
                this.boss.bossImage(),
                this.bo.heroImage(),
                new TextImage(new Posn(width - 30, 10),
                        "Score: " + Integer.toString(this.score),
                        Color.MAGENTA),
                new TextImage(new Posn(30, 10),
                        "Health: " + Integer.toString(this.health),
                        Color.GREEN),
                new TextImage(new Posn(120, 10),
                        "Final Flash: " + Integer.toString(this.finalflash),
                        Color.ORANGE));
    }

    // After each tick checks if this vaderinvader's enemy reaches
    // the bottom of the screen - if so, the game ends
    public WorldEnd worldEnds() {
        if ((this.health <= 0) || this.boss.crossed(height))
            return new WorldEnd(true, this.makeImage().overlayImages(
                    new TextImage(new Posn(width / 2, height / 2),
                            "You Lose, Final Score: " + 
                             Integer.toString(this.score),
                            Color.PINK)));
        else if (this.boss.isDead())
            return new WorldEnd(true, this.lasers.lasersImage().overlayImages(
                    this.powerup.prizeImage(),
                    this.darthvader.enemyImage(),
                    this.boss.bossImage(),
                    this.bo.heroImage(),
                    new TextImage(new Posn(width - 30, 10),
                            "Score: " + Integer.toString(this.score + 1000),
                            Color.MAGENTA),
                    new TextImage(new Posn(30, 10),
                            "Health: " + Integer.toString(this.health),
                            Color.GREEN),
                    new TextImage(new Posn(120, 10),
                            "Final Flash: " + Integer.toString(this.finalflash),
                            Color.ORANGE)).overlayImages(
                    new TextImage(new Posn(width / 2, height / 2),
                            "You Stopped the Invasion, you destroyed: " + 
                             Integer.toString(this.score + 1000) + " Vaders!",
                            Color.PINK)));
        else return (new WorldEnd(false, this.makeImage()));
    }

    // produce the last image if this vaderinvader's enemy
    // reaches the bottom of the screen
    public WorldImage lastImage(String s) {
        return this.makeImage().overlayImages(
                new TextImage(new Posn(width / 2, height / 2), s, Color.black));
    }
}

interface Boss {
    public Boss addBoss(int score);
    public Boss moveBoss();
    public boolean collideLaserBoss(ILoL lasers);
    public Boss subtractHealth(ILoL lasers);
    public WorldImage bossImage();
    public Boss flash();
    public boolean isDead();
    public CartPt bossify();
    public boolean noBoss();
    public boolean crossed(int bottom);
}

class NoBoss implements Boss {
    NoBoss() {}
    
    //a random number generator
    Random rand = new Random();
    
    //add a new Boss to the game
    public Boss addBoss(int score) {
        if (score >= 500)
            return new FBoss(
                    new CartPt(20 + rand.nextInt(440), 0),
                    300,
                    1);
        else return this;
    }
    
    //moves this NoBoss
    public Boss moveBoss() {
        return this;
    }
    
    //checks to see if this boss collided with any of the given lasers
    public boolean collideLaserBoss(ILoL lasers) {
        return false;
    }
     
    //subtracts health if hit by laser
    public Boss subtractHealth(ILoL lasers) {
        return this;
    }
    
    //draws this NoBoss onto the scene
    public WorldImage bossImage() {
        return new RectangleImage(
                new Posn(0, 0),
                0,
                0,
                Color.black);
    }
    
    //subtracts 10 health when a flash is used
    public Boss flash() {
        return this;
    }
    
    //is this NoBoss dead
    public boolean isDead() {
        return false;
    }
    
    //gimme the position
    public CartPt bossify() {
        throw new RuntimeException("no boss, no position!");
    }
    
    //is there no boss
    public boolean noBoss() {
        return true;
    }
    
    //did this boss cross the bottom
    public boolean crossed(int bottom) {
        return false;
    }
}

//this class represents the boss to beat the game
class FBoss implements Boss {
    CartPt position;
    int health;
    int speed;
    FBoss(CartPt position, int health, int speed) {
        this.position = position;
        this.health = health;
        this.speed = speed;
    }
    
    //add a new boss to the game
    public Boss addBoss(int score) {
        return this;
    }
    
    //move this FBoss
    public Boss moveBoss() {
        return new FBoss(
                this.position.moveBy(0, this.speed),
                this.health,
                this.speed);    
    }
    
  //checks to see if this boss collided with any of the given lasers
    public boolean collideLaserBoss(ILoL lasers) {
        if (lasers.emptyLoL())
            return false;
        else if (lasers.lazify().position.collidePos(this.position))
            return true;
        else return this.collideLaserBoss(lasers.iRest());
    }
    
  //subtracts health if hit by laser
    public Boss subtractHealth(ILoL lasers) {
        if (this.collideLaserBoss(lasers))
            return new FBoss(
                this.position,
                this.health - 1,
                this.speed);
        else return this;
    }
    
  //draws this boss onto the scene
    public WorldImage bossImage() {
        if (this.health > 0)
            return new FromFileImage(this.position, "mew2.png");
        else return new FromFileImage(this.position, "nuke.png");
    }
    
  //subtracts 10 health when a flash is used
    public Boss flash() {
        return new FBoss(
                this.position,
                this.health / 2,
                this.speed);
    }
    
  //is this FBoss dead
    public boolean isDead() {
       return this.health <= 0;
    }
    
    //gimme the position
    public CartPt bossify() { 
        return this.position;
    }
    
    //is there no boss
    public boolean noBoss() {
        return false;
    }
    
  //did this boss cross the bottom
    public boolean crossed(int bottom) {
        return this.position.y >= bottom;
    }
}



interface Prize {
    public int whichPrize();
    public Prize addPrize(int cs);
    public Prize makePower();
    public WorldImage prizeImage();
    public boolean collidePrize(ILoL lasers);
    public Prize removePrize(ILoL lasers);
    public int addHealth(ILoL lasers);
    public boolean noPrize();
    public CartPt prizify();
}

abstract class APrize implements Prize {
    
 // a random number generator
    public Random rand = new Random();
    
    //generates a new random number, either 0 and 1
    public int whichPrize() {
        if (rand.nextInt(20) < 5)
            return 0;
        else return 10;
    }
    
    //add power if score is divisible by 20
    public Prize addPrize(int cs) {
        if ((cs % 15) == 0)
            return this.makePower();
        else return this;
    }
    
    //make a new prize out of this one
    public Prize makePower() {
        if (this.whichPrize() <= 5)
            return new NoPower();
        else return new HealthPack(
                new CartPt(50 + rand.nextInt(400), 50 + rand.nextInt(400)),
                10);      //make the value a random int from 10 - 30
    }                     //do the same thing as whichPrize()
    
    //draw this prize
    public WorldImage prizeImage() {
        return new RectangleImage(
                new CartPt(0, 0),
                0, 0, Color.white);
    }
    
    
}

class NoPower extends APrize {
    NoPower() {} 
    
    //did the given list of lasers collide with this NoPower
    public boolean collidePrize(ILoL lasers) {
        return false;
    }
    
    //removes this noprize if hit by laser
    public Prize removePrize(ILoL lasers) {
        return this;
    }
    
    //adds health if hit by laser
    public int addHealth(ILoL lasers) {
        return 0;
    }
    
    //is this an empty prize
    public boolean noPrize() {
        return true;
    }
    
    //gimme the position
    public CartPt prizify() {
        throw new RuntimeException("no prize, no position!");
    }
}

class HealthPack extends APrize {
    CartPt position;
    int value;
    HealthPack(CartPt position, int value) {
        this.position = position;
        this.value = value;
    }
    
    //draw this healthpack
    public WorldImage prizeImage() {
        return new FromFileImage(this.position, "healthpack.png");
    }
    
  //did the given list of lasers collided with this healthpack
    public boolean collidePrize(ILoL lasers) {
        if (lasers.emptyLoL())
            return false;
        else if (this.position.collidePos(lasers.lazify().position))
            return true;
        else return this.collidePrize(lasers.iRest());
    }
    
    //removes healthpack if collided
    public Prize removePrize(ILoL lasers) {
        if (this.collidePrize(lasers))
            return new NoPower();
        else return this;
    }
    
  //adds health if hit by laser
    public int addHealth(ILoL lasers) {
        if (this.collidePrize(lasers))
            return this.value;
        else return 0;
    }
    
    //is this prize empty
    public boolean noPrize() {
        return false;
    }
    
    //gimme the position
    public CartPt prizify() {
        return this.position;
    }
}


//class FPack extends APrize {
   // CartPt position;
  //  FPack(CartPt position) {
  //      this.position = position;
  //  }
        
//}

class Blast extends APrize {
    CartPt position;
    int value;
    Blast(CartPt position, int value) {
        this.position = position;
        this.value = value;
    }
    
  //draw this healthpack
    public WorldImage prizeImage() {
        return new FromFileImage(this.position, "healthpack.png");
    }
    
  //did the given list of lasers collided with this healthpack
    public boolean collidePrize(ILoL lasers) {
        if (lasers.emptyLoL())
            return false;
        else if (this.position.collidePos(lasers.lazify().position))
            return true;
        else return this.collidePrize(lasers.iRest());
    }
    
    //removes healthpack if collided
    public Prize removePrize(ILoL lasers) {
        if (this.collidePrize(lasers))
            return new NoPower();
        else return this;
    }
    
    //adds health if hit by laser
    public int addHealth(ILoL lasers) {
        return 0;
    }
    
    //adds  blasters if hit by laser
    public int addBlasts(ILoL lasers) {
        if (this.collidePrize(lasers))
            return this.value;
        else return 0;
    }
    //is this prize empty
    public boolean noPrize() {
        return false;
    }
    
    //gimme the position
    public CartPt prizify() {
        return this.position;
    }
}

interface Ie {
    public boolean collideIE(Laser laz);
    public boolean collideAll(ILoL laz);
    public Ie removeCollideAllIE(ILoL laz);
    public Ie removeCollided(Laser laz);
    public int countCollide(ILoL laz);
    public Ie faster();
    public Ie newVaders(ILoL laz);
    public Ie vaderGenerator(int lostvaders);
    public Enemy enemify();
    public Ie fall();
    public boolean emptyp();
    public Ie needRest();
    public WorldImage enemyImage();
    public boolean hitGround(int bottom);
    public Ie addVader();
    public int countHits(int bottom);
    public Ie spawnVaders(int bottom);
    public Ie vaderCreator(int hits);
    public Ie removeHits(int height);
    public int countVaders();
}

class EmptyIe implements Ie{
    EmptyIe() {}
    
    //did this empty list of enemies collide with the given laser?
    public boolean collideIE(Laser laz) {
        return false;
    }
    
    //remove given laser in this empty list of enemies
    //if it collides
    public Ie removeCollided(Laser laz) {
        return this;
    }
    
    //remove given lasers in the given list of lasers
    //if any laser collides with this empty list of enemies
    public Ie removeCollideAllIE(ILoL laz) {
        return this;
    }
    
    //any laser in a LoL collide with any enemy
    public boolean collideAll(ILoL laz) {
        return false;
    }
    
    //any laser in a LoL collide with any enemy
    public int countCollide(ILoL laz) {
        return 0;
    }
    
    //make all vaders faster
    public Ie faster() {
        return this;
    }
    
    //add new vaders equal to the amount removed
    public Ie newVaders(ILoL laz) {
        return this;
    }
    
    //vader generator
    public Ie vaderGenerator(int lostvaders) {
        return this;
    }
    
    //gimme the first enemy
    public Enemy enemify() {
        throw new RuntimeException("no first for empty list of enemies!");
    }
    
    //makes the enemies in this list fall
    public Ie fall() {
        return this;
    }
    
    //is this empty list of enemies empty
    public boolean emptyp() {
        return true;
    }
    
    //give the rest of this list
    public Ie needRest() {
        throw new RuntimeException("no rest for empty list of enemies!");
    }
    
    //draw an empty list of lasers
    public WorldImage enemyImage() {
        return new RectangleImage(new Posn(0, 0), 0, 0, Color.black);
    }  
    
    //checks if this empty list of enemies hit the ground
    public boolean hitGround(int bottom) {
        return false;
    }
    
    //counts how many enemies hit the ground in this empty list of enemies
    public int countHits(int bottom) {
        return 0;
    }
    
    //adds a new enemy to this list if the speed resets for the
    //current list of enemies
    public Ie addVader() {
        return this;
    }
    
    //adds new vaders after they cross the screen
    public Ie spawnVaders(int bottom) {
        return this;
    }
    
    //adds new vaders after they cross the screen
    public Ie vaderCreator(int hits) {
        return this;
    }
    
    //remove enemies in this list that hit the ground
    public Ie removeHits(int height) {
        return this;
    }
    
    //counts the number of vaders in this empty list
    public int countVaders() {
        return 0;
    }
    
}

class ConsIe implements Ie {
    Enemy first;
    Ie rest;
    ConsIe(Enemy first, Ie rest) {
        this.first = first;
        this.rest = rest;
    }
    //collide laser in the list of enemies
    public boolean collideIE(Laser laz) {
        if (laz.collideV(this.first))
            return true;
        else return false || this.rest.collideIE(laz);
    }
    
  //remove a laser in the list of enemies
    public Ie removeCollided(Laser laz) {
        if (laz.collideV(this.first))
            return this.rest;
        else return new ConsIe(this.first, this.rest.removeCollided(laz));
    }
    
    //remove lasers in the list of enemies
    public Ie removeCollideAllIE(ILoL laz) {
        if (laz.emptyLoL())    //or if this list of enemies is empty
            return this;
        else if(this.collideIE(laz.lazify()))
            return this.removeCollided(laz.lazify()).removeCollideAllIE(laz.iRest());
        else return this.removeCollideAllIE(laz.iRest());
    }
    
    //any laser in a LoL collide with any enemy
    public boolean collideAll(ILoL laz) {
        if (laz.emptyLoL())
            return false;
        else if (this.collideIE(laz.lazify()))
            return true;
        else return false || this.collideAll(laz.iRest());
    }
    
    //any laser in a LoL collide with any enemy
    public int countCollide(ILoL laz) {
        if (laz.emptyLoL())
            return 0;
        else if (this.collideIE(laz.lazify()))
            return 1 + this.removeCollided(laz.lazify()).countCollide(laz.iRest());
        else return  this.countCollide(laz.iRest());
    }
    
    //make all vaders faster and add new vaders
    //equal to the amount removed
    public Ie newVaders(ILoL laz) {
       return this.vaderGenerator(this.countCollide(laz));
                        
    }
    
    //vader generator and speed enhancer
    public Ie vaderGenerator(int lostvaders) {
        if ((lostvaders == 0))
            return this.faster();
        else 
            return new ConsIe(
                    new Enemy(
                            new CartPt(0,0),
                            this.first.speed,
                            this.first.name).fasterVader(),
                            this.vaderGenerator(lostvaders - 1));
    }
    
    //make all vaders faster
    public Ie faster() {
        if (((this.first.speed + 1/2) % 3) == 0)
        return new ConsIe(
                new Enemy(this.first.position,
                        1,
                        this.first.name),
                this.rest.faster());
        else return new ConsIe(
                new Enemy(this.first.position,
                        (this.first.speed + 1/2) % 3,
                        this.first.name),
                this.rest.faster());
    }
    
    //gimme the first enemy
    public Enemy enemify() {
        return this.first;
    }
    
    //move all enemies in this list down the screen
    public Ie fall() {
        return new ConsIe(this.first.fall(), this.rest.fall());
                
    }
    
    //is this non empty list of enemies empty
    public boolean emptyp() {
        return false;
    }
    
    //give the rest of this list
    public Ie needRest() {
        return this.rest;
    }
    
    //draw this list of enemies on the scene
    public WorldImage enemyImage() {
        if (this.rest.emptyp())
            return this.first.enemyImage();
        else return this.first.enemyImage().overlayImages(this.rest.enemyImage());
    }
    
    //did any of the enemies in this non empty list of enemies
    //hit the ground
    public boolean hitGround(int bottom) {
        return this.first.hitGround(bottom) || this.rest.hitGround(bottom);
    }
    
  //adds a new enemy to this list if the speed resets for the
    //current list of enemies
    public Ie addVader() {
        if (((this.first.speed + 1) % 6) == 0)
        return new ConsIe(
                new Enemy(
                        new CartPt(0,0).randV(),
                        1,
                        this.first.name),
                this);
        else return this;
    }
    
    //count the number of enemies that hit the ground in this non empty list
    public int countHits(int bottom) {
        if (this.first.hitGround(bottom))
            return 1 + this.rest.countHits(bottom);
        else return this.rest.countHits(bottom);
    }
    
  //adds new vaders after they cross the screen
    public Ie spawnVaders(int bottom) {
        return this.vaderCreator(this.countHits(bottom));
    }
    
  //adds new vaders after they cross the screen
    public Ie vaderCreator(int hits) {
        if (hits == 0)
            return this;
        else return new ConsIe(
                new Enemy(new CartPt(0, 0).randV(),
                        this.first.speed,
                        this.first.name),
                        this.vaderCreator(hits - 1));
    }
    
    //remove the enemies that hit the ground in this list
    public Ie removeHits(int height) {
        if (this.first.hitGround(height))
            return this.rest.removeHits(height);
        else return new ConsIe(this.first, this.rest.removeHits(height));
    }
    
  //counts the number of vaders in this empty list
    public int countVaders() {
        return 1 + this.rest.countVaders();
    }
}




//this class represents an enemy
class Enemy {
    CartPt position;
    int speed;
    String name;

    Enemy(CartPt position, int speed, String name) {
        this.position = position;
        this.speed = speed;
        this.name = name;
    }
    
    /*
     * TEMPLATE: 
     * FIELDS: 
     * ...this.position...                           -- CartPt 
     * ...this.speed...                              -- int
     * ...this.name...                               -- String
     * 
     * METHODS: 
     * ...this.fasterVader()...                      -- Enemy
     * ...this.fall()...                             -- Enemy
     * ...this.hitGround(int)...                     -- boolean
     * ...this.vaderImage()...                       -- WorldImage
     * 
     * METHODS FOR FIELDS:
     * ...this.position.randV()...                   -- CartPt
     * ...this.position.moveBy(int, int)...          -- CartPt
     * ...this.position.disTo(CartPt)...             -- double
     * ...this.position.collidePos(CartPt)...        -- boolean
     * ...this.position.outOfBoundsp()...            -- boolean
     * ...this.position.movePointUp(Enemy)...        -- CartPt
     */


    // produce a faster enemy out of this one
    public Enemy fasterVader() {
        if (((this.speed + 1) % 6) == 0)
            return new Enemy(this.position.randV(), 1, this.name);
        else return new Enemy(this.position.randV(), (this.speed + 1) % 6, this.name);
    }

    // move this enemy down by its speed
    public Enemy fall() {
        return new Enemy(this.position.moveBy(0, this.speed), this.speed,
                this.name);
    }

    // did this enemy hit the ground?
    public Boolean hitGround(int height) {
        return this.position.y >= height;
    }

    // produce the image of this enemy at this position
    public WorldImage enemyImage() {
        return new FromFileImage(this.position, this.name);
    }
}

//this class represents a hero 
class Hero {
    CartPt position;
    int speed;

    Hero(CartPt position, int speed) {
        this.position = position;
        this.speed = speed;
    }
    
    /*
     * TEMPLATE: 
     * FIELDS: 
     * ...this.position...                           -- CartPt 
     * 
     * METHODS: 
     * ...this.onKeyEvent(String, Enemy)...          -- Hero
     * ...this.moveLeft(Enemy)...                    -- Hero
     * ...this.moveRight(Enemy)...                   -- Hero
     * ...this.heroImage()...                        -- WorldImage
     * 
     * METHODS FOR FIELDS:
     * ...this.position.randV()...                   -- CartPt
     * ...this.position.moveBy(int, int)...          -- CartPt
     * ...this.position.disTo(CartPt)...             -- double
     * ...this.position.collidePos(CartPt)...        -- boolean
     * ...this.position.outOfBoundsp()...            -- boolean
     * ...this.position.movePointUp(Enemy)...        -- CartPt
     */

    // move this hero left and right in response to a given string
    public Hero onKeyEvent(String ke) {
        if (ke.equals("left"))
            return this.moveLeft();
        else if (ke.equals("right"))
            return this.moveRight();
        else
            return this;
    }

    // from this Hero produce another Hero which moved left a bit (5 pixels)
    public Hero moveLeft() {
        return new Hero(this.position.moveBy(this.speed * -1, 0), this.speed);
    }

    // from this Hero produce another Hero which moved right a bit (5 pixels)
    public Hero moveRight() {
        return new Hero(this.position.moveBy(this.speed, 0), this.speed);
    }

    // produce the image of this Hero at this position
    public WorldImage heroImage() {
        return new FromFileImage(this.position, "v2.jpg");
                //new RectangleImage(this.position, 30, 20, Color.CYAN);
    }
    
    //move this hero as faster if a new enemy is added to the screen
    public Hero doubleIt(Enemy enm) {
        if (this.speed == 10)
           return this;
        else return new Hero(this.position, this.speed + enm.speed);
    }
}

//this class represents a list of lasers
//which can be either empty or nonempty
interface ILoL {

    // checks if this list of lasers has a new laser
    // added to the scene or not, in correspondence
    // to a given string - if so it adds a new laser
    // to this list of lasers at the position of the
    // given hero
    public ILoL onKeyEvent(String ke, Hero bop);

    // did any of the lasers in this list of lasers collide
    // with the given enemy?
    public boolean collide(Enemy enm);
    
    // did any laser in this list of lasers collide with
    // the given list of enemies
    public boolean collideAll(Ie enmz);

    // remove lasers that collided with the given enemy
    // in this list of lasers
    public ILoL removeCollidedLaser(Enemy enm);
    
    // remove all lasers in this list that collide with
    // enemies in the given list
    public ILoL removeAllCollidedLasers(Ie enmz);
    
    // remove lasers that are out of bounds
    // in this list of lasers
    public ILoL removeOutOfBoundLasers();

    // moves every laser in this list of lasers   the screen
    // by the speed of the given enemy
    public ILoL moveLasers();

    // produce the image of this list of lasers
    public WorldImage lasersImage();
    
    //is this empty
    public boolean emptyLoL();
    
    //gimme the first
    public Laser lazify();
    
    //gimme the rest
    public ILoL iRest();
    
    //did this list of lasers hit the prize
    public boolean collidePrize(Prize goodies);
    
    //remove an instance of the laser prize collision
    public ILoL removeLP(Prize goodies);
    
    //remove lasers that hit a Prize
    public ILoL removeLaserPrize(Prize goodies);

    //did this list of lasers hit the boss
    public boolean collideBoss(Boss boss);
    
    //remove an instance of the laser boss collision
    public ILoL removeLB(Boss boss);
    
    //remove lasers that hit a boss
    public ILoL removeLaserBoss(Boss boss);
}

//this class represents an empty list of lasers
class MtLoL implements ILoL {
    MtLoL() {
    }
    
    /*TEMPLATE:
     * FIELDS:
     * There are no fields for this class
     * 
     * METHODS:
     * ...this.onKeyEvent(String, Hero)...           -- ILoL
     * ...this.collide(Enemy)...                     -- boolean
     * ...this.removeCollidedLasers(Enemy)...        -- ILoL
     * ...this.removeOutOfBoundLasers()...           -- ILoL
     * ...this.moveLasers(Enemy)...                  -- ILoL
     * ...this.lasersImage()...                      -- WorldImage
     * 
     * METHODS FOR FIELDS:
     * There are no fields, therefore there are no methods
     * for fields
     */

    // CONSTANTS:
    // Color spaceblue = new Color(50, 150, 255);
    //int width = 500;
    //int height = 500;
    
    WorldImage background = new FromFileImage(
            new Posn(250, 250), "space2.jpg");
            
            //new RectangleImage(new Posn(width / 2, height / 2),
            //width, height, spaceblue);

    // add one laser into this empty list of lasers
    // when the given string is " " at the position of
    // the given hero
    public ILoL onKeyEvent(String ke, Hero bop) {
        if (ke.equals(" "))
            return new ConsLoL(new Laser(5, new CartPt(bop.position.x, bop.position.y - 10)), this);
        else
            return this;
    }

    // did any of the lasers in this empty list of lasers
    // collide with the given enemy?
    public boolean collide(Enemy enm) {
        return false;
    }
    
    // did any of the lasers in this empty list of lasers
    // collide with the given list of enemies?
    public boolean collideAll(Ie enmz) {
        return false;
    }

    // remove lasers that collide with the given enemy
    // from this empty list of lasers
    public ILoL removeCollidedLaser(Enemy enm) {
        return this;
    }
    
    //remove all lasers that collide with the given 
    //list of enemies
    public ILoL removeAllCollidedLasers(Ie enmz) {
        return this;
    }

    // remove out of bound lasers from this empty list of lasers
    public ILoL removeOutOfBoundLasers() {
        return this;
    }

    // moves this empty list of lasers   the screen
    // by the speed of the given enemy
    public ILoL moveLasers() {
        return this;
    }

    // produce the image of this empty list of lasers
    // of lasers
    public WorldImage lasersImage() {
        return this.background;
    }
    
    //is this empty
    public boolean emptyLoL() {
        return true;
    }
    
    //gimme the field
    public Laser lazify() {
        throw new RuntimeException("the empty doesn't have a first");
    }
    
    //gimme the rest
    public ILoL iRest() {
        throw new RuntimeException("the empty doesn't have a rest");
    }
    
    // did any of the lasers in this empty list of lasers
    // collide with the given Prize?
    public boolean collidePrize(Prize goodies) {
        return false;
    }
    
    //remove an instance of a laser hitting the prize
    public ILoL removeLP(Prize goodies) {
        return this;
    }

    // remove lasers that collide with the given Prize
    // from this empty list of lasers
    public ILoL removeLaserPrize(Prize goodies) {
        return this;
    }
    
    //did this empty list of lasers hit the boss
    public boolean collideBoss(Boss boss) {
        return false;
    }
    
    //remove the laser that hit the boss
    public ILoL removeLB(Boss boss) {
        return this;
    }
    
    //remove lasers that hit a boss
    public ILoL removeLaserBoss(Boss boss) {
        return this;
    }
}

//this class represents a non empty list of lasers
class ConsLoL implements ILoL {
    Laser first;
    ILoL rest;

    ConsLoL(Laser first, ILoL rest) {
        this.first = first;
        this.rest = rest;
    }
    
    /*TEMPLATE:
     * FIELDS:
     * ...this.first...                              -- Laser
     * ...this.rest...                               -- ILoL
     * 
     * METHODS:
     * ...this.onKeyEvent(String, Hero)...           -- ILoL
     * ...this.collide(Enemy)...                     -- boolean
     * ...this.removeCollidedLasers(Enemy)...        -- ILoL
     * ...this.removeOutOfBoundLasers()...           -- ILoL
     * ...this.moveLasers(Enemy)...                  -- ILoL
     * ...this.lasersImage()...                      -- WorldImage
     * 
     * METHODS FOR FIELDS:
     * ...this.first.collideV(Enemy)...                    -- boolean
     * ...this.first.outOfBoundsp()...                     -- boolean
     * ...this.first.moveLaser(Enemy)...                   -- Laser
     * ...this.first.laserImage()...                       -- WorldImage
     * 
     * ...this.rest.onKeyEvent(String, Hero)...           -- ILoL
     * ...this.rest.collide(Enemy)...                     -- boolean
     * ...this.rest.removeCollidedLasers(Enemy)...        -- ILoL
     * ...this.rest.removeOutOfBoundLasers()...           -- ILoL
     * ...this.rest.moveLasers(Enemy)...                  -- ILoL
     * ...this.rest.lasersImage()...                      -- WorldImage
     */

    // add one laser into this non empty list of lasers
    // when the given string is " " at the position of the
    // given hero
    public ILoL onKeyEvent(String ke, Hero bop) {
        if (ke.equals(" "))
            return new ConsLoL(new Laser(5, new CartPt(bop.position.x, bop.position.y - 10)), this);
        else
            return this;
    }

    // did any of the lasers in this non empty list of lasers
    // collide with the given enemy?
    public boolean collide(Enemy enm) {
        return this.first.collideV(enm) || this.rest.collide(enm);
    }
    
    //did any of the lasers in this non empty list of lasers
    //collide with the given list of enemies?
    public boolean collideAll(Ie enmz) {
        if (enmz.emptyp())
            return false;
        else return this.collide(enmz.enemify()) ||  //you can reverse and check if the first
                this.rest.collideAll(enmz.needRest()); //laser collides with the enemy list
    }                                              //this does not require bs helpers
                                                   //enemify and needRest, same for the Ie class
    
    // remove lasers that collide with the given enemy
    // from this non empty list of lasers
    public ILoL removeCollidedLaser(Enemy enm) {
        if (this.first.collideV(enm))
            return this.rest;
        else
            return new ConsLoL(this.first, this.rest.removeCollidedLaser(enm));
    }
    
    //remove all lasers that collide with the given enemy
    //list
    public ILoL removeAllCollidedLasers(Ie enmz) {
        if (enmz.emptyp())
            return this;
        else if (this.collide(enmz.enemify()))
                return this.removeCollidedLaser(enmz.enemify()).removeAllCollidedLasers(enmz.needRest());
        else return this.removeAllCollidedLasers(enmz.needRest());
    }

    // remove out of bound lasers from this non empty list of lasers
    public ILoL removeOutOfBoundLasers() {
        if (this.first.outOfBoundsp())
            return this.rest.removeOutOfBoundLasers();
        else
            return new ConsLoL(this.first, this.rest.removeOutOfBoundLasers());
    }

    // move this non empty list of lasers   the screen
    // by the speed of the given enemy
    public ILoL moveLasers() {
        return new ConsLoL(this.first.moveLaser(), 
                this.rest.moveLasers());
    }

    // produce the image of this non empty list of lasers
    public WorldImage lasersImage() {
        return this.rest.lasersImage().overlayImages(this.first.laserImage());
    }
    
    //is this empty
    public boolean emptyLoL() {
        return false;
    }
    
    //gimme the first
    public Laser lazify() {
        return this.first;
    }
    
    //gimme the rest
    public ILoL iRest() {
        return this.rest;
    }
    
    // did any of the lasers in this list of lasers
    // collide with the given Prize?
    public boolean collidePrize(Prize goodies) {
        if (goodies.noPrize())
            return false;
        else if (this.first.position.collidePos(goodies.prizify()))
            return true;
        else return this.rest.collidePrize(goodies); //make the method that checks which prize it is
    }                                                //seperate from the checking of collision
    
    //remove an instance of a laser prize collision
    public ILoL removeLP(Prize goodies) {
        if (this.first.position.collidePos(goodies.prizify()))
            return this.rest;
        else return new ConsLoL(this.first, this.rest.removeLP(goodies));
    }

    // remove lasers that collide with the given Prize
    // from this list of lasers
    public ILoL removeLaserPrize(Prize goodies) {
        if (this.collidePrize(goodies))
            return this.removeLP(goodies);
        else return this;
    }
    
 // did any of the lasers in this list of lasers
    // collide with the given boss?
    public boolean collideBoss(Boss boss) {
        if (boss.noBoss())
            return false;
        else if (this.first.position.collidePos(boss.bossify()))
            return true;
        else return this.rest.collideBoss(boss); //make the method that checks which prize it is
    }                                                //seperate from the checking of collision
    
    //remove an instance of a laser Boss collision
    public ILoL removeLB(Boss boss) {
        if (this.first.position.collidePos(boss.bossify()))
            return this.rest;
        else return new ConsLoL(this.first, this.rest.removeLB(boss));
    }

    // remove lasers that collide with the given Boss
    // from this list of lasers
    public ILoL removeLaserBoss(Boss boss) {
        if (this.collideBoss(boss))
            return this.removeLB(boss);
        else return this;
    }
}

//This class represents a laser
class Laser {
    int size;
    CartPt position;

    Laser(int size, CartPt position) {
        this.size = size;
        this.position = position;
    }
    
    /* TEMPLATE:
     * FIELDS:
     * ...this.size...                               -- int
     * ...this.position...                           -- CartPt
     * 
     * METHODS:
     * ...this.collideV(Enemy)...                    -- boolean
     * ...this.outOfBoundsp()...                     -- boolean
     * ...this.moveLaser(Enemy)...                   -- Laser
     * ...this.laserImage()...                       -- WorldImage
     * 
     * METHODS FOR FIELDS:
     * ...this.position.randV()...                   -- CartPt
     * ...this.position.moveBy(int, int)...          -- CartPt
     * ...this.position.disTo(CartPt)...             -- double
     * ...this.position.collidePos(CartPt)...        -- boolean
     * ...this.position.outOfBoundsp()...            -- boolean
     * ...this.position.movePointUp(Enemy)...        -- CartPt
     */

    // did this laser collide with the given enemy?
    public boolean collideV(Enemy enm) {
        return this.position.collidePos(enm.position);
    }

    // is this laser out of bounds?
    public boolean outOfBoundsp() {
        return this.position.outOfBoundsp();
    }

    // move this laser   the screen
    // by the speed of the given enemy
    public Laser moveLaser() {
        return new Laser(this.size, this.position.movePointUp());
    }

    // produce the image of this laser
    public WorldImage laserImage() {
        return new FromFileImage(this.position, "fireball.png");
                //new RectangleImage(this.position, this.size, 2 * this.size,
                //Color.RED);
    }          //power up ideas:
}              //make different weapons: 
               // fireball which is only removed when offscreen can kill
               // many vaders //make a limit of 20 fireballs, and a fireball pack
               // which adds 10 fireballs
               // also add another power up which happens mod 500 which gives you
               // a new final flash


//This class represents a set of cartesian points
//primarily used to denote the location of an image
//and how we can manipulate it
class CartPt extends Posn {
    CartPt(int x, int y) {
        super(x, y);
    }
    
   /* TEMPLATE:
    * FIELDS:
    * ...this.x...                                   -- int
    * ...this.y...                                   -- int
    * 
    * METHODS:
    * ...this.randV()...                             -- CartPt
    * ...this.moveBy(int, int)...                    -- CartPt
    * ...this.disTo(CartPt)...                       -- double
    * ...this.collidePos(CartPt)...                  -- boolean
    * ...this.outOfBoundsp()...                      -- boolean
    * ...this.movePointUp(Enemy)...                  -- CartPt
    * 
    * METHODS FOR FIELDS:
    * The methods for fields are built-in (Integer class)
    */

    //constant speed for a laser
    int speed = 10;
    
    // a random number generator
    public Random rand = new Random();

    // produce a random cartesian point from this cartesian point
    // within the boundaries of the scene
    public CartPt randV() {
        return new CartPt(30 + rand.nextInt(440), 0);
    }

    // produce a cartesian point move by the given distances, x and y,
    // from this cartesian point
    public CartPt moveBy(int dx, int dy) {
        return new CartPt(this.x + dx, this.y + dy);
    }

    // Compute the distance from this cartesian point to the given one
    public double disTo(CartPt that) {
        return Math.sqrt((this.x - that.x) * (this.x - that.x)
                + (this.y - that.y) * (this.y - that.y));
    }

    // did this cartesian point collide with the given one?
    public boolean collidePos(CartPt enmpt) {
        return this.disTo(enmpt) < 40; 
    }                                

    // is this cartesian point out of bounds?
    public boolean outOfBoundsp() {
        return this.y < 1;
    }

    // move this cartesian point   the screen
    // by the speed of the given enemy
    public CartPt movePointUp() {
        return new CartPt(this.x, this.y - speed);
    }
}