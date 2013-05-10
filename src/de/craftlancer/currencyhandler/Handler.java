package de.craftlancer.currencyhandler;

import org.bukkit.entity.Player;

public interface Handler<T>
{
    /**
     * Check if a player owns enough of the handled currency
     * Check checkInputObject() before!
     * 
     * @param p
     *            the checked player
     * @param amount
     *            the amount of the handled currency
     * @return true if the amount of the handled currency is greater or equal
     *         the amount
     */
    public abstract boolean hasCurrency(Player p, T amount);
    
    /**
     * Withdraw an amount of the handled currency from a player
     * Check checkInputObject() before!
     * 
     * @param p
     *            the checked player
     * @param amount
     *            the amount of the handled currency
     */
    public abstract void withdrawCurrency(Player p, T amount);
    
    /**
     * Give an amount of the handled currency to a player
     * Check checkInputObject() before!
     * 
     * @param p
     *            the checked player
     * @param amount
     *            the amount of the handled currency
     */
    public abstract void giveCurrency(Player p, T amount);
    
    /**
     * Get the name of the currency.
     * 
     * @return the name of the currency as String
     */
    public abstract String getCurrencyName();
    
    /**
     * Check if the given Object is usable by this handler.
     * It's advised to check this method before interacting with the handler.
     * 
     * @param obj the object that needs to be checked
     * @return true if the object can be used by this handler, false if not
     */
    public abstract boolean checkInputObject(Object obj);
    
    /**
     * Get a string, which describes the given object
     * Check checkInputObject() before!
     * 
     * @param value the object that needs to be formated
     * @return the String, after the handler's format
     */
    public abstract String getFormatedString(Object value);
    
}
