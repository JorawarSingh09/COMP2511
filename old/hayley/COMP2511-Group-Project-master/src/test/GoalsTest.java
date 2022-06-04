package test;

import java.util.List;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Cylinder;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import unsw.loopmania.Goals.Goal;
import unsw.loopmania.Goals.GoalAND;
import unsw.loopmania.Goals.GoalOR;
import unsw.loopmania.Goals.GoldLeaf;
import unsw.loopmania.Goals.XpLeaf;
import unsw.loopmania.Gold;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Goals.CycleLeaf;



public class GoalsTest {

    @Test 
    public void cycleLeafConstructorTest() {
        CycleLeaf c = new CycleLeaf(3);
        assertTrue(c instanceof Goal);
    }

    @Test 
    public void cycleLeafMetGoalTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        CycleLeaf c = new CycleLeaf(1);
        assertFalse(c.metGoal(world));
    }

    @Test 
    public void xpLeafConstructorTest() {
        XpLeaf c = new XpLeaf(3);
        assertTrue(c instanceof Goal);
    }

    @Test
    public void xpLeafSetGoalTest() {
        XpLeaf c = new XpLeaf(3);
        c.setGoalXp(3);
        assertEquals(c.getGoalXp(), 3);
    }

    @Test 
    public void xpLeafMetGoalTest () {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        XpLeaf c = new XpLeaf(1);
        assertFalse(c.metGoal(world));
    }

    @Test
    public void xpLeafGetGoalTest() {
        XpLeaf c = new XpLeaf(3);
        assertEquals(c.getGoalXp(), 3);
    }

    @Test 
    public void goldLeafConstructorTest() {
        GoldLeaf c = new GoldLeaf(3);
        assertTrue(c instanceof Goal);
    }

    @Test 
    public void goldLeafMetGoalTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        GoldLeaf c = new GoldLeaf(1);
        assertFalse(c.metGoal(world));
    }

    @Test
    public void goldLeafSetGoalTest() {
        GoldLeaf c = new GoldLeaf(3);
        c.setGoalGold(3);
        assertEquals(c.getGoalGold(), 3);
    }

    @Test
    public void golLeafGetGoalTest() {
        GoldLeaf c = new GoldLeaf(3);
        assertEquals(c.getGoalGold(), 3);
    }

    @Test
    public void goalAndConstructorTest() {

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "experience");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "gold");
        obj2.put("quantity", "2");
        array.put(obj);
        array.put(obj2);


        GoalAND c = new GoalAND(array);
        assertTrue(c instanceof Goal);
    }

    @Test
    public void goalAndMetGoalTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        world.iterateGamecycle();

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "cycles");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "gold");
        obj2.put("quantity", "0");
        array.put(obj);
        array.put(obj2);

        GoalAND c = new GoalAND(array);
        
        assertFalse(c.metGoal(world));

    }

    @Test
    public void goalAndParseJsonCycleTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        world.iterateGamecycle();

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "cycles");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "gold");
        obj2.put("quantity", "0");
        array.put(obj);
        array.put(obj2);

        GoalAND c = new GoalAND(array);
        
        assertTrue(c.parseJson(obj) instanceof CycleLeaf);

    }

    @Test
    public void goalAndParseJsonGoldTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        world.iterateGamecycle();

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "gold");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "cycles");
        obj2.put("quantity", "0");
        array.put(obj);
        array.put(obj2);

        GoalAND c = new GoalAND(array);
        
        assertTrue(c.parseJson(obj) instanceof GoldLeaf);

    }

    @Test
    public void goalAndParseJsonExperienceTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        world.iterateGamecycle();

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "experience");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "cycles");
        obj2.put("quantity", "0");
        array.put(obj);
        array.put(obj2);

        GoalAND c = new GoalAND(array);
        
        assertTrue(c.parseJson(obj) instanceof XpLeaf);

    }

    @Test
    public void goalOrConstructorTest() {
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "experience");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "gold");
        obj2.put("quantity", "2");
        array.put(obj);
        array.put(obj2);

        GoalOR c = new GoalOR(array);
        assertTrue(c instanceof Goal);
    }

    @Test
    public void goalOrMetGoalTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        world.iterateGamecycle();

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "cycles");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "gold");
        obj2.put("quantity", "0");
        array.put(obj);
        array.put(obj2);

        GoalOR c = new GoalOR(array);
        
        assertFalse(c.metGoal(world));

    }

    @Test
    public void goalORParseJsonCyclesTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        world.iterateGamecycle();

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "cycles");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "gold");
        obj2.put("quantity", "0");
        array.put(obj);
        array.put(obj2);

        GoalOR c = new GoalOR(array);
        
        assertTrue(c.parseJson(obj) instanceof CycleLeaf);

    }

    @Test
    public void goalOrParseJsonGoldTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        world.iterateGamecycle();

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "gold");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "cycles");
        obj2.put("quantity", "0");
        array.put(obj);
        array.put(obj2);

        GoalOR c = new GoalOR(array);
        
        assertTrue(c.parseJson(obj) instanceof GoldLeaf);
    }

    @Test
    public void goalOrParseJsonExperienceTest() {
        LoopManiaWorld world = TestHelper.createWorld(TestHelper.createPath());
        world.iterateGamecycle();
        world.iterateGamecycle();

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("goal", "experience");
        obj.put("quantity", "2");
        JSONObject obj2 = new JSONObject();
        obj2.put("goal", "cycles");
        obj2.put("quantity", "0");
        array.put(obj);
        array.put(obj2);

        GoalOR c = new GoalOR(array);
        
        assertTrue(c.parseJson(obj) instanceof XpLeaf);
    }
    
}

    
