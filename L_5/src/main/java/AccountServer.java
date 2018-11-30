public class AccountServer {

    private int usersLimit = 10;

    public int getUsersLimit () {
        return usersLimit;
    }

    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }
}
