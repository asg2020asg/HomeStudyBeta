package homestudy.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    public static Connection getConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/tec_crud", "root", "asg2020asg");
        }catch (Exception e){
            throw new RuntimeException("Erro ao conectar",e);
        }
    }
}

