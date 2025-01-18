package vttp_paf_day24l.vttp_paf_day24l.model;

public class BankAccount {

    private int id;
    private String fullName;
    private Boolean isActive;
    private Float balance;

    public BankAccount() {

    }

    

    public BankAccount(int id, String fullName, Boolean isActive, Float balance) {
        this.id = id;
        this.fullName = fullName;
        this.isActive = isActive;
        this.balance = balance;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    


    
    
}
