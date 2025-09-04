package kh.com.kshrd.productservice.service.serviceimpl;

import kh.com.kshrd.productservice.client.CategoryClient;
import kh.com.kshrd.productservice.exception.NotFoundException;
import kh.com.kshrd.productservice.model.Product;
import kh.com.kshrd.productservice.model.dto.request.ProductRequest;
import kh.com.kshrd.productservice.model.dto.response.CategoryResponse;
import kh.com.kshrd.productservice.model.dto.response.ProductResponse;
import kh.com.kshrd.productservice.repository.ProductRepository;
import kh.com.kshrd.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryClient categoryClient;

    @Override
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();

        for (Product product : products) {
            ResponseEntity<CategoryResponse> response = categoryClient.findById(product.getCategoryId());
            productResponses.add(product.toResponse(response.getBody()));
        }

        return productResponses;
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product not " + id + " found.")
        );
        ResponseEntity<CategoryResponse> response = categoryClient.findById(product.getCategoryId());
        return product.toResponse(response.getBody());
    }

    @Override
    public ProductResponse create(ProductRequest req) {
        ResponseEntity<CategoryResponse> response = categoryClient.findById(req.getCategoryId());
        return productRepository.save(req.toEntity()).toResponse(response.getBody());
    }

    @Override
    public ProductResponse update(Long id, ProductRequest req) {
        ResponseEntity<CategoryResponse> response = categoryClient.findById(req.getCategoryId());
        return productRepository.save(req.toEntity(id)).toResponse(response.getBody());
    }

    @Override
    public void delete(Long id) {
        findById(id);
        productRepository.deleteById(id);
    }
}
