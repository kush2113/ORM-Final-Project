package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgramDAO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.entity.Program;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {

    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);

    @Override
    public boolean saveProgram(ProgramDTO programDTO) throws IOException, SQLException, ClassNotFoundException {
        return programDAO.save(new Program(programDTO.getProgramId(), programDTO.getProgramName(), programDTO.getDuration(), programDTO.getFee()));

    }

    @Override
    public ArrayList<ProgramDTO> getAllPrograms() throws IOException, SQLException, ClassNotFoundException {
        ArrayList<ProgramDTO> allPrograms = new ArrayList<>();

        ArrayList<Program> all = programDAO.getAll();
        for (Program program : all) {
            allPrograms.add(new ProgramDTO(program.getProgramId(), program.getProgramName(), program.getDuration(), program.getFee()));
        }
        return allPrograms;
    }

    @Override
    public String generateNewID() throws SQLException, IOException, ClassNotFoundException {
        return programDAO.generateNewID();
    }

    @Override
    public boolean updateProgram(ProgramDTO programDTO) throws SQLException, IOException, ClassNotFoundException {
        return programDAO.update(new Program(programDTO.getProgramId(), programDTO.getProgramName(), programDTO.getDuration(), programDTO.getFee()));
    }

    @Override
    public boolean deleteProgram(String id) throws SQLException, IOException, ClassNotFoundException {
        return programDAO.delete(id);
    }

    @Override
    public List<String> getProgramNames() throws SQLException, ClassNotFoundException {
        return programDAO.getProgramNames();
    }

    @Override
    public Program searchByName(String name) throws SQLException, ClassNotFoundException {
        return programDAO.searchByName(name);
    }
}
