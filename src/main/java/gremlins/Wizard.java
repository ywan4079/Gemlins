package gremlins;

import processing.core.PImage;

public class Wizard extends Character{
    /**
     * Image should be drown to window
     */
    private PImage sprite;

    /**
     * Invincible status of wizard
     */
    private boolean invincible = false;

    /**
     * Invincible time start to count when wizard step on shield
     */
    public int invincible_time = 0;

    /**
     * The movement status of wizard. wizard stop in all direction by default
     */
    public boolean left_stop = true ,right_stop = true, up_stop = true, down_stop = true;

    /**
     * Wizard direction is right by default
     */
    public String direction = "right";

    /**
     * Constructor of wizard ,required x and y coordinate
     * @param x, x coordinate
     * @param y, y coordinate
     */
    public Wizard(int x, int y){
        super(x, y);
    }

    /**
     * Change wizard image
     * @param sprite, image should drown to window created by app
     */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    /**
     * Draw sprite to window at specified location
     * @param app, update data to app
     */
    public void draw(App app) {
        app.image(this.sprite, this.x, this.y);
        if (cooldown){
            app.image(app.progress_bar_bg,500, 680);
            for(int i = 1; i <= (int)(cooldown_count*100/(app.wizard_cooldown*60));i++){
                app.image(app.progress_bar, 500 + i, 681);
            }
        }
        if(invincible){
            app.image(app.shield_effect, x, y);
            app.image(app.shield, 240, 665);
            app.image(app.progress_bar_bg, 270, 675);
            for (int i =(int)(180-invincible_time)*100/180; i >= 0; i--){
                app.image(app.progress_bar, 271 + i, 676);
            }
        }
    }

    /** Update the information of wizard
     * @param app, update information to app 
     */
    public void tick(App app) {

        if(invincible){
            invincible_time += 1;
            if(invincible_time >= 3*60){ // 3*60 cooldown is 3 sec, 60 is FPS
                invincible_time = 0;
                invincible = false;
            }
        }
        cooldown_check(app.wizard_cooldown);
        nextlevel_check(app);
        invincible_check(app);

        boolean[] collides = collision(app);  // {right, left, up, down, top-right, buttom-right, top-left, buttom-left} whether can move

        if (moveRight && moveUp && !collides[4] && collides[0] && collides[2]){return;} //slove corner bug
        else if (moveRight && moveDown && !collides[5] && collides[0] && collides[3]){return;}
        else if (moveLeft && moveUp && !collides[6] && collides[1] && collides[2]){return;}
        else if (moveLeft && moveDown && !collides[7] && collides[1] && collides[3]){return;}

        else{
            if (moveLeft && collides[1]) {this.x -= 2;} 
            if (moveRight && collides[0]) {this.x += 2;}
            if (moveUp && collides[2]) {this.y -= 2;}
            if (moveDown && collides[3]) {this.y += 2;}
        }

        if (direction == "left") {setSprite(app.wizard_left);}
        else if (direction == "right") {setSprite(app.wizard_right);}
        else if (direction == "up") {setSprite(app.wizard_up);}
        else if (direction == "down") {setSprite(app.wizard_down);}

        if(left_stop && right_stop && up_stop && down_stop){
            StayInTile();
        }
    }

    /**
     * Executed when left arrow key is pressed
     * Set movement status variables and direction
     */
    public void pressLeft(){
        this.moveLeft = true;
        this.direction = "left";
        left_stop = false;
    }

    /**
     * Executed when right arrow key is pressed
     * Set movement status variables and direction
     */
    public void pressRight(){
        this.moveRight = true;
        this.direction = "right";
        right_stop = false;
    }

    /**
     * Executed when up arrow key is pressed
     * Set movement status variables and direction
     */
    public void pressUp(){
        this.moveUp = true;
        this.direction = "up";
        up_stop = false;
    }

    /**
     * Executed when down arrow key is pressed
     * Set movement status variables and direction
     */
    public void pressDown(){
        this.moveDown = true;
        this.direction = "down";
        down_stop = false;
    }

    /**
     * Executed when left arrow key is released
     * Set movement status variables
     */
    public void releaseLeft(){
        this.moveLeft = false;
        left_stop = true;
    }
 
    /**
     * Executed when right arrow key is released
     * Set movement status variables
     */
    public void releaseRight(){
        this.moveRight = false;
        right_stop = true;
    }
    
    /**
     * Executed when up arrow key is released
     * Set movement status variables
     */
    public void releaseUp(){
        this.moveUp = false;
        up_stop = true;
    }
   
    /**
     * Executed when down arrow key is released
     * Set movement status variables
     */
    public void releaseDown(){
        this.moveDown = false;
        down_stop = true;
    }


    /**
     * Create a Fireball base on the location and direction of wizard
     * Cooldown becomes true after wizard attack
     * @param app, update data to app
     */
    public void attack(App app) {
        app.fireballs.add(new Fireball(x, y, direction));
        cooldown = true;
    }


    /**
     * Let wizard stay in the center of tiles
     */
    public void StayInTile(){
        if (direction == "right"){
            if(x%20 != 0){x += 2;}
            if(y%20 != 0){y += 2;}
        }
        else if (direction == "left"){
            if(x%20 != 0){x -= 2;}
            if(y%20 != 0){y -= 2;}
        }
        else if (direction == "up"){
            if(y%20 != 0){y -= 2;}
            if(x%20 != 0){x -= 2;}
        }
        else if (direction == "down"){
            if(y%20 != 0){y += 2;}
            if(x%20 != 0){x += 2;}
        }
    }


    /**
     * If wizard step on the tile where exit locates, go to next level
     * @param app, update data to app
     */
    public void nextlevel_check(App app){
        if(x == app.map.portal_location[0] && y == app.map.portal_location[1]){
            app.map.nextlevel(app);
        }
    }

    /**
     * if wizard steps on the tile where shield locates and shield is availible, wizard becomes invincible for a while
     * @param app, update data to app
     */
    public void invincible_check(App app){
        boolean used = false;
        if(x == app.map.shield_location[0] && y == app.map.shield_location[1]){
            if(app.map.powerUp_available){
                invincible = true;
                used = true;
            }
        }
        if(used){
            app.map.powerUp_available = false;
            app.map.powerUp_count = 0;
        }
    }

    /**
     * wizard, gremlins, and red gremlins go to their original location and reset every figures in game
     * @param app, update data to app
     */
    public void respawn(App app){
        x = app.map.wizard_location[0];
        y = app.map.wizard_location[1];
        for(int i = 0;i < app.gremlins.size();i++){
            app.gremlins.get(i).setX(app.map.gremlin_location.get(i)[0]);
            app.gremlins.get(i).setY(app.map.gremlin_location.get(i)[1]);
        }
        for(int i = 0; i < app.redgremlins.size(); i++){
            app.redgremlins.get(i).setX(app.map.redgremlin_location.get(i)[0]);
            app.redgremlins.get(i).setX(app.map.redgremlin_location.get(i)[0]);
        }
    }

    /**
     * @param status, boolean status set to invincible status
     */
    public void setInvincible(boolean status){
        this.invincible = status;
    }

    /**
     * @return invincible status
     */
    public boolean getInvincible(){return invincible;}

    /**
     * @return current sprite
     */
    public PImage getSprite(){return this.sprite;}
}