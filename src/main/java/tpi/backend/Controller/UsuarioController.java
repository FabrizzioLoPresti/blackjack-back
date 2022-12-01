package tpi.backend.Controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi.backend.Models.LoginDTO;
import tpi.backend.Models.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

@RequestMapping("/usuario")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {


    @GetMapping("/test")
    public boolean test(){
        return true;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody Usuario usuario, @RequestHeader("x-access-token") String authHeader) {
        LoginDTO dto = new LoginDTO("");
        String token = "";

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://root:mVkIuAGNeRjdG0A4ElwN@containers-us-west-48.railway.app:7272/railway", "root", "mVkIuAGNeRjdG0A4ElwN");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM usuario WHERE usuario = ? AND password = ?");
            preparedStatement.setString(1, usuario.getUsuario());
            preparedStatement.setString(2, usuario.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Usuario user = new Usuario(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
                dto.setToken(validarToken(authHeader, user));
                token = dto.getToken();
                return ResponseEntity.ok(dto);
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<LoginDTO> register(@RequestBody Usuario usuario){
        LoginDTO dto = new LoginDTO("");
        String token = "";
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://root:mVkIuAGNeRjdG0A4ElwN@containers-us-west-48.railway.app:7272/railway", "root", "mVkIuAGNeRjdG0A4ElwN");
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO usuario (usuario, password) VALUES (?, ?)");
            preparedStatement.setString(1, usuario.getUsuario());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.execute();

            token = generateToken(usuario);
            dto.setToken(token);

            return ResponseEntity.ok(dto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    private String generateToken(Usuario usuario) {
        String secret = "juegocartas";
        Date date = new Date();
        return Jwts.builder()
                .claim("user", usuario.getUsuario())
                .claim("password", usuario.getPassword())
                .setSubject(usuario.getUsuario()).setSubject(usuario.getPassword())
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private String validarToken(String token, Usuario usuario) {
        String secretKey = "juegocartas";

        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            String user = claims.get("user", String.class);
            String password = claims.get("password", String.class);
            if (user.equals(usuario.getUsuario()) && password.equals(usuario.getPassword())) {
                return token;
            }
        } catch (Exception e) {
            return generateToken(usuario);
        }
        return "";
    }
}
