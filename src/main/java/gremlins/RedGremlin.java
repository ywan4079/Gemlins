package gremlins;

public class RedGremlin extends Enemy{
    /**
     * lives of red gremlin by default is 2
     */
    public int lives = 2;
    /**
     * total lives of red gremlins by default is 2
     */
    public int total_lives = 2;

    /**
     * Constructor og RedGremlin, required x and y coordinate
     * The speed of red gremlins by default is 2
     * @param x, x coordinate
     * @param y, y coordinate
     */
    public RedGremlin(int x, int y){
        super(x, y);
        speed = 2;
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
     * @param app, 
     */
    public void draw(App app){
        app.image(app.redgremlin, this.x, this.y);
    }

    /**
     * Create a red slime base on the location and direction of red gremlin
     * Cooldown status becomes true after attack
     * @param app, update data to app
     */
    public void attack(App app){
        app.redslimes.add(new RedSlime(x, y, direction));
        cooldown = true;
    }
}