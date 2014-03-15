package de.craftlancer.currencyhandler;

public interface Handler<K, T>
{
    /**
     * Check if a player owns enough of the handled currency
     * Check checkInputObject() before!
     * 
     * @param holder
     *            the checked player
     * @param amount
     *            the amount of the handled currency
     * @return true if the amount of the handled currency is greater or equal
     *         the amount
     */
    public abstract boolean hasCurrency(K holder, T amount);
    
    /**
     * Withdraw an amount of the handled currency from a player
     * Check checkInputObject() before!
     * 
     * @param holder
     *            the checked player
     * @param amount
     *            the amount of the handled currency
     */
    public abstract void withdrawCurrency(K holder, T amount);
    
    /**
     * Give an amount of the handled currency to a player
     * Check checkInputObject() before!
     * 
     * @param holder
     *            the checked player
     * @param amount
     *            the amount of the handled currency
     */
    public abstract void giveCurrency(K holder, T amount);
    
    /**
     * Set the amount of the handled currency to a player
     * Check checkInputObject() before!
     * 
     * @param holder
     *            the checked player
     * @param amount
     *            the amount of the handled currency
     * @throws UnsupportedOperationException
     *             when a handler does not support this
     */
    public abstract void setCurrency(K holder, T amount) throws UnsupportedOperationException;
    
    /**
     * Get a string, which describes the given object
     * Check checkInputObject() before!
     * 
     * @param value
     *            the object that needs to be formated
     * @return the String, after the handler's format
     */
    public abstract String getFormatedString(T value);
    
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
     * @param obj
     *            the object that needs to be checked
     * @return true if the object can be used by this handler, false if not
     */
    public abstract boolean checkInputObject(Object obj);
    
    /**
     * Check if the given Object is usable as holder by this handler.
     * It's advised to check this method before interacting with the handler.
     * 
     * @param obj
     *            the object that needs to be checked
     * @return true if the object can be used by this handler, false if not
     */
    public abstract boolean checkInputHolder(Object obj);
}
