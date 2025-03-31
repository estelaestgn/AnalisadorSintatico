import java.util.*;

class Sintatico {
    private static final String ID = "id";
    private static final String NUM = "num";
    private static final String MAIS = "+";
    private static final String MENOS = "-";
    private static final String VAZIO = "";
    private static final String ABRE_PARENTESES = "(";
    private static final String FECHA_PARENTESES = ")";
    
    // Conjuntos First e Follow
    private Map<String, Set<String>> first = new HashMap<>();
    private Map<String, Set<String>> follow = new HashMap<>();

    private List<String> tokens;
    private int pos;

    // Construtor
    public Sintatico(String input) {
        // Chama a função lexer para transformar a entrada em tokens
        this.tokens = lexer(input);
        this.pos = 0;
    }
    
    // Análise Léxica
    // Função que transforma uma string de entrada em tokens válidos
    private List<String> lexer(String input) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(input, "+-*/() ", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.length() > 0) {
                if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    tokens.add(ID);  // Identificadores
                } else if (token.matches("\\d+")) {
                    tokens.add(NUM);  // Números
                } else {
                    tokens.add(token);  // Operadores e parênteses
                }
            }
        }
        return tokens;
    }

    // Função que verifica se o próximo token corresponde a um esperado
    private String lookahead() {
        if (pos < tokens.size()) {
            return tokens.get(pos);
        }
        return VAZIO; // Se não houver mais tokens, retornamos vazio
    }

    // Função que "consome" o token, vai para o próximo
    private void consume() {
        if (pos < tokens.size()) {
            pos++;
        }
    }

    // Função que implementa a regra E -> E + T | E - T | T
    public void E() {
        T();
        while (lookahead().equals(MAIS) || lookahead().equals(MENOS)) {
            consume();
            T();
        }
    }

    // Função que implementa a regra T -> T * F | T / F | F
    public void T() {
        F();
        while (lookahead().equals("*") || lookahead().equals("/")) {
            consume();
            F();
        }
    }

    // Função que implementa a regra F -> + F | - F | ( E ) | id
    public void F() {
        if (lookahead().equals("+") || lookahead().equals("-")) {
            consume(); // consome o operador
            F(); // aplica a regra F -> + F | - F
        } else if (lookahead().equals(ABRE_PARENTESES)) {
            consume(); // consome o '('
            E(); // aplica a regra E dentro dos parênteses
            if (lookahead().equals(FECHA_PARENTESES)) {
                consume(); // consome o ')'
            } else {
                throw new RuntimeException("Erro de sintaxe: Esperado ')'");
            }
        } else if (lookahead().equals(ID) || lookahead().equals(NUM)) {
            consume(); // consome o identificador ou número
        } else {
            throw new RuntimeException("Erro de sintaxe: Esperado um id ou número");
        }
    }
    
    // Função para calcular o conjunto First
    private void calcularFirst() {
        // Adicionando as produções da gramática à estrutura First
        first.put("E", new HashSet<>(Arrays.asList(MAIS, MENOS, "(", ID, NUM)));
        first.put("T", new HashSet<>(Arrays.asList(MAIS, MENOS, "(", ID, NUM)));
        first.put("F", new HashSet<>(Arrays.asList("+", "-", "(", ID, NUM)));
    }

    // Função para calcular o conjunto Follow
    private void calcularFollow() {
        // Inicializando Follow(E) com { $ } (fim de entrada)
        follow.put("E", new HashSet<>(Arrays.asList("$", FECHA_PARENTESES)));
        follow.put("T", new HashSet<>(Arrays.asList(MAIS, MENOS, FECHA_PARENTESES, "$")));
        follow.put("F", new HashSet<>(Arrays.asList("*", "/", MAIS, MENOS, FECHA_PARENTESES, "$")));
    }

    // Função para iniciar o processo de análise sintática
    public void parse() {
        E();
        if (pos != tokens.size()) {
            throw new RuntimeException("Erro de sintaxe: Fim inesperado de entrada");
        }
        System.out.println("Entrada sintaticamente correta!");
        
        // Calculando First e Follow e exibindo
        calcularFirst();
        calcularFollow();
        
        // Exibindo First e Follow
        System.out.println("\nConjunto First:");
        first.forEach((k, v) -> System.out.println("First(" + k + ") = " + v));
        
        System.out.println("\nConjunto Follow:");
        follow.forEach((k, v) -> System.out.println("Follow(" + k + ") = " + v));
    }
}

public class Main
{
	 // Função principal para testar
    public static void main(String[] args) {
        
        // 1º Teste - Sintaticamente correto
        String input = "9 + x * 23 - ( y + 5 )";
        Sintatico analisador = new Sintatico(input);
        analisador.parse();
        
        // 2º Teste - Sintaticamente incorreto
        input = "-10 / 2 )";
        analisador = new Sintatico(input);
        analisador.parse();
        
    }
}