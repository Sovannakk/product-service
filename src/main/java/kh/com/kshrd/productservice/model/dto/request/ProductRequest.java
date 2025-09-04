package kh.com.kshrd.productservice.model.dto.request;

import kh.com.kshrd.productservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private String name;
    private Integer quantity;
    private BigDecimal price;
    private Long categoryId;

    public Product toEntity(){
        return Product.builder()
                .name(this.name)
                .quantity(this.quantity)
                .price(this.price)
                .categoryId(this.categoryId)
                .build();
    }

    public Product toEntity(Long id){
        return Product.builder()
                .id(id)
                .name(this.name)
                .quantity(this.quantity)
                .price(this.price)
                .categoryId(this.categoryId)
                .build();
    }

}
