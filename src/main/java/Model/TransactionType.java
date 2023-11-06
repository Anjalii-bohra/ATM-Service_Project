package Model;

public enum TransactionType {
    DEPOSIT(1, "Deposit"),
    WITHDRAWAL(2, "Withdrawal");

    private final String displayName;

    TransactionType(int value, String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
