package main.tasks;

import main.status.StatusEnum;

/**
 * Класс представляет собой отдельно стоящую задачу
 */
public class SimpleTask extends Task {


    public SimpleTask(String name, String description, StatusEnum status) {
        super(0L, name, description, status);
    }


    public SimpleTask(Long id, String name, String description, StatusEnum status) {
        super(id, name, description, status);
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + "'" +
                ", status='" + getStatus() +
                "'}";
    }
}
