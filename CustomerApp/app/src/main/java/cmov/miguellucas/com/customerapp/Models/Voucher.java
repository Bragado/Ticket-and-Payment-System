package cmov.miguellucas.com.customerapp.Models;

public class Voucher {
    public String description;
    public String ID;
    public int type;

        public Voucher(String ID, int type) {
            this.ID = ID;
            this.type = type;

            description = getMessageFromType(type);
        }



        public String getMessageFromType(int type) {
            switch (type) {
                case 0:
                    return "5% disccount";

                case 1:
                    return "Free Coffe";

                case 2:
                    return "Free Soda Drink";

                case 3:
                    return "Free Popcorn";

                    case 4:
                        return "Free Sandwish";
                            }
            return "";

        }


}
