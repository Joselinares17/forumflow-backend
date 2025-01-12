package org.forumflow.backend.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//TODO: retirar la anotacion Data de todos los entities
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_seq_gen"
    )
    @SequenceGenerator(
            name = "role_seq_gen",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @Column(name = "role_id")
    private int id;
    @Column(nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    private TypeRole typeRole;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
