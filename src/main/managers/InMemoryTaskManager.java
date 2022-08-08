package main.managers;

import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;
import main.tasks.Task;

import java.util.*;

import static main.status.StatusEnum.DONE;
import static main.status.StatusEnum.IN_PROGRESS;
import static main.status.StatusEnum.TODO;


public class InMemoryTaskManager implements TaskManager {

    private final Integer HISTORY_MAX_SIZE = 10;

    private Long nextID;

    private final Map<Long, SimpleTask> simpleTasks;
    private final Map<Long, Subtask> subtasks;
    private final Map<Long, Epic> epics;

    private final List<Task> historyList = new ArrayList<>();


    public InMemoryTaskManager() {
        this.nextID = nextID = 1L;
        this.simpleTasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
    }


    @Override
    public void updateEpicStatus(Long epicID) {

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


    @Override
    public void removeSubtask(Long id) {
        subtasks.remove(id);
    }


    @Override
    public void removeEpic(Long id) {

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


    @Override
    public List<Long> updateSubtasksInEpic(Epic epic) {
        List<Long> lisOfSubtaskIDs = new ArrayList<>();
        for (Long subtaskID : epic.getSubtaskIDs()) {
            Subtask subtask = subtasks.get(subtaskID);
            lisOfSubtaskIDs.add(subtask.getId());
        }
        epic.setSubtaskIDs(lisOfSubtaskIDs);

        return epic.getSubtaskIDs();
    }


    @Override
    public List<SimpleTask> getListSimpleTask() {
        List<SimpleTask> list = new ArrayList<>();
        for (Long task : simpleTasks.keySet()) {
            list.add(simpleTasks.get(task));
        }
        return list;
    }


    @Override
    public List<Subtask> getListSubtask() {
        List<Subtask> list = new ArrayList<>();
        for (Long task : subtasks.keySet()) {
            list.add(subtasks.get(task));
        }
        return list;
    }


    @Override
    public List<Epic> getListEpic() {
        List<Epic> list = new ArrayList<>();
        for (Long task : epics.keySet()) {
            list.add(epics.get(task));
        }
        return list;
    }



    @Override
    public Long add(SimpleTask task) {
        task.setId(nextID++);
        simpleTasks.put(task.getId(), task);
        return nextID - 1;
    }


    @Override
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


    @Override
    public Long add(Epic epic) {
        epic.setId(nextID++);
        epic.setStatus(TODO);
        List<Long> list = updateSubtasksInEpic(epic);
        epic.setSubtaskIDs(list);
        epics.put(epic.getId(), epic);
        return epic.getId();
    }


    @Override
    public void update(SimpleTask task) {
        simpleTasks.put(task.getId(), task);
    }


    @Override
    public void update(Subtask subtask) {

        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicID());
        updateSubtasksInEpic(epic);
        updateEpicStatus(subtask.getEpicID());
    }


    @Override
    public void update(Epic epic) {
        Epic oldEpic = epics.get(epic.getId());
        for (Long idSubtask : oldEpic.getSubtaskIDs()) {
            remove(idSubtask);
        }

        epics.put(epic.getId(), epic);
        updateEpicStatus(epic.getId());
    }


    @Override
    public void removeAllSimpleTasks() {
        simpleTasks.clear();
    }


    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
    }


    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }


    @Override
    public void removeAll() {
        simpleTasks.clear();
        subtasks.clear();
        epics.clear();
    }


    @Override
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


    @Override
    public List<Subtask> getSubtaskListByEpicID(Long id) {
        List<Subtask> currentList = new ArrayList<>();
        for (Long currentSubtask : epics.get(id).getSubtaskIDs()) {
            currentList.add(subtasks.get(currentSubtask));
        }
        return currentList;
    }


    @Override
    public SimpleTask getTaskByID(Long id) {
        if (historyList.size() < HISTORY_MAX_SIZE){
            historyList.add(simpleTasks.get(id));
        } else {
            historyList.remove(0);
            historyList.add(simpleTasks.get(id));
        }
        return simpleTasks.get(id);
    }


    @Override
    public Subtask getSubtaskByID(Long id) {
        if (historyList.size() < HISTORY_MAX_SIZE){
            historyList.add(subtasks.get(id));
        } else {
            historyList.remove(0);
            historyList.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }


    @Override
    public Epic getEpicByID(Long id) {
        if (historyList.size() < HISTORY_MAX_SIZE){
            historyList.add(epics.get(id));
        } else {
            historyList.remove(0);
            historyList.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public Long getNextID() {
        return nextID;
    }


    @Override
    public void setNextID(Long nextID) {
        this.nextID = nextID;
    }

    @Override
    public List<Task> getHistory(){
        return historyList;
    }
}
