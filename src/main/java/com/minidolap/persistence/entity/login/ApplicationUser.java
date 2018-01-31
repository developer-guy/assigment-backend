package com.minidolap.persistence.entity.login;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Data
@Table(name = "APPLICATION_USER")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Proxy(lazy = false)
@TableGenerator(
        name = "AppUserSeqStore",
        table = "APP_USER_SEQ_STORE",
        pkColumnName = "APP_USER_SEQ_NAME",
        pkColumnValue = "LISTENER_PK",
        valueColumnName = "APP_USER_SEQ_VALUE",
        initialValue = 1,
        allocationSize = 1)
public class ApplicationUser implements Serializable {

    @Id
    @GeneratedValue(generator = "AppUserSeqStore", strategy = GenerationType.TABLE)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private boolean isAccountNonExpired;

    @Column
    private boolean isAccountNonLocked;

    @Column
    private boolean isCredentialsNonExpired;

    @Column
    private boolean isEnabled;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ApplicationUser that = (ApplicationUser) o;
        return isAccountNonExpired() == that.isAccountNonExpired() &&
                isAccountNonLocked() == that.isAccountNonLocked() &&
                isCredentialsNonExpired() == that.isCredentialsNonExpired() &&
                isEnabled() == that.isEnabled() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getUsername(), getPassword(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled());
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
