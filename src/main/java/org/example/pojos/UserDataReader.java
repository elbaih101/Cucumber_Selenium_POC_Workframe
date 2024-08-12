package org.example.pojos;


import org.example.tools.JsonUtils;

/**
 * responsible for managing sessions users on the parralel grand scheme of things
 */
public class UserDataReader {
    private static final String JSON_FILE = "src/main/resources/testdata/admin-Users.json";
    private static User[] users;
    private static int currentIndex = 0;

    static {
      users= JsonUtils.readJsonFromFile(JSON_FILE,User[].class);
    }

    public static synchronized User getNextUser() {
        if (users != null && users.length > 0) {
            currentIndex = (currentIndex + 1) % users.length;
            return users[currentIndex];
        }

        return null;
    }



}

