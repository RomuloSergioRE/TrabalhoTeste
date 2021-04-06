package br.com.aula;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import br.com.aula.exception.ContaJaExistenteException;
import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;

public class BancoTest {

	@Test
	public void deveCadastrarConta() throws ContaJaExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta);

		// Verificação
		assertEquals(1, banco.obterContas().size());
	}

	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNumeroRepetido() throws ContaJaExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta conta2 = new Conta(cliente2, 123, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	@Test
	public void deveEfetuarTransferenciaContasCorrentes() throws ContaSemSaldoException, ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		// Verificação
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}

	//item C
	@Test(expected = NumeroInvalidoExpcetion.class)
	public void naoDeveCadastrarContaNumeroInvalido() throws ContaJaExistenteException, NumeroInvalidoExpcetion {

		// Cenario
		Cliente cliente = new Cliente("filho");
		Conta conta1 = new Conta(cliente, -321, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Pai");
		Conta conta2 = new Conta(cliente2, 678, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}
	//item D
	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaComNomeDoClienteQueJaExiste() throws ContaJaExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Joao");
		Conta conta2 = new Conta(cliente2, 1234, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	//item C
	@Test(expectesd = ContaNaoExistenteException.class)
	public void naoDeveEfetuarTransferenciaContasOrigemTipoPoupançaComSaldoNegativo() throws ContaSemSaldoException, ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 1000, TipoConta.POUPANÇA);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 1001);

	}
	//item D
	@Test(expectesd = ContaNaoExistenteException.class)
	public void naoDeveEfetuarTransferenciaContasOrigemInexistente() throws ContaSemSaldoException, ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 1000, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem));

		// Ação
		banco.efetuarTransferencia(123, 456, 500);

	}

	//item E
	@Test(expectesd = ValorNegativoException.class)
	public void naoDeveEfetuarTransferenciaComValorNegativo() throws ContaSemSaldoException, ContaNaoExistenteException,ValorNegativoException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 1000, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, -100);

	}

}
