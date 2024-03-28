package gremlins;

import processing.core.PApplet;

import org.checkerframework.checker.units.qual.s;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class GremlinTest{
    @Test
    public void GremlinMove() { //test move function in Gremlin
        Gremlin g = new Gremlin(200, 200);
        g.moveRight = true;
        g.moveLeft = false;
        g.moveUp = false;
        g.moveDown = false;
        g.move();
        assertEquals(201, g.x);
        assertEquals(200, g.y);
    }

    @Test
    public void GremlinSetAndGet() {
        Gremlin g = new Gremlin(200, 200);
        g.setDirection("right");
        assertEquals("right", g.getDirection());
        g.setDirection("down");
        assertEquals("down", g.getDirection());
    }

    @Test
    public void GremlinTEST() { // test attack and respawn function in Gremlin
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(800);

        Gremlin gremlintest = new Gremlin(200, 150);
        gremlintest.setDirection("right");
        app.slimes.clear(); //reset, ensure line 49 is the first slime
        gremlintest.attack(app);
        assertEquals(200, app.slimes.get(0).x);
        assertEquals(150, app.slimes.get(0).y);
        assertEquals("right", app.slimes.get(0).direction);
        assertTrue(gremlintest.cooldown);
        gremlintest.attack(app);
        assertEquals(2, app.slimes.size());
        app.slimes.clear(); //reset

        for (Gremlin g : app.gremlins){
            g.respawn(app);
            int distance_square = (g.x - app.wizard.x)*(g.x - app.wizard.x) + (g.y - app.wizard.y)*(g.y - app.wizard.y);
            assertFalse(distance_square < 40000);  //the distance between gremlins and wizard should not be less than 10 tiles(200 pixel) 200^2=40000
        }
    }

    @Test
    public void GremlinTick() { //test tick function in Gremlin
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1200);

        app.gremlins.get(0).x = 600;
        app.gremlins.get(0).y = 40;
        app.gremlins.get(0).setDirection("right"); //then gremlin should go down
        app.gremlins.get(0).moveRight = true;
        app.gremlins.get(0).moveLeft = false;
        app.gremlins.get(0).moveUp = false;
        app.gremlins.get(0).moveDown = false;
        //BBB
        // GB
        //  B
        app.gremlins.get(0).tick(app);
        assertEquals(600, app.gremlins.get(0).x);
        assertEquals(41, app.gremlins.get(0).y);
        assertEquals("down", app.gremlins.get(0).getDirection());
        app.delay(200);
        app.gremlins.get(0).x = 640;
        app.gremlins.get(0).y = 40;
        app.gremlins.get(0).setDirection("up"); //then gremlin should go down
        //BBB
        //BGB
        //B B
        app.gremlins.get(0).tick(app);
        assertEquals(640, app.gremlins.get(0).x);
        assertEquals(41, app.gremlins.get(0).y);
        assertEquals("down", app.gremlins.get(0).getDirection());

        app.delay(200);
        app.gremlins.get(0).x = 600;
        app.gremlins.get(0).y = 280;
        app.gremlins.get(0).setDirection("right"); //then gremlin should go up or down
        //  B
        // GB
        //  B
        app.gremlins.get(0).tick(app);
        assertTrue(app.gremlins.get(0).getDirection() == "up" || app.gremlins.get(0).getDirection() == "down");

        app.slimes.clear();
        app.gremlins.get(0).attack(app);
        assertTrue(app.gremlins.get(0).cooldown); //cooldown should be true after attack
        assertEquals(1, app.slimes.size()); // there should be a slime in slimes list
        for (int i = 0; i < 90; i++) {
            app.gremlins.get(0).tick(app);
        }
        assertTrue(app.gremlins.get(0).cooldown); //cooldown is still true after 1.5 sec

        for (int i = 0; i < 90; i++) {
            app.gremlins.get(0).tick(app);
        }
        //cooldown is false now, but it attacks immediately. Thus, cooldown becomes true again. There are two slimes in list because it attacks.
        app.delay(100);
        assertEquals(2, app.slimes.size()); 
    }
}