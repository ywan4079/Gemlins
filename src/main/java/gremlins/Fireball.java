package gremlins;

public class Fireball extends Attack{

    /**
     * Constructor of Fireball, required x and y coordinate, and direction
     * @param x, x coordinate
     * @param y, y coordinate
     * @param direction, the moving direction of fireball
     * the speed of fireball bydefault is 4
     */
    public Fireball(int x, int y, String direction){
        super(x, y, direction);
        speed = 4;
    }
    
    /**
     * Draw fireball to window
     * @param app, update data to app
     */
    public void draw(App app){
        app.image(app.fireball_image, x, y);
    }

    /**
     * Update the location of fireball
     */
    public void tick(){
        if (direction == "right") {x += speed;}
        else if (direction == "left") {x -= speed;}
        else if (direction == "up") {y -= speed;}
        else if (direction == "down") {y += speed;}
    }

    /**
     * Check the fireball collides brickwall or stonewall
     * If it hits brickwall, remove the fireball, break the brickwall, and start the breaking animation
     * If it hits stonewall, just remove the fireball
     * @param app, update data to app
     */
    public void collision_check(App app){
        int i, j;
        if (direction == "right") {
            j = (int)(x+20)/20;
            i = (int)(y+10)/20;
            if (app.map.map_objects[i][j] == 'B'){
                app.map.map_objects[i][j] = ' ';
                app.fireballs.remove(this);
                app.map.brick_destory(i, j);
                return;
            }
            else if (app.map.map_objects[i][j] == 'X'){
                app.fireballs.remove(this);
                return;
            }
        }
        else if (direction == "left") {
            j = (int)x/20;
            i = (int)(y+10)/20;
            if (app.map.map_objects[i][j] == 'B'){
                app.map.map_objects[i][j] = ' ';
                app.fireballs.remove(this);
                app.map.brick_destory(i, j);
                return;
            }
            else if (app.map.map_objects[i][j] == 'X'){
                app.fireballs.remove(this);
                return;
            }
        }
        else if (direction == "up") {
            j = (int)(x+10)/20;
            i = (int)y/20;
            if (app.map.map_objects[i][j] == 'B'){
                app.map.map_objects[i][j] = ' ';
                app.fireballs.remove(this);
                app.map.brick_destory(i, j);
                return;
            }
            else if (app.map.map_objects[i][j] == 'X'){
                app.fireballs.remove(this);
                return;
            }
        }
        else if (direction == "down") {
            j = (int)(x+10)/20;
            i = (int)(y+20)/20;
            if (app.map.map_objects[i][j] == 'B'){
                app.map.map_objects[i][j] = ' ';
                app.fireballs.remove(this);
                app.map.brick_destory(i, j);
                return;
            }
            else if (app.map.map_objects[i][j] == 'X'){
                app.fireballs.remove(this);
                return;
            }
        }
    }
}