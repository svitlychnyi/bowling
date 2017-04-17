package com.conject.bowling.core;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Single User Results, representing a list of Frames with some mediator functionality
 * Created by gennadii on 06.07.16.
 */
public class Results {
    public static final int TOTAL_FRAMES_PER_USER = 10;
    private static final Logger logger = Logger.getLogger(Results.class);
    private List<Frame> frames;

    public Results() {
        frames = new ArrayList<>();
        frames.add(new Frame());
    }

    public Results(List<Frame> frames) {
        this.frames = frames == null
            ? new ArrayList<>()
            : frames;

        if(this.frames.size() == 0) {
            frames.add(new Frame());
        }
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public boolean isComplete() {
        return frames.size() == TOTAL_FRAMES_PER_USER
                && getLastIncompleteFrame().isDone();
    }

    /**
     * Add single hit to user's results and update scores of previous Frames in case of Strike or Spare
     * This is the main control for calculations
     * @param hit
     */
    public void addHit(Integer hit) {
        Frame lastFrame = getLastIncompleteFrame();
        lastFrame.addHit(hit);

        ListIterator<Frame> iterator = getFrames().listIterator(getFrames().indexOf(lastFrame));
        try {
            Frame previousFrame = iterator.previous();
            if(previousFrame.isSpare() || previousFrame.isStrike()) {
                previousFrame.addToScore(hit);
            }

            previousFrame = iterator.previous();
            if(previousFrame.isStrike()) {
                previousFrame.addToScore(hit);
            }
        }
        catch (NoSuchElementException ex) {
            logger.debug("No previous frame found. Silently skipping bonus calculation.");
        }
    }

    /**
     * Retrieve last incomplete frame or create a new one if such not found.
     *
     * @return Last Frame or null if last frame is already complete
     */
    private Frame getLastIncompleteFrame() {
        if (!frames.isEmpty()
                && frames.size() != TOTAL_FRAMES_PER_USER
                && frames.get(frames.size() - 1).isDone()) {
            Frame newFrame = frames.size() == TOTAL_FRAMES_PER_USER - 1
                    ? new LastFrame()
                    : new Frame();
            frames.add(newFrame);
        }
        return frames.get(frames.size() - 1);
    }

    /**
     * Frame validation used to verify total amount of pins specified by user
     * @param nextHit
     * @return
     */
    public boolean isNextHitValid(int nextHit) {
        boolean isValid = true;
        if(getLastIncompleteFrame().getTotalPins() + nextHit > getLastIncompleteFrame().getMaxPinsPerFrame()) {
            isValid = false;
        }
        return  isValid;
    }

    @Override
    public String toString() {
        int i = 0;
        int totalScore = 0;
        StringBuilder stringBuilder = new StringBuilder(32);

        // Display slots for empty Frames as well
        while (i < TOTAL_FRAMES_PER_USER) {
            if (getFrames().size() > i) {
                Frame currentFrame = getFrames().get(i);

                if(! currentFrame.getHits().isEmpty()) {
                    totalScore += currentFrame.getScore();
                    stringBuilder.append(currentFrame);

                    stringBuilder.append(" (")
                            .append(totalScore)
                            .append(")");

                }

                stringBuilder.append(" || ");
            } else {
                stringBuilder.append(" || ");
            }
            i++;
        }

        stringBuilder.append(totalScore)
                .append(" |");

        return stringBuilder.toString();
    }
}
