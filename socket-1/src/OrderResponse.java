import java.io.Serializable;

public class OrderResponse implements Serializable {
    private String confirmation;
    private String product;
    private int quantity;

    public OrderResponse() {}

    public OrderResponse(String confirmation, String product, int quantity) {
        this.confirmation = confirmation;
        this.product = product;
        this.quantity = quantity;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
