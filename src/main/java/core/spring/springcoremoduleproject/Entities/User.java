package core.spring.springcoremoduleproject.Entities;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final int id;
    private final String login;
    private final List<Account> accountList = new ArrayList<>();

    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ",\naccountList=" + accountList +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}
