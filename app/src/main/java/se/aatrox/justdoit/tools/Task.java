package se.aatrox.justdoit.tools;

import android.util.Log;

import java.sql.Timestamp;

public class Task {
    private int _id;
    private final String task;
    private final Timestamp deadline, makespan;

    public String getTask() {
        return task;
    }

    public boolean isDeadlineSet() {
        return this.deadline != null;
    }

    public boolean isDone() {
        return this.makespan != null;
    }

    public String get_id() {
        return "" + _id;
    }

    public String getType() {
        return isDone() ? "Done" : "Todo";
    }

    public String getS_deadline() {
        return deadline.toString();
    }

    public String getS_makespan() {
        return makespan.toString();
    }

    public Task(int _id, String task, String deadline, String makespan) {
        Log.e("Task", "Task: " + _id + task + deadline + (makespan == null));
        this._id = _id;
        this.task = task;
        this.deadline = deadline == null ? null : Timestamp.valueOf(deadline);
        this.makespan = makespan == null ? null : Timestamp.valueOf(makespan);
    }
}
