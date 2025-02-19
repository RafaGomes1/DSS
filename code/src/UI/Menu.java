package UI;

import java.io.IOException;
import java.util.*;


public class Menu {

    //Interface funcional para Handler.

    public interface Handler {
        void execute() throws IOException;
    }

    public interface PreCondition {
        boolean validate();
    }

    //Variávies de Instância
    private Scanner scan;
    private List<String> opcoes;            //Lista de opções
    private List<PreCondition> disponivel;  //Lista de pré-condições
    private List<Handler> handlers;         //Lista de handlers

    //Construtor

    /*
     Construtor vazio para objetos da classe Menu.
     Cria um menu vazio, ao qual se podem adicionar opções
    */
    public Menu() throws IOException{
        this.scan = new Scanner(System.in);
        this.opcoes = new ArrayList<>();
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
    }

        /*
         Constructor para objetos da classe Menu
         Cria um menu de opções sem event handlers.
        */

    public Menu(List<String> opcoes) throws IOException{
        this.scan = new Scanner(System.in);
        this.opcoes = new ArrayList<>(opcoes);
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.opcoes.forEach(s -> {
            this.disponivel.add(() -> true);
            this.handlers.add(() -> System.out.println("\nErro: Funcionalidade ainda não implementada!"));
        });
    }

        /*
         Construtor para objetos da classe Menu(com titulo e com array de opções).
         Cria um menu de opções sem event handlers.
        */

    public Menu(String[] opcoes) throws IOException{
        this(Arrays.asList(opcoes));
    }

    //Métodos de instância

    //Adicionar opções a um Menu.

    public void option(String name,PreCondition p, Handler h){
        this.opcoes.add(name);
        this.disponivel.add(p);
        this.handlers.add(h);
    }

    //Correr multiplas vezes o menu (terminar quando escolhe 0

    public void run() throws IOException {
        int op;
        do {
            show();
            System.out.println("\033[1;33m" + "\n0 - Logout || Back" + "\033[0m");
            op = readOption();
            if (op > 0 && !this.disponivel.get(op - 1).validate()) {
                System.out.println("\u001B[31mOpção Indisponível! Try again\n\n\u001b[0m");
            } else if (op > 0) {
                this.handlers.get(op - 1).execute();
            }
        } while (op != 0);
    }

    //Método para registar uma pré-condição numa opção do menu

    public void setPreCondition(int i,PreCondition b){
        this.disponivel.set(i-1,b);
    }

    // Método para registar uma handler numa opção do menu.

    public void setHandlers(int i,Handler h){
        this.handlers.set(i-1,h);
    }

    //Métodos Auxiliares

    //Apresentar Menu

    private void show(){
        System.out.println("\033[1;36m" + "**************** ESIdeal ****************" + "\033[0m");
        for (int i = 0; i < this.opcoes.size(); i++) {
            System.out.print("\033[1;33m" + (i + 1) + "\033[0m");
            System.out.print("\033[1;33m" + " - " + "\033[0m");
            System.out.println(this.disponivel.get(i).validate() ? this.opcoes.get(i) : "\u001B[31mOpção Indisponivel\u001b[0m");
        }
        System.out.print("\033[1;36m" + "******************************************" + "\033[0m");
    }

    private int readOption() throws IOException {
        int op;

        System.out.print("\nOpção: ");
        try {
            String line = scan.nextLine();
            op = Integer.parseInt(line);
            System.out.println("\n");
        } catch (NumberFormatException e) {
            op = -1;
        }
        if (op < 0 || op > this.opcoes.size()) {
            System.out.println("\u001B[31mOpção Inválida!!\n\u001B[0m");
            op = -1;
        }
        return op;
    }

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void Logo(){
        System.out.print("\n\033[H\033[2J");
        System.out.flush();
        System.out.println(
                ANSI_RED + "                      _____       ____     " + ANSI_GREEN + "  ___    _            _ \n" +
                        ANSI_RED + "                     | ____|     / ___|     " + ANSI_GREEN + " |_ _|__| | ___  __ _| |\n" +
                        ANSI_RED + "                     |  _|       \\___ \\    " + ANSI_GREEN + "   | |/ _` |/ _ \\/ _` | |\n" +
                        ANSI_RED + "                     | |___   _   ___) |  _  " + ANSI_GREEN + " | | (_| |  __/ (_| | |\n" +
                        ANSI_RED + "                     |_____| (_) |____/  (_)" + ANSI_GREEN + " |___\\__,_|\\___|\\__,_|_|\n" +
                        ANSI_RED + "                                                                    \n"
                        + ANSI_RESET);
    };
}