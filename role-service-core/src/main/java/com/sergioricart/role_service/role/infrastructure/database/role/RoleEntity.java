package com.sergioricart.role_service.role.infrastructure.database.role;

import com.sergioricart.role_service.role.infrastructure.database.page.PageEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class RoleEntity {

    @Id
    private String id;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "role_page",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "page_id")
    )
    private List<PageEntity> pages;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

}
