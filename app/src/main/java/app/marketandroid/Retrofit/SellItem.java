package app.marketandroid.Retrofit;

public class SellItem {
    private int id;
    private int count;
    private PriceNLimitItem priceNlimit;
    private String created_at;
    private LoginItem user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public LoginItem getUser() {
        return user;
    }

    public void setUser(LoginItem user) {
        this.user = user;
    }

    public PriceNLimitItem getPriceNlimit() {
        return priceNlimit;
    }

    public void setPriceNlimit(PriceNLimitItem priceNlimit) {
        this.priceNlimit = priceNlimit;
    }

    public static class LoginItem {
        private int id;
        private String name;
        private String phone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class PriceNLimitItem {
        private int price;
        private int limit;
        private int id;
        private DemandItem demand;

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

        public DemandItem getDemand() {
            return demand;
        }

        public void setDemand(DemandItem demand) {
            this.demand = demand;
        }

        public static class DemandItem {
            private int id;
            private String weight;
            private ProductItem product;

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

            public ProductItem getProduct() {
                return product;
            }

            public void setProduct(ProductItem product) {
                this.product = product;
            }

            public static class ProductItem {
                private String name; //출하 물품 등록 페이지 품종
                private int id;

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
            }
        }
    }
}
