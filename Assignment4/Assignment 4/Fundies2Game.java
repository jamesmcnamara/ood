import tester.*;

import javalib.worldimages.*;
import javalib.colors.*;
import javalib.funworld.*;

import java.awt.Color;
import java.util.*;

///////////////////////////////////////////////////////////////////////////////////////
//////////////////////////Captains Log/////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
/*
 * We spent just about every free second this week working on this project. In between
 * all the midterms, interviews, and projects, we spent every spare second in WVH. It's
 * been one heck of a ride.
 * Pair 008
 */


///////////////////////////////////////////////////////////////////////////////////////
//////////////////////////Problem one//////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////

interface IBook{}

abstract class aBook implements IBook{
    String title;
    int dayTaken;
    
    //constructor
    aBook(String title, int dayTaken){
        this.title=title;
        this.dayTaken=dayTaken;
    }
    
  //computes the fine for the typical book
    int daysOverdue(int today){
        return today-(dayTaken+14);
    }
    
    //determines whether this book is overdue
    boolean isOverdue(int today){
        return (this.daysOverdue(today)>0);
    }
    
    //computes how much you owe on this book
    int computeFine(int today){
        if (this.isOverdue(today))
            return this.daysOverdue(today)*10;
        else return 0;
    }
}

class Book extends aBook{
    String author;
    
    //constructor
    Book(String title, int dayTaken, String author){
        super(title,dayTaken);
        this.author=author;
    }
    /*
     * template
     * ...this...             Book
     * ...this.title...       String
     * ...this.dayTaken...    int
     * ...this.author...      String
     * ...this.isOverdue...   boolean
     * ...this.daysOverdue... int
     * ...this.computeFine... int
     */
}

class RefBook extends aBook{

    
    //constructor
    RefBook(String title, int dayTaken){
        super(title,dayTaken);
    }
    /*
     * template
     * ...this...             RefBook
     * ...this.title...       String
     * ...this.dayTaken...    int
     * ...this.isOverdue...   boolean
     * ...this.daysOverdue... int
     * ...this.computeFine... int
     */
    
  //computes the fine for the typical book
    int daysOverdue(int today){
        return today-(dayTaken+2);
    }
}

class AudioBook extends aBook{
    String author;
    
    //constructor
    AudioBook(String title, int dayTaken, String author){
        super(title,dayTaken);
        this.author=author;
    }
    
    /*
     * template
     * ...this...             RefBook
     * ...this.title...       String
     * ...this.dayTaken...    int
     * ...this.author...      String
     * ...this.isOverdue...   boolean
     * ...this.daysOverdue... int
     * ...this.computeFine... int
     */
  //computes how much you owe on this book
    int computeFine(int today){
        if (this.isOverdue(today))
            return this.daysOverdue(today)*20;
        else return 0;
    }
}


class exampleBooks{
	Book fal= new Book("Fear and Loathing in Las Vegas", 4000, "Hunter Thompson");
	Book ekaat= new Book("The Electric Kool-Aid Acid Test", 4014, "Tom Wolfe");
	RefBook tbs=new RefBook("The Big Short", 4002);
	AudioBook bnw=new AudioBook("Brave New World", 4010, "Aldous Huxley");
	
	
	boolean testDaysOverdue(Tester t){
		return t.checkExpect(fal.daysOverdue(4014), 0)&&
				t.checkExpect(ekaat.daysOverdue(4014), -14)&&
				t.checkExpect(tbs.daysOverdue(4014), 10)&&
				t.checkExpect(bnw.daysOverdue(4020), -4);
	}
	
	boolean testIsOverdue(Tester t){
		return t.checkExpect(fal.isOverdue(4014), false)&&
				t.checkExpect(ekaat.isOverdue(4030), true)&&
				t.checkExpect(tbs.isOverdue(4015), true)&&
				t.checkExpect(tbs.isOverdue(4003), false)&&
				t.checkExpect(bnw.isOverdue(4015), false);
	}
	
	boolean testComputeFine(Tester t){
		return t.checkExpect(fal.computeFine(4014), 0)&&
				t.checkExpect(ekaat.computeFine(4030), 20)&&
				t.checkExpect(tbs.computeFine(4015), 110)&&
				t.checkExpect(tbs.computeFine(4003), 0)&&
				t.checkExpect(bnw.computeFine(4025), 20);
	}
}
///////////////////////////////////////////////////////////////////////////////////////
//////////////////////////Space Invaders Game /////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////

