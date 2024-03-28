package gremlins;

public class RedSlime extends EnemyAttack{
    /**
     * Constructor of RedSlime, required x and y coordinate, and direction
     * @param x, x coordinate
     * @param y, y coordinate
     * @param direction, the moving direction of fireball
     * The speed of red slime by default is 6
     */
    public RedSlime(int x ,int y, String direction){
        super(x, y, direction);
        speed = 6;
    }

    /**
     * Check the red slime collides brickwall or stonewall
     * If it hits brickwall or stonewall, just remove the slime
     * @param app, update data to app
     */
    public void collision_check(App app){
        int i, j;
        if (direction == "right"){
            j = (int)(x+20)/20;
            i = (int)(y+10)/20;
            if (app.map.map_objects[i][j] == 'B' || app.map.map_objects[i][j] == 'X'){
                app.redslimes.remove(this);
            }
        }
        else if (direction == "left"){
            j = (int)x/20;
            i = (int)(y+10)/20;
            if (app.map.map_objects[i][j] == 'B' || app.map.map_objects[i][j] == 'X'){
                app.redslimes.remove(this);
            }
        }
        else if (direction == "up"){
            j = (int)(x+10)/20;
            i = (int)y/20;
            if (app.map.map_objects[i][j] == 'B' || app.map.map_objects[i][j] == 'X'){
                app.redslimes.remove(this);
            }
        }
        else{
            j = (int)(x+10)/20;
            i = (int)(y+20)/20;
            if (app.map.map_objects[i][j] == 'B' || app.map.map_objects[i][j] == 'X'){
                app.redslimes.remove(this);
            }
        }
    }

    /**
     * Draw red slime to window created by app
     * @param app, update data to app
     */
    public void draw(App app){
        app.image(app.redslime, x, y);
    }
}