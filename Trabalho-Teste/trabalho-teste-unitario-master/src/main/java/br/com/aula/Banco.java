package br.com.aula;

import java.util.ArrayList;
import java.util.List;

import br.com.aula.exception.ContaJaExistenteException;
import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;

public class Banco {

	private List<Conta> contas = new ArrayList<Conta>();

	public Banco() {
	}

	public Banco(List<Conta> contas) {
		this.contas = contas;
	}


	public void cadastrarConta(Conta conta) throws ContaJaExistenteException, NumeroDeContaInvalidoException {

		for (Conta c : contas) {
			boolean isNomeClienteIgual = c.getCliente().getNome().equals(conta.getCliente().getNome());
			boolean isNumeroContaIgual = c.getNumeroConta() == conta.getNumeroConta();

			if (isNomeClienteIgual || isNumeroContaIgual) {
				throw new ContaJaExistenteException();
			}
			if(c.getNumeroConta()<0){
				throw new NumeroDeContaInvalidoException();
			}
		}
		
		this.contas.add(conta);

	}


	public void efetuarTransferencia(int numeroContaOrigem, int numeroContaDestino, int valor)
			throws ContaNaoExistenteException, ContaSemSaldoException, ValorNegativoException {

		Conta contaOrigem = this.obterContaPorNumero(numeroContaOrigem);
		Conta contaDestino = this.obterContaPorNumero(numeroContaDestino);

		boolean isContaOrigemExistente = contaOrigem != null;
		boolean isContaDestinoExistente = contaDestino != null;
		// condicao para nao permitir transferir valor negativo
		// item E
		if( valor > 0) {
			if (isContaOrigemExistente && isContaDestinoExistente) {

				boolean isContaOrigemPoupanÃ§a = contaOrigem.getTipoConta().equals(TipoConta.POUPANCA);
				boolean isSaldoContaOrigemNegativo = contaOrigem.getSaldo() - valor < 0;

				if (isContaOrigemPoupanÃ§a && isSaldoContaOrigemNegativo) {
					throw new ContaSemSaldoException();
				}

				contaOrigem.debitar(valor);
				contaDestino.creditar(valor);

			} else {
				throw new ContaNaoExistenteException();
			}
		} else {
			throw new ContaNaoExistenteException();
		}
	}

	public Conta obterContaPorNumero(int numeroConta) {

		for (Conta c : contas) {
			if (c.getNumeroConta() == numeroConta) {
				return c;
			}
		}

		return null;
	}

	public List<Conta> obterContas() {
		return this.contas;
	}
}
