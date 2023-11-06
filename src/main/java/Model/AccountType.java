package Model;
public enum AccountType {
    CURRENT(2, "Current"),
    SAVINGS(1, "Savings");
    private final int value;
    private final String type;

    AccountType(int value, String type) {
        this.value = value;
        this.type = type;
    }
//    public int getValue() {
//        return value;
//    }
    public String getType() {
        return type;
    }
    public static AccountType getByValue(int value) {
        for (AccountType accountType : values()) {
            if (accountType.value == value) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("Invalid AccountType value: " + value);
    }
}
