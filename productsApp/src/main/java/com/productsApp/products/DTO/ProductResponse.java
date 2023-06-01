package com.productsApp.products.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponse  implements Serializable {
    @Id
    private String id;
    //    @Field(name="name")
    private String productName;
    //    @Field(name="price")
    private BigDecimal productPrice;
    //    @Field(name="description")
    private String description;
}
