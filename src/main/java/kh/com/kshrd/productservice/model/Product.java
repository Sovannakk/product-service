package kh.com.kshrd.productservice.model;

import jakarta.persistence.*;
import kh.com.kshrd.productservice.model.dto.response.CategoryResponse;
import kh.com.kshrd.productservice.model.dto.response.ProductResponse;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer quantity;
    private BigDecimal price;

    private Long categoryId;

    public ProductResponse toResponse(CategoryResponse categoryResponse){
        return ProductResponse.builder()
                .id(this.id)
                .name(this.name)
                .quantity(this.quantity)
                .price(this.price)
                .categoryResponse(categoryResponse)
                .build();
    }

}
