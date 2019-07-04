package com.yinchuan.yunshu.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="storages")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
    /**
     * The id of the storage. Automatically created when the good is created
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * The name of a good in the storage.
     */
    @Column(name = "type")
    private String type;

    /**
     * The id of which user that good in storage belongs to.
     */
    @Column(name = "user_id")
    private int userId;

    /**
     * The weight of the good in the storage
     */
    @Column(name = "weight")
    private double weight;

    /**
     * The description of the good in the storage
     */
    private String description;

    /**
     * The origin place where the good is from
     */
    private String origin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Edit description of the good.
     * @param description
     */
    public void editDescription(String description){
        this.description=description;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", userId=" + userId +
                ", weight=" + weight +
                ", description='" + description + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }
}