//represents an object
interface IObject{
    //produces a WorldImage of this object
    WorldImage makeImage();    
}
//represents an abstract object
abstract class AObject implements IObject{
    Posn center;
    
    AObject(Posn center){
        this.center=center;
    }
    
  //to check if the given object is hitting this object
    boolean nearby (AObject that){
        return (((this.center.x-that.center.x)<15)&& ((this.center.x-that.center.x))>-15) &&
               (((this.center.y-that.center.y)<15)&& ((this.center.y-that.center.y))>-15);
        
    }
}
//represeents the player
class Ship extends AObject{

int score;
	
	/** The constructor */
	Ship(Posn center, int score) {
		super(center);
		this.score=score;
	}
	
	/*
	 *...this...              ship
	 *...this.center...       posn
	 *...this.center.x...     int
	 *...this.center.y...     int
	 *...this.score...        int
	 *...this.move...         ship
	 *...this.fire(ILoB a)... ILoB
	 *
	 */
	//produces the image of the player
	public WorldImage makeImage() {
		return new FromFileImage(this.center, "tank.png");
	}

	

	/** move this Ship 20 pixels in the direction given by the ke */
	public Ship moveShip(String ke, int w){
		if ((ke.equals("right"))&&(this.center.x<w)){
			return new Ship(new Posn(this.center.x + 20, this.center.y),this.score);
		}
		else if ((ke.equals("left"))&&(this.center.x>0)){
			return new Ship(new Posn(this.center.x - 20, this.center.y), this.score);
		}
		else
			return this;
	}
	
	//makes the ship retaliate against the evil invaders
	public ILoB fire (ILoB a){
	    return new ConsLoB(new Bullet(
                new Posn(this.center.x, this.center.y - 60), false),
                a);
	}

}
//represents a space invader
class Alien extends AObject{
	String dir;
	int moves;

	//constructor
	Alien(Posn center, String dir, int moves){
		super(center);
		this.dir = dir;
		this.moves=moves;
	}
	/*
	 * template
	 *...this...             alien
	 *...this.center...      posn
	 *...this.center.x...    int
	 *...this.center.y...    int
	 *...this.dir...         String
	 *...this.moves...       int
	 *...this.alienMove...   alien
	 *...this.fire(ILoB b)...ILoB
	 *
	 */
	
	//creates an image of this alien
	public WorldImage makeImage() {
		return new FromFileImage(this.center, "spaceinvader.png");
	}


	//move this alien over and down the screen
	Alien alienMove(){
	        if (this.moves==0)
	            return new Alien(new Posn(this.center.x, this.center.y+10), "right", this.moves+1);
	        if (this.moves==50)
	            return new Alien(new Posn(this.center.x, this.center.y+10),"left",this.moves-1);
	        if (this.dir=="right")
	            return new Alien(new Posn(this.center.x+5,this.center.y), this.dir,this.moves+1);
	        else return new Alien(new Posn(this.center.x-5,this.center.y),this.dir,this.moves-1);
	    }
	
	//produces invading fire from this alien 3% of the time
	public ILoB fire (ILoB b){
	    int rand=new Random().nextInt(100);
	    if(rand < 2)
	        return new ConsLoB(new Bullet(new Posn(this.center.x, this.center.y+15), true),b);
	    else return b;
	}
}

//represents the firefight
class Bullet extends AObject{
	
    boolean downwards;
	
    //constructor
	Bullet(Posn center, boolean downwards){
	    super(center);
	    this.downwards=downwards;
	}
	/*template
	 * ...this...           Bullet
	 *...this.center...     Posn
	 *...this.center.x...   int
	 *...this.center.y...   int
	 *...this.downwards...  boolean
	 *...this.bulletMove... Bullet
	 * 
	 */
	
	//creates the image of this bullet
	public WorldImage makeImage() {
		return new LineImage(new Posn(this.center.x,this.center.y-2),new Posn(this.center.x, this.center.y+2), new White());
	}

	//moves this bullet on tick 
	Bullet bulletMove(){
	    if (this.downwards)
		return new Bullet(new Posn(this.center.x, this.center.y + 5),this.downwards);
	    else return new Bullet(new Posn(this.center.x, this.center.y - 5), this.downwards);
	}
}

interface ILoB{
    //produces the image of all these bullet
	WorldImage makeImage();
	
	//moves this list of bullets
	ILoB lobMove();
	
	//determines whether any of these shots have destroyed the given alien
	boolean hasDestroyed(Alien a);
	
