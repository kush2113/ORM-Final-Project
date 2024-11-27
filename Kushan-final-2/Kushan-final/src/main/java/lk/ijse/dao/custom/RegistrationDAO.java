package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Registration;
import org.hibernate.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface RegistrationDAO extends CrudDAO<Registration> {
    boolean saveRegistration(Registration registration, Session session) throws SQLException, ClassNotFoundException, IOException;

    List<Object[]> searchForStudent(int regId) throws IOException;

    boolean updateAmount(Registration registration, Session session);
}
