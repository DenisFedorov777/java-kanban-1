package main;

import main.tasks.Epic;
import main.tasks.SimpleTask;
import main.tasks.Subtask;

import static main.status.StatusEnum.DONE;
import static main.status.StatusEnum.IN_PROGRESS;
import static main.status.StatusEnum.TODO;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();

        //Создаем две таски
        Long simpleTask1 = manager.add(new SimpleTask("Первый обычный таск","", TODO));
        Long simpleTask2 = manager.add(new SimpleTask("Второй обычный таск","", TODO));

        //Создаем эпик с двумя новыми сабтасками
        Long epic1 = manager.add(new Epic("Мой первый эпик", ""));
        Long subtask1 = manager.add(new Subtask("Первый сабтаск первого эпика","", TODO, epic1));
        Long subtask2 = manager.add(new Subtask("Второй сабтаск первого эпика","", TODO, epic1));

        //Создаем второй эпик с одной сабтаской
        Long epic2 = manager.add(new Epic("Второй эпик", ""));
        Long subtask3 = manager.add(new Subtask("Первый сабтаск второго эпика","", TODO,epic2));

        //Выводим все задачи
        System.out.println(manager.getListEpic());
        System.out.println(manager.getListSimpleTask());
        System.out.println(manager.getListSubtask());

        //Обновляем первый таск, сабтаск первого эпика и обновляем второй эпик
        manager.update(new SimpleTask(simpleTask1, "Новая первая таска", "",IN_PROGRESS));
        manager.update(new Subtask(subtask1, "Новый сабтаск","", DONE, epic1));
        manager.update(new Epic(epic2, "Новый второй эпик",""));

        //Выводим все задачи
        System.out.println();
        System.out.println(manager.getListEpic());
        System.out.println(manager.getListSimpleTask());
        System.out.println(manager.getListSubtask());

        //Удаляем вторую задачу и эпик
        manager.remove(simpleTask2);
        manager.remove(epic2);

    }
}