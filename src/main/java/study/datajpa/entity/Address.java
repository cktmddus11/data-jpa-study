package study.datajpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable // Embeddable 클래스 - 기본생성자 필요
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;


}
