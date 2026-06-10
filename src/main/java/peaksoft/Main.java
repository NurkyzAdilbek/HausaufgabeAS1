package peaksoft;
import peaksoft.taskManager.*;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
TaskManager taskManager = new TaskManager();
UserInterface userInterface = new UserInterface();
        Task task = new Task("AS",TaskType.STUDIUM,"Hausaufgabe 1", Priority.HOCH, TaskStatus.IN_BEARBEITUNG, LocalDate.of(2026,06,12));
        Task task2 = new Task("putzen",TaskType.HAUSHALT,"punzen and Mull rausbringen", Priority.NIEDRIG, TaskStatus.OFFEN, LocalDate.of(2026,06,1));
        Task task3 = new Task(" ISDA ",TaskType.STUDIUM,"Vorleistung: Datenbankerstellung", Priority.HOCH, TaskStatus.OFFEN, LocalDate.of(2026,05,20));
        Task task4 = new Task(" Bewerbungsgesprach ",TaskType.SONSTIGES,"Vorbereitung auf neuen Job", Priority.NIEDRIG, TaskStatus.FERTIG, LocalDate.of(2026,9,12));
        Task task5 = new Task("AS",TaskType.STUDIUM,"Hausaufgabe 2", Priority.HOCH, TaskStatus.IN_BEARBEITUNG, LocalDate.of(2026,06,22));
      Task task6 = new Task(" Statistik I",TaskType.STUDIUM,"Wahrscheinlichkeiten", Priority.HOCH, TaskStatus.IN_BEARBEITUNG, LocalDate.of(2026,06,9));
      Task task7 = new Task("SQL",TaskType.STUDIUM,"HA 3", Priority.HOCH, TaskStatus.OFFEN, LocalDate.of(2026,06,20));
      Task task8 = new Task("IuF",TaskType.STUDIUM,"HA 5", Priority.MITTEL, TaskStatus.FERTIG, LocalDate.of(2026,06,12));
      Task task9 = new Task("Zahnartzt",TaskType.SONSTIGES,"Zahnreinigung", Priority.HOCH, TaskStatus.OFFEN, LocalDate.of(2026,06,25));
      Task task10 = new Task("Finanzplanung",TaskType.SONSTIGES,"bei Horbach", Priority.NIEDRIG, TaskStatus.OFFEN, LocalDate.of(2026,06,28));

       if(taskManager.getTasks().isEmpty()) {
taskManager.addTask(task);
taskManager.addTask(task2);
taskManager.addTask(task3);
taskManager.addTask(task4);
taskManager.addTask(task5);
taskManager.addTask(task6);
taskManager.addTask(task7);
taskManager.addTask(task8);
taskManager.addTask(task9);
taskManager.addTask(task10);
       }
        userInterface.start();
    }
}