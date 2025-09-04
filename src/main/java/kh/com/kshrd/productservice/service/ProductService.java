package kh.com.kshrd.productservice.service;


import kh.com.kshrd.productservice.model.dto.request.ProductRequest;
import kh.com.kshrd.productservice.model.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse create(ProductRequest req);

    ProductResponse update(Long id, ProductRequest req);

    void delete(Long id);
}
