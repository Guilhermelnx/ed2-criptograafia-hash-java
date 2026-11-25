package br.com.ed2.seguranca.ui;

import br.com.ed2.seguranca.model.ComparacaoHash;
import br.com.ed2.seguranca.model.ResultadoCriptografia;
import br.com.ed2.seguranca.service.CriptografiaService;
import br.com.ed2.seguranca.service.HashService;
import br.com.ed2.seguranca.util.ConsoleUtil;

/**
 * Camada de apresentação em modo texto (CLI) com o menu interativo exigido no trabalho.
 */
public class MenuConsole {

    private final CriptografiaService criptografiaService;
    private final HashService hashService;
    private final ConsoleUtil console;
    private ResultadoCriptografia ultimoResultado;

    public MenuConsole() {
        this.criptografiaService = new CriptografiaService();
        this.hashService = new HashService();
        this.console = new ConsoleUtil();
    }

    public void iniciar() {
        int opcao;
        do {
            exibirCabecalho();
            opcao = console.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> executarCriptografia();
                case 2 -> executarDescriptografia();
                case 3 -> executarGeracaoEComparacaoHash();
                case 0 -> System.out.println("\nEncerrando a aplicação...");
                default -> System.out.println("\nOpção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        console.fechar();
    }

    private void exibirCabecalho() {
        System.out.println("\n=======================================================");
        System.out.println("   ESTRUTURA DE DADOS II - CRIPTOGRAFIA E HASH (JAVA)  ");
        System.out.println("=======================================================");
        System.out.println("1. Atividade 1A - Criptografar mensagem (AES-256)");
        System.out.println("2. Atividade 1B - Descriptografar mensagem (AES-256)");
        System.out.println("3. Atividade 2  - Gerar e comparar Hash (SHA-256)");
        System.out.println("0. Sair");
        System.out.println("-------------------------------------------------------");
    }

    private void executarCriptografia() {
        System.out.println("\n--- CRIPTOGRAFIA SIMÉTRICA (AES) ---");
        String mensagem = console.lerTexto("Digite a mensagem original: ");
        String chave = console.lerTexto("Defina a chave de criptografia: ");

        try {
            ultimoResultado = criptografiaService.criptografar(mensagem, chave);
            System.out.println("\n[Resultado]");
            System.out.println("Algoritmo utilizado : " + ultimoResultado.getAlgoritmo());
            System.out.println("Texto Original      : " + ultimoResultado.getTextoOriginal());
            System.out.println("Texto Criptografado : " + ultimoResultado.getTextoCriptografadoBase64());
        } catch (Exception e) {
            System.out.println("Erro ao criptografar: " + e.getMessage());
        }
    }

    private void executarDescriptografia() {
        System.out.println("\n--- DESCRIPTOGRAFIA SIMÉTRICA (AES) ---");
        String textoCifrado;

        if (ultimoResultado != null) {
            String usarUltimo = console.lerTexto("Deseja usar o último texto criptografado? (S/N): ");
            if (usarUltimo.equalsIgnoreCase("S")) {
                textoCifrado = ultimoResultado.getTextoCriptografadoBase64();
            } else {
                textoCifrado = console.lerTexto("Cole o texto criptografado (Base64): ");
            }
        } else {
            textoCifrado = console.lerTexto("Cole o texto criptografado (Base64): ");
        }

        String chave = console.lerTexto("Informe a chave de descriptografia: ");

        try {
            String textoRecuperado = criptografiaService.descriptografar(textoCifrado, chave);
            System.out.println("\n[Resultado]");
            System.out.println("Texto Recuperado: " + textoRecuperado);
        } catch (Exception e) {
            System.out.println("\nFalha ao descriptografar! Chave incorreta ou texto corrompido.");
        }
    }

    private void executarGeracaoEComparacaoHash() {
        System.out.println("\n--- GERAÇÃO E COMPARAÇÃO DE HASH (SHA-256) ---");
        try {
            String msg1 = console.lerTexto("Digite a primeira mensagem: ");
            String hash1 = hashService.gerarHash(msg1);
            System.out.println("Hash SHA-256 (Msg 1): " + hash1);

            System.out.println("\nAgora informe uma segunda mensagem para comparar integridade:");
            String msg2 = console.lerTexto("Digite a segunda mensagem: ");

            ComparacaoHash comparacao = hashService.compararMensagens(msg1, msg2);

            System.out.println("\n[Comparação de Integridade]");
            System.out.println("Hash 1: " + comparacao.getHash1());
            System.out.println("Hash 2: " + comparacao.getHash2());
            System.out.println("Status: " + (comparacao.isIguais()
                    ? "IGUAIS (Integridade confirmada - mensagens idênticas)"
                    : "DIFERENTES (Alteração detectada - efeito avalanche)"));
        } catch (Exception e) {
            System.out.println("Erro ao gerar hash: " + e.getMessage());
        }
    }
}