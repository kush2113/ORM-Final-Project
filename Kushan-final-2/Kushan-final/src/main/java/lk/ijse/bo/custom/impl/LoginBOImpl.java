package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.LoginBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.dao.custom.impl.LoginDAOImpl;
import lk.ijse.dto.LoginDTO;
import lk.ijse.entity.Login;

import java.io.IOException;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public boolean checkCredential(LoginDTO loginDTO) throws IOException {
        return loginDAO.checkCredential(new Login(loginDTO.getUserName(), loginDTO.getPassword()));

    }
}
