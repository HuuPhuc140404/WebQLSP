package edu.poly.asm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
    private int productId;
    private String name;
    private int quantity;
    private double unitPrice;
}