package dev.pyetro.desafioanotaai.domain.products;

public record ProductDTO(String tittle,
                         String description,
                         String ownerId,
                         Integer price,
                         String categoryId) {
}
