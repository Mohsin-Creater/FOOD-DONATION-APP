package food.donation.app;

import java.io.Serializable;

public class Donation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String donorName;
    private String phone;
    private String foodType;
    private String specificFood;
    private String quantity;
    private String location;
    private String notes;

    public Donation(String username, String donorName, String phone, String foodType, String specificFood,
                    String quantity, String location, String notes) {
        this.username = username;
        this.donorName = donorName;
        this.phone = phone;
        this.foodType = foodType;
        this.specificFood = specificFood;
        this.quantity = quantity;
        this.location = location;
        this.notes = notes;
    }

    public String getUsername() {
        return username;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getPhone() {
        return phone;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getSpecificFood() {
        return specificFood;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }
}
