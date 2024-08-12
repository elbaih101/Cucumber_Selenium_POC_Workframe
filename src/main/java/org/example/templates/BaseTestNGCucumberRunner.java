package org.example.templates;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.example.pojos.User;
import org.example.pojos.UserDataReader;

@CucumberOptions
        (

                features = "src/main/resources/features",
                glue = {"org.example.stepDefs"}
        )
public class BaseTestNGCucumberRunner extends AbstractTestNGCucumberTests
{

    protected static final ThreadLocal<User> user = new ThreadLocal<>();

    static
    {
        setUser(UserDataReader.getNextUser());
    }

    /**
     * sets the user for individual runner class
     *
     * @param value User the user
     */
    public static void setUser(User value)
    {
        user.set(value);
    }

    /**
     * gets the user for the individual runner class
     *
     * @return User the user for the runner
     */
    public static User getUSer()
    {
        return user.get();
    }
}