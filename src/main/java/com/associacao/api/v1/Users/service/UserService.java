package com.associacao.api.v1.Users.service;
import com.associacao.api.Security.EncryptionUtil;
import com.associacao.api.v1.Servidor.domain.Servidor;
import com.associacao.api.v1.Users.domain.UserRole;
import com.associacao.api.Exceptions.AlreadyExistsException;
import com.associacao.api.Exceptions.UnauthorizedAccessException;
import com.associacao.api.v1.SuperClasses.service.AbstractBaseService;
import com.associacao.api.v1.Users.domain.Users;
import com.example.work3.Security.*;
import com.associacao.api.v1.Users.repository.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;


@Service
@DependsOn("passwordEncoder")
public class UserService extends AbstractBaseService<Users, String> {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }

    @Autowired private UserRepository userRepository;

    @Value("${encryption.secret.key}")
    private String encryptionKey;

    @Override public Users findById(String id) {
            return validarId(id);
    }

    public Users register(Users user, UserDetails userDetails) throws Exception {
        checkAuthorization(userDetails);
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new AlreadyExistsException("Login já existe: " + user.getLogin());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secret = key.getKey();
        String encryptedSecret = EncryptionUtil.encrypt(secret, encryptionKey);
        user.setSecret(encryptedSecret);
        return save(user);
    }

    public void checkAuthorization(UserDetails userDetails) throws UnauthorizedAccessException {
        if (userDetails == null || !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new UnauthorizedAccessException("Usuário não tem permissão para registrar novos usuários.");
        }
    }

    public String getQRBarcodeURL(Users user) {
        try {
            String decryptedSecret = EncryptionUtil.decrypt(user.getSecret(), encryptionKey);
            String account = user.getLogin();
            String issuer = "Work3"; String url = String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", issuer, account, decryptedSecret, issuer);
            return "https://api.qrserver.com/v1/create-qr-code/?data=" + URLEncoder.encode(url, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            logger.error("Erro ao gerar URL do código QR para o usuário: {}", user.getLogin(), e);
        throw new RuntimeException("Erro ao gerar URL do código QR", e);
        }
    }

    public boolean verifyCode(String login, String secret, int code) throws Exception {
        Users user = (Users) userRepository.findByLogin(login);
        if (user != null) {
            String decryptedSecret = EncryptionUtil.decrypt(user.getSecret(), encryptionKey);
            return gAuth.authorize(decryptedSecret, code);
        }
        return false;
    }

    @Override
    public Users update(String id, Users user) throws Exception {
        Users existingUser = validarId(id);
        updateEntityFields(existingUser, user);

        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Verifica o valor de verifyCode no banco de dados se não for fornecido na atualização
        if (user.getVerifyCode() != null) {
            if (Boolean.TRUE.equals(user.getVerifyCode()) && existingUser.getSecret() == null) {
                GoogleAuthenticatorKey key = gAuth.createCredentials();
                String encryptedSecret = EncryptionUtil.encrypt(key.getKey(), encryptionKey);
                existingUser.setSecret(encryptedSecret);
            } else if (Boolean.FALSE.equals(user.getVerifyCode())) {
                existingUser.setSecret(null);
            }
        } else {
            // Mantém o valor atual de verifyCode e secret se não for fornecido na atualização
            user.setVerifyCode(existingUser.getVerifyCode());
        }
        return save(existingUser);
    }

    public void updateEntityFields(Users existingUser, Users newUser) {
        if (newUser.getLogin() != null) {
            existingUser.setLogin(newUser.getLogin());
        }
        if (newUser.getRole() != null) {
            existingUser.setRole(newUser.getRole());
        }
        if (newUser.getAtivo() != null) {
            existingUser.setAtivo(newUser.getAtivo());
        }
        if (newUser.getVerifyCode() != null) {
            existingUser.setVerifyCode(newUser.getVerifyCode());
        }
        if (newUser.getPassword() != null) {
            existingUser.setPassword(newUser.getPassword());
        }
    }

    public String updateAndGetQRUrl(String id, Users user) throws Exception {
        Users updatedUser = update(id, user);
        if (Boolean.TRUE.equals(updatedUser.getVerifyCode())) {
            return getQRBarcodeURL(updatedUser);
        }
        return null;
    }
    public Users save(Users user) { return repository.save(user); }

    public Users criarUsuarioParaServidor(Servidor servidor, UserDetails userDetails) throws Exception {
        String cpf = servidor.getCpf();
        String nome = servidor.getName();
        String login = cpf;

        // Verificar se o usuário já existe
        UserDetails existingUser = userRepository.findByLogin(login);
        if (existingUser != null) {
            return (Users) existingUser;
        }

        // Remover acentos e caracteres especiais do nome
        String nomeSemAcentos = Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        String password = cpf + nomeSemAcentos.substring(0, 3).toLowerCase().replaceAll("[^a-zA-Z]", "");
        Users usuario = new Users(servidor.getId(), login, password, UserRole.EMPLOYEE, true, false);
        return register(usuario, userDetails);
    }

    @Override
    protected void setAtivoFalse(Users users) {
        users.setAtivo(false);
    }

}
