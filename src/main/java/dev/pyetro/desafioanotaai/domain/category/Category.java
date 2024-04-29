package dev.pyetro.desafioanotaai.domain.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@Getter
@Setter
@NoArgsConstructor

public class Category {

    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;

    public Category(CategoryDTO categoryDto){
        this.title = categoryDto.tittle();
        this.description = categoryDto.description();
        this.ownerId = categoryDto.ownerId();
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("tittle", this.title);
        json.put("description", this.description);
        json.put("ownerId", this.ownerId);
        json.put("id", this.id);
        json.put("type", "category");

        return json.toString();
    }
}