	//removes all  of these bullets which are off screen
	ILoB remove();
	
	//checks to see if the given ship has been hit by these shots
    boolean hit(Ship s);
	
}

class MtLoB implements ILoB{
    
    //creates an ignored image for empty lists
	public WorldImage makeImage(){
		return new LineImage(new Posn(0, 0), new Posn(0,0), new Black());
	}
	
	//moves this list of bullets
	public ILoB lobMove(){
		return this;
	}
	
	//determines whether any of these shots have destroyed the given alien
	public boolean hasDestroyed(Alien a){
	    return false;
	}
	
	//removes all bullets in this list which are off screen
	public ILoB remove(){
	    return this;
	}
	
    //determines whether any of these shots have destroyed the given ship
    public boolean hit(Ship s){
        return false;
}
}

class ConsLoB implements ILoB{
	Bullet first;
	ILoB rest;
	
	//constructor
	ConsLoB(Bullet first, ILoB rest){
		this.first = first;
		this.rest = rest;
	}
	/*
	 *template
	 *...this...                            ILoB
	 *...this.first...                      Bullet
	 *...this.rest...                       ILoB
	 *...this.lobMove...                    ILoB
	 *...this.hasDestroyed(Alien a)...      boolean
	 *...this.remove...                     ILoB
	 *...this.hit(Ship)...                  boolean
	 *...this.first.center...               Posn
	 *...this.first.center.x...             int
	 *...this.first.center.y...             int
	 *...this.first.downwards...            boolean
	 *...this.first.bulletMove...           Bullet
	 *...this.rest.lobMove...               ILoB
	 *...this.rest.hasDestroyed(Alien a)... boolean
	 *...this.rest.remove...                ILoB
	 *...this.rest.hit(Ship)...             boolean
	 */

	//produce an image containing all bullets in the list
	public WorldImage makeImage(){
		return new OverlayImages(this.first.makeImage(),
				this.rest.makeImage());
	}

	//move all the bullets in the list
	public ILoB lobMove(){
		return new ConsLoB(this.first.bulletMove(), this.rest.lobMove());
	}
	
	 //determines whether any of these shots have destroyed the given alien
	public boolean hasDestroyed(Alien a){
	    return ((this.first.nearby(a) &&
	            (!this.first.downwards))||
	            this.rest.hasDestroyed(a));
	}
	
	 //removes all bullets in this list which are off screen
	 public ILoB remove(){
	    if ((this.first.center.y>500)||
	            (this.first.center.y<0))
	        return rest.remove();
	    else return new ConsLoB(this.first, this.rest.remove());
	    
	}
	   //determines whether any of these shots have destroyed the given ship
	    public boolean hit(Ship s){
	        return ((this.first.nearby(s) &&
	                (this.first.downwards))||
	                this.rest.hit(s));
	    }
	 
}

//to represent a list of aliens
interface ILoA{
    //to produce an image of this list of aliesn      
    WorldImage makeImage();
    
    //to move this empty list of aliens
    ILoA move();
    
    //determines whether any alien in this list has been destroyed by this bullet
    boolean hasBeenDestroyedBy(Bullet b);
    
  //remove all aliens which have been hit with bullets
    ILoA removeAliens(ILoB b);
    
  //returns fire from 20% of this list of aliens
    ILoB fire(ILoB b);
    
  //count how many aliens are in this list
    int howMany();
    
  //determines if the aliens have landed
    boolean landed();
}

//to represent an empty list of aliens
class MTLoA implements ILoA{
    //to move this empty list of aliens
    public ILoA move(){
        return this;
    }
    
    //to produce an image of this list of aliens
    public WorldImage makeImage(){
        return new LineImage(new Posn(0,0), new Posn(0,0),new Black());
        }
    
    //determines whether any alien in this list has been destroyed by this bullet
    public boolean hasBeenDestroyedBy(Bullet b){
        return false;
    }
    //destroys all aliens which have been hit with bullets
    public ILoA removeAliens(ILoB b){
        return this;
    }
    
    //returns fire from 3% of this list of aliens
    public ILoB fire(ILoB b){
        return b;
    }
  //count how many aliens in this list
    public int howMany(){
        return 0;
    }
    
  //determines if the aliens in this list have landed
    public boolean landed(){
    	return false;
    }
}

class ConsLoA implements ILoA{
    Alien first;
    ILoA rest;
    
