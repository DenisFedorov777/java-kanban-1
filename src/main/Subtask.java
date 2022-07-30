package main;

import main.status.StatusEnum;

/**
 * Класс описывающий объект "подзадача" (сабтаск)
 */
public class Subtask extends Task {

    protected StatusEnum Status;
    protected Long epicID;


    public Subtask(String name, String description, StatusEnum status, Long epicID) {
        super(0L, name, description);
        this.Status = status;
        this.epicID = epicID;
    }

    public Subtask(Long id, String name, String description, StatusEnum status, Long epicID) {
        super(id, name, description);
        this.Status = status;
        this.epicID = epicID;
    }

    public StatusEnum getStatus() {
        return Status;
    }

    public void setStatus(StatusEnum status) {
        Status = status;
    }

    public Long getEpicID() {
        return epicID;
    }

    public void setEpicID(Long epicID) {
        this.epicID = epicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", epicID=" + epicID +
                ", status='" + Status +
                "'}";
    }
}
