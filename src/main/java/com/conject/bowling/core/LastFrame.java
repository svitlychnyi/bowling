package com.conject.bowling.core;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by gennadii on 06.07.16.
 */
public class LastFrame extends Frame {
    public static final Logger logger = Logger.getLogger(LastFrame.class);
    public static final int LAST_FRAME_MAX_PINS = 30;
    public static final int LAST_FRAME_MAX_HITS = 3;
    private int maxAllowedHits = FRAME_MAX_HITS;

    public LastFrame() {
        super();
    }

    public LastFrame(List<Integer> hits, List<Integer> bonuses) {
        super(hits, bonuses);
    }

    @Override
    public boolean isDone() {
        return getHits().size() == maxAllowedHits;
    }

    @Override
    public boolean isSpare() {
        boolean isSpare = super.isSpare();
        if(getHits().size() > FRAME_MAX_HITS) {
            isSpare = getHits().get(0) + getHits().get(1) == TOTAL_PINS;
        }
        return isSpare;
    }

    @Override
    public void addHit(Integer hit) {
        super.addHit(hit);
        if(isSpare() || isStrike()) {
            addOneHitToFrame();
        }
    }

    private void addOneHitToFrame(){
        if(maxAllowedHits < LAST_FRAME_MAX_HITS) {
            logger.info("Wow! Try one more!");
            maxAllowedHits++;
        }
        else {
            logger.info("Last Frame max hits is reached! You are already amazing, give others chance to win.");
        }
    }

    @Override
    public int getMaxPinsPerFrame() {
        return LAST_FRAME_MAX_PINS;
    }
}
