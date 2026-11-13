// Configure window.GRANALAB_API_URL before this script when the API is not on localhost:8080.
const API_BASE_URL = window.GRANALAB_API_URL || 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    const cadastro = document.getElementById('form-cadastro');
    const login = document.getElementById('form-login');
    const recuperar = document.getElementById('form-recuperar-senha');
    const redefinir = document.getElementById('form-redefinir-senha');

    if (cadastro) configurarCadastro(cadastro);
    if (login) configurarLogin(login);
    if (recuperar) configurarRecuperacao(recuperar);
    if (redefinir) configurarRedefinicao(redefinir);
    configurarPaginaDePerfil();
});

function configurarCadastro(form) {
    const camposAluno = document.getElementById('campos-aluno');
    const camposMentor = document.getElementById('campos-mentor');
    const perfilAluno = document.getElementById('perfil-aluno');
    const perfilMentor = document.getElementById('perfil-mentor');
    const dataNascimento = document.getElementById('data-nascimento');
    const mensagem = document.getElementById('mensagem-erro');
    const botao = document.getElementById('btn-cadastrar');

    if (dataNascimento) dataNascimento.max = dataLocalISO();

    const atualizarPerfil = () => {
        const alunoSelecionado = perfilAluno.checked;
        camposAluno.hidden = !alunoSelecionado;
        camposMentor.hidden = alunoSelecionado;
        dataNascimento.required = alunoSelecionado;
        document.getElementById('genero').required = alunoSelecionado;
    };
    perfilAluno.addEventListener('change', atualizarPerfil);
    perfilMentor.addEventListener('change', atualizarPerfil);
    atualizarPerfil();

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        limparMensagem(mensagem);
        limparInvalidos(form);

        const perfil = perfilAluno.checked ? 'aluno' : 'mentor';
        const nome = document.getElementById('nome').value.trim();
        const email = document.getElementById('email').value.trim();
        const senha = document.getElementById('senha').value;
        const confirmaSenha = document.getElementById('confirma-senha').value;
        const campos = perfil === 'aluno'
            ? [document.getElementById('nome'), dataNascimento, document.getElementById('genero'), document.getElementById('email'), document.getElementById('senha'), document.getElementById('confirma-senha')]
            : [document.getElementById('nome'), document.getElementById('email'), document.getElementById('senha'), document.getElementById('confirma-senha')];

        if (!validarObrigatorios(campos, mensagem)) return;
        if (!validarEmail(email, document.getElementById('email'), mensagem)) return;
        if (senha.length < 8) return invalidar(document.getElementById('senha'), mensagem, 'A senha deve possuir no mínimo 8 caracteres.');
        if (/\s/.test(senha)) return invalidar(document.getElementById('senha'), mensagem, 'A senha não deve conter espaços em branco.');
        if (senha !== confirmaSenha) return invalidar(document.getElementById('confirma-senha'), mensagem, 'As senhas não correspondem.');
        if (perfil === 'aluno' && dataNascimento.value > dataNascimento.max) {
            return invalidar(dataNascimento, mensagem, 'A data de nascimento não pode ser futura.');
        }

        const payload = perfil === 'aluno'
            ? { nome, dataNascimento: dataNascimento.value, genero: document.getElementById('genero').value, email, senha, confirmaSenha }
            : { nome, escolaAtua: document.getElementById('escola').value.trim() || null, email, senha, confirmaSenha };

        await enviarFormulario({
            form, button: botao, message: mensagem, url: `/auth/cadastro/${perfil}`,
            payload,
            successMessage: 'Cadastro realizado com sucesso. Você será direcionado para o login.',
            onSuccess: () => window.setTimeout(() => { window.location.href = '../index.html'; }, 1200)
        });
    });
}

