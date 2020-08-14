package dsfomin.cube.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "usr")
@Data
public class User {
    @Id
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "userpic")
    private String userpic;

    @Column(name = "gender")
    private String gender;

    @Column(name = "locale")
    private String locale;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;
}
