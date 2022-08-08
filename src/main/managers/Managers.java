package main.managers;

/**
 * Подбирает нужную реализацию TaskManager и возвращает объект правильного типа
 */
public class Managers {

    TaskManager taskManager = new InMemoryTaskManager();

    /**
     * @return объект-менеджер
     */
    TaskManager getDefault() {
        return taskManager;
    }
}
