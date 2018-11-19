package logic;

import java.io.Serializable;

public class Voucher implements Serializable {

    public static final int FREE_COFFEE = 1;
    public static final int FREE_POPCORN = 2;
    public static final int DISCOUNT = 3;

    private String id;
    private int client_id;
    private int type;
    private VoucherStatus voucherStatus;

    public Voucher(String id, int type) {
        this.id = id;
        this.type = type;
        voucherStatus = VoucherStatus.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public void checkVoucherStatus(int status){
        if (type == status){
            voucherStatus = VoucherStatus.USED;
        }
    }

}
