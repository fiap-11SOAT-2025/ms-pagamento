package org.fiap.Pagamento.core.exception;

public class RecursoNaoEncontradoExcecao extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RecursoNaoEncontradoExcecao(String mensagem) {
        super(mensagem);
    }

    public RecursoNaoEncontradoExcecao(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
