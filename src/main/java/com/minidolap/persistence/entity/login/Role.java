package com.minidolap.persistence.entity.login;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "ROLE")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Proxy(lazy = false)
@TableGenerator(
        name = "RoleSeqStore",
        table = "ROLE_SEQ_STORE",
        pkColumnName = "ROLE_SEQ_NAME",
        pkColumnValue = "LISTENER_PK",
        valueColumnName = "ROLE_SEQ_VALUE",
        initialValue = 1,
        allocationSize = 1)
public class Role implements Serializable {

    @Id
    @GeneratedValue(generator = "RoleSeqStore", strategy = GenerationType.TABLE)
    private Long id;

    @Column
    private String roleName;


    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<ApplicationUser> applicationUser = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) &&
                Objects.equals(getRoleName(), role.getRoleName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getRoleName());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName +
                '}';
    }
}
