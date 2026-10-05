public class PedidoEntrega extends Pedido{
    public static final int MAX_PIZZAS =8;
    
    private ETaxaEntrega taxaEntrega;
    private double distanciaEntrega;

    public PedidoEntrega(double distancia){
        super();
        if(distancia < 0.1)
            distancia = 0.1;
        distanciaEntrega = distancia;
        taxaEntrega = ETaxaEntrega.definirEntrega(distancia);
    }

    @Override 
    protected boolean podeAdicionar(){
        return super.podeAdicionar() && pizzas.size() < MAX_PIZZAS;
    }

    @Override 
    public double precoAPagar(){
        return valorPizzas() + taxaEntrega.valorTaxa();
    }

    @Override 
    public String toString(){
        String linhaEntrega  =
            String.format("TAXA ENTREGA: R$ %.2f (%.1f km)", 
                            taxaEntrega.valorTaxa(), distanciaEntrega);
        String precoFinal  =
            String.format("\nVALOR DO PEDIDO: R$ %.2f ", precoAPagar());
        StringBuilder relat = new StringBuilder(cabecalho());
        relat.append("PEDIDO PARA ENTREGA\n");
        relat.append(detalhesPedido()+"\n");
        relat.append(linhaEntrega);
        relat.append(precoFinal);
        return relat.toString();
    }
}
