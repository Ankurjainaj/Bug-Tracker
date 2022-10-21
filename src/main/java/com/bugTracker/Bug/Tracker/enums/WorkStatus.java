package com.bugTracker.Bug.Tracker.enums;

public enum WorkStatus {

    InProgress(1),
    Pending(2),
    Done(3),
    Cancelled(5);


    private int status;

    WorkStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
