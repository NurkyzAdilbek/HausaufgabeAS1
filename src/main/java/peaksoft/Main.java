package peaksoft;
import peaksoft.taskManager.*;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
FileManager fileManager = new FileManager();
TaskManager taskManager = new TaskManager();
UserInterface userInterface = new UserInterface();
        Task task = new Task("AS",TaskType.STUDIUM,"Hausaufgabe 1", Priority.HOCH, TaskStatus.IN_BEARBEITUNG, LocalDate.of(2026,06,12));
        Task task2 = new Task("putzen",TaskType.HAUSHALT,"punzen and Mull rausbringen", Priority.NIEDRIG, TaskStatus.OFFEN, LocalDate.of(2026,06,1));
        Task task3 = new Task(" ISDA ",TaskType.STUDIUM,"Vorleistung: Datenbankerstellung", Priority.HOCH, TaskStatus.OFFEN, LocalDate.of(2026,05,20));
        Task task4 = new Task(" Bewerbungsgesprach ",TaskType.SONSTIGES,"Vorbereitung auf neuen Job", Priority.NIEDRIG, TaskStatus.FERTIG, LocalDate.of(2026,9,12));
        Task task5 = new Task(" AS ",TaskType.STUDIUM,"Hausaufgabe 1", Priority.HOCH, TaskStatus.IN_BEARBEITUNG, LocalDate.of(2026,06,12));
      Task task6 = new Task(" AS ",TaskType.STUDIUM,"Hausaufgabe 1", Priority.HOCH, TaskStatus.IN_BEARBEITUNG, LocalDate.of(2026,06,12));
userInterface.start();

    }
}