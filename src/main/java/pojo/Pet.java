package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet {
    private List<String> photoUrls;
    private String name;
    private Long id;
    private Category category;
    private List<TagsItem> tags;
    private String status;
}