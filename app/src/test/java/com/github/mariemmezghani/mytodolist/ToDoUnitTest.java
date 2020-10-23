package com.github.mariemmezghani.mytodolist;

import com.github.mariemmezghani.mytodolist.Model.Task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class ToDoUnitTest {
    @Test
    public void evaluateConstructor(){
        Task task = new Task("task1", false);
        assertEquals("task1",task.getDescription());
        assertEquals(false, task.isCompleted());
        assertNotNull(task);
    }

}
