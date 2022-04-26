package com.km.socserv.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "t_orders")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {

    public static enum StatusEnum {
        ОТПРАВЛЕНО,
        ВЫПОЛНЯЕТСЯ,
        ВЫПОЛНЕНО,
        ОТМЕНЕНО;

        StatusEnum() {
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Accommodation accommodation;
    @Transient
    private int accommodationId;
    private int accommodationCount;
    private String address;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @ManyToOne
    private User user;
    private String startDate;
    private String finalDate;
    @ManyToOne
    private Executor executor;
    @Transient
    private int executorId;


    public Order(StatusEnum status) {
        this.status = status;
    }
}
