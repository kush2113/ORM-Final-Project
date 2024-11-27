package lk.ijse.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class StudentDTO {
    private int studentId;
    private String name;
    private String address;
    private String phone;
    private Date regDate;
    private int user;

    public StudentDTO(int id, String name, String address, String tel) {
        this.studentId = id;
        this.name = name;
        this.address = address;
        this.phone = tel;
    }

    public StudentDTO(String id, String name, String phoneNumber, String address, Date date, int userId) {

        this.studentId = Integer.parseInt(id);
        this.name = name;
        this.phone = phoneNumber;
        this.address = address;
        this.regDate = date;
        this.user = userId;

    }
}
