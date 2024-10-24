package crm_app07repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import crm_app07config.MysqlConfig;
import crm_app07entity.LoginEntity;

public class LoginRepository {
	public List<LoginEntity> findByEmailAndPassword(String email, String password){
		List<LoginEntity> ListUsers = new ArrayList<LoginEntity>();

		  String query = "SELECT u.*, r.name AS role_name " +
                  "FROM users u " +
                  "JOIN roles r ON r.id = u.role_id " +
                  "WHERE u.email = ? AND u.password = ?";

		
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				LoginEntity loginEntity = new LoginEntity();
				loginEntity.setEmail(result.getString("email"));
				loginEntity.setPassword(result.getString("password"));
				loginEntity.setRole_name(result.getString("role_name"));
				
				ListUsers.add(loginEntity);
				
			}
			
		} catch (Exception e) {
			 System.out.println("findByEmailAndPassword : " + e.getLocalizedMessage());
		}
		
		return ListUsers;
	}
}
