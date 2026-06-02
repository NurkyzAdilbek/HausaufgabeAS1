package peaksoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import peaksoft.taskManager.*;

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
           System.out.println("1. Aufgaben anzeigen");
           System.out.println("2. Neue Aufgaben erstellen");
           System.out.println("3. Aufgaben suchen");
           System.out.println("4. Aufgabe aktualisieren");
           System.out.println("5. Aufgabe loschen");
           System.out.println("6.Statistik anzeigen");
           System.out.println("0. Beenden");
           int wahlen=scanner.nextInt();
           switch (wahlen) {
               case 1->aufgabenZeigen();
               case 2->neueAufgabenErstellen();
               case 3->aufgabenSuchen();
              // case 4->
             //  case 5->
               case 0->gehtLos=false;
               default -> System.out.println("Ungultige Eingabe!");
           }

       }

   }

public void aufgabenZeigen(){
       boolean gehtLos=true;
    log.info("Userin hat alle Tasks abgerufen");
    System.out.println("-----Aufgaben anzeigen--------");
    System.out.println("1. Alle Aufgaben anzeigen" );
    System.out.println("2. Offene Aufgaben anzeigen" );
    System.out.println("3 Aufgaben, die ich daran arbeite" );
    System.out.println( "4 Überfällige Aufgaben anzeigen" );
    System.out.println( "5 Aufgaben dieser  Woche anzeigen" );
    System.out.println("0 Beenden");
    int wahlen=scanner.nextInt();
    switch (wahlen) {
        case 1->zeigeTasks(taskManager.getTasks());
        case 2->zeigeTasks(taskManager.getOffeneTasks());
        case 3->zeigeTasks(taskManager.getTasksIN_Bearbeitung());
        case 4->zeigeTasks(taskManager.getUberfalligeTasks() );
        case 5->zeigeTasks(taskManager.getTasksOfWeek() );
        case 0->gehtLos=false;
        default -> System.out.println("Ungultige Eingabe!");
    }
}
private void zeigeTasks(List<Task>tasks){
       if (tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden, geniss es!!!");
       }
       else{
           for (Task task : tasks) {
               System.out.println(task);
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
public void aufgabenSuchen() {
    boolean gehtlos = true;
    while (gehtlos){
        log.info("Userin mochte aufgabenSuchen");
        System.out.println("-------Aufgaben suchen--------");
        System.out.println("1 Suchen nach Name");
        System.out.println("2 Suchen nach Priority");
        System.out.println("3 Suchen nach Datum");
        System.out.println("4 Suchen nach Type");
        System.out.println("5 Suchen nach Status");
        System.out.println("0 Beenden");

        int wahlen=scanner.nextInt();
        switch (wahlen) {
            case 1->suchenName();
            case 2->suchenPriority();
            case 5-> sucheByStatus();
            case 4->suchenType();
            case 3->sucheByDate();
            case 0-> gehtlos=false;
            default -> System.out.println("Ungultige Eingabe!");
        }
    }
}
private void suchenName(){
    System.out.println("Geben Sie Name ein");
    String name = scanner.next();
    Task task=taskManager.getTaskByName(name);
    System.out.println(task);
}
private void suchenPriority(){
    System.out.println("------Priority Suchen------ ");
    System.out.println("1. Alle Aufgaben mit Priority");
    System.out.println("2. Offene Aufgaben mit Priority");
    System.out.println("3. Aufgaben nach Type und Priority");
    int wahlen=scanner.nextInt();
    switch (wahlen){
        case 1->suchallePriority();
        case 2-> offeneTasksMitPriority();
        case 3->typeAndPriority();
        default -> System.out.println("Ungtige Eingabe!");


    }
}
private Priority lesePriority(){
    System.out.println("----Priority----");
    System.out.println("1 HOCH");
    System.out.println("2 MITTEL");
    System.out.println("3 NIEDRIG");
    int wahlen=scanner.nextInt();
   return switch (wahlen){
        case 1->Priority.HOCH;
        case 2->Priority.MITTEL;
        default -> Priority.NIEDRIG;
    };
}
private TaskType leseType(){
    System.out.println("-----Type-----");
    System.out.println("1 STUDIUM");
    System.out.println("2 HAUSHALT");
    System.out.println("3 PRIVAT");
    System.out.println("4 SONSTIGES");
    int wahlen=scanner.nextInt();
    return switch (wahlen){
        case 1->TaskType.STUDIUM;
        case 2->TaskType.HAUSHALT;
        case 3 -> TaskType.PRIVAT;
        default -> TaskType.SONSTIGES;

    };
}
private TaskStatus leseStatus(){
    System.out.println("-----Status----");
       System.out.println("1 OFFEN");
       System.out.println("2 IN BEARBEITUNG");
       System.out.println("3 FERTIG");
       int wahlen=scanner.nextInt();
      return switch (wahlen){
           case 1->TaskStatus.OFFEN;
           case 2->TaskStatus.IN_BEARBEITUNG;
           default -> TaskStatus.FERTIG;
       };

}
private void suchallePriority(){
       Priority lesePriority=lesePriority();
       List<Task>tasks= taskManager.getTasksByPriority(lesePriority);
       for(Task task:tasks){
           System.out.println(task);
       }
}
private void offeneTasksMitPriority(){
       Priority lesePriority=lesePriority();
       List<Task>tasks= taskManager.getOffeneTasksByPriority(lesePriority);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden, geniss es!!!");
           return;
       }
       for(Task task:tasks){
           System.out.println(task);
       }
}
private void typeAndPriority(){
       Priority priority=lesePriority();
       TaskType taskType=leseType();
       List<Task>tasks=taskManager.getTasksbyTypeAndPriority(taskType,priority);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden");
       }
       for(Task task:tasks){
           System.out.println(task);
       }
}
private void sucheByStatus(){
       TaskStatus status=leseStatus();
       List<Task>tasks=taskManager.getByStatus(status);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden");
       }
       for (Task task:tasks){
           System.out.println(task);
       }

}
private void suchenType(){
            System.out.println("------Type Suchen------ ");
            System.out.println("1. Alle Aufgaben mit Type");
            System.out.println("2. Aufgaben nach Type und Status");
            int wahlen=scanner.nextInt();
            switch (wahlen){
                case 1->sucheByType();
                case 2-> suchByTypeAndStatus();
                default -> System.out.println("Ungtige Eingabe!");
            }

    }
