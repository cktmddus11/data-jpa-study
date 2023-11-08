package study.datajpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

// JPA 상속과는 별개임.
@MappedSuperclass // 슈퍼클래스로서 이 클래스를 상속받으면 자식클래스에서 상위클래스의 필드만을 상속받음
@Getter
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();

        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedDate = LocalDateTime.now();
    }


}
