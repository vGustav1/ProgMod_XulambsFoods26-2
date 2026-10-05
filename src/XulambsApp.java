import java.util.LinkedList;
import java.util.List;

public class XulambsApp {
    private List<Pedido> listaPedidos;

    void limparTela() {
        IO.print("\033[H\033[2J");
    }

    void pausa(){
        IO.readln("Digite <ENTER> para continuar");
        limparTela();
    }

    int lerNumero(String mensagem){
        return Integer.parseInt(IO.readln(mensagem));
    }

    void config(){
        listaPedidos = new LinkedList<>();

    }

    void cabecalho(){
        limparTela();
        IO.println("XULAMBS PIZZA - v0.4");
        IO.println("=====================");
        IO.println("Pizzas vendidas hoje: " +
                    Pizza.getPizzasVendidas());
    }

    int exibirMenu() {
        cabecalho();
        IO.println("1 - Abrir pedido");
        IO.println("2 - Alterar pedido");
        IO.println("3 - Relatório de um pedido");
        IO.println("4 - Encerrar pedido");
        IO.println("0 - Sair");
        return lerNumero("Digite sua opção: ");
    }

    Pizza comprarPizza(){
        cabecalho();
        int adicionais; 
        Pizza novaPizza = new Pizza();

        EBorda borda = escolherBorda();
        novaPizza.adicionarBorda(borda);
        
        adicionais = lerNumero("Quantos ingredientes? ");
        novaPizza.adicionarIngredientes(adicionais);

        exibirRelatorio(novaPizza);
        return novaPizza;
    }

    EBorda escolherBorda(){
        EBorda[] bordas = EBorda.values();
        int i = 1;
        IO.println("Escolha sua borda: ");
        for (EBorda eBorda : bordas) {
            IO.println(String.format("%d - Borda %s", i, eBorda.getNome()));
            i++;
        }
        int escolha = lerNumero("Digite sua opção: ");
        return bordas[escolha-1];
    }

   
    void armazenarPedido(Pedido pedido){
        if(pedido != null)
            listaPedidos.add(pedido);
    }

    PedidoEntrega criarPedidoEntrega(){
        String dist = IO.readln("Qual a distância da entrega? ");
        double distancia = Double.parseDouble(dist);
        return new PedidoEntrega(distancia);
    }
    
    Pedido escolherTipoPedido(){
        cabecalho();
        IO.println("Escolha o tipo do pedido: ");
        IO.println("1 - Local ");
        IO.println("2 - Para entrega");
        int escolha = lerNumero("Digite sua opção: ");
        return switch (escolha) {
            case 1 -> new PedidoLocal();
            case 2 -> criarPedidoEntrega();
            default -> null;
        };
    }
    void abrirPedido(){
        Pedido novoPedido = escolherTipoPedido();
        String novaPizza;
        do {
            Pizza pizza = comprarPizza();
            novoPedido.adicionarPizza(pizza);
            novaPizza = IO.readln("Mais pizza? (s/n)");
        } while (novaPizza.equals("s"));
        exibirRelatorio(novoPedido);
        armazenarPedido(novoPedido);
    }

    void alterarPedido(){
        Pedido buscado = (Pedido)localizar();
        if(buscado != null){
            Pizza pizza = comprarPizza();
            buscado.adicionarPizza(pizza);
            exibirRelatorio(buscado);
        }
    }

    void relatorioPedido(){
        Pedido buscado = (Pedido)localizar();
        if(buscado != null){
            exibirRelatorio(buscado);
        }
    }

     void encerrarPedido(){
        Pedido buscado = (Pedido)localizar();
        if(buscado != null){
            buscado.fecharPedido();
            exibirRelatorio(buscado);
        }
    }

    Object localizar(){
        cabecalho();
        IO.println("LOCALIZAÇÃO DE PEDIDOS\n");
        int codigo = lerNumero("Código do pedido: ");
        Object localizado = null;
        for (int i = 0; i < listaPedidos.size() && localizado == null; i++) {
            Object candidato = listaPedidos.get(i);
            if(candidato.hashCode() == codigo){
                localizado = candidato;
            }
        }
        return localizado;            
    }

    void exibirRelatorio(Object objeto){
        cabecalho();
        IO.println("RELATÓRIO:\n");
        String mensagem = "Objeto não encontrado";
        if(objeto != null)
            mensagem = objeto.toString();
       
        IO.println(mensagem);
    }

    //  void mostrarNota(Pizza pizza){
    //     IO.println("Pizza comprada:");
    //     IO.println(pizza.toString());
    //     IO.println("=====================");
    // }

    void main(){
        int opcao;
        config();
        do {
            opcao = exibirMenu();
            switch (opcao) {
                case 1 -> abrirPedido();
                case 2 -> alterarPedido();
                case 3 -> relatorioPedido();
                case 4 -> encerrarPedido();
                case 0 -> IO.println("Encerrando!");
                default -> IO.println("Opção inválida");
            }   
            pausa(); 
        } while (opcao != 0);
    }
}
