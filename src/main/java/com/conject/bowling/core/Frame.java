package com.conject.bowling.core;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gennadii on 01.07.16.
 */
public class Frame {
    public static final int TOTAL_PINS = 10;
    public static final int FRAME_MAX_HITS = 2;
    private static final Logger logger = Logger.getLogger(Frame.class);

    private List<Integer> hits;
    private List<Integer> bonuses;

    public Frame() {
        this.hits = new ArrayList<>();
        this.bonuses = new ArrayList<>();
    }

    public Frame(List<Integer> hits, List<Integer> bonuses) {
        this.hits = hits == null
            ? new ArrayList<>()
            : hits;

        this.bonuses = bonuses == null
            ? new ArrayList<>()
            : bonuses;
    }

    public int getScore() {
        int score = 0;
        for(Integer bonus : bonuses) {
            score += bonus;
        }
        for(Integer hit : hits) {
            score += hit;
        }
        return score;
    }

    protected List<Integer> getHits(){
        return hits;
    }

    public void addHit(Integer hit) {
        hits.add(hit);
    }

    public void addToScore(int addition) {
        if(isSpare() && bonuses.size() < 1
                || isStrike() && bonuses.size() < 2) {
            this.bonuses.add(addition);
        }
        else {
            logger.debug("Maximum allowed bonuses number is reached for this frame, skipping score addition.");
        }
    }

    public boolean isDone() {
        return getTotalPins() == TOTAL_PINS || this.hits.size() == FRAME_MAX_HITS;
    }

    public boolean isStrike() {
        return getTotalPins() == TOTAL_PINS && hits.size() == 1;
    }

    public boolean isSpare() {
        return getTotalPins() == TOTAL_PINS && ! isStrike();
    }

    public int getTotalPins() {
        int total = 0;
        for(Integer singleResult : this.hits) {
            total += singleResult;
        }
        return total;
    }

    public int getMaxPinsPerFrame(){
        return TOTAL_PINS;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(16);
        int i = 0;
        for(Integer value : getHits()) {
            if(value == TOTAL_PINS) {
                stringBuilder.append("X");
            }
            else if(isSpare() && i > 0 && i < FRAME_MAX_HITS) {
                stringBuilder.append("+");
            }
            else {
                stringBuilder.append(value);
            }

            i++;
            if(i < getHits().size()) {
                stringBuilder.append(" / ");
            }
        }
        return stringBuilder.toString();
    }
}
