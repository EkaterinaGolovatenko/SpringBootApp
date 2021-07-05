package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

  private static int currentId = 1;
  private static final ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();

  public static List<Task> getAllTasks() {
    ArrayList<Task> toDoList = new ArrayList<>(tasks.values());
    return toDoList;
  }

  public static int addTask(Task task) {
    int id = currentId++;
    task.setId(id);
    tasks.put(id, task);
    return id;
  }

  public static Task getTask(int taskId) {
    if (tasks.containsKey(taskId)) {
      return tasks.get(taskId);
    }
    return null;
  }

  public static void removeTask(int taskId) {
    if (tasks.containsKey(taskId)) {
      tasks.remove(taskId);
      currentId--;
    }
  }

  public static synchronized void removeAllTasks() {
    Iterator<Entry<Integer, Task>> it = tasks.entrySet().iterator();
    while (it.hasNext()) {
      Entry<Integer, Task> entry = it.next();
      if (entry.getKey() != 0) {
        it.remove();
        currentId--;
      }
    }
  }

  public static Task updateTask(int taskId, Task newTask) {
    if (tasks.containsKey(taskId)) {
      newTask.setId(taskId);
      return tasks.put(taskId, newTask);
    }
    return null;
  }

  public static synchronized void updateAllTasks() {
    Iterator<Entry<Integer, Task>> it = tasks.entrySet().iterator();
    while (it.hasNext()) {
      Entry<Integer, Task> entry = it.next();
      if (entry.getKey() != 0) {
        ArrayList<Task> toDoList = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
          toDoList.add(new Task());
        }
        for (Task task : toDoList) {
          task.setId(entry.getKey());
          tasks.put(entry.getKey(), task);
        }
      }
    }
  }


}
