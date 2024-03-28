package gremlins;

public class Gremlin extends Enemy{
    /**
     * Constructor of Gremlin class, required x and y coordinate
     * The speed of Gremlin by default is 1
     * @param x, x coordinate
     * @param y, y coordinate
     */
    public Gremlin(int x, int y){
        super(x, y);
        speed = 1;
    }

    /**
     * Update gremlins information to app
     * @param app, update data to app
     */
    public void tick(App app){
        choose_direction(app);
        move();
        cooldown_check(app.enemy_cooldown);
        if (!cooldown){
            attack(app);
        }
    }

    /**
     * Draw gremlin image to window
     * @param app, update data to app
     */
    public void draw(App app){
        app.image(app.gremlin, this.x, this.y);
    }

    /**
     * Create a slime base on the location and direction of gremlin
     * cooldown status becomes true after attack
     * @param app, update data to app
     */
    public void attack(App app){
        app.slimes.add(new Slime(x, y, direction));
        cooldown = true;
    }
}