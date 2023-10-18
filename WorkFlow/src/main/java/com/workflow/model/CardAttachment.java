package com.workflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Date uploader_date;

    @Column(nullable = false)
    private String file_name;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

}
