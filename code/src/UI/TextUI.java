package UI;

// check agendados empty
// check notifacoes empty
// check proxservico existe
// getagendados cliente
// get postos

import oficina.ESIDEALFacade;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class TextUI {

    private ESIDEALFacade model;
    private Scanner scan;

    public TextUI() {
        this.model = null;
        this.scan = new Scanner(System.in);
    }

    public void run() throws IOException {
        String oficina = oficina();
        this.model = new ESIDEALFacade(oficina);
        Menu.Logo();
        UILogin();
    }
    private String oficina(){
        String oficina;
        System.out.println("\nIndique o nome da sua oficina para iniciar o programa: ");
        oficina = scan.nextLine();
        return oficina;
    }
    private void UILogin() throws IOException {
        Menu menu = new Menu(new String[]{
                "Login"
        });
        menu.setHandlers(1, () -> {
            String id = null;
            String password = null;
            int sucesso = -1;
            int tentativas = 0;
            if (tentativas == 3)
                System.out.println("\n\n O Sistema será encerrado agora.");

            while (sucesso == -1 && tentativas < 3) {
                try {
                    System.out.print("\nInserir id: ");
                    id = scan.nextLine();
                    System.out.print("Inserir password: ");
                    password = scan.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println(e);
                }
                sucesso = this.model.login(id, password);

                if (sucesso == -1 && ++tentativas < 3) {
                    System.out.println("\u001B[31mDados Inválidos, tente novamente.\n" + "Tentativas restantes: " + (3 - tentativas) + "\u001b[0m");
                }
            }
            switch (sucesso) {
                case (1):
                    System.out.println("\n\033[1;35mBem vindo ao Sistema da ESIdeal!\033[0m\n");
                    this.gestaoDeCliente(id);
                    break;
                case (2):
                    System.out.println("\n\033[1;35mBem vindo ao Sistema da ESIdeal!\033[0m\n");
                    List<String> postos = this.model.getPostos();
                    printLista(postos);
                    boolean i = true;
                    while (i) {
                        System.out.print("\nInserir o posto onde vai trabalhar : ");
                        String idPosto = scan.nextLine();
                        if (this.model.checkCompetenciaMecPosto(id,Integer.parseInt(idPosto))) {
                            i = false;
                            this.gestaoDeMecanico(id, String.valueOf(idPosto));
                        }
                    }
                    break;
                case (-1):
                    this.ExitScreen(false);
                    break;
            }
        });
        menu.run();
    }

    private void gestaoDeCliente(String Id) throws IOException {
        Menu menu = new Menu(new String[]{
                "Agendar servico",
                "Ver as minhas notificacoes"
        });
        menu.setPreCondition(2,()-> !this.model.checkNotificacoes(Id));

        menu.setHandlers(1, () -> {

            String matricula = null;
            String idServ = null;
            boolean i=true;

            System.out.print("\nInserir matricula do seu veiculo: ");
            matricula = scan.nextLine();

            List <String> servicos = this.model.listaServicos(matricula);
            System.out.print("\nTipos de servicos disponiveis: ");
            printLista(servicos);

            System.out.print("\nInserir o id do tipo de servico que pretende ser realizado: ");
            idServ = scan.nextLine();

            LocalTime hora = this.model.agendarServiço(Id,matricula,Integer.parseInt(idServ));

            
            if (hora == null) System.out.print("\n Nao foi possivel agendar o servico\n");
            else System.out.print("\nServico agendado com sucesso para a hora " + hora.toString() + "\n");
        });
        menu.setHandlers(2, () -> {
            List <String> notificacoes = this.model.getNotificacoesCliente(Id);
            System.out.print("\nLista das suas notificacoes: ");
            printLista(notificacoes);
        });
        menu.run();

    }

    private void gestaoDeMecanico(String Id,String IdPosto) throws IOException {

        Menu menu = new Menu(new String[]{
                "Iniciar turno",
                "Agendar servico",
                "Ver proximo servico agendado",
                "Finalizar turno"
        });
        menu.setPreCondition(1,()-> this.model.checkInicioTurnoMec(Id));
        menu.setPreCondition(2,()-> this.model.checkFimTurnoMec(Id));
        menu.setPreCondition(3,()-> (!this.model.checkProxServico(Integer.parseInt(IdPosto)) && this.model.checkFimTurnoMec(Id)));
        menu.setPreCondition(4,()-> this.model.checkFimTurnoMec(Id));

        menu.setHandlers(1,() -> {
            this.model.iniciaTurno(Id, Integer.parseInt(IdPosto));
            System.out.print("\n Turno iniciado com sucesso \n");
        });
        menu.setHandlers(2, () -> {
            String idCliente = null;
            String matricula = null;
            String idServ = null;
            String dia = null;
            boolean i=true;

            System.out.print("\nInserir o Id do cliente : ");
            idCliente = scan.nextLine();

            System.out.print("\nInserir matricula do seu veiculo: ");
            matricula = scan.nextLine();

            List <String> servicos = this.model.listaServicos(matricula);
            System.out.print("\nTipos de servicos disponiveis: ");
            printLista(servicos);

            System.out.print("\nInserir o id do tipo de servico que pretende ser realizado: ");
            idServ = scan.nextLine();

            LocalTime hora = this.model.agendarServiço(Id,matricula,Integer.parseInt(idServ));

            if (hora == null) System.out.print("\n Nao foi possivel agendar o servico\n");
            else System.out.print("\nServico agendado com sucesso para a hora " + hora.toString() + "\n");

        });

        menu.setHandlers(3, ()-> {
            String agendamento = this.model.getProximoServico(Integer.parseInt(IdPosto));
            System.out.print(agendamento + "\n");
            System.out.print("\n 1 - Realizar o agendamento");
            System.out.print("\n 2 - Cancelar o agendamento");
            System.out.print("\n Opcao : ");

            int resposta = Integer.parseInt(scan.nextLine());

            switch(resposta){
                case(1):
                    this.model.completaServico(Integer.parseInt(IdPosto));
                    System.out.print("\n Agendamento realizado com sucesso \n");
                    break;

                case(2):
                    System.out.print("\nInsira a razao para o cancelamento : ");
                    String razao = scan.nextLine();
                    this.model.rejeitaServico(Integer.parseInt(IdPosto),razao);
                    System.out.print("\nCancelamento com sucesso \n");
                    break;
            }
        });

        menu.setHandlers(4, () -> {
            this.model.finalizaTurno(Id);
            System.out.print("\nTurno finalizado com sucesso\n");
        });
        menu.run();
    }


    private void printLista (List <String> lista){
        for (String string : lista) {
            System.out.println("\n" + string);
        }
    }
    private void ExitScreen(boolean login){
        Menu.Logo();
        if (login) System.out.println("\u001B[31m\nNúmero máximo de tentativas excedido.\u001b[0m");
    }
}