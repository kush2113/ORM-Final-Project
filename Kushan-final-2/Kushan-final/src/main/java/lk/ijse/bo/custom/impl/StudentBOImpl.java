package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO =(StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public boolean saveStudent(StudentDTO studentDTO) throws SQLException, IOException, ClassNotFoundException {
        return studentDAO.save(new Student(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getPhone(), studentDTO.getRegDate(), new User(studentDTO.getUser())));
    }

    @Override
    public ArrayList<StudentDTO> getAllStudent() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<StudentDTO> allStudents = new ArrayList<>();

        ArrayList<Student> all = studentDAO.getAll();
        for (Student student : all) {
            allStudents.add(new StudentDTO(student.getStudentId(), student.getName(), student.getAddress(), student.getPhone(), student.getRegDate(), student.getUser().getUserId()));
        }
        return allStudents;
    }

    @Override
    public String generateNewID() throws SQLException, IOException, ClassNotFoundException {
        return studentDAO.generateNewID();
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, IOException, ClassNotFoundException {
        return studentDAO.update(new Student(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getPhone(), studentDTO.getRegDate(), new User(studentDTO.getUser())));
    }

    @Override
    public boolean deleteStudent(int id) throws SQLException, IOException, ClassNotFoundException {
        return studentDAO.delete(String.valueOf(id));
    }

    @Override
    public Student studentSearch(int id) throws SQLException, ClassNotFoundException {
        return studentDAO.search(id);
    }
}
