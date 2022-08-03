package main;

import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;
import main.tasks.Task;

import java.util.*;

import static main.status.StatusEnum.DONE;
import static main.status.StatusEnum.IN_PROGRESS;
import static main.status.StatusEnum.TODO;

/**
 * Класс для управления задачами
 */
public class Manager {


    private Long nextID;

    private final Map<Long, SimpleTask> simpleTasks;
    private final Map<Long, Subtask> subtasks;
    private final Map<Long, Epic> epics;

    public Manager() {
        this.nextID = nextID = 1L;
        this.simpleTasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    /**
     * Обновляем статус эпика, в зависимости от статусов его сабтасков
     *
     * @param epicID айдишник эпика, статус которого обновляем
     */
    private void updateEpicStatus(Long epicID) {

        Long countOfNEW = 0L;
        Long countOfDONE = 0L;
        Long countOfSubtask = (long) epics.get(epicID).getSubtaskIDs().size();

        for (Long subtaskID : epics.get(epicID).getSubtaskIDs()) {
            if (subtasks.get(subtaskID).getStatus() == TODO) {
                countOfNEW++;
            } else if (subtasks.get(subtaskID).getStatus() == DONE) {
                countOfDONE++;
            }
        }

        if (countOfNEW.equals(countOfSubtask) || (countOfSubtask == 0)) {
            Epic epic = epics.get(epicID);
            epic.setStatus(TODO);
            epics.put(epicID, epic);
        } else if (countOfDONE.equals(countOfSubtask)) {
            Epic epic = epics.get(epicID);
            epic.setStatus(DONE);
            epics.put(epicID, epic);
        } else {
            Epic epic = epics.get(epicID);
            epic.setStatus(IN_PROGRESS);
            epics.put(epicID, epic);
        }
    }

    /**
     * Удаляем сабтаск и актуализируем статус эпика
     *
     * @param id айдишник сабтаска, которого удаляем
     */
    private void removeSubtask(Long id) {
        subtasks.remove(id);
    }

    /**
     * Удаляем эпик со всеми его сабтасками
     *
     * @param id айдишник эпика, который удаляем
     */
    private void removeEpic(Long id) {

        for (Long key : subtasks.keySet()) {
            if (Objects.equals(subtasks.get(key).getEpicID(), id)) {
                subtasks.remove(key);
                if (subtasks.size() <= 1) {
                    subtasks.clear();
                    break;
                }
            }
        }
        epics.remove(id);
    }

    /**
     * Обновление списка айдишников сабтасков в эпике
     *
     * @param epic эпик, айдишники сабтасков которого обновляем
     * @return обновленный список айдишников сабтасков эпика
     */
    private List<Long> updateSubtasksInEpic(Epic epic) {
        List<Long> lisOfSubtaskIDs = new ArrayList<>();
        for (Long subtaskID : epic.getSubtaskIDs()) {
            Subtask subtask = subtasks.get(subtaskID);
            lisOfSubtaskIDs.add(subtask.getId());
        }
        epic.setSubtaskIDs(lisOfSubtaskIDs);

        return epic.getSubtaskIDs();
    }

    /**
     * Получаем список всех простых тасков
     *
     * @return список всех тасков
     */
    public List<SimpleTask> getListSimpleTask() {
        List<SimpleTask> list = new ArrayList<>();
        for (Long task : simpleTasks.keySet()) {
            list.add(simpleTasks.get(task));
        }
        return list;
    }

    /**
     * Получаем список всех сабтасков
     *
     * @return список всех сабтасков
     */
    public List<Subtask> getListSubtask() {
        List<Subtask> list = new ArrayList<>();
        for (Long task : subtasks.keySet()) {
            list.add(subtasks.get(task));
        }
        return list;
    }

    /**
     * Получаем список всех эпиков
     *
     * @return список всех эпиков
     */
    public List<Epic> getListEpic() {
        List<Epic> list = new ArrayList<>();
        for (Long task : epics.keySet()) {
            list.add(epics.get(task));
        }
        return list;
    }


    /**
     * Добавляем обычный таск
     *
     * @param task новый таск
     * @return айдишник нового таска
     */
    public Long add(SimpleTask task) {
        task.setId(nextID++);
        simpleTasks.put(task.getId(), task);
        return nextID - 1;
    }

    /**
     * Добавляем новый сабтаск
     *
     * @param task новый сабтаск
     * @return айдишник нового сабтаска
     */
    public Long add(Subtask task) {
        task.setId(nextID++);
        subtasks.put(task.getId(), task);
        List<Long> newSubtaskList = updateSubtasksInEpic(epics.get(task.getEpicID()));
        Epic epic = epics.get(task.getEpicID());
        newSubtaskList.add(task.getId());
        epic.setSubtaskIDs(newSubtaskList);
        epics.put(task.getEpicID(), epic);
        return task.getId();
    }

    /**
     * Добавляем новый эпик
     *
     * @param epic новый эпик
     * @return айдишник нового эпика
     */
    public Long add(Epic epic) {
        epic.setId(nextID++);
        epic.setStatus(TODO);
        List<Long> list = updateSubtasksInEpic(epic);
        epic.setSubtaskIDs(list);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    /**
     * Обновляем старый таск
     *
     * @param task новый таск
     */
    public void update(SimpleTask task) {
        simpleTasks.put(task.getId(), task);
    }

    /**
     * Обновляем старый сабтаск
     *
     * @param subtask новый сабтаск
     */
    public void update(Subtask subtask) {

        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicID());
        updateSubtasksInEpic(epic);
        updateEpicStatus(subtask.getEpicID());
    }

    /**
     * Обновляем старый эпик
     *
     * @param epic эпик на замену старого
     */
    public void update(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        for (Long idSubtask : oldEpic.getSubtaskIDs()) {
            remove(idSubtask);
        }

        epics.put(epic.getId(), epic);
        updateEpicStatus(epic.getId());
    }

    /**
     * Удаляем все простые таски
     */
    public void removeAllSimpleTasks() {
        simpleTasks.clear();
    }

    /**
     * Удаляем все сабтаски
     */
    public void removeAllSubtasks() {
        subtasks.clear();
    }

    /**
     * Удаляем все эпики
     */
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    /**
     * Удаляем все задачи
     */
    public void removeAll() {
        simpleTasks.clear();
        subtasks.clear();
        epics.clear();
    }

    /**
     * Удаляем таск/сабтаск/эпик по идентификатору
     *
     * @param id айдишник, по которому удаляем
     */
    public void remove(Long id) {
        if (simpleTasks.containsKey(id)) {
            simpleTasks.remove(id);
        } else if (subtasks.containsKey(id)) {
            removeSubtask(id);
        } else if (epics.containsKey(id)) {
            removeEpic(id);
        } else {
            System.out.println("Нет такого id");
        }
    }

    /**
     * Метод получения списка задач по эпику (по id эпика)
     *
     * @param id айдишник эпика, список подзадач которого хотим получить
     * @return готовый список с сабтасками
     */
    public List<Subtask> getSubtaskListByEpicID(Long id) {
        List<Subtask> currentList = new ArrayList<>();
        for (Long currentSubtask : epics.get(id).getSubtaskIDs()) {
            currentList.add(subtasks.get(currentSubtask));
        }
        return currentList;
    }

    /**
     * Получение объекта SimpleTask по ID
     *
     * @param id id объекта, который хотим получить
     * @return объект класса SimpleTask
     */
    public SimpleTask getTaskByID(Long id) {
        return simpleTasks.get(id);
    }

    /**
     * Получение объекта Subtask по ID
     *
     * @param id id объекта, который хотим получить
     * @return объект класса Subtask
     */
    public Subtask getSubtaskByID(Long id) {
        return subtasks.get(id);
    }

    /**
     * Получение объекта Epic по ID
     *
     * @param id id объекта, который хотим получить
     * @return объект класса Epic
     */
    public Epic getEpicByID(Long id) {
        return epics.get(id);
    }

    /**
     * Следующий присваеваемый уникальный ID
     *
     * @return ID для некст таска
     */
    public Long getNextID() {
        return nextID;
    }

    /**
     * Выбираем с какого номера будут выдаваться уникальные ID
     */
    public void setNextID(Long nextID) {
        this.nextID = nextID;
    }
}
