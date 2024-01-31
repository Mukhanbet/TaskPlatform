package com.example.taskPlatform.entities;

import com.example.taskPlatform.enums.Level;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private boolean available;
    @Column(name = "created_day")
    private LocalDate createdDay;
    private String solver;
    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Author", referencedColumnName = "email")
    private User user;
}
