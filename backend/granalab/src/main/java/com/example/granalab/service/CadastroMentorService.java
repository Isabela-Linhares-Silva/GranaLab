package com.example.granalab.service;

import com.example.granalab.dto.CadastroMentorRequest;
import com.example.granalab.dto.UsuarioResponse;
import com.example.granalab.model.Mentor;
import com.example.granalab.model.TipoPerfil;
import com.example.granalab.model.Usuario;
import com.example.granalab.repository.MentorRepository;
import com.example.granalab.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Serviço de cadastro de usuário Mentor.
 * Orquestra validação, geração de ID, hash de senha e persistência transacional.
 */
@Service
public class CadastroMentorService {

    private final UsuarioRepository usuarioRepository;
    private final MentorRepository mentorRepository;
    private final IdGeneratorService idGeneratorService;
    private final CadastroValidationService cadastroValidationService;
    private final CadastroUnicidadeService cadastroUnicidadeService;
    private final PasswordEncoder passwordEncoder;
    private final AvatarCatalog avatarCatalog;

    public CadastroMentorService(
            UsuarioRepository usuarioRepository,
            MentorRepository mentorRepository,
            IdGeneratorService idGeneratorService,
            CadastroValidationService cadastroValidationService,
            CadastroUnicidadeService cadastroUnicidadeService,
            PasswordEncoder passwordEncoder,
            AvatarCatalog avatarCatalog) {
        this.usuarioRepository = usuarioRepository;
        this.mentorRepository = mentorRepository;
        this.idGeneratorService = idGeneratorService;
        this.cadastroValidationService = cadastroValidationService;
        this.cadastroUnicidadeService = cadastroUnicidadeService;
        this.passwordEncoder = passwordEncoder;
        this.avatarCatalog = avatarCatalog;
    }

    /**
     * Cadastra um novo usuário do tipo Mentor.
     *
     * @param request dados de cadastro do mentor
     * @return resposta pública do usuário cadastrado (sem senha_hash)
     */
    @Transactional
    public UsuarioResponse cadastrar(CadastroMentorRequest request) {
        // Valida regras de negócio (RN01, RN02, RN05, RN06, EX004, EX006, EX008, EX009)
        cadastroValidationService.validarCadastroMentor(request);

        // Verifica unicidade de e-mail (RN04, EX003)
        cadastroUnicidadeService.verificarEmailNaoCadastrado(request.email());

        // Gera identificador UUID (T005)
        UUID id = idGeneratorService.gerarId();
        Integer idInt = id.hashCode(); // Converte UUID para Integer compatível com o banco

        // Cria e persiste Usuario
        Usuario usuario = new Usuario();
        usuario.setIdUser(idInt);
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        usuario.setUrlAvatar(avatarCatalog.selecionarAleatorio());
        usuario.setTipoPerfil(TipoPerfil.MENTOR);

        Usuario usuarioPersistido = usuarioRepository.save(usuario);

        // MapsId deriva a chave do perfil a partir da conta já gerenciada.
        Mentor mentor = new Mentor();
        mentor.setUsuario(usuarioPersistido);
        mentor.setEscolaAtua(request.escolaAtua());

        mentorRepository.save(mentor);

        return UsuarioResponse.from(usuarioPersistido);
    }
}
