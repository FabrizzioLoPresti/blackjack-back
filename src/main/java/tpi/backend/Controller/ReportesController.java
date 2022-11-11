package tpi.backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi.backend.Models.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@RequestMapping("/reportes")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReportesController {

    @GetMapping("/test")
    public boolean test(){
        return true;
    }


    @GetMapping("/victorias")
    public ResponseEntity<Integer> getPorcentajeVictorias(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_b038d7c98f39121", "b902534b0a0d2e", "f00230c6");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE resultado = 2");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return ResponseEntity.ok(resultSet.getInt(1));
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/victoriasCrupier")
    public ResponseEntity<Integer> getPorcentajeVictoriasCrupier(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_b038d7c98f39121", "b902534b0a0d2e", "f00230c6");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE resultado = 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return ResponseEntity.ok(resultSet.getInt(1));
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/juegosPorDia")
    public ResponseEntity<Integer> getJuegosPorDia(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_b038d7c98f39121", "b902534b0a0d2e", "f00230c6");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE fecha = CURDATE()");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return ResponseEntity.ok(resultSet.getInt(1));
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/victorias21")
    public ResponseEntity<Integer> getVictorias21(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_b038d7c98f39121", "b902534b0a0d2e", "f00230c6");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE resultado = 2 AND puntajeJugador = 21");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return ResponseEntity.ok(resultSet.getInt(1));
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/victorias21Crupier")
    public ResponseEntity<Integer> getVictorias21Crupier(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://us-cdbr-east-06.cleardb.net:3306/heroku_b038d7c98f39121", "b902534b0a0d2e", "f00230c6");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(*) FROM partida WHERE resultado = 1 AND puntajeCrupier = 21");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return ResponseEntity.ok(resultSet.getInt(1));
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
