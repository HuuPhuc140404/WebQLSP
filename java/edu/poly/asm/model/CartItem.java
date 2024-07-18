package edu.poly.asm.model;

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
