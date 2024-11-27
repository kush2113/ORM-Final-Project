package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.Login;

import java.io.IOException;
import java.util.function.Supplier;

public interface LoginDAO extends SuperDAO {
    boolean checkCredential(Login login) throws IOException;
}
