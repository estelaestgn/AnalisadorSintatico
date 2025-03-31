# Analisador Sintatico

## Descrição da Gramática Fornecida

A gramática fornecida descreve uma linguagem que envolve expressões aritméticas com adição, subtração, multiplicação e divisão, além do uso de parênteses e identificadores. A gramática é definida pelas seguintes regras de produção:

1. E → E + T | E - T | T
2. T → T * F | T / F | F
3. F → + F | - F | ( E ) | id

Onde:
- E representa uma expressão, que pode ser uma soma ou subtração de termos (T).
- T representa um termo, que pode ser uma multiplicação ou divisão de fatores (F).
- F representa um fator, que pode ser um identificador (id), um número, ou uma expressão entre parênteses. Além disso, F pode começar com um operador unário (+ ou -).

## Conjuntos First e Follow Calculados

### Conjunto First

O conjunto **First** de um símbolo não-terminal é o conjunto de terminais que podem aparecer no início de uma string derivada a partir desse símbolo.

- First(E) = { "+", "-", "(", "id", "num" }

- First(T) = { "+", "-", "(", "id", "num" }

- First(F) = { "+", "-", "(", "id", "num" }

### Conjunto Follow

O conjunto **Follow** de um símbolo não-terminal é o conjunto de terminais que podem seguir esse símbolo em alguma derivação da gramática.

- Follow(E) = { ")", "$" }

- Follow(T) = { "+", "-", ")", "$" }

- Follow(F) = { "*", "/", "+", "-", ")", "$" }

## Implementação do Analisador

O analisador sintático é implementado em Java e realiza a análise de uma expressão de entrada utilizando a gramática fornecida. Ele consiste em duas partes principais: análise léxica e análise sintática.

### Análise Léxica

A função **lexer** transforma a string de entrada em uma lista de tokens. Ela utiliza o StringTokenizer` para dividir a entrada com base em operadores aritméticos, parênteses e espaços, e categoriza cada token como um identificador (id), número (num), operador (+, -, *, /), ou parênteses ('(', ')').

### Análise Sintática

A análise sintática é realizada pela implementação das funções E(), T() e F() que representam as produções da gramática. A função E() lida com expressões, T() lida com termos e F() lida com fatores. As funções consumem tokens da lista de tokens e verificam se a entrada segue a estrutura da gramática fornecida.

Se um erro de sintaxe for detectado, uma exceção é lançada. Caso contrário, o código imprime uma mensagem indicando que a entrada é sintaticamente correta.

### Conjuntos First e Follow

Além da análise sintática, o código também calcula e exibe os conjuntos **First** e **Follow** da gramática. Esses conjuntos são calculados com base nas regras de produção e são úteis para a construção de analisadores preditivos em compiladores.

## Casos de Teste Utilizados e Resultados Obtidos

### Caso de Teste 1: Expressão Simples

**Entrada:**

```java
String input = "9 + x * 23 - ( y + 5 )";
```

**Execução:**

O analisador primeiro divide a entrada em tokens, e depois tenta aplicar as regras da gramática. Ele executa a análise sintática corretamente, construindo a árvore sintática correspondente e exibindo os conjuntos **First** e **Follow** calculados.

**Saída Esperada:**

```
Entrada sintaticamente correta!

Conjunto First:
First(E) = [+ , ( , id , num , -]
First(T) = [+ , ( , id , num , -]
First(F) = [+ , ( , id , num , -]

Conjunto Follow:
Follow(E) = [), $]
Follow(T) = [+ , - , ) , $]
Follow(F) = [* , / , + , - , ) , $]
```

Neste caso de teste, a entrada foi corretamente analisada e os conjuntos **First** e **Follow** foram calculados e exibidos. Além disso, a árvore sintática da expressão foi impressa, mostrando a estrutura hierárquica da expressão conforme as regras da gramática.

### Caso de Teste 2: Entrada Inválida

**Entrada:**

```java
String input = "9 + * 23";
```

**Execução:**

A entrada contém um erro de sintaxe, pois o operador `*` aparece imediatamente após um operador `+`. Isso viola as regras da gramática, e o analisador deve lançar uma exceção indicando um erro de sintaxe.

**Saída Esperada:**

```
Erro de sintaxe: Esperado um id ou número
```

Neste caso, a entrada não segue a estrutura esperada da gramática e um erro de sintaxe é detectado e exibido.

### Conclusão

Este analisador sintático foi capaz de processar expressões aritméticas de acordo com a gramática fornecida. Ele divide a entrada em tokens, constrói a árvore sintática e exibe os conjuntos **First** e **Follow** calculados. A documentação também fornece detalhes sobre os casos de teste usados e os resultados obtidos.
