package com.sparta.myselectshop.service;

import com.sparta.myselectshop.model.Product;
import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

//    @Autowired
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    public Product createProduct(ProductRequestDto requestDto, Long userId) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
        int myprice = requestDto.getMyprice();
        product.setMyprice(myprice);
        productRepository.save(product);
        return product;
    }

    // 사용자용 등록상품 전체조
    public List<Product> getProducts(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    // 관리자용 상품 전체 조회
    public List<Product> getProducts() { return productRepository.findAll(); }
}