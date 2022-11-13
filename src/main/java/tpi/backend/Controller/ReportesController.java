package tpi.backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi.backend.Models.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@RequestMapping("/reportes")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReportesController {

    @GetMapping("/test")
    public boolean test(){
        return true;
    }


    @GetMapping("/victorias")
    public ArrayList<Integer> getVictorias(){
        try {
            ArrayList<Integer> victorias = new ArrayList<>();

            Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_b038d7c98f39121", "b902534b0a0d2e", "f00230c6");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE resultado =1");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                victorias.add(resultSet.getInt(1));
            }

            preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE resultado =2");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                victorias.add(resultSet.getInt(1));
            }

            return victorias;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/juegosPorDia")
    public ArrayList<Integer> getJuegosPorDia(){
        try {
            ArrayList<Integer> juegosPorDia = new ArrayList<>();
            Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_b038d7c98f39121", "b902534b0a0d2e", "f00230c6");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida group by WEEKDAY(fecha)");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                juegosPorDia.add(resultSet.getInt(1));
            }
            return juegosPorDia;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/victorias21")
    public ArrayList<Integer> getVictorias21(){
        try {
            ArrayList<Integer> victorias21 = new ArrayList<>();

            Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_b038d7c98f39121", "b902534b0a0d2e", "f00230c6");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE resultado =1 AND puntaje = 21");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                victorias21.add(resultSet.getInt(1));
            }

            preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE resultado =2 AND puntaje = 21");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                victorias21.add(resultSet.getInt(1));
            }

            return victorias21;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
