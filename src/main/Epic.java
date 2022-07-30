package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляет собой эпик, состоящий из подзадач
 */
public class Epic extends Task {

    private Enum status;
    protected List<Long> subtaskIDs; //список айдишников сабтасков


    public Epic(String name, String description) {
        super(0L, name, description);
        subtaskIDs = new ArrayList<>();
    }

    public Epic(Long id, String name, String description) {
        super(id, name, description);
        subtaskIDs = new ArrayList<>();
    }

    public void addSubtask(Long subtaskID) {
        this.subtaskIDs.add(subtaskID);
    }

    public List<Long> getSubtaskIDs() {
        return subtaskIDs;
    }

    public void setSubtaskIDs(List<Long> subtaskIDs) {
        this.subtaskIDs = subtaskIDs;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + "'" +
                ", subtaskIDs=" + subtaskIDs +
                ", status='" + status + "'" +
                '}';
    }


}
