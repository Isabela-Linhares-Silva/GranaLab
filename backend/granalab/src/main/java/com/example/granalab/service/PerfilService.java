package com.example.granalab.service;

import com.example.granalab.dto.AtualizacaoPerfilRequest;
import com.example.granalab.dto.PerfilResponse;
import com.example.granalab.exception.ErroPerfilException;
import com.example.granalab.model.Aluno;
import com.example.granalab.model.Genero;
import com.example.granalab.model.Mentor;
import com.example.granalab.model.TipoPerfil;
import com.example.granalab.model.Usuario;
import com.example.granalab.repository.AlunoRepository;
import com.example.granalab.repository.MentorRepository;
import com.example.granalab.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final MentorRepository mentorRepository;
    private final AvatarCatalog avatarCatalog;

    public PerfilService(UsuarioRepository usuarioRepository, AlunoRepository alunoRepository,
                         MentorRepository mentorRepository, AvatarCatalog avatarCatalog) {
        this.usuarioRepository = usuarioRepository;
        this.alunoRepository = alunoRepository;
        this.mentorRepository = mentorRepository;
        this.avatarCatalog = avatarCatalog;
    }

    @Transactional(readOnly = true)
    public PerfilResponse consultar(Integer idUser) {
        return montarResposta(buscarUsuario(idUser));
    }

    @Transactional
    public PerfilResponse atualizar(Integer idUser, AtualizacaoPerfilRequest request) {
        Usuario usuario = buscarUsuario(idUser);
        Aluno aluno = null;
        Mentor mentor = null;
        LocalDate dataNascimento = null;
        Genero genero = null;

        if (usuario.getTipoPerfil() == TipoPerfil.ALUNO) {
            if (request.isEscolaAtuaInformada()) {
                throw erro("DADOS_INVALIDOS", "Instituição/escola não se aplica ao perfil Aluno");
            }
            aluno = alunoRepository.findById(idUser)
                    .orElseThrow(() -> new IllegalStateException("Perfil Aluno não encontrado"));
            if (request.isDataNascimentoInformada()) dataNascimento = validarDataNascimento(request.getDataNascimento());
            if (request.isGeneroInformado()) genero = validarGenero(request.getGenero());
        } else if (usuario.getTipoPerfil() == TipoPerfil.MENTOR) {
            if (request.isDataNascimentoInformada() || request.isGeneroInformado()) {
                throw erro("DADOS_INVALIDOS", "Data de nascimento e gênero não se aplicam ao perfil Mentor");
            }
            mentor = mentorRepository.findById(idUser)
                    .orElseThrow(() -> new IllegalStateException("Perfil Mentor não encontrado"));
            if (request.isEscolaAtuaInformada()) validarEscola(request.getEscolaAtua());
        } else {
            throw new IllegalStateException("Tipo de perfil não reconhecido");
        }

        String nome = request.isNomeInformado() ? validarNome(request.getNome(), usuario) : null;
        String avatar = request.isUrlAvatarInformado() ? validarAvatar(request.getUrlAvatar()) : null;

        if (request.isNomeInformado()) usuario.setNome(nome);
        if (request.isUrlAvatarInformado()) usuario.setUrlAvatar(avatar);
        if (aluno != null) {
            if (request.isDataNascimentoInformada()) aluno.setDataNascimento(dataNascimento);
            if (request.isGeneroInformado()) aluno.setGenero(genero);
            alunoRepository.save(aluno);
        }
        if (mentor != null && request.isEscolaAtuaInformada()) {
            String escola = request.getEscolaAtua();
            mentor.setEscolaAtua(escola == null || escola.isBlank() ? null : escola.trim());
            mentorRepository.save(mentor);
        }

        usuarioRepository.save(usuario);
        return montarResposta(usuario);
    }

    private Usuario buscarUsuario(Integer idUser) {
        return usuarioRepository.findById(idUser)
                .orElseThrow(() -> new IllegalStateException("Conta autenticada não encontrada"));
    }

    private PerfilResponse montarResposta(Usuario usuario) {
        if (usuario.getTipoPerfil() == TipoPerfil.ALUNO) {
            Aluno aluno = alunoRepository.findById(usuario.getIdUser())
                    .orElseThrow(() -> new IllegalStateException("Perfil Aluno não encontrado"));
            return new PerfilResponse(usuario.getNome(), usuario.getEmail(), usuario.getUrlAvatar(),
                    usuario.getTipoPerfil().getValor(), aluno.getDataNascimento(), aluno.getGenero(), null);
        }
        if (usuario.getTipoPerfil() == TipoPerfil.MENTOR) {
            Mentor mentor = mentorRepository.findById(usuario.getIdUser())
                    .orElseThrow(() -> new IllegalStateException("Perfil Mentor não encontrado"));
            return new PerfilResponse(usuario.getNome(), usuario.getEmail(), usuario.getUrlAvatar(),
                    usuario.getTipoPerfil().getValor(), null, null, mentor.getEscolaAtua());
        }
        throw new IllegalStateException("Tipo de perfil não reconhecido");
    }

    private String validarNome(String nome, Usuario usuario) {
        if (nome == null || nome.isBlank()) throw erro("DADOS_INVALIDOS", "Nome é obrigatório");
        String normalizado = nome.trim();
        if (normalizado.length() < 5 || normalizado.length() > 100) {
            throw erro("DADOS_INVALIDOS", "Nome deve possuir entre 5 e 100 caracteres");
        }
        if (!normalizado.equals(usuario.getNome())
                && usuarioRepository.existsByNomeAndIdUserNot(normalizado, usuario.getIdUser())) {
            throw new ErroPerfilException("NOME_DUPLICADO", "Nome já está em uso (EX001)", HttpStatus.CONFLICT);
        }
        return normalizado;
    }

    private String validarAvatar(String avatar) {
        if (avatar == null || !avatarCatalog.contem(avatar)) {
            throw erro("AVATAR_INVALIDO", "Avatar inválido: selecione uma opção do catálogo");
        }
        return avatar;
    }

    private LocalDate validarDataNascimento(String valor) {
        if (valor == null || valor.isBlank()) throw erro("DADOS_INVALIDOS", "Data de nascimento é obrigatória");
        try {
            LocalDate data = LocalDate.parse(valor);
            if (!data.isBefore(LocalDate.now())) {
                throw erro("DADOS_INVALIDOS", "Data de nascimento não pode ser futura ou igual à data atual");
            }
            return data;
        } catch (DateTimeParseException ex) {
            throw erro("DADOS_INVALIDOS", "Data de nascimento inválida; use o formato AAAA-MM-DD");
        }
    }

    private Genero validarGenero(String valor) {
        if (valor == null) throw erro("DADOS_INVALIDOS", "Gênero é obrigatório");
        try {
            return Genero.valueOf(valor);
        } catch (IllegalArgumentException ex) {
            throw erro("DADOS_INVALIDOS", "Gênero inválido; valores aceitos: M, F ou O");
        }
    }

    private void validarEscola(String escola) {
        if (escola != null && escola.trim().length() > 100) {
            throw erro("DADOS_INVALIDOS", "Instituição/escola deve ter no máximo 100 caracteres");
        }
    }

    private ErroPerfilException erro(String codigo, String mensagem) {
        return new ErroPerfilException(codigo, mensagem, HttpStatus.BAD_REQUEST);
    }
}
