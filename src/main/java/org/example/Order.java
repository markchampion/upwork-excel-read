package org.example;

public class Order {
    private String oid;
    private String orderType;
    private String date;
    private String paymentMethod;
    private String mealPeriod;
    private double commission;
    private double additionalChanges;
    private double subTotal;
    private double tax;
    private double total;
    private double totalEarned;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMealPeriod() {
        return mealPeriod;
    }

    public void setMealPeriod(String mealPeriod) {
        this.mealPeriod = mealPeriod;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getAdditionalChanges() {
        return additionalChanges;
    }

    public void setAdditionalChanges(double additionalChanges) {
        this.additionalChanges = additionalChanges;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalEarned() {
        return totalEarned;
    }

    public void setTotalEarned(double totalEarned) {
        this.totalEarned = totalEarned;
    }
}
