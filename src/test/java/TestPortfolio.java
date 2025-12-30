import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestPortfolio {

    @Test
    public void testTotalValue(){
    
        Stock stock1 = new Stock("Apple", 150.0);
        Stock stock2 = new Stock("Google", 200.0);
        Stock stock3 = new Stock("Microsoft", 100.0);

        HashMap<Stock, Integer> shares = new HashMap<>();
        shares.put(stock1, 10);
        shares.put(stock2, 20);
        shares.put(stock3, 30);
        
        Portfolio portfolio = new Portfolio(shares);
        System.out.println("Total value: " + portfolio.getTotalValue());
        assertEquals(8500.0, portfolio.getTotalValue());
    }

    
    @Test
    public void testRebalancingPortfolio(){
    
        Stock stock1 = new Stock("Apple", 150.0);
        Stock stock2 = new Stock("Google", 200.0);
        Stock stock3 = new Stock("Microsoft", 100.0);

        HashMap<Stock, Integer> shares = new HashMap<>();
        shares.put(stock1, 10); //10 acciones de Apple =~ 17%
        shares.put(stock2, 20); //20 acciones de Google =~ 33%
        shares.put(stock3, 30); //30 acciones de Microsoft = 50%
        //total de acciones en el portafolio = 10 + 20 + 30 = 60 acciones
        
        Portfolio portfolio = new Portfolio(shares);

        HashMap<String, Double> allocation = new HashMap<>();

        // Asumiendo que mantenemos el número de acciones iniciales (60)
        allocation.put("Apple", 30.0); //30% de la cartera en Apple = 18 acciones = 8 acciones a comprar
        allocation.put("Google", 40.0); //40% de la cartera en Google = 24 acciones = 4 acciones a comprar
        allocation.put("Microsoft", 0.0); //0% de la cartera en Microsoft = 0 acciones = 30 acciones a vender
        allocation.put("Dell", 20.0); //20% de la cartera en Dell = 12 acciones = 12 acciones a comprar
        allocation.put("Amazon", 10.0); //10% de la cartera en Amazon = 6 acciones = 6 acciones a comprar

        Map<String, Integer> rebalancing = portfolio.calculateRebalance(allocation);

        assertEquals(8, rebalancing.get("Apple"));
        assertEquals(4, rebalancing.get("Google"));
        assertEquals(-30, rebalancing.get("Microsoft"));
        assertEquals(12, rebalancing.get("Dell"));
        assertEquals(6, rebalancing.get("Amazon"));

        for (String company : rebalancing.keySet()){
            System.out.println(company + ": " + rebalancing.get(company));
        }
    }
}
