package com.github.mariemmezghani.mytodolist;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class AddTaskTest {
    @Rule
    public ActivityTestRule<AddTaskActivity> testRule =
            new ActivityTestRule<>(AddTaskActivity.class);

    @Test
    public void editTextDisplayedTest() {
        onView(withId(R.id.editTextTaskDescription)).check(matches(isDisplayed()));
    }

    @Test
    public void addActivityDisplayedTest() {
        onView(withId(R.id.addTaskActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void addButtonDisplayedTest() {
        onView(withId(R.id.addButton)).check(matches(isDisplayed()));
    }

}
