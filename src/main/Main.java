package main;

import main.managers.*;
import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;

import static main.status.StatusEnum.DONE;
import static main.status.StatusEnum.IN_PROGRESS;
import static main.status.StatusEnum.TODO;

public class Main {

    public static void main(String[] args) {

        Managers managers = new Managers();

        TaskManager taskManager = managers.getDefault();

        //Создаем две таски
        Long simpleTask1 = taskManager.add(new SimpleTask("Первый обычный таск","", TODO));
        Long simpleTask2 = taskManager.add(new SimpleTask("Второй обычный таск","", TODO));

        //Создаем эпик с двумя новыми сабтасками
        Long epic1 = taskManager.add(new Epic("Мой первый эпик", ""));
        Long subtask1 = taskManager.add(new Subtask("Первый сабтаск первого эпика","", TODO, epic1));
        Long subtask2 = taskManager.add(new Subtask("Второй сабтаск первого эпика","", TODO, epic1));

        //Создаем второй эпик с одной сабтаской
        Long epic2 = taskManager.add(new Epic("Второй эпик", ""));
        Long subtask3 = taskManager.add(new Subtask("Первый сабтаск второго эпика","", TODO,epic2));

        taskManager.getTaskByID(1L);
        System.out.println("Печатаем историю поиска");
        System.out.println(managers.getDefaultHistory());

        taskManager.getEpicByID(3L);
        System.out.println("Печатаем историю поиска");
        System.out.println(managers.getDefaultHistory());

        taskManager.getSubtaskByID(4L);
        System.out.println("Печатаем историю поиска");
        System.out.println(managers.getDefaultHistory());

        System.out.println("Печатаем все задачи");
        System.out.println(taskManager.getListEpic());
        System.out.println(taskManager.getListSimpleTask());
        System.out.println(taskManager.getListSubtask());

        //Обновляем первый таск, сабтаск первого эпика и обновляем второй эпик
        taskManager.update(new SimpleTask(simpleTask1, "Новая первая таска", "",IN_PROGRESS));
        taskManager.update(new Subtask(subtask1, "Новый сабтаск","", DONE, epic1));
        taskManager.update(new Epic(epic2, "Новый второй эпик",""));

        System.out.println("Печатаем все задачи");
        System.out.println(taskManager.getListEpic());
        System.out.println(taskManager.getListSimpleTask());
        System.out.println(taskManager.getListSubtask());

        //Удаляем вторую задачу и эпик
        taskManager.remove(simpleTask2);
        taskManager.remove(epic2);

    }
}