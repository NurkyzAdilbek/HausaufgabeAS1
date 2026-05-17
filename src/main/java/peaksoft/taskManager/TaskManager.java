package peaksoft.taskManager;

import lombok.extern.slf4j.Slf4j;
import peaksoft.FileManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TaskManager {
    private List<Task>tasks;
    private FileManager fileManager;


    public TaskManager() {
        fileManager = new FileManager();
        tasks=fileManager.taskLaden();
    }
  //CREATE METHOD
    public void addTask(Task task) {
       this.tasks.add(task);
       fileManager.taskSpeichern(tasks);
    }

    //GET METHODS
    public List<Task> getTasks() {
        return this.tasks;
    }

    public Task getTaskWithName(String name) {
        for (Task task:this.tasks) {
            if (task.getTaskName().equalsIgnoreCase(name)){
                return task;
            }
        }
        return null;
    }
    public List<Task> getTaskWithPriority(Priority priority) {
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getPriority() == priority){
              ergebnis.add(task);
            }
        }
        return ergebnis;
    }
public List<Task>getTasksWithDate(LocalDate date) {
        List<Task> ergebnis =new ArrayList<>();
        for (Task task: this.tasks) {
            if (task.getFalligkeit().equals(date)){
                ergebnis.add(task);
            }
        }
        return ergebnis;
}

    public List<Task> getTaskWithDateFromTo(LocalDate from, LocalDate to ) {
        List<Task> ergebnis=new ArrayList<Task>();
        for (Task task:this.tasks) {
            if (!task.getFalligkeit().isBefore(from)&&
            !task.getFalligkeit().isAfter(to)){
                ergebnis.add(task);
            }
        }
        return ergebnis;
    }

    public List<Task>getOffeneTasks(){
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if(task.getStatus().equals(TaskStatus.OFFEN)){
                ergebnis.add(task);
            }
        }
        return ergebnis;
    }
    public List<Task>getTaskIN_Bearbeitung(){
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getStatus().equals(TaskStatus.IN_BEARBEITUNG)){
                ergebnis.add(task);
            }
        }
        return ergebnis;
    }
public List<Task>getTaskWithType(TaskType type){
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getTaskType().equals(type)){
                ergebnis.add(task);
            }
        }
        return ergebnis;
}
public List<Task>getWithStatus(TaskStatus status){
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getStatus().equals(status)){
                ergebnis.add(task);
            }
        }
        return ergebnis;
}

    //UPDATE METHODS
    public void updateStatus(Task gtask, TaskStatus newStatus) {
        for (Task ftask:this.tasks) {
            if (ftask.equals(gtask)){
                ftask.setStatus(newStatus);
            }
        }
       fileManager.taskSpeichern(this.tasks);
        log.info("Upgrade status of task " + gtask.getTaskName() + " to " + newStatus);
    }

public void updatePriority(Task gtask, Priority newPriority) {
        for (Task ftask:this.tasks) {
            if (ftask.equals(gtask)){
                ftask.setPriority(newPriority);
            }
        }
        fileManager.taskSpeichern(this.tasks);
        log.info("Upgrade priority of task " + gtask.getTaskName() + " to " + newPriority);
}
public void updateDate(Task gtask, LocalDate newDate) {
        for (Task ftask:this.tasks) {
            if (ftask.equals(gtask)){
                ftask.setFalligkeit(newDate);
            }
        }
        fileManager.taskSpeichern(this.tasks);
        log.info("Upgrade date of task " + gtask.getTaskName() + " to " + newDate);
}

public void searchAndUpdateStatus(String taskName, TaskStatus newStatus) {
        for (Task task:this.tasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName)){
                task.setStatus(newStatus);
            }
        }
        fileManager.taskSpeichern(this.tasks);
}
}
