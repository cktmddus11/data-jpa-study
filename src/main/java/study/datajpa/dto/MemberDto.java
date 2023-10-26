package study.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class MemberDto {

    private Long id;

    private String username;
    private String teamName;


}
