package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.*;

public class App extends PApplet {

    /**
     * Width of window
     */
    public static final int WIDTH = 720;
    /**
     * Height of window
     */
    public static final int HEIGHT = 720;
    /**
     * Number of frames per sec
     */
    public static final int FPS = 60;

    /**
     * Path of config file
     */
    public String configPath;
    
    /**
     * Images of brickwall
     */
    public PImage brickwall, brickwall_destroy_0, brickwall_destroy_1, brickwall_destroy_2, brickwall_destroy_3;
    /**
     * Image of stonewall
     */
    public PImage stonewall;
    /**
     * Image of exit
     */
    public PImage portal;
    /**
     * Image of fireball
     */
    public PImage fireball_image;
    /**
     * Images of wizard facing different direction
     */
    public PImage wizard_left ,wizard_right, wizard_up, wizard_down;
    /**
     * Images enemies, and it attack
     */
    public PImage gremlin, slime, redgremlin, redslime;
    /**
     * Image of progress bar and its background
     */
    public PImage progress_bar_bg, progress_bar;
    /**
     * Image of shield, and its effect on wizard
     */
    public PImage shield, shield_effect;
    
    /**
     *Player
     */
    public Wizard wizard;

    /**
     * Game map
     */
    public GameMap map;

    /**
     * Figures of lives
     */
    public int current_lives, total_lives;
    /**
     * Figures of level
     */
    public int current_level, total_level;
    /**
     * The counter starts to work after player wins or loses in case game restart immediately
     */
    public int restart_countdown = 0;
    /**
     * Cooldown of character
     */
    public double wizard_cooldown, enemy_cooldown;
    /**
     * Win and lose status. they are both false by default
     */
    public boolean win = false, lose = false;
    /**
     * Key board pressed status. it is false by default
     */
    public boolean keypressed = false;

    /**
     * List of storing the name of different map files with different level
     */
    public List<String> layout = new ArrayList<String>();
    /**
     * Wizard cooldown of different level
     */
    public List<Double> wizard_cooldowns = new ArrayList<Double>();
    /**
     * Enemy cooldown of different level
     */
    public List<Double> enemy_cooldowns = new ArrayList<Double>();
    /**
     * Fireballs discharged by wizard
     */
    public List<Fireball> fireballs = new ArrayList<Fireball>();
    /**
     * Gremlins read from map
     */
    public List<Gremlin> gremlins = new ArrayList<Gremlin>();
    /**
     * Slimes discharged by gremlin
     */
    public List<Slime> slimes = new ArrayList<Slime>();
    /**
     * Red gremlins read from map
     */
    public List<RedGremlin> redgremlins = new ArrayList<RedGremlin>();
    /**
     * Red slimes discharged by red gremlin
     */
    public List<RedSlime> redslimes = new ArrayList<RedSlime>();

