public class Stock {
    private String company;
    private double currentPrice;

    public Stock(String company, double currentPrice){
        this.company = company;
        this.currentPrice = currentPrice;
    }

    public String getCompany(){
        return company;
    }

    public double getCurrentPrice(){
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice){
        this.currentPrice = currentPrice;
    }

}

