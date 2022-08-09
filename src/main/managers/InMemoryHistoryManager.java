package main.managers;

import main.tasks.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public static final Integer HISTORY_MAX_SIZE = 10;
    private static final List<Task> historyList = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (historyList.size() < HISTORY_MAX_SIZE) {
            historyList.add(task);
        } else {
            historyList.remove(0);
            historyList.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
