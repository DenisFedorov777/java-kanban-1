package main;

import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;

import java.util.*;

public interface TaskManager {

    /**
     * Обновляем статус эпика, в зависимости от статусов его сабтасков
     *
     * @param epicID айдишник эпика, статус которого обновляем
     */
    void updateEpicStatus(Long epicID);

    /**
     * Удаляем сабтаск и актуализируем статус эпика
     *
     * @param id айдишник сабтаска, которого удаляем
     */
    void removeSubtask(Long id);

    /**
     * Удаляем эпик со всеми его сабтасками
     *
     * @param id айдишник эпика, который удаляем
     */
    void removeEpic(Long id);

    /**
     * Обновление списка айдишников сабтасков в эпике
     *
     * @param epic эпик, айдишники сабтасков которого обновляем
     * @return обновленный список айдишников сабтасков эпика
     */
    List<Long> updateSubtasksInEpic(Epic epic);

    /**
     * Получаем список всех простых тасков
     *
     * @return список всех тасков
     */
    List<SimpleTask> getListSimpleTask();

    /**
     * Получаем список всех сабтасков
     *
     * @return список всех сабтасков
     */
    List<Subtask> getListSubtask();

    /**
     * Получаем список всех эпиков
     *
     * @return список всех эпиков
     */
    List<Epic> getListEpic();


    /**
     * Добавляем обычный таск
     *
     * @param task новый таск
     * @return айдишник нового таска
     */
    Long add(SimpleTask task);

    /**
     * Добавляем новый сабтаск
     *
     * @param task новый сабтаск
     * @return айдишник нового сабтаска
     */
    Long add(Subtask task);

    /**
     * Добавляем новый эпик
     *
     * @param epic новый эпик
     * @return айдишник нового эпика
     */
    Long add(Epic epic);

    /**
     * Обновляем старый таск
     *
     * @param task новый таск
     */
    void update(SimpleTask task);
    /**
     * Обновляем старый сабтаск
     *
     * @param subtask новый сабтаск
     */
    void update(Subtask subtask);

    /**
     * Обновляем старый эпик
     *
     * @param epic эпик на замену старого
     */
    void update(Epic epic);

    /**
     * Удаляем все простые таски
     */
    void removeAllSimpleTasks();

    /**
     * Удаляем все сабтаски
     */
    void removeAllSubtasks();

    /**
     * Удаляем все эпики
     */
    void removeAllEpics();

    /**
     * Удаляем все задачи
     */
    void removeAll();

    /**
     * Удаляем таск/сабтаск/эпик по идентификатору
     *
     * @param id айдишник, по которому удаляем
     */
    void remove(Long id);

    /**
     * Метод получения списка задач по эпику (по id эпика)
     *
     * @param id айдишник эпика, список подзадач которого хотим получить
     * @return готовый список с сабтасками
     */
    List<Subtask> getSubtaskListByEpicID(Long id);

    /**
     * Получение объекта SimpleTask по ID
     *
     * @param id id объекта, который хотим получить
     * @return объект класса SimpleTask
     */
    SimpleTask getTaskByID(Long id);

    /**
     * Получение объекта Subtask по ID
     *
     * @param id id объекта, который хотим получить
     * @return объект класса Subtask
     */
    Subtask getSubtaskByID(Long id);

    /**
     * Получение объекта Epic по ID
     *
     * @param id id объекта, который хотим получить
     * @return объект класса Epic
     */
    Epic getEpicByID(Long id);

    /**
     * Следующий присваеваемый уникальный ID
     *
     * @return ID для некст таска
     */
    Long getNextID();

    /**
     * Выбираем с какого номера будут выдаваться уникальные ID
     */
    void setNextID(Long nextID);

}
