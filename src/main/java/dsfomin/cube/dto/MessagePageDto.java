package dsfomin.cube.dto;

import com.fasterxml.jackson.annotation.JsonView;
import dsfomin.cube.domain.Message;
import dsfomin.cube.domain.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonView(Views.FullMessage.class)
public class MessagePageDto {
    private List<Message> messages;
    private int currentPage;
    private int totalPages;
}
