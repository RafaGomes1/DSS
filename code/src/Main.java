import UI.TextUI;

import java.util.Arrays;
import java.util.List;
public class Main {
    public static void main(String[] args){
        try{
            new TextUI().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível iniciar o sistema!: " + e.getMessage());
        }
        System.out.println("\nO sistema será encerrado agora.");
        System.out.println("\033[1;36m" + "Sessão Terminada!" + "\033[0m");
    }
}