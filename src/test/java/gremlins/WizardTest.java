package gremlins;

import processing.core.PApplet;

import org.checkerframework.checker.units.qual.s;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class WizardTest{
    @Test
    public void WizardSetSprite() { //test setsprite function in Wizard
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        assertEquals(app.wizard_right, app.wizard.getSprite());
        app.wizard.setSprite(app.wizard_left);
        assertEquals(app.wizard_left, app.wizard.getSprite());
    }

    @Test
    public void WizardKeyPressedAndReleased() { //test wizard can deal with keyboard pressed and released correctly
        Wizard wizard = new Wizard(40, 40);
        assertTrue(wizard.left_stop);
        assertTrue(wizard.right_stop);
        assertTrue(wizard.up_stop);
        assertTrue(wizard.down_stop);
        assertFalse(wizard.moveRight);
        assertFalse(wizard.moveLeft);
        assertFalse(wizard.moveUp);
        assertFalse(wizard.moveDown);
        assertEquals("right", wizard.direction);

        wizard.pressLeft();
        assertFalse(wizard.left_stop);
        assertTrue(wizard.right_stop);
        assertTrue(wizard.up_stop);
        assertTrue(wizard.down_stop);
        assertFalse(wizard.moveRight);
        assertTrue(wizard.moveLeft);
        assertFalse(wizard.moveUp);
        assertFalse(wizard.moveDown);
        assertEquals("left", wizard.direction);

        wizard.pressUp();
        assertFalse(wizard.left_stop);
        assertTrue(wizard.right_stop);
        assertFalse(wizard.up_stop);
        assertTrue(wizard.down_stop);
        assertFalse(wizard.moveRight);
        assertTrue(wizard.moveLeft);
        assertTrue(wizard.moveUp);
        assertFalse(wizard.moveDown);
        assertEquals("up", wizard.direction);

        wizard.releaseLeft();
        assertTrue(wizard.left_stop);
        assertTrue(wizard.right_stop);
        assertFalse(wizard.up_stop);
        assertTrue(wizard.down_stop);
        assertFalse(wizard.moveRight);
        assertFalse(wizard.moveLeft);
        assertTrue(wizard.moveUp);
        assertFalse(wizard.moveDown);
        assertEquals("up", wizard.direction);
    }

    @Test
    public void WizardAttack() {  // test wizard attack function
        App app = new App();
        Wizard wizard = new Wizard(40, 60);
        assertEquals(0, app.fireballs.size());
        wizard.attack(app);
        assertEquals(1, app.fireballs.size());
        assertEquals(40, app.fireballs.get(0).x);
        assertEquals(60, app.fireballs.get(0).y);
        assertEquals("right", app.fireballs.get(0).direction);
    }

    @Test
    public void WizardStayInTile() { //test wizard StayInTile function
        Wizard wizard = new Wizard(40, 60);
        wizard.StayInTile();
        assertEquals(40, wizard.x);
        assertEquals(60, wizard.y);

        wizard.direction = "left";
        wizard.x += 2;
        wizard.StayInTile();
        assertEquals(40, wizard.x);
        assertEquals(60, wizard.y);

        wizard.direction = "up";
        wizard.x = 280;
        wizard.y = 150;
        wizard.StayInTile();
        assertEquals(280, wizard.x);
        assertEquals(148, wizard.y);

        wizard.direction = "down";
        wizard.x = 140;
        wizard.y = 144;
        wizard.StayInTile();
        assertEquals(140, wizard.x);
        assertEquals(146, wizard.y);
    }

    @Test
    public void WizardStatusCheck() { //test wizard can determine it reach exit, become invincible when step on shield, and stay in original location after respawn
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1200);

        app.wizard.nextlevel_check(app);
        app.delay(200);
        assertEquals(1, app.current_level);

        app.wizard.x = app.map.portal_location[0];
        app.wizard.y = app.map.portal_location[1];
        app.current_level = 1; //reset, avoid error
        app.delay(100);
        app.wizard.nextlevel_check(app);
        app.delay(100);
        assertEquals(2, app.current_level);

        app.wizard.x = app.map.shield_location[0];
        app.wizard.y = app.map.shield_location[1];
        app.map.powerUp_available = true;
        app.wizard.invincible_check(app);
        app.delay(300);
        assertTrue(app.wizard.getInvincible());

        app.wizard.respawn(app);
        app.delay(200);
        assertEquals(app.map.wizard_location[0], app.wizard.x);
        assertEquals(app.map.wizard_location[1], app.wizard.y);
    }

    @Test
    public void WizardSetAndGet() { //test wizard setInvincible and getInvincible function
        Wizard w = new Wizard(100, 200);
        assertFalse(w.getInvincible());
        w.setInvincible(true);
        assertTrue(w.getInvincible());
    }

    @Test
    public void WizardTick() { //test tick function in Wizard
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(800);

        app.wizard.pressRight();
        app.wizard.tick(app);
        assertEquals(42, app.wizard.x); //test pressRight function
        assertEquals(20, app.wizard.y);
        app.wizard.releaseRight();
        for(int i = 0; i < 4; i++){
            app.wizard.tick(app);
        }
        assertEquals(50, app.wizard.x); //test releasedRight and StayInTile function
        assertEquals(20, app.wizard.y);
        for(int i = 0; i < 10; i++){
            app.wizard.tick(app);
        }
        assertEquals(60, app.wizard.x); //test StayInTile function
        assertEquals(20, app.wizard.y);
        app.wizard.pressRight();
        app.wizard.tick(app);
        app.delay(200);
        assertEquals(60, app.wizard.x); //test collision function
        assertEquals(20, app.wizard.y);

        app.wizard.x = 60; //a brickwall at top-right corner of wizard
        app.wizard.y = 40;
        app.wizard.pressRight();
        app.wizard.pressUp();
        app.wizard.tick(app);
        app.delay(300);
        assertEquals(60, app.wizard.x); //test collision function can deal with corner bug
        assertEquals(40, app.wizard.y);

        app.wizard.releaseRight();
        app.wizard.releaseUp();
        app.wizard.pressLeft();
        app.wizard.tick(app);
        app.delay(300);
        assertEquals(app.wizard_left, app.wizard.getSprite()); // test wizard setSprite function work correctly
        assertEquals("left", app.wizard.direction); //test wizard direction is correct
        app.wizard.releaseLeft();

        app.wizard.setInvincible(true);
        app.delay(200);
        assertTrue(app.wizard.getInvincible());
        for(int i = 0; i < 180; i++){
            app.wizard.tick(app);
        }
        app.delay(500);
        assertFalse(app.wizard.getInvincible()); //test wizard invincible status when get invincible status after 3 sec in game. It should be false
        app.wizard.attack(app);
        app.delay(200);
        assertTrue(app.wizard.cooldown); // test wizard cooldown status after attack
        for(int i = 0; i < 20; i++){
            app.wizard.tick(app);
        }
        app.delay(300);
        assertFalse(app.wizard.cooldown); // test wizard cooldown status after 0.3333 sec in game

        app.wizard.x = app.map.portal_location[0];
        app.wizard.y = app.map.portal_location[1];
        app.wizard.tick(app);
        assertEquals(2, app.current_level); //test wizard can reach level 2 when step on exit
    }
}