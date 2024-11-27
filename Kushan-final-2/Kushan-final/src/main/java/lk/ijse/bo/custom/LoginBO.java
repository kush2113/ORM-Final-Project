package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.LoginDTO;

import java.io.IOException;

public interface LoginBO extends SuperBO {
    boolean checkCredential(LoginDTO loginDTO) throws IOException;

}
