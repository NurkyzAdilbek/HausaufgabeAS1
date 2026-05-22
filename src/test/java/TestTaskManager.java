import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import peaksoft.taskManager.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class TestTaskManager {
private TaskManager taskManager;
private Task testTask;


@BeforeEach
    public void setUp() {
    taskManager = new TaskManager();
    testTask = new Task("AS", TaskType.STUDIUM,"Hausaufgabe 1", Priority.HOCH, TaskStatus.IN_BEARBEITUNG, LocalDate.of(2026,06,12));
}

@Test
@DisplayName("Task erfolgreich hinzufügen")
void testAddTask(){
    taskManager.addTask(testTask);
    assertTrue(taskManager.getTasks().contains(testTask));
}
@Test
@DisplayName("Null Task wirft Exception")
    void testAddTaskNull(){
    assertThrows(IllegalArgumentException.class,()->{
        taskManager.addTask(null);
            });
}

@Test
@DisplayName("Get all Tasks ")
    void testGetAllTasks(){
    taskManager.addTask(testTask);
    assertFalse(taskManager.getTasks().isEmpty());
}
@Test
@DisplayName("Task wurde erfolgreich gefunden")
    void testTaskByName(){
    taskManager.addTask(testTask);
   assertNotNull(taskManager.getTaskByName("AS"));
}
@Test
@DisplayName("Null Name wirft Exception")
    void testTaskByNameNull(){
    assertThrows(IllegalArgumentException.class,()->{
        taskManager.addTask(null);
    });
}
@Test
@DisplayName("Leerer Name wirft Exception")
    void testTaskByNameEmpty(){
    assertThrows(IllegalArgumentException.class,()->{
        taskManager.getTaskByName("");
    });
}

@Test
@DisplayName("Null Priority wirft Exception")
    void testTaskByPriorityNull(){
    assertThrows(IllegalArgumentException.class,()->{
        taskManager.getTasksByPriority(null);
    });
}
@Test
@DisplayName("Task wurde erfolgreich bei Priority gefunden")
    void testTaskByPriority(){
    taskManager.addTask(testTask);
   assertFalse(taskManager.getTasksByPriority(testTask.getPriority()).isEmpty());
}

@Test
@DisplayName("Get by Date ")
    void testTaskByDate(){
    taskManager.addTask(testTask);
    assertFalse(taskManager.getTasksByDate(testTask.getFalligkeit()).isEmpty());
}
@Test
@DisplayName("Null Falligkeit wirft Exception")
    void testTaskByFalligkeitNull(){
    assertThrows(IllegalArgumentException.class,()->{
        taskManager.getTasksByDate(null);
    });
}


@Test
@DisplayName("Task in diesem Zeitraum wurde erfolgreich gefunden ")
    void testTaskFromDateTo(){
    taskManager.addTask(testTask);
    assertFalse(taskManager.getTasksByDateFromTo
            (testTask.getFalligkeit(),testTask.getFalligkeit()).isEmpty());

}
@Test
@DisplayName("Null Datum wirft Exception")
    void testTaskFromDateNull(){
    assertThrows(IllegalArgumentException.class,()->{
        taskManager.getTasksByDateFromTo(null,null);
    });
}

@Test
@DisplayName("Kein Task in diesem Zeitraum")
    void testTaskFromDate(){
    taskManager.addTask(testTask);
    assertTrue(taskManager.getTasksByDateFromTo(
            LocalDate.of(2023,02,23),
            LocalDate.of(2024,04,12)
    ).isEmpty());
}

@Test
@DisplayName("Offene Task wurde erfolgreich gefunden")
   void testGetOffenTask(){
    Task offenTask = new Task("Einkaufen",TaskType.HAUSHALT,
            "Brot kaufen",Priority.MITTEL,
            TaskStatus.OFFEN,LocalDate.of(2026,06,12));
    taskManager.addTask(offenTask);
    assertTrue(taskManager.getOffeneTasks().contains(offenTask));
}
@Test
@DisplayName("IN_Bearbeirung Task wurde erfolggreich gefunden")
    void testGetInBearbeirungTask(){
    Task inBearbeuntgTask=new Task("Termin beim Arzt",TaskType.PRIVAT,
            "Kompletter Check Up",Priority.HOCH,TaskStatus.IN_BEARBEITUNG,LocalDate.of(2026,06,12));
}
@Test
    @DisplayName("Type wurde erfolgreich gefunden")
    void testGetByType(){
    taskManager.addTask(testTask);
    assertTrue(taskManager.getTasksByPriority(testTask.getPriority()).contains(testTask));
}
@Test
@DisplayName("Null Type wirft Exception ")
void testGetByTypeNull(){
    assertThrows(IllegalArgumentException.class,()->{
        taskManager.getTasksByPriority(null);
    });
}

@Test
@DisplayName("Status wurde erfolgreich gefunden")
    void testGetByStatus(){
    taskManager.addTask(testTask);
    assertTrue(taskManager.getByStatus(testTask.getStatus()).contains(testTask));
}
@Test
@DisplayName("Null Status wirft Exception")
    void testGetByStatusNull(){
    assertThrows(IllegalArgumentException.class,()->{
        taskManager.getByStatus(null);
    });
}
}