function configurarLogin(form) {
    const mensagem = document.getElementById('mensagem-erro-login');
    const botao = document.getElementById('btn-login');
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        limparMensagem(mensagem);
        limparInvalidos(form);

        const emailInput = document.getElementById('email');
        const senhaInput = document.getElementById('senha');
        const email = emailInput.value.trim();
        if (!validarObrigatorios([emailInput, senhaInput], mensagem)) return;
        if (!validarEmail(email, emailInput, mensagem)) return;

        await enviarFormulario({
            form, button: botao, message: mensagem, url: '/auth/login', payload: { email, senha: senhaInput.value },
            onSuccess: (data) => {
                const usuario = data.usuario || data;
                const token = data.token;
                if (!token || !usuario.tipoPerfil) {
                    mostrarMensagem(mensagem, 'A resposta do servidor não contém as informações de sessão esperadas.');
                    return;
                }
                sessionStorage.setItem('granalab.token', token);
                sessionStorage.setItem('granalab.usuario', JSON.stringify(usuario));
                const perfil = normalizarPerfil(usuario.tipoPerfil);
                window.location.href = perfil === 'aluno' ? 'pages/aluno.html' : 'pages/mentor.html';
            }
        });
    });
}

function configurarRecuperacao(form) {
    const mensagem = document.getElementById('mensagem-recuperacao');
    const botao = document.getElementById('btn-recuperar');
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        limparMensagem(mensagem);
        limparInvalidos(form);
        const emailInput = document.getElementById('email');
        const email = emailInput.value.trim();
        if (!validarObrigatorios([emailInput], mensagem)) return;
        if (!validarEmail(email, emailInput, mensagem)) return;
        sessionStorage.setItem('granalab.email-recuperacao', email);

        await enviarFormulario({
            form, button: botao, message: mensagem, url: '/auth/redefinir-senha', payload: { email },
            successMessage: 'Solicitação processada. Se você não receber o token, o envio por e-mail ainda não está disponível no backend.'
        });
    });
}

function configurarRedefinicao(form) {
    const mensagem = document.getElementById('mensagem-redefinicao');
    const botao = document.getElementById('btn-redefinir');
    const emailInput = document.getElementById('email');
    const emailSalvo = sessionStorage.getItem('granalab.email-recuperacao');
    const parametros = new URLSearchParams(window.location.search);
    if (emailSalvo) emailInput.value = emailSalvo;
    if (parametros.has('email')) emailInput.value = parametros.get('email');
    if (parametros.has('token')) document.getElementById('token').value = parametros.get('token');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        limparMensagem(mensagem);
        limparInvalidos(form);
        const tokenInput = document.getElementById('token');
        const senhaInput = document.getElementById('nova-senha');
        const confirmaInput = document.getElementById('confirma-senha');
        const email = emailInput.value.trim();
        const senha = senhaInput.value;
        const confirmaSenha = confirmaInput.value;

        if (!validarObrigatorios([emailInput, tokenInput, senhaInput, confirmaInput], mensagem)) return;
        if (!validarEmail(email, emailInput, mensagem)) return;
        if (senha.length < 8) return invalidar(senhaInput, mensagem, 'A nova senha deve possuir no mínimo 8 caracteres.');
        if (/\s/.test(senha)) return invalidar(senhaInput, mensagem, 'A nova senha não deve conter espaços em branco.');
        if (senha !== confirmaSenha) return invalidar(confirmaInput, mensagem, 'As senhas não correspondem.');

        await enviarFormulario({
            form, button: botao, message: mensagem, url: '/auth/redefinir-senha',
            payload: { email, novaSenha: senha, confirmaSenha, token: tokenInput.value.trim() },
            onSuccess: (data) => {
                // O endpoint atual responde confirmando geração de token, não a troca da senha.
                const retorno = String(data.mensagem || data.message || '').toLowerCase();
                if (retorno.includes('token') && retorno.includes('enviado')) {
                    mostrarMensagem(mensagem, 'O backend ainda não confirma a redefinição da senha. A operação de atualização precisa ser disponibilizada pela API.');
                    return;
                }
                const senhaConfirmada = data.sucesso === true || (retorno.includes('senha') && retorno.includes('redefinida'));
                if (!senhaConfirmada) {
                    mostrarMensagem(mensagem, 'A resposta do servidor não confirmou a redefinição da senha.');
                    return;
                }
                mostrarMensagem(mensagem, 'Senha redefinida com sucesso. Você será direcionado para o login.', 'success');
                sessionStorage.removeItem('granalab.email-recuperacao');
                window.setTimeout(() => { window.location.href = '../index.html'; }, 1400);
            }
        });
    });
}

