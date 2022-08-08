package main.managers;

import main.tasks.Task;

import java.util.List;

/**
 * Подбирает нужную реализацию TaskManager и возвращает объект правильного типа
 */
public class Managers {

    TaskManager taskManager = new InMemoryTaskManager();
    static HistoryManager historyManager = new InMemoryHistoryManager();

    /**
     * @return объект-менеджер
     */
    public TaskManager getDefault() {
        return taskManager;
    }

    /**
     * @return объект "InMemoryHistoryManager" — историю просмотров
     */
    public static List<Task> getDefaultHistory() {
        return historyManager.getHistory();
    }
}
