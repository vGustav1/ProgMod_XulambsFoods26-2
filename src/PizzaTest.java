import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PizzaTest {
    Pizza pizza;

    @BeforeEach
    public void init() {

        // Arrange padrão
        pizza = new Pizza(4);

    }

    @Test
    public void adicionaIngredientesCorretamente() {

        // Act
        int quantos = pizza.adicionarIngredientes(4);

        // Assert
        assertEquals(8, quantos);
    }

    @Test
    public void naoAdicionaIngredientesNegativos() {

        // Act
        int quantos = pizza.adicionarIngredientes(-1);

        // Assert
        assertEquals(4, quantos);
    }

    @Test
    public void naoAdicionaIngredientesEmExcesso() {

        int quantos = pizza.adicionarIngredientes(7);

        assertEquals(4, quantos);

    }

    @Test
    public void calculaValorDaPizzaCorretamente() {

        // Act
        double valor = pizza.valorFinal();
        // Assert
        assertEquals(49d, valor, 0.01);

    }

    @Test
    public void geraCupomComDetalhamento() {

        // Act
        String cupom = pizza.gerarCupom();

        // Assert
        assertTrue(
                cupom.contains("4 ingredientes") &&
                        cupom.contains("29,00") &&
                        cupom.contains("20,00") &&
                        cupom.contains("49,00"));

    };

}
