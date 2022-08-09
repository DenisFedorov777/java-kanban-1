package main.tasks;

import main.status.StatusEnum;

/**
 * Класс описывающий объект "подзадача" (сабтаск)
 */
public class Subtask extends Task {
    private final Long epicID;

    public Subtask(String name, String description, StatusEnum status, Long epicID) {
        super(0L, name, description, status);
        this.epicID = epicID;
    }

    public Subtask(Long id, String name, String description, StatusEnum status, Long epicID) {
        super(id, name, description, status);
        this.epicID = epicID;
    }

    public Long getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", epicID=" + epicID +
                ", status='" + getStatus() +
                "'}";
    }
}
