package blog.personnel.dm.entity.dto;

import lombok.Data;

@Data
public class ArticleDto {
    private String titre;
    private String img;
    private String contenu;
    private boolean estPublic;
    private boolean allowComments;
}
