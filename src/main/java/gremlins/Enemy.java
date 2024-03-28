package gremlins; 

import java.util.Random;

public class Enemy extends Character{
    /**
     * Random number generator
     */
    protected Random random = new Random();

    /**
     * The direction of enemy
     */
    protected String direction;
    /**
     * The speed of enemy
     */
    protected double speed;

    /**
     * Constructor of enemy, required x and y coordinate
     * @param x, x coordinate
     * @param y, y coordinate
     */
    public Enemy(int x, int y){
        super(x, y);
        int d = random.nextInt(4);
        if (d == 0){ //right = 0, left = 1, up = 2, down = 3
            moveRight = true;
            direction = "right";
        }
        else if (d == 1){
            moveLeft = true;
            direction = "left";
        }
        else if (d == 2){
            moveUp = true;
            direction = "up";
        }
        else if (d == 3){
            moveDown = true;
            direction = "down";
        }
    }

    /**
     * Respawn enemy at random location but at least 10 tiles radius away from player, and not on brickwall or stonewall
     * @param app, update data to app
     */
    public void respawn(App app){
        int new_j = random.nextInt(35);  //x coord
        int new_i = random.nextInt(32); // y coord
        int g_X = new_j*20;
        int g_Y = new_i*20;
        int distance_square = (g_X - app.wizard.getX())*(g_X - app.wizard.getX()) + (g_Y - app.wizard.getY())*(g_Y - app.wizard.getY());
        while (app.map.map_objects[new_i][new_j] == 'B' || app.map.map_objects[new_i][new_j] == 'X' || distance_square < 40000){
            new_j = random.nextInt(35);
            new_i = random.nextInt(32);
            g_X = new_j*20;
            g_Y = new_i*20;
            distance_square = (g_X - app.wizard.getX())*(g_X - app.wizard.getX()) + (g_Y - app.wizard.getY())*(g_Y - app.wizard.getY());
        }
        cooldown_count = 0;
        cooldown = false;
        x = g_X;
        y = g_Y;
    }

    /**
     * If the gremlin hits a wall with more than one possible new direction to go in, it will randomly choose a new direction but won’t go back the way it just came.
     * @param app, update data to app
     */
    public void choose_direction(App app) {
        boolean[] collides = collision(app); // {right, left, up, down, right_up, right_down, left_up, left_down} whether can move

        if (!collides[0] && direction == "right"){
            moveRight = false;
            if (!collides[2] && !collides[3]){direction = "left";}
            else if (!collides[2]){direction = "down";}
            else if(!collides[3]){direction = "up";}
            else{
                int d = random.nextInt(2) + 2; //choose 2 or 3 => up or down
                if(d == 2){direction = "up";}
                else{direction = "down";}
            }
        }
        else if (!collides[1] && direction == "left"){
            moveLeft = false;
            if (!collides[2] && !collides[3]){direction = "right";}
            else if (!collides[2]){direction = "down";}
            else if(!collides[3]){direction = "up";}
            else{
                int d = random.nextInt(2) + 2; //choose 2 or 3 => up or down
                if(d == 2){direction = "up";}
                else{direction = "down";}
            } 
        }
        else if (!collides[2] && direction == "up"){
            moveUp = false;
            if (!collides[0] && !collides[1]){direction = "down";}
            else if (!collides[0]){direction = "left";}
            else if (!collides[1]){direction = "right";}
            else{
                int d = random.nextInt(2); // choose 0 or 1 => right or left
                if (d == 0){direction = "right";}
                else{direction = "left";}
            }
        }
        else if (!collides[3] && direction == "down"){
            moveDown = false;
            if (!collides[0] && !collides[1]){direction = "up";}
            else if (!collides[0]){direction = "left";}
            else if (!collides[1]){direction = "right";}
            else{
                int d = random.nextInt(2); // choose 0 or 1 => right or left
                if (d == 0){direction = "right";}
                else{direction = "left";}
            }
        }

        if (direction == "right"){moveRight = true;}
        else if (direction == "left"){moveLeft = true;}
        else if (direction == "up"){moveUp = true;}
        else if (direction == "down"){moveDown = true;}
    }

    /**
     * Change the location based on its direction
     */
    public void move(){
        if(moveRight){x += speed;}
        else if (moveLeft){x -= speed;}
        else if (moveUp){y -= speed;}
        else if(moveDown){y += speed;}
    }

    /**
     * @param s, new direction
     */
    public void setDirection(String s){direction = s;}

    /**
     * @return current direction
     */
    public String getDirection() {return direction;}
}