    /**
     * Constructor of App
     */
    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup(){
        frameRate(FPS);

        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.brickwall_destroy_0 = loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        this.brickwall_destroy_1 = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        this.brickwall_destroy_2 = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        this.brickwall_destroy_3 = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
        
        this.portal = loadImage(this.getClass().getResource("portal.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.redgremlin = loadImage(this.getClass().getResource("red_gremlin.png").getPath().replace("%20", " "));
        this.redslime = loadImage(this.getClass().getResource("red_slime.png").getPath().replace("%20", " "));
        this.fireball_image = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        this.wizard_left = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.wizard_right = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.wizard_up = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.wizard_down = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.progress_bar_bg = loadImage(this.getClass().getResource("progress_bar_bg.png").getPath().replace("%20", " "));
        this.progress_bar = loadImage(this.getClass().getResource("progress_bar.png").getPath().replace("%20", " "));
        this.shield = loadImage(this.getClass().getResource("shield.png").getPath().replace("%20", " "));
        this.shield_effect = loadImage(this.getClass().getResource("shield_effect.png").getPath().replace("%20", " "));
        
        //read json file
        JSONObject conf = loadJSONObject(new File(this.configPath));
        JSONArray jarray = conf.getJSONArray("levels");
        total_level = jarray.size();
        for (int i = 0; i < jarray.size(); i++){
            JSONObject data = jarray.getJSONObject(i);
            layout.add(data.getString("layout"));
            wizard_cooldowns.add(data.getDouble("wizard_cooldown"));
            enemy_cooldowns.add(data.getDouble("enemy_cooldown"));
        }
        total_lives = conf.getInt("lives");
        current_lives = total_lives;

        //load map
        current_level = 1;
        String map_doc = layout.get(current_level - 1);
        this.map = new GameMap(map_doc);

        map.ValidMapCheck(layout);
        if (map.error){System.exit(0);}
        map.read(this);

        //set cooldown value
        wizard_cooldown = wizard_cooldowns.get(current_level - 1);
        enemy_cooldown = enemy_cooldowns.get(current_level - 1);
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){
        if (this.keyCode == 37) {  //Left
            this.wizard.pressLeft();
        } 
        else if (this.keyCode == 39) { //Right
            this.wizard.pressRight();
        }
        else if (this.keyCode == 38) { //Up
            this.wizard.pressUp();
        }
        else if (this.keyCode == 40) { //Down
            this.wizard.pressDown();
        }
        if (this.keyCode == 32) { // Space
            if(!this.wizard.get_cooldown_status()){
                this.wizard.attack(this);
            }
        }
        keypressed = true;
    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){
        if (this.keyCode == 37) {  //Left
            this.wizard.releaseLeft();
        } 
        if (this.keyCode == 39) { //Right
            this.wizard.releaseRight();
        }
        if (this.keyCode == 38) { //Up
            this.wizard.releaseUp();
        }
        if (this.keyCode == 40) { //Down
            this.wizard.releaseDown();
        }
        keypressed = false;
    }


    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        if (win){
            background(155, 118, 83);
            textSize(40);
            text("You win", 280, 350);
            if (restart_countdown < 60){restart_countdown += 1;}
            if(keypressed && restart_countdown == 60){ // restart st least after 1 sec
                map.restart(this);
                win = false;
                restart_countdown = 0;
            }
        }   
        else if (lose){
            background(155, 118, 83);
            textSize(40);
            text("Game over", 250, 350);
            if (restart_countdown < 60){restart_countdown += 1;} 
            if(keypressed && restart_countdown == 60){ // restart st least after 1 sec
                map.restart(this);
                lose = false;
                restart_countdown = 0;
            }
        }
        else{
            background(155, 118, 83);
            textSize(15);
            text("Lives:", 10, 690);
            for(int i = 0;i < current_lives;i++){
                this.image(this.wizard_right,55 + i*20 , 675);
            }
            text("Level " + current_level + "/" + total_level,150 ,690);
            map.generate(this);
            map.destory_animation(this);

            wizard.tick(this);
            for (int i = 0; i < fireballs.size(); i++){
                fireballs.get(i).tick();  // update fireballs information
                fireballs.get(i).collision_check(this);  //check fireballs collision
            }
            for (int i = 0; i < slimes.size(); i++){
                slimes.get(i).tick();  // update slimes information
                slimes.get(i).collision_check(this);  //check slimes collision
            }
            for(int i = 0; i < redslimes.size(); i++){
                redslimes.get(i).tick();
                redslimes.get(i).collision_check(this);
            }
            for(Gremlin g : gremlins){g.tick(this);} //update gremlins
            for(RedGremlin r : redgremlins){r.tick(this);}
            Attack.attack_collision_check(this);
            Character.character_collision_check(this);

            wizard.draw(this);
            for (int i = 0; i < fireballs.size(); i++){fireballs.get(i).draw(this);}  //draw fireballs
            for (int i = 0; i < slimes.size(); i++) {slimes.get(i).draw(this);} // draw slimes
            for (int i = 0; i < redslimes.size(); i++) {redslimes.get(i).draw(this);}
            for(Gremlin g : gremlins){g.draw(this);} //draw gremlins
            for(RedGremlin r : redgremlins){r.draw(this);}
            }

    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
