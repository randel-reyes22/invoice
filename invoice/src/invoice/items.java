/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

/**
 *
 * @author Randel Reyes
 * description: holds the item name, item qty
 *              and, item price
 */
public class items {
    
    private String item_name;
    private int qty = 1;
    private double price;
    private double total_amount;
    
    public items(){};
    
    public items(String item_name, int qty, double price, double total) {
        super();
        this.item_name = item_name;
        this.qty = qty; 
        this.price = price;
        this.total_amount = total;
    }
    
     public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

   
    
}
