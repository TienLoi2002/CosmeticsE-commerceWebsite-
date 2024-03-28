package doanjavanhom4.webbanmypham.entities;

import jakarta.validation.constraints.NotBlank;

public class OrderViewModel {
    @NotBlank(message = "Tên người nhận không được để trống")
    private String nameReceive;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phoneReceive;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String addressReceive;

    private String email;
    private String note;
    private int typePayment;
    private int price;

    public String getNameReceive() {
        return nameReceive;
    }

    public void setNameReceive(String nameReceive) {
        this.nameReceive = nameReceive;
    }

    public String getPhoneReceive() {
        return phoneReceive;
    }

    public void setPhoneReceive(String phoneReceive) {
        this.phoneReceive = phoneReceive;
    }

    public String getAddressReceive() {
        return addressReceive;
    }

    public void setAddressReceive(String addressReceive) {
        this.addressReceive = addressReceive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTypePayment() {
        return typePayment;
    }

    public void setTypePayment(int typePayment) {
        this.typePayment = typePayment;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
