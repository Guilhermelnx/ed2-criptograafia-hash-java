# Criptografia Simétrica (AES-256) e Funções Hash (SHA-256)

Aplicação desenvolvida em **Java** seguindo os princípios de **Programação Orientada a Objetos (POO)** para demonstrar, na prática, mecanismos fundamentais de segurança da informação: sigilo de dados por meio de criptografia simétrica e verificação de integridade via funções hash criptográficas.

Projeto desenvolvido como **Trabalho Prático da disciplina Estrutura de Dados II** (Curso de Engenharia de Software – Prof. Ricardo Vilaverde).

---

## Funcionalidades

O sistema opera via menu interativo no terminal (`CLI`) e contempla duas atividades principais:

### 1. Criptografia e Descriptografia Simétrica (`AES-256`)
* **Derivação segura de chave:** Converte qualquer senha informada pelo usuário em uma chave fixa de 256 bits (32 bytes) utilizando `SHA-256`, evitando erros de tamanho de chave no algoritmo AES.
* **Vetor de Inicialização (IV) aleatório:** Utiliza o modo `AES/CBC/PKCS5Padding` gerando um IV único de 16 bytes via `SecureRandom` a cada operação, garantindo que mensagens idênticas cifradas com a mesma chave produzam resultados distintos.
* **Codificação Base64:** Concatena `[IV + Texto Cifrado]` para transporte seguro em texto e realiza o processo inverso na descriptografia para recuperar a mensagem original.

### 2. Geração e Comparação de Resumo Hash (`SHA-256`)
* **Função unidirecional:** Utiliza a classe nativa `java.security.MessageDigest` para gerar uma assinatura hexadecimal única de 64 caracteres (256 bits).
* **Verificação de integridade e Efeito Avalanche:** Compara os hashes de duas mensagens digitadas pelo usuário, demonstrando como a alteração de um único caractere ou espaço modifica completamente o código gerado.

---

## Arquitetura e Estrutura do Projeto

O código foi organizado em camadas para separar responsabilidades entre interface, regras de negócio e modelos de dados:

```text
ed2-criptograafia-hash-java/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── br/
                └── com/
                    └── ed2/
                        └── seguranca/
                            ├── Main.java                       # Ponto de entrada da aplicação
                            ├── model/
                            │   ├── ResultadoCriptografia.java  # Encapsula dados da operação AES
                            │   └── ComparacaoHash.java         # Encapsula hashes e status de igualdade
                            ├── service/
                            │   ├── CriptografiaService.java    # Lógica de cifragem e decifragem AES-256
                            │   └── HashService.java            # Geração e comparação SHA-256
                            ├── ui/
                            │   └── MenuConsole.java            # Interface interativa em modo texto
                            └── util/
                                └── ConsoleUtil.java            # Leitura e validação de entradas do Scanner
```

---

## Tecnologias Utilizadas

* **Linguagem:** Java 21 (OpenJDK 21)
* **Gerenciador de Projeto:** Apache Maven
* **Bibliotecas Nativas:** `javax.crypto.Cipher`, `javax.crypto.spec.SecretKeySpec`, `javax.crypto.spec.IvParameterSpec`, `java.security.MessageDigest`, `java.security.SecureRandom` e `java.util.Base64` (sem dependências externas).

---

## Como Executar

### Pré-requisitos
* **JDK 21** (ou superior) instalado.
* **Maven** ou uma IDE Java (IntelliJ IDEA, Eclipse ou VS Code).

### Passo a passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/Guilhermelnx/ed2-criptograafia-hash-java.git](https://github.com/Guilhermelnx/ed2-criptograafia-hash-java.git)
   cd ed2-criptograafia-hash-java
   ```

2. **Compile e execute via Maven:**
   ```bash
   mvn compile exec:java -Dexec.mainClass="br.com.ed2.seguranca.Main"
   ```
   *(Alternativamente, abra o projeto no IntelliJ IDEA e execute diretamente o método `main` da classe `br.com.ed2.seguranca.Main`.)*

---

## Exemplo de Saída no Console

```text
=======================================================
   ESTRUTURA DE DADOS II - CRIPTOGRAFIA E HASH (JAVA)  
=======================================================
1. Atividade 1A - Criptografar mensagem (AES-256)
2. Atividade 1B - Descriptografar mensagem (AES-256)
3. Atividade 2  - Gerar e comparar Hash (SHA-256)
0. Sair
-------------------------------------------------------
Escolha uma opção: 3

--- GERAÇÃO E COMPARAÇÃO DE HASH (SHA-256) ---
Digite a primeira mensagem: Teste 123 www
Hash SHA-256 (Msg 1): de58cc076c07d2abbf19a0a2a66ebf26295e42c3ca0fb716f294bd038d86b94b

Agora informe uma segunda mensagem para comparar integridade:
Digite a segunda mensagem: Teste 123www

[Comparação de Integridade]
Hash 1: de58cc076c07d2abbf19a0a2a66ebf26295e42c3ca0fb716f294bd038d86b94b
Hash 2: 24ac8abaad808dc1da012644ce0feb45527348a19635078925cc95c87662f26d
Status: DIFERENTES (Alteração detectada - efeito avalanche)
```

---

## Fluxo de Contribuição (Git & Pull Requests)

A branch `main` possui regras de proteção ativas. Para adicionar melhorias ou correções:

1. Crie uma branch a partir da `main` atualizada:
   ```bash
   git checkout main
   git pull origin main
   git checkout -b feat/nome-da-melhoria
   ```
2. Realize os commits e envie a branch para o GitHub:
   ```bash
   git add .
   git commit -m "feat: descrição objetiva da alteração"
   git push -u origin feat/nome-da-melhoria
   ```
3. Abra um **Pull Request** na aba *Pull requests* do repositório e aguarde a revisão de um integrante do grupo antes do merge.

---

## Integrantes do Grupo

* **Gilvan Dias**
* **Guilherme Conceição** ([@Guilhermelnx](https://github.com/Guilhermelnx))
* **Matheus Castro**
