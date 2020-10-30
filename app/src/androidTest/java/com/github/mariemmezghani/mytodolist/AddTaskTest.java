package com.github.mariemmezghani.mytodolist;

import android.app.Activity;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

    @Test
    public void testAddingNoTask(){
        launchAddTaskActivity();
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));
    }
    public void launchAddTaskActivity(){
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.main)).check(matches(isDisplayed()));
        onView(withId(R.id.add_fab)).perform(click());
        onView(withId(R.id.addTaskActivity)).check(matches(isDisplayed()));
    }

    }
