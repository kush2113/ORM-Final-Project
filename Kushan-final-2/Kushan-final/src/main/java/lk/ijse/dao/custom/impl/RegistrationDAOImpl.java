package lk.ijse.dao.custom.impl;

import lk.ijse.config.SessionFactoryConfiguration;
import lk.ijse.dao.custom.RegistrationDAO;
import lk.ijse.entity.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public ArrayList<Registration> getAll() throws SQLException, ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public boolean save(Registration entity) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean update(Registration entity) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException, IOException {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        try {
            // HQL query to fetch the latest registration id
            Query<Integer> query = session.createQuery("SELECT r.regId FROM Registration r ORDER BY r.regId DESC", Integer.class);
            query.setMaxResults(1); // Limit to get only the latest result

            Integer lastId = query.uniqueResult(); // Fetch the last registration

            // Check if lastId is null, which happens if there are no entries in the database
            if (lastId == null) {
                return String.valueOf(1); // Start from ID 1 if no registration exist
            } else {
                return String.valueOf(lastId + 1); // Increment the last ID by 1 if it exists
            }
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException, IOException {
        return false;
    }

    @Override
    public Registration search(Object... args) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean saveRegistration(Registration registration, Session session) throws SQLException, ClassNotFoundException, IOException {
        session.save(registration);
        return true;
    }

    @Override
    public List<Object[]> searchForStudent(int regId) throws IOException {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        List<Object[]> resultList = null;

        try {
            // Create the HQL query
            String hql = "SELECT r.regId, r.student.studentId, r.paidAmount, p.programName, p.fee " +
                    "FROM Registration r JOIN Program p ON r.program.programId = p.programId " +
                    "WHERE r.regId = :regId";

            // Create the query object
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("regId", regId);

            // Execute the query and get the result
            resultList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the session
            if (session != null) {
                session.close();
            }
        }

        return resultList;
    }

    @Override
    public boolean updateAmount(Registration registration, Session session) {
        try {
            // HQL update query to set paidAmount where regId matches
            String hql = "UPDATE Registration r SET r.paidAmount = :paidAmount WHERE r.regId = :regId";
            Query query = session.createQuery(hql);
            query.setParameter("paidAmount", registration.getPaidAmount());
            query.setParameter("regId", registration.getRegId());

            // Execute update and check the number of affected rows
            int affectedRows = query.executeUpdate();
            return affectedRows > 0;  // Returns true if at least one row was updated

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
