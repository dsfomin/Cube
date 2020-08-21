package dsfomin.cube.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;

    @Column(name = "text")
    @JsonView(Views.IdName.class)
    private String text;

    @Column(updatable = false, name = "creation_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonView(Views.FullMessage.class)
    private LocalDateTime creationTime;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullMessage.class)
    private User author;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @JsonView(Views.FullMessage.class)
    private List<Comment> comments;

    @JsonView(Views.FullMessage.class)
    private String link;
    @JsonView(Views.FullMessage.class)
    private String linkTitle;
    @JsonView(Views.FullMessage.class)
    private String linkDescription;
    @JsonView(Views.FullMessage.class)
    private String linkCover;
}