private void sucheByType(){
       TaskType taskType=leseType();
       List<Task>tasks=taskManager.getTasksByType(taskType);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden");
       }
       for (Task task:tasks){
           System.out.println(task);
       }
}
private void suchByTypeAndStatus(){
       TaskType taskType=leseType();
       TaskStatus status=leseStatus();
       List<Task>tasks=taskManager.getTasksByTypeAndStatus(taskType,status);
       if(tasks.isEmpty()){
           System.out.println("Keine Aufgaben vorhanden");
       }
       for (Task task:tasks){
           System.out.println(task);
       }
}
private void sucheByDate(){
    System.out.println("-----Datum suchen-----");
    System.out.println("1 Bestimmtes Datum");
    System.out.println("2 Zeitraum");
    int wahlen=scanner.nextInt();
    switch (wahlen){
        case 1->sucheBestimDatum();
        case 2->zeitraumSuchen();
    }
}
private void sucheBestimDatum(){
    System.out.println("Welches DAtum suchen Sie?");
    System.out.println("Geben Sie Datum ein YYYY-MM-DD");
    LocalDate localDate=LocalDate.parse(scanner.next());
    List<Task>tasks=taskManager.getTasksByDate(localDate);
    if(tasks.isEmpty()){
        System.out.println("Keine Aufgaben vorhanden");
    }
    for (Task task:tasks){
        System.out.println(task);
    }
}
private void zeitraumSuchen(){
    System.out.println("Von Datum YYYY-MM-DD");
    LocalDate from=LocalDate.parse(scanner.next());
    System.out.println("bis DAtum YYYY-MM-DD");
    LocalDate to=LocalDate.parse(scanner.next());
    List<Task>tasks=taskManager.getTasksByDateFromTo(from,to);
    for(Task task:tasks){
        System.out.println(task);
    }
}

public void aufgabenAktualisieren(){

}

}