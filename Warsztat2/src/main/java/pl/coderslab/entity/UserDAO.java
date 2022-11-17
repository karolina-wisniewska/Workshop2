package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;

public class UserDAO {

    private static final String CREATE_USER_QUERY =

            "INSERT INTO workshop2.users(username, email, password) VALUES (?, ?, ?)";

    public static final String UPDATE_USER_QUERY=
            "UPDATE workshop2.users SET username = ?, email = ?, password = ? WHERE id = ?;";

    public static final String DELETE_USER_QUERY="DELETE FROM workshop2.users WHERE id = ?;";

    public static final String SELECT_USER_BY_ID_QUERY="SELECT * FROM workshop2.users WHERE id = ?;";

    public static void main(String[] args) {
/*        User user2 = new User();
        user2.setUserName("User2");
        user2.setEmail("user2@email.com");
        user2.setPassword("pass2");

        UserDAO userDAO = new UserDAO();
        userDAO.create(user2);*/

        UserDAO userDAO = new UserDAO();

/*        User userToUpdate = userDAO.read(4);
        userToUpdate.setUserName("User4");
        userToUpdate.setEmail("user4@email.com");
        userToUpdate.setPassword("pass4");
        userDAO.update(userToUpdate);*/

        userDAO.delete(2);

    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) {

        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));

            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId){
        User selectedUser = new User();
        try (Connection conn = DbUtil.getConnection()){
            try(PreparedStatement statement = conn.prepareStatement(SELECT_USER_BY_ID_QUERY)){
                statement.setInt(1, userId);
                try(ResultSet result = statement.executeQuery()){
                    while(result.next()){
                        selectedUser.setId(result.getInt(1));
                        selectedUser.setEmail(result.getString(2));
                        selectedUser.setUserName(result.getString(3));
                        selectedUser.setPassword(result.getString(4));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(selectedUser.getId()==0){
            return null;
        }
        return selectedUser;
    }

    public void update(User user){
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(UPDATE_USER_QUERY);

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4,user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(DELETE_USER_QUERY);

            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
