package com.sergioricart.role_service.role.infrastructure.database.page;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "page")
public class PageEntity {

    @Id
    private String id;

    private String name;

    private String description;

    private String url;

}
