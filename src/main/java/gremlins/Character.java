package gremlins;

import processing.core.PImage;
import gremlins.App;
import gremlins.Gremlin;
import processing.core.PApplet;

public class Character{

    /**
     * x and y coordinate
     */
    protected int x, y;

    /**
     * Counter for cooldown. it increases when cooldown status is true
     */
    protected int cooldown_count = 0;

    /**
     * Cooldown status
     */
    protected boolean cooldown = false;

    /**
     * Moving status. all of them ate false by default
     */
    protected boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false;

    /**
     * Constructor of Charactor, required x and y coordinate
     * @param x, x coordinate
     * @param y, y coordinate
     */
    public Character(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Detect which direction is not obstacled in 8 direction.
     * @param app, update data to app
     * @return the result of whether 8 direction are obstacled
     */
    public boolean[] collision(App app){ // {right, left, up, down, right_up, right_down, left_up, left_down} whether can move
        boolean[] output = {true, true, true, true, true, true, true, true};
        int i, j;
        if(x%20 == 0){
            j = (int)(x+20)/20;
            i = (int)y/20;
            if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[0] = false;} //right

            j = (int)(x-20)/20;
            if (app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[1] = false;} //left
            
            if (y%20 != 0){
                j = (int)(x+20)/20;
                i = (int)y/20 + 1;
                if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[0] = false;} //right

                j = (int)(x-20)/20;
                if (app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[1] = false;} //left
            }
        }
        if (y%20 == 0){
            j = (int)x/20;
            i = (int)(y-20)/20;
            if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[2] = false;} //up
            
            i = (int)(y+20)/20;
            if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[3] = false;} //down

            if (x%20 != 0){
                j = (int)x/20 + 1;
                i = (int)(y-20)/20;
                if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[2] = false;} //up

                i = (int)(y+20)/20;
                if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[3] = false;} //down
            }

        if(x%20 == 0 && y%20 == 0){
            j = (int)(x+20)/20;
            i = (int)(y-20)/20;
            if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[4] = false;} //right_up
            i = (int)(y+20)/20;
            if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[5] = false;} //right_down

            j = (int)(x-20)/20;
            i = (int)(y-20)/20;
            if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[6] = false;} //left_up
            i = (int)(y+20)/20;
            if(app.map.map_objects[i][j] == 'X' || app.map.map_objects[i][j] == 'B'){output[7] = false;} //left_down
        }

        }
        return output;
    }

    /**
     * @param cooldown, how long is the attack cooldown of this character
     * If the character stays in cooldown status for longer time than the specified attack cooldown, cooldown status is false
     */
    public void cooldown_check(double cooldown){
        if (this.cooldown) {
            cooldown_count += 1;
            if (cooldown_count >= (int)(cooldown*60)){  //60 is FPS
                cooldown_count = 0;
                this.cooldown = false;
            }
        }
    }

    /**
     * If the distance between wizard and the gremlins or red gremlins is less than 20 pixel, decrease the lives of wizard by 1 and the wizard respawn when wizard is not invincible
     * If the lives of wizard is less or equal to 0, then player loses
     * @param app, update data to app
     */
    public static void character_collision_check (App app){
        int w_center_X = app.wizard.getX() + 10;
        int w_center_Y = app.wizard.getY() + 10;
        for (Gremlin g : app.gremlins){
            int g_center_X = g.getX() + 10;
            int g_center_Y = g.getY() + 10;
            int distance_square = (w_center_X - g_center_X)*(w_center_X - g_center_X) + (w_center_Y - g_center_Y)*(w_center_Y - g_center_Y);
            if (distance_square < 400){
                if (!app.wizard.getInvincible()){
                    app.current_lives -= 1;
                    app.wizard.respawn(app);

                    if (app.current_lives <= 0){app.lose = true;}
                }
            }
        }

        for (RedGremlin r : app.redgremlins){
            int r_center_X = r.getX() + 10;
            int r_center_Y = r.getY() + 10;
            int distance_square = (w_center_X - r_center_X)*(w_center_X - r_center_X) + (w_center_Y - r_center_Y)*(w_center_Y - r_center_Y);
            if (distance_square < 400){
                if (!app.wizard.getInvincible()){
                    app.current_lives -= 1;
                    app.wizard.respawn(app);

                    if (app.current_lives <= 0){app.lose = true;}
                }
            }
        }
    }

    /**
     * @return current cooldown status
     */
    public boolean get_cooldown_status(){return this.cooldown;}

    /**
     * @return x coordinate
     */
    public int getX(){return x;}
    /**
     * @return y coordinate
     */
    public int getY(){return y;}

    /**
     * @param x, new x coordinate
     */
    public void setX(int x){this.x = x;}
    /**
     * @param y, new y coordinate
     */
    public void setY(int y){this.y = y;}

}