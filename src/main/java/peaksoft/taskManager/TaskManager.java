package peaksoft.taskManager;


import lombok.extern.slf4j.Slf4j;
import peaksoft.FileManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

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
        if (task==null){
            throw new IllegalArgumentException("Task is null");
        }
       this.tasks.add(task);
       fileManager.taskSpeichern(tasks);
       log.info("Task wurde erfolgleich hinzugefugt: "+task.getTaskName());
    }

    //GET METHODS
    public List<Task> getTasks() {
        return this.tasks;
    }
    public Task getTaskByName(String name) {
        if (name==null||name.isEmpty()){
            throw new IllegalArgumentException("Name ist null oder leer");
        }
        for (Task task:this.tasks) {
            if (task.getTaskName().equalsIgnoreCase(name)){
                return task;
            }
        }
      log.warn(" Kein Task mit Name "+name+" gefunden");
        return null;
    }
    public List<Task> getTasksByPriority(Priority priority) {
        if (priority==null){
            throw new IllegalArgumentException("Priority is null");
        }
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
        if (date==null){
            throw new IllegalArgumentException("Date is null");
        }
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
        if (from==null||to==null){
            throw new IllegalArgumentException("From and To are null");
        }
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
        if (type==null){
            throw new IllegalArgumentException("Type is null");
        }
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
        if (status==null){
            throw new IllegalArgumentException("Status is null");
        }
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
    public List<Task>getOffeneTasksByPriority(Priority priority){
        if (priority==null){
            throw new IllegalArgumentException("Priority is null");
        }
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getStatus().equals(TaskStatus.OFFEN)&&task.getPriority().equals(priority)){
                ergebnis.add(task);
            }
        }
        if (ergebnis.isEmpty()){
            log.warn("Keine Tasks mit Priority "+priority+" gefunden");
        }
        else {
            log.info("Tasks wurden erfolgleich gefunden");
        }
        return  ergebnis;

}
    public List<Task>getTasksByTypeAndStatus(TaskType type,TaskStatus status){
        if (type==null||status==null){
            throw new IllegalArgumentException("Type and status are null");
        }
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getStatus().equals(status)&&task.getTaskType().equals(type)){
                ergebnis.add(task);
            }
        }
        if (ergebnis.isEmpty()){
            log.warn("Keine Tasks mit Status "+status+"und "+type +" gefunden");
        }
        else {
            log.info("Tasks wurden erfolgleich gefunden");
        }
        return ergebnis;
}
    public List<Task>getUberfalligeTasks(){
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getFalligkeit().isBefore(LocalDate.now())){
                ergebnis.add(task);
            }
        }
        if (ergebnis.isEmpty()){
            log.warn(" Keine uberfalligen Tasks wurden gefunden");
        }
        else {
            log.info("Tasks wurden erfolgleich gefunden");
        }
        return ergebnis;
}
    public List<Task> getTasksOfWeek(){

        List<Task>ergebnis=new ArrayList<>();
        LocalDate date=LocalDate.now();
        LocalDate wochenende=date.plusDays(7);
        for (Task task:this.tasks) {
            if(task.getFalligkeit().isAfter(date)&&task.getFalligkeit().isBefore(wochenende)){
                ergebnis.add(task);
            }
        }
        if (ergebnis.isEmpty()){
            log.warn("Keine Tasks fur diese Woche wurden gefunden");
        }
        else{
            log.info("Tasks wurden erfolgleich gefunden");
        }
        return  ergebnis;
}
    public List<Task>getTasksbyTypeAndPriority(TaskType type,Priority priority){
        if (type==null||priority==null){
            throw new IllegalArgumentException("Type and priority are null");
        }
        List<Task>ergebnis=new ArrayList<>();
        for (Task task:this.tasks) {
            if (task.getTaskType().equals(type)
            &&task.getPriority().equals(priority)){
                ergebnis.add(task);
            }
        }
        if (ergebnis.isEmpty()){
            log.warn("Keine Tasks mit Type "+type+ " and "+priority +" gefunden");
        }
        else {
            log.info("Tasks wurden erfolgleich gefunden");
        }
        return ergebnis;
}
    public String getMotivation(){
        List<String>satze=fileManager.motivationLaden();
        if(satze.isEmpty()){
            return "Super gemacht";
        }
        Random random=new Random();
        int zufall=random.nextInt(satze.size());
        return satze.get(zufall);
}
    public void taskAlsErledingtMarkieren(String taskname){
        Task task=getTaskByName(taskname);
        if (task!=null){
            task.setStatus(TaskStatus.FERTIG);
            fileManager.taskSpeichern(this.tasks);
            log.info("Task "+taskname+ " als Fertig erledigt markiert");
            System.out.println("Aufgabe erledingt!");
            System.out.println(getMotivation());
        }
        else {
            log.warn("Task "+taskname+" nicht gefunden");
            System.out.println("Aufgabe mit Name"+taskname+ " wurde nicht gefunden");
        }
}
    //UPDATE METHODS
    public void updateStatus(Task gtask, TaskStatus newStatus) {
        if(gtask==null||newStatus==null){
            throw new IllegalArgumentException("Eingaben durfen nicht null sein");
        }
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
       if(gtask==null||newPriority==null){
           throw new IllegalArgumentException("Eingaben durfen nicht null sein");
       }
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
       if (gtask==null||newDate==null){
           throw new IllegalArgumentException("Task der Date darf nicht null sein");
       }
       Task task=getTaskByName(gtask.getTaskName());
        if(task!=null) {
            task.setFalligkeit(newDate);
            fileManager.taskSpeichern(this.tasks);
            log.info("Upgrade date of task " + gtask.getTaskName() + " to " + newDate);
        }
        else{
            log.warn(" Task "+gtask.getTaskName()+ " nicht gefunden");
        }
        }
    public void getByNameAndUpdateStatus(String taskName, TaskStatus newStatus) {
       if (taskName==null|| taskName.isEmpty()||newStatus==null){
           throw new IllegalArgumentException("Name darf nicht leer sein");
       }
       Task task=getTaskByName(taskName);
        if(task!=null) {
            task.setStatus(newStatus);
            fileManager.taskSpeichern(this.tasks);
            log.info("Upgrade status of task " + taskName + " to " + newStatus);
        }
        else {
            log.warn(" Task "+taskName+" nicht gefunden");
        }

}




            //DELETE METHODS
    public void deleteTask(Task gtask) {
        if (gtask==null){
            throw new IllegalArgumentException("Eingabe darf nicht leer sein");
        }
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
        if (taskName==null||taskName.isEmpty()){
            throw new IllegalArgumentException("Eingabe darf nicht leer sein");
        }
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
    if (priority==null){
        throw new IllegalArgumentException("Eingabe darf nicht leer sein");
    }
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
        if(status==null){
            throw new IllegalArgumentException("Eingabe darf nicht leer sein");
        }
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

//Statistik
    public List<Task>sortierenNachFalligkeit(){
        List<Task>sortierteTasks=new ArrayList<>(this.tasks);
        sortierteTasks.sort(Comparator.comparing(Task::getFalligkeit));
        log.info("Ergolgreich sortiert");
return sortierteTasks;
    }
    public List<Task>sortierenNachStatus(){
        List<Task>sortierteTasks=new ArrayList<>(this.tasks);
        sortierteTasks.sort(Comparator.comparing(Task::getStatus));
        log.info("Ergolgreich sortiert");
        return sortierteTasks;
    }
    public List<Task>sortierenNachPriority(){
        List<Task>sortierteTasks=new ArrayList<>(this.tasks);
        sortierteTasks.sort(Comparator.comparing(Task::getPriority));
        log.info("Ergolgreich sortiert");
        return sortierteTasks;
    }
    public List<Task>sortierenNachDAtumUndPriority(){
        List<Task>sortierteTasks=new ArrayList<>(this.tasks);
        sortierteTasks.sort(Comparator.comparing(Task::getFalligkeit).thenComparing(Task::getPriority));
        log.info("Ergolgreich sortiert");
        return sortierteTasks;
    }
    public List<Task>erledigteAufgaben(){
        List<Task>erledigteTasks=new ArrayList<>();
        for(Task task:this.tasks){
            if (task.getStatus().equals(TaskStatus.FERTIG)){
                erledigteTasks.add(task);
            }
        }
       // log.info("Ergolgreich sortiert");
        return  erledigteTasks;
    }
    public int getAnzahlOffeneTasks(){
        int counter=0;
        for (Task task:this.tasks){
            if (task.getStatus().equals(TaskStatus.OFFEN)){
                counter++;
            }
        }
        return counter;
}
    public int getAnzahlErledingteAufgabe(){
        int counter=0;
        for (Task task:this.tasks){
            if (task.getStatus().equals(TaskStatus.FERTIG)){
                counter++;
            }
        }
        return counter;
}

}
