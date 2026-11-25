package br.com.ed2.seguranca.service;

import br.com.ed2.seguranca.model.ResultadoCriptografia;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * Serviço responsável pela criptografia e descriptografia simétrica utilizando AES-256.
 */
public class CriptografiaService {

    private static final String ALGORITMO = "AES";
    private static final String TRANSFORMACAO = "AES/CBC/PKCS5Padding";
    private static final int TAMANHO_IV = 16; // Bloco padrão do AES = 128 bits (16 bytes)

    /**
     * Deriva uma chave fixa de 256 bits (32 bytes) a partir de qualquer texto digitado pelo usuário,
     * utilizando SHA-256. Isso evita erros de tamanho inválido de chave no AES.
     */
    private SecretKeySpec gerarChaveAES(String chaveUsuario) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(chaveUsuario.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, ALGORITMO);
    }

    /**
     * Criptografa uma mensagem em texto plano usando AES/CBC/PKCS5Padding.
     * Gera um Vetor de Inicialização (IV) aleatório e o anexa ao início do resultado
     * antes de codificar em Base64, garantindo que textos iguais gerem saídas diferentes.
     */
    public ResultadoCriptografia criptografar(String mensagem, String chaveUsuario) throws Exception {
        SecretKeySpec secretKey = gerarChaveAES(chaveUsuario);

        // 1. Gera um IV aleatório para o modo CBC
        byte[] iv = new byte[TAMANHO_IV];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 2. Inicializa o Cipher em modo de encriptação
        Cipher cipher = Cipher.getInstance(TRANSFORMACAO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        // 3. Executa a transformação dos bytes do texto original
        byte[] textoCifrado = cipher.doFinal(mensagem.getBytes(StandardCharsets.UTF_8));

        // 4. Combina [IV + Texto Cifrado] para permitir a descriptografia posterior
        byte[] combinado = new byte[iv.length + textoCifrado.length];
        System.arraycopy(iv, 0, combinado, 0, iv.length);
        System.arraycopy(textoCifrado, 0, combinado, iv.length, textoCifrado.length);

        String base64 = Base64.getEncoder().encodeToString(combinado);
        return new ResultadoCriptografia(mensagem, base64, TRANSFORMACAO);
    }

    /**
     * Reverte o processo de criptografia extraindo o IV dos primeiros 16 bytes
     * e decifrando o restante com a chave fornecida.
     */
    public String descriptografar(String textoCriptografadoBase64, String chaveUsuario) throws Exception {
        SecretKeySpec secretKey = gerarChaveAES(chaveUsuario);

        // 1. Decodifica de Base64 para bytes brutos
        byte[] combinado = Base64.getDecoder().decode(textoCriptografadoBase64);

        if (combinado.length < TAMANHO_IV) {
            throw new IllegalArgumentException("Texto criptografado inválido ou corrompido.");
        }

        // 2. Separa o IV (primeiros 16 bytes) da mensagem cifrada
        byte[] iv = Arrays.copyOfRange(combinado, 0, TAMANHO_IV);
        byte[] textoCifrado = Arrays.copyOfRange(combinado, TAMANHO_IV, combinado.length);

        // 3. Inicializa o Cipher em modo de decriptação
        Cipher cipher = Cipher.getInstance(TRANSFORMACAO);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        // 4. Recupera o texto original
        byte[] textoDecifrado = cipher.doFinal(textoCifrado);
        return new String(textoDecifrado, StandardCharsets.UTF_8);
    }
}