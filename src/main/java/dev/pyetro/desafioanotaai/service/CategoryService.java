package dev.pyetro.desafioanotaai.service;

import dev.pyetro.desafioanotaai.domain.category.exception.CategoryNotFoundException;
import dev.pyetro.desafioanotaai.domain.category.Category;
import dev.pyetro.desafioanotaai.domain.category.CategoryDTO;
import dev.pyetro.desafioanotaai.repositories.CategoryRepository;
import dev.pyetro.desafioanotaai.service.aws.AwsSnsService;
import dev.pyetro.desafioanotaai.service.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private final AwsSnsService snsService;

    public CategoryService(CategoryRepository categoryRepository, AwsSnsService snsService) {
        this.categoryRepository = categoryRepository;
        this.snsService = snsService;
    }

    public Category insert(CategoryDTO categoryData){
        Category newCategory = new Category(categoryData);
        this.categoryRepository.save(newCategory);
        this.snsService.publish(new MessageDTO(newCategory.toString()));
        return newCategory;
    }

    public List<Category> getAll() {
       return this.categoryRepository.findAll();
    }

    public Category update(String id, CategoryDTO categoryData) {

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        if(!categoryData.tittle().isEmpty()) category.setTitle(categoryData.tittle());
        if(!categoryData.description().isEmpty()) category.setDescription(categoryData.description());

        this.categoryRepository.save(category);
        this.snsService.publish(new MessageDTO(category.toString()));
        return category;
    }

    public void delete(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.categoryRepository.delete(category);
    }

    public Optional<Category> getById(String id) {
        return this.categoryRepository.findById(id);
    }
}