function configurarPaginaDePerfil() {
    const destino = document.getElementById('perfil-usuario');
    if (!destino) return;
    const usuario = lerUsuarioDaSessao();
    if (!usuario) {
        window.location.replace('../index.html');
        return;
    }
    destino.textContent = usuario.nome ? `Olá, ${usuario.nome}.` : 'Login realizado com sucesso.';
}

async function enviarFormulario({ form, button, message, url, payload, successMessage, onSuccess }) {
    button.disabled = true;
    button.setAttribute('aria-busy', 'true');
    try {
        const response = await fetch(`${API_BASE_URL}${url}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
            body: JSON.stringify(payload)
        });
        const data = await lerResposta(response);
        if (!response.ok) {
            mostrarMensagem(message, mensagemDaApi(data, response.status));
            marcarCamposDaApi(form, data);
            return;
        }
        if (successMessage) mostrarMensagem(message, successMessage, 'success');
        if (onSuccess) onSuccess(data);
    } catch (error) {
        mostrarMensagem(message, 'Não foi possível conectar ao servidor. Tente novamente em instantes.');
    } finally {
        button.disabled = false;
        button.removeAttribute('aria-busy');
    }
}

async function lerResposta(response) {
    const texto = await response.text();
    if (!texto) return {};
    try { return JSON.parse(texto); } catch { return { mensagem: texto }; }
}

function mensagemDaApi(data, status) {
    if (typeof data === 'string') return data;
    const mensagem = data.erro || data.message || data.mensagem || data.detail;
    if (mensagem) return mensagem;
    if (data.errors && typeof data.errors === 'object') return Object.values(data.errors).join(' ');
    if (status === 409) return 'Este e-mail já está cadastrado.';
    if (status === 401) return 'E-mail ou senha inválidos.';
    return 'Não foi possível concluir a solicitação. Confira os dados e tente novamente.';
}

function validarObrigatorios(campos, mensagem) {
    const vazio = campos.find((campo) => !campo.value.trim());
    if (!vazio) return true;
    const label = document.querySelector(`label[for="${vazio.id}"]`);
    invalidar(vazio, mensagem, `Preencha o campo ${label ? label.textContent.replace('(opcional)', '').trim() : 'obrigatório'}.`);
    return false;
}

function validarEmail(email, input, mensagem) {
    if (/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) return true;
    invalidar(input, mensagem, 'Informe um e-mail em formato válido.');
    return false;
}

function invalidar(campo, mensagem, texto) {
    campo.setAttribute('aria-invalid', 'true');
    campo.focus();
    mostrarMensagem(mensagem, texto);
    return false;
}

function marcarCamposDaApi(form, data) {
    const erros = data.errors || data.fieldErrors;
    if (!erros || typeof erros !== 'object') return;
    Object.keys(erros).forEach((nome) => {
        const campo = form.elements.namedItem(nome);
        if (campo && typeof campo.setAttribute === 'function') campo.setAttribute('aria-invalid', 'true');
    });
}

function limparInvalidos(form) {
    form.querySelectorAll('[aria-invalid="true"]').forEach((campo) => campo.removeAttribute('aria-invalid'));
}

function limparMensagem(elemento) {
    elemento.hidden = true;
    elemento.textContent = '';
    elemento.removeAttribute('data-kind');
}

function mostrarMensagem(elemento, texto, tipo = 'error') {
    elemento.textContent = texto;
    elemento.dataset.kind = tipo;
    elemento.hidden = false;
}

function normalizarPerfil(tipo) {
    return String(tipo).normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase();
}

function dataLocalISO() {
    const hoje = new Date();
    const deslocamento = hoje.getTimezoneOffset() * 60000;
    return new Date(hoje.getTime() - deslocamento).toISOString().slice(0, 10);
}

function lerUsuarioDaSessao() {
    try { return JSON.parse(sessionStorage.getItem('granalab.usuario') || 'null'); }
    catch { return null; }
}
