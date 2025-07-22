package org.example.minorproject1.dtos;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.minorproject1.models.Author;
import org.example.minorproject1.models.Book;
import org.example.minorproject1.models.Genre;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank             // name != null && name.length() > 0
//    @NotNull              // name != null
    private String title;

    @NotNull
    private Genre genre;

    private Boolean isAvailable;

    @NotBlank(message = "Author name is empty or blank")// only for logging or debugging purposes
    private String authorName;

    @NotBlank
    @Email
    private String authorEmail;

    private String country;


    public Book covertToBook(){

        return Book.builder()
                .title(this.title)
                .genre(this.genre)
                .isAvailable(this.isAvailable == null || this.isAvailable)
                .issueCount(0L).author(
                        Author.builder()
                                .name(this.authorName)
                                .email(this.authorEmail)
                                .country(this.country)
                                .build()
                ).build();
    }
}
