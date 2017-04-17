package com.conject.bowling.core;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gennadii on 07.07.16.
 */
public class FrameTest {

    @Test
    public void testGetScore() {
        Frame testedFrame = new Frame();
        Assert.assertEquals(testedFrame.getScore(), 0);

        List<Integer> hits = new ArrayList<>();
        List<Integer> bonuses = new ArrayList<>();
        testedFrame = new Frame(hits, bonuses);
        Assert.assertEquals(0, testedFrame.getScore());

        hits.add(1);
        Assert.assertEquals(1, testedFrame.getScore());

        bonuses.add(1);
        Assert.assertEquals(2, testedFrame.getScore());

        hits.add(1);
        hits.add(1);
        bonuses.add(1);
        Assert.assertEquals(5, testedFrame.getScore());
    }

    @Test
    public void testGetScoreNegativeScenarios() {
        Frame testedFrame = new Frame(null, null);
        Assert.assertEquals(0, testedFrame.getScore());

        List<Integer> hits = new ArrayList<>();
        List<Integer> bonuses = new ArrayList<>();

        testedFrame = new Frame(hits, null);
        Assert.assertEquals(0, testedFrame.getScore());

        testedFrame = new Frame(null, bonuses);
        Assert.assertEquals(0, testedFrame.getScore());
    }

    @Test
    public void testAddToScore() {
        Frame partlyMockedFrame = EasyMock
                .createMockBuilder(Frame.class)
                .withConstructor()
                .addMockedMethod("isStrike")
                .addMockedMethod("isSpare")
                .createMock();

        // Create a Spare Frame
        EasyMock.expect(partlyMockedFrame.isSpare()).andReturn(true).anyTimes();
        EasyMock.expect(partlyMockedFrame.isStrike()).andReturn(false).anyTimes();
        EasyMock.replay(partlyMockedFrame);

        partlyMockedFrame.addToScore(1);
        Assert.assertEquals(1, partlyMockedFrame.getScore());

        partlyMockedFrame.addToScore(1);
        Assert.assertEquals(1, partlyMockedFrame.getScore());

        // Create a Strike Frame
        EasyMock.reset(partlyMockedFrame);
        EasyMock.expect(partlyMockedFrame.isSpare()).andReturn(false).anyTimes();
        EasyMock.expect(partlyMockedFrame.isStrike()).andReturn(true).anyTimes();
        EasyMock.replay(partlyMockedFrame);

        partlyMockedFrame.addToScore(1);
        Assert.assertEquals(2, partlyMockedFrame.getScore());

        partlyMockedFrame.addToScore(1);
        Assert.assertEquals(2, partlyMockedFrame.getScore());
    }

    @Test
    public void testGetTotalPins() {
        Frame testedFrame = new Frame();
        Assert.assertEquals(0, testedFrame.getTotalPins());

        List<Integer> hits = new ArrayList<>();
        testedFrame = new Frame(hits, null);
        Assert.assertEquals(0, testedFrame.getTotalPins());

        hits = new ArrayList<>();
        hits.add(1);
        testedFrame = new Frame(hits, null);
        Assert.assertEquals(1, testedFrame.getTotalPins());

        hits = new ArrayList<>();
        hits.add(1);
        testedFrame = new Frame(hits, null);
        Assert.assertEquals(1, testedFrame.getTotalPins());

        hits = new ArrayList<>();
        List<Integer> bonuses = new ArrayList<>();
        hits.add(1);
        hits.add(1);
        hits.add(1);
        bonuses.add(1);
        testedFrame = new Frame(hits, null);
        Assert.assertEquals(3, testedFrame.getTotalPins());

        // Negative scenario: no hits, only bonuses
        hits = new ArrayList<>();
        bonuses = new ArrayList<>();
        bonuses.add(1);
        testedFrame = new Frame(hits, bonuses);
        Assert.assertEquals(0, testedFrame.getTotalPins());
    }

    @Test
    public void testToString() {
        // Normal numbers
        List<Integer> hits = new ArrayList<>();
        Frame testedFrame = new Frame(hits, null);
        Assert.assertEquals("", testedFrame.toString());

        hits.add(1);
        Assert.assertEquals("1", testedFrame.toString());

        hits.add(2);
        Assert.assertEquals("1 / 2", testedFrame.toString());

        hits.add(3);
        Assert.assertEquals("1 / 2 / 3", testedFrame.toString());

        // Spare
        hits = new ArrayList<>();
        hits.add(1);
        hits.add(9);
        testedFrame = new Frame(hits, null);
        Assert.assertEquals("1 / +", testedFrame.toString());

        hits.add(8);
        testedFrame = new LastFrame(hits, null);
        Assert.assertEquals("1 / + / 8", testedFrame.toString());

        // Strike
        hits = new ArrayList<>();
        hits.add(10);
        testedFrame = new LastFrame(hits, null);
        Assert.assertEquals("X", testedFrame.toString());

        hits.add(10);
        Assert.assertEquals("X / X", testedFrame.toString());

        hits.add(10);
        Assert.assertEquals("X / X / X", testedFrame.toString());

        // Strike with number less then 10 at the end
        hits = new ArrayList<>();
        hits.add(10);
        testedFrame = new LastFrame(hits, null);
        Assert.assertEquals("X", testedFrame.toString());

        hits.add(10);
        Assert.assertEquals("X / X", testedFrame.toString());

        hits.add(8);
        Assert.assertEquals("X / X / 8", testedFrame.toString());
    }
}
