import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private Map<Stock, Integer> shares;

    public Portfolio(Map<Stock, Integer> shares){
        this.shares = shares;
    }

    public double getTotalValue(){
        double total = 0;
        for (Stock stock : shares.keySet()){
            total += stock.getCurrentPrice() * shares.get(stock);
        }
        return total;
    }

    public double getTotalQuantity(){
        double total = 0;
        for (Integer quantity : shares.values()){
            total += quantity;
        }
        return total;
    }

    //Calcula el rebalanceo del portafolio, manteniendo el número de acciones iniciales
    public Map<String, Integer> calculateRebalance(Map<String, Double> allocation){

        //Se verifica si la asignación es válida
        if( !checkIfValidAllocation(allocation)){
            throw new IllegalArgumentException("Target portfolio is not valid, the sum of the allocation must be 100%");
        }
        double totalQuantity = getTotalQuantity();

        Map<String, Integer> rebalancing = new HashMap<>();
        
        // Se inicializa la cantidad de acciones a comprar/vender de cada compañia en 0
        for (String company : allocation.keySet()){
            rebalancing.put(company, 0);
        }
        for (Stock stock : shares.keySet()){
            rebalancing.put(stock.getCompany(), 0); 
        }   

        // Se calcula la cantidad de acciones de cada compañia en el portafolio actual y se compara con la cantidad objetivo
        for (String company : rebalancing.keySet()){
            double sharePercentage;
            int shareQuantity;

            //Revisamos si la compañia existe en el portafolio
            Stock stock = findStockByCompany(company);
            if(stock != null){
                shareQuantity = shares.get(stock);
                sharePercentage = (shares.get(stock) * 100 ) / totalQuantity;
            } else{
                shareQuantity = 0;
                sharePercentage = 0;
            }

            //Se obtiene la cantidad objetivo de la compañia
            double targetPercentage = allocation.get(company) != null ? allocation.get(company) : 0;

            if(sharePercentage != targetPercentage){
                
                // Se debe redondear el resultado ya que las acciones no se pueden dividir en fracciones
                int targetQuantity = (int)Math.round((targetPercentage * totalQuantity) / 100);

                // Se agrega la diferencia entre la cantidad objetivo y la cantidad actual a la cantidad de acciones de la compañia
                rebalancing.put(company, targetQuantity - shareQuantity); 
            }

        }
        //Se retorna el mapa de rebalanceo, siendo la llave el nombre de la compañia y el valor la cantidad de acciones a 
        //comprar (positivo) o vender (negativo). Si el valor es 0, no se debe realizar ninguna acción
        return rebalancing;
    }

    /* 
    //Este metodo se encarga de realizar el rebalanceo del portafolio, es decir, comprar/vender las acciones necesarias para que el 
    //portafolio cumpla con la asignacion objetivo
    public void doRebalance(Map<String, Integer> rebalancing){
        for (String company : rebalancing.keySet()){
            if(rebalancing.get(company) != 0){
                Stock stock = findStockByCompany(company);
                if(stock != null){
                    shares.put(stock, shares.get(stock) + rebalancing.get(company));
                } else{
                    //Si la compañia no existe en el portafolio, se crea una nueva accion con el nombre de la compañia y el precio 
                    //actual del mercado (se asume que existe un método para obtener el precio actual de la accion getStockCurrentPrice) 
                    shares.put(new Stock(company, getStockCurrentPrice(company)), rebalancing.get(company));
                }
            }
        }
    */

    // Revisa si la asignacion es valida, es decir, la suma de las asignaciones debe ser 100%
    private boolean checkIfValidAllocation(Map<String, Double> allocation) {
        double total = 0;
        for (Double share : allocation.values()){
            total += share;
        }
        return total == 100;
    }

    // Busca una accion en el portafolio por el nombre de la compañia
    public Stock findStockByCompany(String company) {
        for (Stock stock : shares.keySet()) {
            if (stock.getCompany().equals(company)) {
                return stock;
            }
        }
        return null;
    }
}