    //constructor
    ConsLoA (Alien f, ILoA rest){
        this.first=f;
        this.rest=rest;
    }
    /*
     * template
     *...this...                                   ILoA
     *...this.first...                             Alien
     *...this.rest...                              ILoA
     *...this.first.center...                      posn
	 *...this.first.center.x...                    int
	 *...this.first.center.y...                    int
	 *...this.first.dir...                         String
	 *...this.first.moves...                       int
	 *...this.first.alienMove...                   alien
	 *...this.first.fire(ILoB b)...                ILoB
	 *...this.move...                              ILoA
	 *...this.hasBeenDestroyedBy(Bullet b)...      boolean
	 *...this.removeAliens(ILoB)...                ILoA
	 *...this.fire(ILoB)...                        ILoB
	 *...this.howMany...                           int
	 *...this.rest.move...                         ILoA
	 *...this.rest.hasBeenDestroyedBy(Bullet b)... boolean
	 *...this.rest.removeAliens(ILoB)...           ILoA
	 *...this.rest.fire(ILoB)...                   ILoB
	 *...this.rest.howMany...                      int
     *...this.landed...                            boolean
     *...this.rest.landed...                       boolean
     */
    //to move this empty list of aliens
    public ILoA move(){
        return new ConsLoA(this.first.alienMove(), this.rest.move());
    }
    
    //to produce the image of all the aliens in this list
    public WorldImage makeImage(){
        return new OverlayImages (this.first.makeImage(),this.rest.makeImage());
    }
    
    //determines whether any alien in this list has been destroyed by this bullet
    public boolean hasBeenDestroyedBy(Bullet b){
        return !b.downwards &&
        		(this.first.nearby(b)||
                this.rest.hasBeenDestroyedBy(b));
    }
    
    //destroys all aliens which have been hit with bullets
    public ILoA removeAliens(ILoB b){
        if (b.hasDestroyed(this.first))
            return rest.removeAliens(b);
        else return new ConsLoA(this.first, this.rest.removeAliens(b));
    }
    
  //returns fire from 20% of this list of aliens
    public ILoB fire(ILoB b){
        return rest.fire(this.first.fire(b));
    }
    
  //counts how many aliens in this list
    public int howMany(){
        return 1+rest.howMany();
    }
    
  //determines if any of the aliens in this list have landed
    public boolean landed(){
    	return ((this.first.center.y>500)||
    			(this.rest.landed()));
    }
    
}

class MyGame extends World {

	int w=500;
	int h=500;
	Ship ship;
	ILoA aliens;
	ILoB alob;
	WorldImage background= new RectangleImage(new Posn(w/2,h/2),w,h,new Black());

	/** The constructor */
	public MyGame(Ship ship, ILoA aliens, ILoB alob) {
		super();
		this.ship = ship;
		this.aliens = aliens;
		this.alob = alob;
	}

	/*
	 * template
	 *...this...                                     MyGame
	 *...this.ship...                                ship
	 *...this.aliens...                              ILoA
	 *...this.alob...                                ILoB
	 *...this.ship.moveShip...                       ship
	 *...this.ship.fire(ILoB b)...                   ILoB
	 *...this.aliens.move...                         ILoA
	 *...this.aliens.hasBeenDestroyedBy(Bullet b)... boolean
	 *...this.aliens.removeAliens(ILoB)...           ILoA
	 *...this.aliens.howMany...                      int
	 *...this.alob.lobMove...                        ILoB
	 *...this.alob.hasDestroyed(Alien a)...          boolean
	 *...this.alob.remove...                         ILoB
	 *...this.alob.hit(Ship s)...                    boolean
	 *...this.onTick...                              MyGame
	 *...this.worldEnds...                           MyGame
	 */
	
	
	/** Move the Ship when the player presses a key */
	public World onKeyEvent(String ke) {
		if (ke.equals(" "))
			return new MyGame(this.ship.moveShip(ke, w), this.aliens,
					this.ship.fire(this.alob));
		else
			return new MyGame(this.ship.moveShip(ke, w), this.aliens, this.alob);
	}
	//creates the next world of the game after one clock tick
	public World onTick() {
	    ILoA NewAliens=aliens.move().removeAliens(alob);
	    ILoB NewBullets= aliens.fire(alob).lobMove().remove();
	    Ship NewShip= new Ship(this.ship.center,ship.score+aliens.howMany()-NewAliens.howMany());
		return new MyGame(NewShip,
				NewAliens,
				NewBullets);
		
	}
	//returns the image of this world
	public WorldImage makeImage() {
		return new OverlayImages(background, new OverlayImages(this.aliens.makeImage(), 
				new OverlayImages(this.ship.makeImage(), this.alob.makeImage())));
	}
	
