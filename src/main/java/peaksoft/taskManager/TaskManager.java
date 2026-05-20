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
       log.info("Task wurde erfolgleich hinzugefugt: "+task.getTaskName());
    }

    //GET METHODS
    public List<Task> getTasks() {
        return this.tasks;
    }

    public Task getTaskByName(String name) {
        for (Task task:this.tasks) {
            if (task.getTaskName().equalsIgnoreCase(name)){
                return task;
            }

        }
      log.warn(" Kein Task mit Name "+name+" gefunden");
        return null;
    }
    public List<Task> getTasksByPriority(Priority priority) {
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getPriority() == priority){
              ergebnis.add(task);
            }
        }
        if (ergebnis.isEmpty()){
            log.warn(" Keine Tasks mit Priority " + priority+ " wurden gefunden");
        }
        else {
            log.info("Task wurde erfolgleich gefunden");
        }
        return ergebnis;
    }
public List<Task> getTasksByDate(LocalDate date) {
        List<Task> ergebnis =new ArrayList<>();
        for (Task task: this.tasks) {
            if (task.getFalligkeit().equals(date)){
                ergebnis.add(task);
            }
        }
        if (ergebnis.isEmpty()){
            log.warn("Keine Tasks zum "+ date);
        }
        else {
            log.info("Tasks wurden erfolgleich gefunden");
        }
        return ergebnis;
}

    public List<Task> getTasksByDateFromTo(LocalDate from, LocalDate to ) {
        List<Task> ergebnis=new ArrayList<Task>();
        for (Task task:this.tasks) {
            if (!task.getFalligkeit().isBefore(from)&&
            !task.getFalligkeit().isAfter(to)){
                ergebnis.add(task);
            }
        }
        if (ergebnis.isEmpty()){
            log.warn("Keine Tasks von "+from+ "bis "+to+ " gefunden");
        }
        else {
            log.info("Tasks wurden erfolgleich gefunden");
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
      if (ergebnis.isEmpty()){
          log.warn("Keine offenen tasks gefunden");
      }
      else{
          log.info("Tasks wurden erfolgleich gefunden");
      }
        return ergebnis;
    }
    public List<Task>getTasksIN_Bearbeitung(){
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getStatus().equals(TaskStatus.IN_BEARBEITUNG)){
                ergebnis.add(task);
            }
        } if(ergebnis.isEmpty()){
            log.warn("Keine Tasks im Status IN_BEARBEITUNG gefunden");
        }
        else{
        log.info("Tasks wurden erfolgleich gefunden");}
        return ergebnis;
    }
public List<Task> getTasksByType(TaskType type){
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getTaskType().equals(type)){
                ergebnis.add(task);
            }
        }
        if(ergebnis.isEmpty()){
            log.warn("Keine Tasks mit Type "+type+" gefunden");
        }
        else {
            log.info("Tasks wurden erfolgleich gefunden");
        }
        return ergebnis;
}

public List<Task>getByStatus(TaskStatus status){
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getStatus().equals(status)){
                ergebnis.add(task);
            }
        }
        if(ergebnis.isEmpty()){
            log.warn("Keine Tasks mit Status "+status+ " gefunden");
        }
        else {
            log.info("Tasks wurden erfolgleich gefunden");
        }

        return ergebnis;
}

    //UPDATE METHODS
    public void updateStatus(Task gtask, TaskStatus newStatus) {
        boolean gefunden = false;
        for (Task ftask : this.tasks) {
            if (ftask.equals(gtask)) {
                ftask.setStatus(newStatus);
                gefunden = true;
            }
        }
        if (gefunden) {
            fileManager.taskSpeichern(tasks);
            log.info("Status von Task " + gtask.getTaskName() + " geandert  zu " + newStatus);
        }
        else {
            log.warn(" Task "+gtask.getTaskName()+ " nicht gefunden");
        }
    }

public void updatePriority(Task gtask, Priority newPriority) {
       boolean gefunden = false;
        for (Task ftask:this.tasks) {
            if (ftask.equals(gtask)){
                ftask.setPriority(newPriority);
                gefunden = true;
            }
        }
        if (gefunden){
            fileManager.taskSpeichern(this.tasks);
            log.info("Upgrade priority of task " + gtask.getTaskName() + " to " + newPriority);
        }
        else {
            log.warn(" Task "+gtask.getTaskName()+ " nicht gefunden");
        }
        }

public void updateDate(Task gtask, LocalDate newDate) {
       boolean gefunden = false;
        for (Task ftask:this.tasks) {
            if (ftask.equals(gtask)){
                ftask.setFalligkeit(newDate);
                gefunden = true;
            }
        }
        if(gefunden) {
            fileManager.taskSpeichern(this.tasks);
            log.info("Upgrade date of task " + gtask.getTaskName() + " to " + newDate);
        }
        else{
            log.warn(" Task "+gtask.getTaskName()+ " nicht gefunden");
        }
        }

public void searchAndUpdateStatus(String taskName, TaskStatus newStatus) {
       boolean gefunden = false;
        for (Task task:this.tasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName)){
                task.setStatus(newStatus);
                gefunden = true;
            }
        }
        if(gefunden) {
            log.info("Upgrade status of task " + taskName + " to " + newStatus);
            fileManager.taskSpeichern(this.tasks);
        }
        else {
            log.warn(" Task "+taskName+" nicht gefunden");
        }

}



            //DELETE METHODS
    public void deleteTask(Task gtask) {
        int vorher=this.tasks.size();
       this.tasks.removeIf(task->task.equals(gtask));
       boolean gefunden=this.tasks.size()<vorher;
        if (gefunden){
            fileManager.taskSpeichern(this.tasks);
            log.info("Delete task " + gtask.getTaskName());

        }
        else {
            log.warn(" Task "+gtask.getTaskName()+ " nicht gefunden");
        }
        }


    public void deleteTaskByName(String taskName) {

        int vorher=this.tasks.size();
        this.tasks.removeIf(task -> task.getTaskName().equalsIgnoreCase(taskName));
        boolean gefunden=this.tasks.size()<vorher;
        if (gefunden){
            fileManager.taskSpeichern(this.tasks);
            log.info("Delete task " + taskName);
        }
        else {
            log.warn(" Task "+taskName+" nicht gefunden");
        }
    }
public void deleteByPriority(Priority priority){
        int vorher=this.tasks.size();
        this.tasks.removeIf(task -> task.getPriority().equals(priority));
        boolean gefunden=this.tasks.size()<vorher;
        if (gefunden){
            fileManager.taskSpeichern(this.tasks);
            log.info("Delete priority of task " + priority);
        }
        else {
            log.warn(" Task "+priority+" nicht gefunden");
        }
}

public void deleteByStatus(TaskStatus status){
        int vorher=this.tasks.size();
        this.tasks.removeIf(task -> task.getStatus().equals(status));
        boolean gefunden=this.tasks.size()<vorher;
        if (gefunden){
            fileManager.taskSpeichern(this.tasks);
            log.info("Delete status of task " + status);
        }
        else {
            log.warn(" Task "+status+" nicht gefunden");
        }
}

}
