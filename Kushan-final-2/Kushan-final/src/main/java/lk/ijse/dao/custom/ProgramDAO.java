package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Program;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProgramDAO extends CrudDAO<Program> {

    List<String> getProgramNames() throws SQLException, ClassNotFoundException;

    Program searchByName(String name) throws SQLException, ClassNotFoundException;
}
