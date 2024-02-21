package org.example.appsimplemassenger.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String text;

    @OneToOne
    Attachment attachment;

    @ManyToOne(optional = false)
    Chat chat;

    @ManyToOne(optional = false)
    @CreatedBy
    User user;

    @CreatedDate
    Timestamp createdAt;

    @LastModifiedDate
    Timestamp updatedAt;
}
