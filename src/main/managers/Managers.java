package main.managers;

import main.tasks.Task;

import java.util.List;

/**
 * Подбирает нужную реализацию TaskManager и возвращает объект правильного типа
 */
public class Managers {
    /**
     * @return объект-менеджер
     */
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    /**
     * @return объект "InMemoryHistoryManager" — историю просмотров
     */
    public static List<Task> getDefaultHistory() {
        return new InMemoryHistoryManager().getHistory();
    }
}
