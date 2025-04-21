package com.arpit.AssignmentReview.entities;

import java.util.HashMap;

public class Status {
    private static HashMap<Integer, String> status = new HashMap<>();
    public Status(){
        status.put(1, "Needs to be Submitted");
        status.put(2, "Submitted");
        status.put(3, "In Review");
        status.put(4, "Needs Update");
        status.put(5, "Completed");
        status.put(6, "Rejected");
    }
}
