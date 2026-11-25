package br.com.ed2.seguranca.model;

/**
 * Modelo que armazena os dados e o resultado da comparação entre dois hashes.
 */
public class ComparacaoHash {
    private final String mensagem1;
    private final String hash1;
    private final String mensagem2;
    private final String hash2;
    private final boolean iguais;

    public ComparacaoHash(String mensagem1, String hash1, String mensagem2, String hash2) {
        this.mensagem1 = mensagem1;
        this.hash1 = hash1;
        this.mensagem2 = mensagem2;
        this.hash2 = hash2;
        this.iguais = hash1.equals(hash2);
    }

    public String getMensagem1() { return mensagem1; }
    public String getHash1() { return hash1; }
    public String getMensagem2() { return mensagem2; }
    public String getHash2() { return hash2; }
    public boolean isIguais() { return iguais; }
}