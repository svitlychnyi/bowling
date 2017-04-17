package com.conject.bowling.core;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gennadii on 07.07.16.
 */
public class ResultsTest {

    @Test
    public void testIsComplete() {
        Results results = new Results();
        Assert.assertFalse(results.isComplete());

        Frame mockedFrame = EasyMock
                .createMockBuilder(Frame.class)
                .addMockedMethod("isDone")
                .createMock();
        EasyMock.expect(mockedFrame.isDone()).andReturn(true).anyTimes();
        EasyMock.replay(mockedFrame);

        // Create list of completed Frames without last one
        List<Frame> mockedFrames = new ArrayList<>();
        int i = 0;
        while(i++ < Results.TOTAL_FRAMES_PER_USER - 1) {
            mockedFrames.add(mockedFrame);
        }
        results = new Results(mockedFrames);
        Assert.assertFalse(results.isComplete());

        // Add last frame to finish the game
        mockedFrames.add(mockedFrame);
        results = new Results(mockedFrames);
        Assert.assertTrue(results.isComplete());
    }

    /**
     * Sample games:
     * 1 / 4 (5) || 4 / 5 (14) || 6 / + (29) || 5 / + (49) || X (60) || 0 / 1 (61) || 7 / + (77) || 6 / + (97) || X (117) || 2 / + / + (133) || 133 |
     * X (30) || X (60) || X (90) || X (120) || X (150) || X (180) || X (210) || X (240) || X (270) || X / X / X (300) || 300 |
     * 1 / 1 (2) || 1 / 1 (4) || 1 / 1 (6) || 1 / 1 (8) || 1 / 1 (10) || 1 / 1 (12) || 1 / 1 (14) || 1 / 1 (16) || 1 / 1 (18) || 1 / 1 (20) || 20 |
     * 5 / + (15) || 5 / + (30) || 5 / + (45) || 5 / + (60) || 5 / + (75) || 5 / + (90) || 5 / + (105) || 5 / + (120) || 5 / + (135) || 5 / + / + (150) || 150 |
     * 0 / 0 (0) || 0 / 0 (0) || 0 / 0 (0) || 0 / 0 (0) || 0 / 0 (0) || 0 / 0 (0) || 0 / 0 (0) || 0 / 0 (0) || 0 / 0 (0) || 0 / 0 (0) || 0 |
     */
    @Test
    public void testAddHit() {
        runTest(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

        runTest(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                new int[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20});

        runTest(new int[]{1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6},
                new int[]{5, 14, 29, 49, 60, 61, 77, 97, 117, 133});

        runTest(new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                new int[]{15, 30, 45, 60, 75, 90, 105, 120, 135, 150});

        runTest(new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                new int[]{30, 60, 90, 120, 150, 180, 210, 240, 270, 300});
    }

    private void runTest(int[] hits, int[] expectedScores) {
        Results results = new Results();
        for (int hit : hits) {
            results.addHit(hit);
        }

        int score = 0;
        int index = 0;
        for (int expectedScore : expectedScores) {
            score += results.getFrames().get(index++).getScore();
            Assert.assertEquals(expectedScore, score);
        }
    }

    @Test
    public void testIsNextHitValid() {
        Results results = new Results();

        Assert.assertTrue(results.isNextHitValid(0));
        Assert.assertTrue(results.isNextHitValid(1));
        Assert.assertTrue(results.isNextHitValid(5));
        Assert.assertTrue(results.isNextHitValid(10));

        Assert.assertFalse(results.isNextHitValid(11));
        List<Integer> hits = new ArrayList<>();
        hits.add(2);
        Frame frame = new Frame(hits, null);
        List<Frame> frames = new ArrayList<>();
        frames.add(frame);
        results = new Results(frames);

        Assert.assertTrue(results.isNextHitValid(0));
        Assert.assertTrue(results.isNextHitValid(1));
        Assert.assertTrue(results.isNextHitValid(5));
        Assert.assertTrue(results.isNextHitValid(8));

        Assert.assertFalse(results.isNextHitValid(9));
        Assert.assertFalse(results.isNextHitValid(10));
    }

    @Test
    public void testToString() {
        Results results = new Results();
        Assert.assertEquals(" ||  ||  ||  ||  ||  ||  ||  ||  ||  || 0 |", results.toString());

        Frame mockedFrame = EasyMock
                .createMockBuilder(Frame.class)
                .addMockedMethod("getScore")
                .addMockedMethod("isSpare")
                .addMockedMethod("isStrike")
                .addMockedMethod("getHits")
                .createMock();

        List<Integer> hits = new ArrayList<>();
        hits.add(3);
        hits.add(3);

        EasyMock.expect(mockedFrame.getScore()).andReturn(6).anyTimes();
        EasyMock.expect(mockedFrame.getHits()).andReturn(hits).anyTimes();
        EasyMock.expect(mockedFrame.isSpare()).andReturn(false).anyTimes();
        EasyMock.expect(mockedFrame.isStrike()).andReturn(false).anyTimes();
        EasyMock.replay(mockedFrame);

        List<Frame> frames = new ArrayList<>();
        frames.add(mockedFrame);
        results = new Results(frames);
        Assert.assertEquals("3 / 3 (6) ||  ||  ||  ||  ||  ||  ||  ||  ||  || 6 |", results.toString());

        frames.add(mockedFrame);
        Assert.assertEquals("3 / 3 (6) || 3 / 3 (12) ||  ||  ||  ||  ||  ||  ||  ||  || 12 |", results.toString());
    }
}
