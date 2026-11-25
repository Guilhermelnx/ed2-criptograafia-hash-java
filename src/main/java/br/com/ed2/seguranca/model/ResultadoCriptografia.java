package br.com.ed2.seguranca.model;

/**
 * Modelo que encapsula o estado de uma operação de criptografia.
 */
public class ResultadoCriptografia {
    private final String textoOriginal;
    private final String textoCriptografadoBase64;
    private final String algoritmo;

    public ResultadoCriptografia(String textoOriginal, String textoCriptografadoBase64, String algoritmo) {
        this.textoOriginal = textoOriginal;
        this.textoCriptografadoBase64 = textoCriptografadoBase64;
        this.algoritmo = algoritmo;
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public String getTextoCriptografadoBase64() {
        return textoCriptografadoBase64;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }
}