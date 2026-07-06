package homestudy.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    public static Connection getConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Alterado o nome do banco de dados de 'tec_crud' para 'home_study'
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/home_study", "root", "asg2020asg");
        }catch (Exception e){
            throw new RuntimeException("Erro ao conectar",e);
        }
    }
}
