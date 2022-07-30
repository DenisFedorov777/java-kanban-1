package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static main.status.StatusEnum.DONE;
import static main.status.StatusEnum.IN_PROGRESS;
import static main.status.StatusEnum.TODO;

/**
 * Класс для управления задачами
 */
public class Manager {
    private Long nextID = 1L;

    private HashMap<Long, SimpleTask> simpleTasks = new HashMap<>();
    private HashMap<Long, Subtask> subtasks = new HashMap<>();
    private HashMap<Long, Epic> epics = new HashMap<>();

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
        for (Long idSubtask : oldEpic.subtaskIDs) {
            remove(idSubtask);
        }

        epics.put(epic.getId(), epic);
        updateEpicStatus(epic.getId());
    }

    /**
     * Получаем список всех задач всех типов
     *
     * @return список всех задач
     */
    public List<Object> getListOfAllTask() {
        List<Object> list = new ArrayList<>();
        for (Long key : simpleTasks.keySet()) {
            list.add(simpleTasks.get(key));
        }
        for (Long key : subtasks.keySet()) {
            list.add(subtasks.get(key));
        }
        for (Long key : epics.keySet()) {
            list.add(epics.get(key));
        }
        return list;
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
     * Обновляем статус эпика, в зависимости от статусов его сабтасков
     *
     * @param epicID айдишник эпика, статус которого обновляем
     */
    private void updateEpicStatus(Long epicID) {

        Long countOfNEW = 0L;
        Long countOfDONE = 0L;
        Long countOfSubtask = (long) epics.get(epicID).subtaskIDs.size();

        for (Long subtaskID : epics.get(epicID).subtaskIDs) {
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
     * Получаем список всех подзадач определенного эпика
     *
     * @param id айдишник эпика, список подзадач которого хотим получить
     * @return готовый список с сабтасками
     */
    public List<Subtask> getSubtaskListByEpicID(Long id) {
        List<Subtask> currentList = new ArrayList<>();
        for (Long currentSubtask : epics.get(id).subtaskIDs) {
            currentList.add(subtasks.get(currentSubtask));
        }
        return currentList;
    }

    /**
     * Получаем таск/сабтаск/эпик по уникальному идентификатору
     *
     * @param id уникальный айдишник по которому получаем объект
     * @return успех: получаем таск/сабтаск/эпик, неудача: получаем 'null'
     */
    public Object getObject(Long id) {
        if (simpleTasks.containsKey(id)) {
            return simpleTasks.get(id);
        } else if (subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            return null;
        }
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

        return epic.subtaskIDs;
    }

}
