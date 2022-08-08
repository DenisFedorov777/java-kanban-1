package main.managers;

import main.tasks.Task;

import java.util.List;

public interface HistoryManager {


    /**
     * Помечаем задачи как просмотренные
     *
     * @param task просмотренная задача
     */
    void add(Task task);

    /**
     * История просмотров задач
     *
     * @return Список просмотренных задач
     */
    List<Task> getHistory();
}