	public WorldEnd worldEnds(){
	    if (this.alob.hit(ship))
            return 
            new WorldEnd(true,
                new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(250, 40),
                            "Your ship has been destroyed! You lose"+
                            " Your score was "+ ship.score, 
                                  Color.red)));
            else if (aliens.howMany()==0)
                return new WorldEnd(true,
                        new OverlayImages (this.makeImage(),
                                new TextImage(new Posn(250, 200),
                                        "You have vanquished the enemies! You Win!"
                                        +" Your score was "+ ship.score, 
                                        Color.red)));
            else if (aliens.landed())
            	return new WorldEnd(true,
            			new OverlayImages (this.makeImage(),
            					new TextImage (new Posn(250,200),
            					"The enemies have landed! you lose!. Your score was "+ ship.score,
            					Color.red)));
            else return new WorldEnd(false, this.makeImage());
	
	    
	}
	
}
  
	


class ExamplesFundies2Game{

	public static void main(String[] argv){

	    Ship s= new Ship(new Posn(250, 480),0);
	    
	    Alien alien1=new Alien(new Posn(0, 100), "right",0);
	    Alien alien2=new Alien(new Posn(50,100),"right",0);
	    Alien alien3=new Alien(new Posn(100,100), "right",0);
	    Alien alien4=new Alien(new Posn(150, 100), "right",0);
        Alien alien5=new Alien(new Posn(200,100),"right",0);
        Alien alien6=new Alien(new Posn(100,200), "right",0);
        Alien alien7=new Alien(new Posn(0, 200), "right",0);
        Alien alien8=new Alien(new Posn(50,200),"right",0);
        Alien alien9=new Alien(new Posn(150,200), "right",0);
	    
	    
	    
	    ILoA alist=new ConsLoA(alien1, new ConsLoA(alien2, new ConsLoA(alien3, 
	            new ConsLoA(alien4, new ConsLoA (alien5, new ConsLoA(alien6,
	                    new ConsLoA(alien7, new ConsLoA (alien8, new ConsLoA(
	                            alien9, new MTLoA())))))))));
	    
		// run the tests - showing only the failed test results
		ExamplesFundies2Game be = new ExamplesFundies2Game();
		Tester.runReport(be, false, false);
		 MyGame game = 
	            new MyGame(s,alist, new MtLoB());
	    game.bigBang(500, 500, .05);
}
	
	 Ship s= new Ship(new Posn(250, 480),0);
     
     Alien alien1=new Alien(new Posn(0, 100), "right",0);
     Alien alien2=new Alien(new Posn(50,100),"left",0);
     Alien alien3=new Alien(new Posn(100,100), "right",50);
     Alien alien4=new Alien(new Posn(150, 100), "right",0);
     Alien alien5=new Alien(new Posn(200,100),"right",0);
     Alien alien6=new Alien(new Posn(100,200), "right",0);
     Alien alien7=new Alien(new Posn(0, 200), "right",0);
     Alien alien8=new Alien(new Posn(50,200),"right",0);
     Alien alien9=new Alien(new Posn(150,200), "right",0);
     
     Bullet shot1=new Bullet(new Posn(100,100),true);
     Bullet shot2=new Bullet(new Posn(250,360),false);
     
     Bullet destroyer1=new Bullet(new Posn(100,100),false);
     Bullet destroyer2=new Bullet(new Posn(250, 470),true);
     
     ILoA alist=new ConsLoA(alien1, new ConsLoA(alien2, new ConsLoA(alien3, 
             new ConsLoA(alien4, new ConsLoA (alien5, new ConsLoA(alien6,
                     new ConsLoA(alien7, new ConsLoA (alien8, new ConsLoA(
                             alien9, new MTLoA())))))))));
     ILoA MTA= new MTLoA();
     
     ILoB blist=new ConsLoB(shot1, new ConsLoB(shot2, new MtLoB()));
     ILoB MT= new MtLoB();
     ILoB destroyers= new ConsLoB(destroyer1, new ConsLoB(destroyer2, new MtLoB()));
	//tests
     
    //movement
	boolean testAlienMove(Tester t){
	    return t.checkExpect(alien1.alienMove(),new Alien(new Posn(0, 110), "right",1))&&
	    t.checkExpect(alien2.alienMove(),new Alien(new Posn(50, 110), "right",1))&&
	    t.checkExpect(alien3.alienMove(),new Alien(new Posn(100, 110), "left",49))&&
	    t.checkExpect(alien4.alienMove(),new Alien(new Posn(150, 110), "right",1));
	}
	
