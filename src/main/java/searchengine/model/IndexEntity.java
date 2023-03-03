package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "`index`")
@Getter
@Setter
public class IndexEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long pageId;

    @Column(nullable = false)
    Long lemmaId;

    @Column(name = "`rank`", nullable = false)
    Float rank;

    @ManyToOne
    @JoinColumn(name = "pageId", insertable = false, updatable = false)
    PageEntity page;

    @ManyToOne
    @JoinColumn(name = "lemmaId", insertable = false, updatable = false)
    LemmaEntity lemma;

}