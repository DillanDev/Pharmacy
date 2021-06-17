package models;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class EmployeesDao {

    //Instanciamos la conexión
    ConnectionMySQL cn = new ConnectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //Método de login
    public Employees loginQuery(String user, String password) {
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        //Instancia a la clase empleados
        Employees employee = new Employees();
        //Como trabajaremos con base de datos utilizamos try catch
        try {
            //Llamar al método de la conexión
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            //Enviar los parámetros
            pst.setString(1, user);
            pst.setString(2, password);
            //Ejecutar la consulta
            rs = pst.executeQuery();

            if (rs.next()) {
                //Acceder a los métodos setter del empleado
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setUsername(rs.getString("username"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setPassword(rs.getString("password"));
                employee.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener empleado " + e);
        }
        return employee;
    }

    //Registrar empleado
    public boolean registerEmployeeQuery(Employees employee) {

        String query = "INSERT INTO employees (id, full_name, username, address, telephone, email, password, rol, created, updated) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, employee.getId());
            pst.setString(2, employee.getFull_name());
            pst.setString(3, employee.getUsername());
            pst.setString(4, employee.getAddress());
            pst.setString(5, employee.getTelephone());
            pst.setString(6, employee.getEmail());
            pst.setString(7, employee.getPassword());
            pst.setString(8, employee.getRol());
            pst.setTimestamp(9, datetime);
            pst.setTimestamp(10, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado ");
            return false;
        }
    }

    //Listar empleados
    public List listEmployeesQuery(String value) {

        List<Employees> list_employees = new ArrayList();

        String query = "SELECT * FROM employees";
        String query_search_employee = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_employee);
                rs = pst.executeQuery();
            }

            //Recorrer 
            while (rs.next()) {
                Employees employee = new Employees();
                employee.setId(rs.getInt("id"));
                employee.setFull_name(rs.getString("full_name"));
                employee.setUsername(rs.getString("username"));
                employee.setAddress(rs.getString("address"));
                employee.setTelephone(rs.getString("telephone"));
                employee.setEmail(rs.getString("email"));
                employee.setRol(rs.getString("rol"));
                list_employees.add(employee);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_employees;
    }

//Modificar empleado
    public boolean updateEmployeeQuery(Employees employee) {

        String query = "UPDATE employees SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?, password = ?, rol = ?, updated = ? WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());

        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employee.getFull_name());
            pst.setString(2, employee.getUsername());
            pst.setString(3, employee.getAddress());
            pst.setString(4, employee.getTelephone());
            pst.setString(5, employee.getEmail());
            pst.setString(6, employee.getPassword());
            pst.setString(7, employee.getRol());
            pst.setTimestamp(8, datetime);
            pst.setInt(9, employee.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al empleado ");
            return false;
        }
    }

//Eliminar empleaado
    public boolean deleteEmployeeQuery(int id) {
        String query = "DELETE FROM employees WHERE id=" + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

}
