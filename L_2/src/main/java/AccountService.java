import java.util.HashMap;

public class AccountService {
    private final HashMap<String, UserProfile> users = new HashMap<>();

    public void addNewUser(UserProfile userProfile) {
        users.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return users.get(login);
    }

}
