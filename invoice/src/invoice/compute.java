/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoice;

/**
 *
 * @author Randel Reyes
 */
interface compute {
        
        public double undiscountedAmount(double price1, int qty1);
        public double computePrice(double total, int discount);
        public double computeTotalAmount(double mainTotalAmount);
        public int count_item(int item);
}
