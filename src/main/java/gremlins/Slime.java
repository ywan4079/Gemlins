package gremlins;

public class Slime extends EnemyAttack{
    /**
     * Constructor of Slime, required x and y coordinate, and direction
     * @param x, x coordinate
     * @param y, y coordinate
     * @param direction, the moving direction of fireball
     * the speed of slime by default is 4
     */
    public Slime(int x ,int y, String direction){
        super(x, y, direction);
        speed = 4;
    }

    /**
     * Check the slime collides brickwall or stonewall
     * If it hits brickwall or stonewall, just remove the slime
     * @param app, update data to app
     */
    public void collision_check(App app){
        int i, j;
        if (direction == "right"){
            j = (int)(x+20)/20;
            i = (int)(y+10)/20;
            if (app.map.map_objects[i][j] == 'B' || app.map.map_objects[i][j] == 'X'){
                app.slimes.remove(this);
            }
        }
        else if (direction == "left"){
            j = (int)x/20;
            i = (int)(y+10)/20;
            if (app.map.map_objects[i][j] == 'B' || app.map.map_objects[i][j] == 'X'){
                app.slimes.remove(this);
            }
        }
        else if (direction == "up"){
            j = (int)(x+10)/20;
            i = (int)y/20;
            if (app.map.map_objects[i][j] == 'B' || app.map.map_objects[i][j] == 'X'){
                app.slimes.remove(this);
            }
        }
        else{
            j = (int)(x+10)/20;
            i = (int)(y+20)/20;
            if (app.map.map_objects[i][j] == 'B' || app.map.map_objects[i][j] == 'X'){
                app.slimes.remove(this);
            }
        }
    }

    /**
     * Draw slime to window created by app
     * @param app, update data to app
     */
    public void draw(App app){
        app.image(app.slime, x, y);
    }

}