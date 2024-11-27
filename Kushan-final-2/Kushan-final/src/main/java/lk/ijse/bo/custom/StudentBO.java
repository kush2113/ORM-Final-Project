package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dao.custom.impl.StudentDAOImpl;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {

    boolean saveStudent(StudentDTO studentDTO) throws SQLException, IOException, ClassNotFoundException;

    ArrayList<StudentDTO> getAllStudent() throws SQLException, IOException, ClassNotFoundException;

    String generateNewID() throws SQLException, IOException, ClassNotFoundException;

    boolean updateStudent(StudentDTO studentDTO) throws SQLException, IOException, ClassNotFoundException;

    boolean deleteStudent(int id) throws SQLException, IOException, ClassNotFoundException;

    Student studentSearch(int id) throws SQLException, ClassNotFoundException;
}