	boolean testShipMove(Tester t){
	    return t.checkExpect(s.moveShip("left",500), new Ship (new Posn(230,480),0))&&
	    		t.checkExpect(s.moveShip("right",500), new Ship (new Posn(270,480),0))&&
	    		t.checkExpect(s.moveShip("x",500), new Ship (new Posn(250,480),0))&&
	    		t.checkExpect(s.moveShip("right",250), s);
	}
	
	boolean testBulletMove(Tester t){
		return t.checkExpect(shot1.bulletMove(), new Bullet( new Posn(100, 105), true))&&
				t.checkExpect(shot2.bulletMove(), new Bullet( new Posn(250, 355), false));
	}
	
	boolean testLoBMove(Tester t){
		return t.checkExpect(blist.lobMove(), new ConsLoB(new Bullet(new Posn(100, 105), true),
				new ConsLoB(new Bullet( new Posn(250, 355), false), MT)))&&
				t.checkExpect(MT.lobMove(), MT);
	}
	boolean testILoAMove(Tester t){
	    return t.checkExpect(alist.move(), new ConsLoA(new Alien(new Posn(0,110),"right",1), 
	    								   new ConsLoA(new Alien(new Posn(50,110), "right", 1),
	    								   new ConsLoA(new Alien(new Posn(100,110), "left", 49),
	    								   new ConsLoA(new Alien(new Posn(150,110),"right", 1), 
	    								   new ConsLoA(new Alien(new Posn(200,110),"right",1),
	    								   new ConsLoA(new Alien(new Posn(100,210), "right", 1),
	    								   new ConsLoA(new Alien(new Posn(0,210), "right",1),
	    								   new ConsLoA(new Alien(new Posn(50,210),"right",1),
	    								   new ConsLoA(new Alien(new Posn(150,210),"right", 1),
	    										   new MTLoA()))))))))))&&
	    		t.checkExpect(MTA.move(), MTA);
	    		                  
	}
	
	//end movement

	boolean testShipFire(Tester t){
		return t.checkExpect(s.fire(new MtLoB()), new ConsLoB(new Bullet (new Posn(250, 420), false), new MtLoB()))&&
				t.checkExpect(s.fire(blist),new ConsLoB(new Bullet(new Posn(250,420), false), blist));
	}

	boolean testHasDestroyedAlien(Tester t){
		return t.checkExpect(blist.hasDestroyed(alien3), false)&&
				t.checkExpect(destroyers.hasDestroyed(alien3), true)&&
				t.checkExpect(MT.hasDestroyed(alien3), false);
	}
	
	boolean testRemove(Tester t){
		ILoB aBList=new ConsLoB(new Bullet(new Posn(200,580),true), new ConsLoB(new Bullet (new Posn(200, -40), false), blist));
		return t.checkExpect(aBList.remove(),blist)&&
				t.checkExpect(blist.remove(),blist);
	}
	
	boolean testDestroyShip(Tester t){
		return t.checkExpect(destroyers.hit(s), true)&&
				t.checkExpect(blist.hit(s), false)&&
				t.checkExpect(MT.hit(s), false);
	}
	
	boolean testHasBeenDestroyedBy(Tester t){
		return t.checkExpect(alist.hasBeenDestroyedBy(destroyer1), true)&&
				t.checkExpect(alist.hasBeenDestroyedBy(shot1), false)&&
				t.checkExpect(MTA.hasBeenDestroyedBy(destroyer1), false);
	}
	
	boolean testRemoveAliens(Tester t){
		return t.checkExpect(alist.removeAliens(blist), alist)&&
				t.checkExpect(MTA.removeAliens(blist), MTA)&&
				t.checkExpect(alist.removeAliens(destroyers), new ConsLoA(alien1, new ConsLoA(alien2, 
			             									  new ConsLoA(alien4, new ConsLoA (alien5,
			             									  new ConsLoA(alien6, new ConsLoA(alien7, 
			             									  new ConsLoA (alien8, new ConsLoA(alien9, new MTLoA())))))))));
	}
	
	boolean testHowMany(Tester t){
		return t.checkExpect(alist.howMany(),9)&&
				t.checkExpect(MTA.howMany(),0)&&
				t.checkExpect(alist.removeAliens(destroyers).howMany(),8);
	}
	
	
	
}

