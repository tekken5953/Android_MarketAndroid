package app.marketandroid.Retrofit;

public class ProductItem {
    private String name; //출하 물품 등록 페이지 품종
    private int id;
    private DemandItem[] demands;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DemandItem[] getDemands() {
        return demands;
    }

    public void setDemands(DemandItem[] demands) {
        this.demands = demands;
    }

    public class DemandItem{
        private int id;
        private String weight;
        private int product;
        private PriceNLimitItem priceNlimits;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public int getProduct() {
            return product;
        }

        public void setProduct(int product) {
            this.product = product;
        }

        public PriceNLimitItem getPriceNlimits() {
            return priceNlimits;
        }

        public void setPriceNlimits(PriceNLimitItem priceNlimits) {
            this.priceNlimits = priceNlimits;
        }

        public class PriceNLimitItem{
            private int demand;
            private int price;
            private int limit;
            private int id;

            public int getDemand() {
                return demand;
            }

            public void setDemand(int demand) {
                this.demand = demand;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }

}