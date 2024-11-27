package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.entity.Program;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProgramBO extends SuperBO {
    boolean saveProgram(ProgramDTO programDTO) throws IOException, SQLException, ClassNotFoundException;

    ArrayList<ProgramDTO> getAllPrograms() throws IOException, SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, IOException, ClassNotFoundException;

    boolean updateProgram(ProgramDTO programDTO) throws SQLException, IOException, ClassNotFoundException;

    boolean deleteProgram(String id) throws SQLException, IOException, ClassNotFoundException;

    public List<String> getProgramNames() throws SQLException, ClassNotFoundException;

    Program searchByName(String name) throws SQLException, ClassNotFoundException;
}
