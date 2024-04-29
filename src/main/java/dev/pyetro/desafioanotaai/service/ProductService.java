package dev.pyetro.desafioanotaai.service;

import dev.pyetro.desafioanotaai.domain.category.Category;
import dev.pyetro.desafioanotaai.domain.products.exception.ProductNotFoundException;
import dev.pyetro.desafioanotaai.domain.category.exception.CategoryNotFoundException;
import dev.pyetro.desafioanotaai.domain.products.Product;
import dev.pyetro.desafioanotaai.domain.products.ProductDTO;
import dev.pyetro.desafioanotaai.repositories.ProductRepository;
import dev.pyetro.desafioanotaai.service.aws.AwsSnsService;
import dev.pyetro.desafioanotaai.service.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;
    private final AwsSnsService snsService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, AwsSnsService snsService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.snsService = snsService;
    }

    public Product insert(ProductDTO productData){
        Category category = this.categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);

        this.productRepository.save(newProduct);
        this.snsService.publish(new MessageDTO(newProduct.toString()));

        return newProduct;
    }

    public Product update(String id, ProductDTO productData){
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if(productData.categoryId() != null) {
            this.categoryService.getById(productData.categoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            product.setCategory(productData.categoryId());
        }

        if(!productData.tittle().isEmpty()) product.setTitle(productData.tittle());
        if(!productData.description().isEmpty()) product.setDescription(productData.description());
        if(!(productData.price() == null)) product.setPrice(productData.price());


        this.productRepository.save(product);

        this.snsService.publish(new MessageDTO(product.toString()));

        return product;
    }

    public void delete(String id){
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.productRepository.delete(product);
    }

    public List<Product> getAll(){
        return this.productRepository.findAll();
    }
}
