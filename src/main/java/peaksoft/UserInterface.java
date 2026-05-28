package peaksoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import peaksoft.taskManager.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private static final Logger log = LoggerFactory.getLogger(UserInterface.class);
    private TaskManager taskManager;
   private Scanner scanner;

   public UserInterface() {
       taskManager = new TaskManager();
       scanner = new Scanner(System.in);
   }
   public void start() {
       boolean gehtLos=true;
       while(gehtLos) {
           System.out.println("|n-----TO DO LISTE-----");
           System.out.println("1. Alle Aufgaben anzeigen");
           System.out.println("2. Neue Aufgaben erstellen");
           System.out.println("3. Aufgaben suchen");
           System.out.println("4. Aufgabe aktualisieren");
           System.out.println("5. Aufgabe loschen");
           System.out.println("6.Statistik anzeigen");
           System.out.println("0. Beenden");
           int wahlen=scanner.nextInt();
           switch (wahlen) {
               case 1->alleAufgabenZeigen();
               case 2->neueAufgabenErstellen();
              // case 3->
              // case 4->
             //  case 5->
               case 0->gehtLos=false;
               default -> System.out.println("Ungultige Eingabe!");
           }

       }

   }

private void alleAufgabenZeigen(){
    log.info("Userin hat alle Tasks abgerufen");
    List<Task> tasks = taskManager.getTasks();
    if (tasks.isEmpty()){
        System.out.println("Keine Aufgaben vorhanden, geniss es!!!");
    }
    else {
        for (Task task : tasks) {
            System.out.println(" Deine Aufgaben:  ");
            System.out.println(" Name: "+task.getTaskName());
            System.out.println("Typ: "+task.getTaskType());
            System.out.println("Description: "+task.getTaskDescription());
            System.out.println("Priority: "+task.getPriority());
            System.out.println("Status: "+task.getStatus());
            System.out.println("Datum "+task.getFalligkeit());
        }
    }
}
private void neueAufgabenErstellen(){
       log.info("Userin mochte neue Aufgabe erstellen");
    System.out.println("Name von Task: ");
    String taskName = scanner.next();
    System.out.println("Type von Task wahlen:  1-STUDIUM, 2-HAUSHALT, 3-PRIVAT, 4->SONSTIGES ");
    int taskType = scanner.nextInt();
   TaskType type= switch (taskType) {
        case 1-> TaskType.STUDIUM;
        case 2->TaskType.HAUSHALT;
        case 3->TaskType.PRIVAT;
       default ->TaskType.SONSTIGES;
    };

    System.out.println("Beschreibung von Task: ");
    String taskDescription = scanner.next();

    System.out.println( "Priority von Task:  1-HOCH, 2-MITTEL, 3-NIEDRIG");
    int priority = scanner.nextInt();
    Priority p=switch (priority){
        case 1-> Priority.HOCH;
        case 2-> Priority.MITTEL;
        default-> Priority.NIEDRIG;
    };

    System.out.println( "Status von Task:  1-OFFEN, 2-IN BEARBEITUNG, 3-FERTIG");
int status = scanner.nextInt();
TaskStatus s=switch (status){
    case 1-> TaskStatus.OFFEN;
    case 2->TaskStatus.IN_BEARBEITUNG;
    default ->TaskStatus.FERTIG;
};

    System.out.println("Falligkeitsdatum (YYYY-MM-DD): ");
    String falligkeitsdatum = scanner.next();
    LocalDate datum=LocalDate.parse(falligkeitsdatum);

    Task task=new Task(taskName,type,taskDescription,p,s,datum);
    taskManager.addTask(task);
    System.out.println("Aufgabe erfolgreich erstellt");

   }



}