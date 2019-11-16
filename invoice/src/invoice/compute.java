/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

/**
 *
 * @author Randel Reyes
 * @description: interface that will be implemented on model controller
 */
interface compute {
    
    //computes the undiscounted amount of the item
    public double undiscountedAmount(double price1, int qty1);
    
    //computes the discounted amount of the item
    public double computePrice(double total, int discount);
    
    //computes the total amount of all the items
    public double computeTotalAmount(double mainTotalAmount);
    
    //counts the number of items bought
    public int count_item(int item);
        
}
