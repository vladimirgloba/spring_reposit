package skbx.example.struct.AuthorsAndBooks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.loader.plan.build.internal.returns.ScalarReturnImpl;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ColumnDefault("0")
    @Column(columnDefinition = "SMALLINT")
    private int discount;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(columnDefinition = "INT NOT NULL")
    private Integer price;

    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate pub_date;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;

    @Column(columnDefinition = "BOOLEAN NOT NULL")
    private boolean is_bestseller;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String title;

}
