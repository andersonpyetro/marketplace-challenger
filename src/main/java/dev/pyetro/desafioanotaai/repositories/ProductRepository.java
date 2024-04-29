package dev.pyetro.desafioanotaai.repositories;

import dev.pyetro.desafioanotaai.domain.products.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
