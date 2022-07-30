package main;

import main.status.StatusEnum;

/**
 * Класс представляет собой отдельно стоящую задачу
 */
public class SimpleTask extends Task {
    protected StatusEnum Status;


    public SimpleTask(String name, String description, StatusEnum status) {
        super(0L, name, description);
        this.Status = status;
    }


    public SimpleTask(Long id, String name, String description, StatusEnum status) {
        super(id, name, description);
        this.Status = status;
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + "'" +
                ", status='" + Status +
                "'}";
    }
}
