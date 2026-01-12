package core.spring.springcoremoduleproject.Entities;

import core.spring.springcoremoduleproject.Util.HibernateUtility;
import jakarta.persistence.*;

@Entity
@Table(name = "Accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "moneyAmount")
    private double moneyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
    }

    public Account(User user, double moneyAmount) {
        this.user = user;
        this.moneyAmount = moneyAmount;
    }

    public Account(User user) {
        this.user = user;
        this.moneyAmount = 0;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user_id=" + id +
                ", moneyAmount=" + moneyAmount +
                ", user=" + user +
                '}';
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return user != null ? user.getId() : null;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
