package br.com.ed2.seguranca.service;

import br.com.ed2.seguranca.model.ComparacaoHash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Serviço responsável pela geração e comparação de resumos criptográficos (Hash) via SHA-256.
 */
public class HashService {

    private static final String ALGORITMO_HASH = "SHA-256";

    /**
     * Gera o resumo hash SHA-256 de um texto e o converte para representação hexadecimal.
     * Função unidirecional: não é possível reverter o hash para o texto original.
     */
    public String gerarHash(String texto) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITMO_HASH);
        byte[] hashBytes = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
        return bytesParaHex(hashBytes);
    }

    /**
     * Gera os hashes de duas mensagens e verifica se são idênticos,
     * demonstrando o efeito avalanche (qualquer mudança mínima altera todo o hash).
     */
    public ComparacaoHash compararMensagens(String mensagem1, String mensagem2) throws NoSuchAlgorithmException {
        String hash1 = gerarHash(mensagem1);
        String hash2 = gerarHash(mensagem2);
        return new ComparacaoHash(mensagem1, hash1, mensagem2, hash2);
    }

    /**
     * Converte um array de bytes (256 bits = 32 bytes) em uma String hexadecimal de 64 caracteres.
     */
    private String bytesParaHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}