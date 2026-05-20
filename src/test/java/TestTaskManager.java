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
}
