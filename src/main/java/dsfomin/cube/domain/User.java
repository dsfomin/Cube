package dsfomin.cube.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "usr")
@Data
public class User implements Serializable {
    @Id
    @JsonView(Views.IdName.class)
    private String id;

    @JsonView(Views.IdName.class)
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @JsonView(Views.IdName.class)
    @Column(name = "userpic")
    private String userpic;

    @Column(name = "gender")
    private String gender;

    @Column(name = "locale")
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "last_visit")
    private LocalDateTime lastVisit;
}
