package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDao;
import views.Administrador;
import views.LoginView;
import models.ProductsDao;

//Implementar ActionListener
public class LoginController implements ActionListener {

    //Encapsular la clase empleados
    private Employees employee;
    //Encapsular la clase DAO
    private EmployeesDao employees_dao;
    //Encapsular la vista Login
    private LoginView login_view;

    //Constructor
    public LoginController(Employees employee, EmployeesDao employees_dao, LoginView login_view) {
        this.employee = employee;
        this.employees_dao = employees_dao;
        this.login_view = login_view;
        this.login_view.btn_enter.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Obtener los datos que vienen de la vista
        String user = login_view.txt_username.getText().trim();
        String pass = String.valueOf(login_view.txt_password.getPassword());
        
       if(e.getSource() == login_view.btn_enter){
           //Validación para que los campos no esten vacios
           if(!user.equals("") || !pass.equals("")){
               //Pasar dos parámetros al método Login creado en EmployeesDao
               //Almacenar en la vista Employees para verificar si existe este usuario
               employee = employees_dao.loginQuery(user, pass);
               //Verificar existencia del usuario
               if(employee.getUsername() != null){
                   Administrador admin = new Administrador();
                   admin.setVisible(true);
                   //Cerrar login
                   this.login_view.dispose();
                   
               }else{
                   JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
               }
           }else{
               JOptionPane.showMessageDialog(null, "Los campos estan vacios");
           }
       }
    }
}
