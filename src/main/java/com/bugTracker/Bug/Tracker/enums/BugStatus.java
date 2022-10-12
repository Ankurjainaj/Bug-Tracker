package com.bugTracker.Bug.Tracker.enums;

public enum BugStatus {

    InProgress(1),
    Pending(2),
    Done(3),
    Cancelled(5);


    private int status;

    BugStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
