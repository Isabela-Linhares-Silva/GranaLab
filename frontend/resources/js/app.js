// T020 - Validações e integração dos formulários de cadastro

document.addEventListener('DOMContentLoaded', function() {
    const formCadastro = document.getElementById('form-cadastro');
    const perfilAluno = document.getElementById('perfil-aluno');
    const perfilMentor = document.getElementById('perfil-mentor');
    const camposAluno = document.getElementById('campos-aluno');
    const camposMentor = document.getElementById('campos-mentor');
    const mensagemErro = document.getElementById('mensagem-erro');

    // Alternar campos conforme perfil selecionado (T017)
    if (perfilAluno && perfilMentor) {
        perfilAluno.addEventListener('change', function() {
            camposAluno.style.display = 'block';
            camposMentor.style.display = 'none';
        });
        perfilMentor.addEventListener('change', function() {
            camposAluno.style.display = 'none';
            camposMentor.style.display = 'block';
        });
    }

    // Validações client-side (T020)
    if (formCadastro) {
        formCadastro.addEventListener('submit', function(event) {
            mensagemErro.style.display = 'none';
            mensagemErro.textContent = '';

            const nome = document.getElementById('nome').value.trim();
            const email = document.getElementById('email').value.trim();
            const senha = document.getElementById('senha').value;
            const confirmaSenha = document.getElementById('confirma-senha').value;
            const perfil = document.querySelector('input[name="perfil"]:checked').value;

            // RN01: campos obrigatórios não vazios
            if (!nome) {
                exibirErro('Preencha o campo Nome.');
                event.preventDefault();
                return;
            }
            if (!email) {
                exibirErro('Preencha o campo E-mail.');
                event.preventDefault();
                return;
            }
            if (!senha) {
                exibirErro('Preencha o campo Senha.');
                event.preventDefault();
                return;
            }
            if (!confirmaSenha) {
                exibirErro('Preencha o campo Confirmação de senha.');
                event.preventDefault();
                return;
            }

            // RN02: formato de e-mail válido
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                exibirErro('E-mail em formato inválido.');
                event.preventDefault();
                return;
            }

            // RN05: senha mínima 8 caracteres
            if (senha.length < 8) {
                exibirErro('A senha deve possuir no mínimo 8 caracteres.');
                event.preventDefault();
                return;
            }

            // RN06: senha sem espaço em branco
            if (senha.includes(' ')) {
                exibirErro('A senha não deve conter espaço em branco.');
                event.preventDefault();
                return;
            }

            // EX004: senha e confirmação iguais
            if (senha !== confirmaSenha) {
                exibirErro('As senhas não correspondem.');
                event.preventDefault();
                return;
            }

            // Campos específicos do Aluno
            if (perfil === 'aluno') {
                const dataNascimento = document.getElementById('data-nascimento').value;
                const genero = document.getElementById('genero').value;
                if (!dataNascimento) {
                    exibirErro('Preencha a Data de nascimento.');
                    event.preventDefault();
                    return;
                }
                if (!genero) {
                    exibirErro('Selecione o Gênero.');
                    event.preventDefault();
                    return;
                }
            }

            // Integração com API (T026 - preparação)
            // Os dados serão enviados para POST /auth/cadastro/aluno ou /auth/cadastro/mentor
        });
    }

    function exibirErro(mensagem) {
        if (mensagemErro) {
            mensagemErro.textContent = mensagem;
            mensagemErro.style.display = 'block';
        }
    }
});
