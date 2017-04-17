package com.conject.bowling.core;

import java.util.*;

/**
 * Just one more level of abstraction in order to reduce load for multiple users game
 * Created by gennadii on 04.07.16.
 */
public class GameResults {
    Map<String, Results> results = new HashMap<>();

    public Set<String> getUsers() {
        return results.keySet();
    }

    /**
     * Get Results for specific user with lazy initialization
     * @param username
     * @return
     */
    public Results getUserResults(String username) {
        Results singleUserResults = results.get(username);
        if(singleUserResults == null) {
            singleUserResults = new Results();
            results.put(username, singleUserResults);
        }
        return singleUserResults;
    }

    /**
     * Game is complete only when all users have played all frames
     * @return
     */
    public boolean isComplete(){
        boolean isComplete = results.isEmpty() ? false : true;
        for(Map.Entry<String, Results> item : results.entrySet()) {
            Results results = item.getValue();
            isComplete = results.isComplete()
                ? isComplete
                : false;
        }
        return isComplete;
    }
}
