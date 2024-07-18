package edu.poly.asm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String name;
    private int quantity;
    private double unitPrice;
    private String image;

    private MultipartFile imageFile;

    private String description;
    private double discount;
    private Date enteredDate;
    private short status;
    private Long categoryId;

    private  Boolean isEdit;
}
