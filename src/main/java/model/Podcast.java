package model;

import javax.persistence.*;

@Entity
@Table(name = "podcasts")
public class Podcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(unique = false, nullable = false)
    private String title;

    @Column(unique = false, length = 1024)
    private String description;

    @Column(unique = false, nullable = false)
    private Integer duration;

    @Column(unique = false, nullable = false)
    private Integer views;

    @Column(unique = false, nullable = false)
    private Integer likes;

    @Column(unique = false)
    private String banner;

}
