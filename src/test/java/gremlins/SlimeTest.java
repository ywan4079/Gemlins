package gremlins;

import processing.core.PApplet;

import org.checkerframework.checker.units.qual.s;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class SlimeTest{
    @Test
    public void SlimeWallCollision() { //test slime can disappear when collide both brickwall and stonewall
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1200);

        app.gremlins.get(0).x = 600;
        app.gremlins.get(0).y = 40;
        app.gremlins.get(0).setDirection("right");
        //BBB
        // GB
        //  B
        app.gremlins.get(0).attack(app);
        app.slimes.get(0).tick();
        app.slimes.get(0).collision_check(app);
        boolean[] result = app.gremlins.get(0).collision(app);
        app.delay(400);
        assertFalse(result[0]);//slime cannot break brickwall, so right side is still blocked
        assertEquals(0, app.slimes.size()); //slime disappear when collide brickwall

        app.gremlins.get(0).x = 580;
        app.gremlins.get(0).y = 40;
        //BBBB
        // G B
        //X  B
        app.slimes.clear(); //reset
        app.gremlins.get(0).attack(app);
        for(int i = 0; i < 3; i++){ //slime hit wall after 6 tick
            app.slimes.get(0).tick();
            app.slimes.get(0).collision_check(app);
        }
        assertEquals(1, app.slimes.size());
        for(int i = 0; i < 2; i++){
            app.slimes.get(0).tick();
            app.slimes.get(0).collision_check(app);
        }
        app.delay(50); //3 tick
        assertEquals(0, app.slimes.size()); //slime disappear when collide brickwall
    }
}