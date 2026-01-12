package core.spring.springcoremoduleproject.Services;

import core.spring.springcoremoduleproject.Entities.Account;
import core.spring.springcoremoduleproject.Entities.User;
import core.spring.springcoremoduleproject.Util.HibernateUtility;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Value("${account.default-amount}")
    private double defaultAmount;

    @Autowired
    private HibernateUtility hibernateUtility;

    public void createAccountNewUser(User user, Session session) {
        Account newAccount = new Account(user, defaultAmount);
        session.persist(newAccount);
    }

    public void createAccount(User user) {
        Account newAccount = new Account(user);

        hibernateUtility.executeTransaction(session -> {session.persist(newAccount);});
    }

    public Account findAccountById(int accountId) {
        List<Account> accounts = hibernateUtility.executeTransaction(session -> {
            return session
                    .createQuery("SELECT a FROM Account a LEFT JOIN FETCH a.user WHERE a.id = :id", Account.class)
                    .setParameter("id", accountId)
                    .getResultList();
        });
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    public void plusMoney (Account account, double plusMoney){

        if (plusMoney >= 0) {
            account.setMoneyAmount(account.getMoneyAmount() + plusMoney);
            hibernateUtility.executeTransaction(session -> {session.update(account);});
        } else {
            throw new IllegalArgumentException("Invalid amount: " + plusMoney);
        }

    }

    public void minusMoney (Account account, double minusMoney){
        double acceptableAmount = (account.getMoneyAmount() - minusMoney);
        if (acceptableAmount >= 0 && minusMoney >= 0) {
            account.setMoneyAmount(acceptableAmount);
            hibernateUtility.executeTransaction(session -> {session.update(account);});
        } else {
            throw new IllegalArgumentException("Invalid amount: " + minusMoney);
        }
    }

}
