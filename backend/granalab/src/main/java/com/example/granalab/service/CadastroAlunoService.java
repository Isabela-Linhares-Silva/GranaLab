package com.example.granalab.service;

import com.example.granalab.dto.CadastroAlunoRequest;
import com.example.granalab.dto.UsuarioResponse;
import com.example.granalab.model.Aluno;
import com.example.granalab.model.TipoPerfil;
import com.example.granalab.model.Usuario;
import com.example.granalab.repository.AlunoRepository;
import com.example.granalab.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Serviço de cadastro de usuário Aluno.
 * Orquestra validação, geração de ID, hash de senha e persistência transacional.
 */
@Service
public class CadastroAlunoService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final IdGeneratorService idGeneratorService;
    private final CadastroValidationService cadastroValidationService;
    private final CadastroUnicidadeService cadastroUnicidadeService;
    private final PasswordEncoder passwordEncoder;
    private final AvatarCatalog avatarCatalog;

    public CadastroAlunoService(
            UsuarioRepository usuarioRepository,
            AlunoRepository alunoRepository,
            IdGeneratorService idGeneratorService,
            CadastroValidationService cadastroValidationService,
            CadastroUnicidadeService cadastroUnicidadeService,
            PasswordEncoder passwordEncoder,
            AvatarCatalog avatarCatalog) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.idGeneratorService = idGeneratorService;
        this.cadastroValidationService = cadastroValidationService;
        this.cadastroUnicidadeService = cadastroUnicidadeService;
        this.passwordEncoder = passwordEncoder;
        this.avatarCatalog = avatarCatalog;
    }

    /**
     * Cadastra um novo usuário do tipo Aluno.
     *
     * @param request dados de cadastro do aluno
     * @return resposta pública do usuário cadastrado (sem senha_hash)
     */
    @Transactional
    public UsuarioResponse cadastrar(CadastroAlunoRequest request) {
        // Valida regras de negócio (RN01, RN02, RN03, RN05, RN06, EX004, EX006, EX007, EX008, EX009)
        cadastroValidationService.validarCadastroAluno(request);

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
        usuario.setTipoPerfil(TipoPerfil.ALUNO);

        Usuario usuarioPersistido = usuarioRepository.save(usuario);

        // MapsId deriva a chave do perfil a partir da conta já gerenciada.
        Aluno aluno = new Aluno();
        aluno.setUsuario(usuarioPersistido);
        aluno.setNivelPerfil("Básico");
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setGenero(request.genero());

        alunoRepository.save(aluno);

        return UsuarioResponse.from(usuarioPersistido);
    }
}
