package com.spring.security.entity;

import com.spring.security.enums.ERole;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role extends AbstractAuditingEntity<Long> {
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
