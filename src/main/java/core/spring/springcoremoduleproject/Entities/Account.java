package core.spring.springcoremoduleproject.Entities;

public class Account {

    private final int id;
    private final int userId;
    private double moneyAmount;

    public Account(int id, int userId, double moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public Account(int id, int userId) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = 0;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }

    public void plusMoney (double plusMoney){
        moneyAmount += plusMoney;
    }

    public void minusMoney (double minusMoney){
        moneyAmount -= minusMoney;
    }

    public int getId() {
        return id;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public int getUserId() {
        return userId;
    }
}
