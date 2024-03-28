package gremlins;

import processing.core.PApplet;

import org.checkerframework.checker.units.qual.s;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class FireballTest{
    @Test
    public void FireballWallCollision() { //test fireball can work properly when colliding brickwall(break it) and stonewall(absorbed)
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(600);

        app.wizard.x = 60;
        app.wizard.y = 20;
        //XXX
        // WB
        //B
        boolean[] result = new boolean[8];
        result = app.wizard.collision(app);
        app.delay(200);
        assertFalse(result[0]);
        app.wizard.attack(app);
        app.fireballs.get(0).tick();
        app.fireballs.get(0).collision_check(app);
        result = app.wizard.collision(app);
        app.delay(200);
        assertTrue(result[0]); //brickwall on the right side should be deleted, so wizard can go right

        app.wizard.x = 240;
        app.wizard.y = 40;
        //B
        //W B
        //BXB
        app.wizard.attack(app);
        for(int i = 0; i < 5; i++){ //fireball collide after 5 tick
            app.fireballs.get(0).tick();
            app.fireballs.get(0).collision_check(app);
        }
        app.wizard.x += 20;
        //B
        // WB <-this is broken
        // XB
        result = app.wizard.collision(app);
        app.delay(200);
        assertTrue(result[0]); //brickwall on the right side should be deleted, so wizard can go right

        app.wizard.x += 20;
        // WB
        //XBB
        app.wizard.direction = "down";
        app.wizard.attack(app);
        app.fireballs.get(0).tick();
        app.fireballs.get(0).collision_check(app);
        result = app.wizard.collision(app);
        app.delay(200);
        assertTrue(result[3]); //brickwall on the down side should be deleted, so wizard can go down
    }
}