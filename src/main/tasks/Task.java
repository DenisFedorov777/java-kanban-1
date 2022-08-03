package main.tasks;

import main.status.StatusEnum;

/**
 * Класс - родитель, описывает универсальный объект "задача"
 */
public class Task {
    private Long id;
    private String name;
    private String description;
    private StatusEnum status;

    public Task(Long id, String name, String description, StatusEnum status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public StatusEnum getStatus() {
        return status;
    }
}
