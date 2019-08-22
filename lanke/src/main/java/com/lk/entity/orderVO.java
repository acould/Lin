package com.lk.entity;


import lombok.Data;

/**
 * @功能：
 * @date:2019.4.10
 * @author：wgh
 */
@Data
public class orderVO {
    private String ANAME ;
    private String DELIVERY;
    private Integer INTEGRAL;
    private String CONTENT;
    private String PATH;
    private String payInt;
    private String payCash;
    private Double price;
}
