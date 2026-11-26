
// Gerenciamento da tela de edição de perfil do GranaLab.
// Depende do token salvo pelo app.js em sessionStorage.

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('form-editar-perfil');
    if (!form) return;

    const API_BASE_URL = window.GRANALAB_API_URL || window.location.origin;
    const token = sessionStorage.getItem('granalab.token');

    const elementos = {
        avatarSecao: document.getElementById('secao-avatar'),
        avatarAtual: document.getElementById('avatar-atual'),
        avatarNavbar: document.getElementById('avatar-navbar'),
        avatarDropdown: document.getElementById('avatar-dropdown'),
        dadosAluno: document.getElementById('dados-aluno'),
        dadosMentor: document.getElementById('dados-mentor'),
        nomeAluno: document.getElementById('nome-aluno'),
        dataNascimento: document.getElementById('data-nascimento'),
        genero: document.getElementById('genero'),
        emailAluno: document.getElementById('email-aluno'),
        nomeMentor: document.getElementById('nome-mentor'),
        instituicao: document.getElementById('instituicao'),
        emailMentor: document.getElementById('email-mentor'),
        senhaAtual: document.getElementById('senha-atual'),
        novaSenha: document.getElementById('nova-senha'),
        confirmarSenha: document.getElementById('confirmar-senha'),
        nomeDropdown: document.getElementById('nome-dropdown'),
        tipoDropdown: document.getElementById('tipo-dropdown'),
        mensagem: document.getElementById('mensagem-perfil'),
        salvar: document.getElementById('btn-salvar'),
        editar: document.getElementById('btn-editar'),
        cancelar: document.getElementById('btn-cancelar')
    };

    let perfilOriginal = null;
    let avatarSelecionado = null;
    let tipoPerfil = null;
    let modoEdicao = false;

    const camposEditaveis = [
        elementos.nomeAluno,
        elementos.dataNascimento,
        elementos.genero,
        elementos.nomeMentor,
        elementos.instituicao,
        elementos.senhaAtual,
        elementos.novaSenha,
        elementos.confirmarSenha
    ].filter(Boolean);

    function mostrarMensagem(texto, tipo = 'erro') {
        elementos.mensagem.textContent = texto;
        elementos.mensagem.dataset.kind = tipo;
        elementos.mensagem.hidden = false;
    }

    function limparMensagem() {
        elementos.mensagem.hidden = true;
        elementos.mensagem.textContent = '';
        delete elementos.mensagem.dataset.kind;
    }

    async function lerResposta(response) {
        const texto = await response.text();
        if (!texto) return null;

        try {
            return JSON.parse(texto);
        } catch {
            return { mensagem: texto };
        }
    }

    function mensagemErro(data, status) {
        if (data && typeof data === 'object') {
            return data.mensagem || data.message || data.erro || data.detail ||
                (data.errors ? Object.values(data.errors).join(' ') : null) ||
                `Não foi possível concluir a operação (HTTP ${status}).`;
        }

        return data || `Não foi possível concluir a operação (HTTP ${status}).`;
    }

    async function requisicao(url, metodo = 'GET', corpo = undefined) {
        const opcoes = {
            method: metodo,
            headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        };

        if (corpo !== undefined) {
            opcoes.headers['Content-Type'] = 'application/json';
            opcoes.body = JSON.stringify(corpo);
        }

        const response = await fetch(`${API_BASE_URL}${url}`, opcoes);
        const data = await lerResposta(response);

        if (!response.ok) {
            throw new Error(mensagemErro(data, response.status));
        }

        return data;
    }

    function caminhoImagem(caminho) {
        if (!caminho) return '../resources/img/Tartaruga.jpeg';

        if (/^(https?:|data:)/i.test(caminho)) {
            return caminho;
        }

        return `../${String(caminho).replace(/^\/+/, '')}`;
    }

    function atualizarAvatarNaTela(caminho) {
        const src = caminhoImagem(caminho);

        if (elementos.avatarAtual) elementos.avatarAtual.src = src;
        if (elementos.avatarNavbar) elementos.avatarNavbar.src = src;
        if (elementos.avatarDropdown) elementos.avatarDropdown.src = src;
    }

    function selecionarAvatar(caminho) {
        avatarSelecionado = caminho;
        atualizarAvatarNaTela(caminho);

        document.querySelectorAll('.opcao-avatar').forEach((botao) => {
            const selecionado = String(caminho || '').endsWith(botao.dataset.avatar);

            botao.classList.toggle('selecionado', selecionado);
            botao.setAttribute('aria-pressed', String(selecionado));
        });
    }

    function preencherTela(perfil) {
        tipoPerfil = String(perfil.tipoPerfil || '').toLowerCase();

        const ehAluno = tipoPerfil === 'aluno';

        elementos.avatarSecao.hidden = !ehAluno;
        elementos.dadosAluno.hidden = !ehAluno;
        elementos.dadosMentor.hidden = ehAluno;

        if (ehAluno) {
            elementos.nomeAluno.value = perfil.nome || '';
            elementos.dataNascimento.value = perfil.dataNascimento || '';
            elementos.genero.value = perfil.genero || '';
            elementos.emailAluno.value = perfil.email || '';

            selecionarAvatar(perfil.urlAvatar);
        } else {
            elementos.nomeMentor.value = perfil.nome || '';
            elementos.instituicao.value = perfil.escolaAtua || '';
            elementos.emailMentor.value = perfil.email || '';

            atualizarAvatarNaTela(perfil.urlAvatar);
        }

        elementos.nomeDropdown.textContent = perfil.nome || '@usuario';
        elementos.tipoDropdown.textContent = ehAluno ? 'Aluno' : 'Mentor';

        perfilOriginal = { ...perfil };

        limparCamposSenha();
    }

    function limparCamposSenha() {
        elementos.senhaAtual.value = '';
        elementos.novaSenha.value = '';
        elementos.confirmarSenha.value = '';
    }

    function definirModoEdicao(ativo) {
        modoEdicao = ativo;

        camposEditaveis.forEach((campo) => {
            if (campo) campo.disabled = !ativo;
        });

        elementos.emailAluno.disabled = true;
        elementos.emailMentor.disabled = true;

        document.querySelectorAll('.opcao-avatar').forEach((botao) => {
            botao.disabled = !ativo || tipoPerfil !== 'aluno';
        });

        elementos.editar.hidden = ativo;
        elementos.salvar.disabled = !ativo;
        elementos.cancelar.hidden = !ativo;
    }

    function restaurarPerfilOriginal() {
        if (perfilOriginal) {
            preencherTela(perfilOriginal);
        }

        limparMensagem();
        definirModoEdicao(false);
    }

    function validarFormulario() {
        const nome = tipoPerfil === 'aluno'
            ? elementos.nomeAluno.value.trim()
            : elementos.nomeMentor.value.trim();

        if (nome.length < 5 || nome.length > 100) {
            mostrarMensagem('O nome deve possuir entre 5 e 100 caracteres.');
            return false;
        }

        if (tipoPerfil === 'aluno') {
            if (!elementos.dataNascimento.value) {
                mostrarMensagem('Informe sua data de nascimento.');
                return false;
            }

            if (elementos.dataNascimento.value >= new Date().toISOString().slice(0, 10)) {
                mostrarMensagem('A data de nascimento deve ser anterior à data atual.');
                return false;
            }

            if (!elementos.genero.value) {
                mostrarMensagem('Selecione uma opção de gênero.');
                return false;
            }
        }

        if (
            tipoPerfil === 'mentor' &&
            elementos.instituicao.value.trim().length > 100
        ) {
            mostrarMensagem('A instituição deve ter no máximo 100 caracteres.');
            return false;
        }

        const senhaAtual = elementos.senhaAtual.value;
        const novaSenha = elementos.novaSenha.value;
        const confirmarSenha = elementos.confirmarSenha.value;

        const iniciouAlteracaoSenha = senhaAtual || novaSenha || confirmarSenha;

        if (iniciouAlteracaoSenha) {
            if (!senhaAtual || !novaSenha || !confirmarSenha) {
                mostrarMensagem('Para alterar a senha, preencha os três campos.');
                return false;
            }

            if (
                novaSenha.length < 8 ||
                novaSenha.length > 255 ||
                /\s/.test(novaSenha)
            ) {
                mostrarMensagem('A nova senha deve ter entre 8 e 255 caracteres e não pode conter espaços.');
                return false;
            }

            if (novaSenha !== confirmarSenha) {
                mostrarMensagem('A nova senha e a confirmação não correspondem.');
                return false;
            }
        }

        return true;
    }

    async function salvarPerfil(evento) {
        evento.preventDefault();

        if (!modoEdicao || !validarFormulario()) return;

        elementos.salvar.disabled = true;
        limparMensagem();

        const payload = tipoPerfil === 'aluno'
            ? {
                nome: elementos.nomeAluno.value.trim(),
                dataNascimento: elementos.dataNascimento.value,
                genero: elementos.genero.value,
                urlAvatar: avatarSelecionado
            }
            : {
                nome: elementos.nomeMentor.value.trim(),
                escolaAtua: elementos.instituicao.value.trim()
            };

        const dadosSenha = {
            senhaAtual: elementos.senhaAtual.value,
            novaSenha: elementos.novaSenha.value,
            confirmacaoNovaSenha: elementos.confirmarSenha.value
        };

        const temAlteracaoSenha = Boolean(
            dadosSenha.senhaAtual ||
            dadosSenha.novaSenha ||
            dadosSenha.confirmacaoNovaSenha
        );

        try {
            const perfilAtualizado = await requisicao(
                '/usuarios/me',
                'PATCH',
                payload
            );

            preencherTela(perfilAtualizado);

            if (temAlteracaoSenha) {
                await requisicao(
                    '/usuarios/me/senha',
                    'POST',
                    dadosSenha
                );

                limparCamposSenha();
            }

            try {
                const usuarioSessao = JSON.parse(
                    sessionStorage.getItem('granalab.usuario') || '{}'
                );

                usuarioSessao.nome = perfilAtualizado.nome;
                usuarioSessao.urlAvatar = perfilAtualizado.urlAvatar;

                sessionStorage.setItem(
                    'granalab.usuario',
                    JSON.stringify(usuarioSessao)
                );
            } catch (_) {
                // A sessão será consultada novamente pelo backend na próxima tela.
            }

            definirModoEdicao(false);

            mostrarMensagem(
                temAlteracaoSenha
                    ? 'Perfil e senha atualizados com sucesso!'
                    : 'Perfil atualizado com sucesso!',
                'sucesso'
            );

        } catch (erro) {
            mostrarMensagem(
                erro.message || 'Não foi possível salvar as alterações.'
            );

        } finally {
            elementos.salvar.disabled = !modoEdicao;
        }
    }

    async function iniciar() {
        if (!token) {
            mostrarMensagem('Sua sessão expirou. Faça login novamente.');
            elementos.editar.disabled = true;
            return;
        }

        try {
            const perfil = await requisicao('/usuarios/me');

            preencherTela(perfil);
            definirModoEdicao(false);

        } catch (erro) {
            mostrarMensagem(
                erro.message || 'Não foi possível carregar seu perfil.'
            );

            elementos.editar.disabled = true;
        }
    }

    elementos.editar.addEventListener('click', () => {
        limparMensagem();
        definirModoEdicao(true);
    });

    elementos.cancelar.addEventListener('click', restaurarPerfilOriginal);

    form.addEventListener('submit', salvarPerfil);

    document.querySelectorAll('.opcao-avatar').forEach((botao) => {
        botao.addEventListener('click', () => {
            if (!modoEdicao || tipoPerfil !== 'aluno') return;

            selecionarAvatar(`/resources/img/${botao.dataset.avatar}`);
        });
    });

    document.querySelectorAll('.mostrar-senha').forEach((botao) => {
        botao.addEventListener('click', () => {
            const campo = document.getElementById(botao.dataset.target);

            if (!campo) return;

            campo.type = campo.type === 'password' ? 'text' : 'password';

            botao.setAttribute(
                'aria-label',
                campo.type === 'password' ? 'Mostrar senha' : 'Ocultar senha'
            );
        });
    });

    const dropdownToggle = document.getElementById('dropdown-toggle');
    const userMenu = document.getElementById('user-menu');

    if (dropdownToggle && userMenu) {
        dropdownToggle.addEventListener('click', () => {
            userMenu.style.display =
                userMenu.style.display === 'none' ? 'block' : 'none';
        });

        document.addEventListener('click', (evento) => {
            if (
                !dropdownToggle.contains(evento.target) &&
                !userMenu.contains(evento.target)
            ) {
                userMenu.style.display = 'none';
            }
        });
    }

    iniciar();
});
