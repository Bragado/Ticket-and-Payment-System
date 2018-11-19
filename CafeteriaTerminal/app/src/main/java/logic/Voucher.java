package logic;

import java.io.Serializable;

public class Voucher implements Serializable {
    private static int next_id = 1;
    private final int id;
    private float discount;
    private int client_id;
    private VoucherStatus voucherStatus;

    public Voucher(int id, float discount) {
        this.id = id;
        next_id++;
        setDiscount(discount);
        setVoucherStatus(VoucherStatus.AVAILABLE);
    }

    public int getId() {
        return id;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public VoucherStatus getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(VoucherStatus voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public boolean assignClient(int client_id){
        if (voucherStatus == VoucherStatus.AVAILABLE) {
            setClient_id(client_id);
            setVoucherStatus(VoucherStatus.ASSIGNED);
            return true;
        }

        return false;
    }
}
