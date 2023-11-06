package Model;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {
    private int customerId;
    private int bankId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private Date dateOfBirth;
    private Date createdAt;

    public Customer(int bankID, int customerId, String firstName, String lastName, String email, String phoneNumber, String address, Date dateOfBirth, Date createdAt) {
        this.bankId = bankID;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getBankId() {
        return bankId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}