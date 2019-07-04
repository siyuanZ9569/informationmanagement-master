package com.example.deliveryapp.Model;

public class Good {
    private int id, userId;
    private String origin, description, type;
    private double weight;

    /**
     * Empty constructor
     */
    public Good() {
    }

    /**
     * full constructor. Needs 6 params.
     * @param id
     * @param userId
     * @param origin
     * @param description
     * @param type
     * @param weight
     */
    public Good(int id, int userId, String origin, String description, String type, double weight) {
        this.id = id;
        this.userId = userId;
        this.origin = origin;
        this.description = description;
        this.type = type;
        this.weight = weight;
    }

    /**
     * Default getter and setter
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "id: "+this.getId()+"; userId: "+this.getUserId()+"; orgin: "+this.getOrigin()+"; Description: "+this.getDescription()
                +"; type: "+this.getType()+"; weight: "+this.getWeight();
    }
}
