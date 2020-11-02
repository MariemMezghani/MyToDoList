package com.github.mariemmezghani.mytodolist;

import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
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

    @Test
    public void testAddTask() {
        launchAddTaskActivity();
        onView(withId(R.id.editTextTaskDescription)).perform(typeText("Test"));
        onView(withId(R.id.addButton)).perform(click());

        onView(new RecyclerViewMatcher(R.id.toDoRecyclerView)
                .atPositionOnView(getRVcount()-1,R.id.taskDescription))
                .check(matches(withText("Test")));

    }
    private int getRVcount() {
        RecyclerView recyclerView = (RecyclerView) ActivityScenario.launch(MainActivity.class).findViewById(R.id.toDoRecyclerView);
        return recyclerView.getAdapter().getItemCount();
    }
    public void launchAddTaskActivity(){
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.main)).check(matches(isDisplayed()));
        onView(withId(R.id.add_fab)).perform(click());
        onView(withId(R.id.addTaskActivity)).check(matches(isDisplayed()));
    }
    public  class RecyclerViewMatcher {
        private final int recyclerViewId;

        private RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        private Matcher<View> atPositionOnView(final int position, final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;

                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource name not found)",
                                    new Object[] { Integer.valueOf
                                            (recyclerViewId) });
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {

                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView =
                                (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                        }
                        else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }

                }
            };
        }
    }

}
