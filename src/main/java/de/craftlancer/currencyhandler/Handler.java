package de.craftlancer.currencyhandler;

public interface Handler
{
    /**
     * Check if a player owns enough of the handled currency
     * 
     * @param holder the "owner" of the currency
     * @param amount the amount of the handled currency
     * @return true if the amount of the handled currency is greater or equal
     *         the amount, false if not or when one of the input parameters does not match the requirements of the Handler
     */
    public abstract boolean hasCurrency(Object holder, Object amount);
    
    /**
     * Withdraw an amount of the handled currency from a player
     * If one of the input parameter does not match the requirements, nothing will happen.
     * 
     * @param holder the "owner" of the currency
     * @param amount the amount of the handled currency
     */
    public abstract void withdrawCurrency(Object holder, Object amount);
    
    /**
     * Give an amount of the handled currency to a player
     * If one of the input parameter does not match the requirements, nothing will happen.
     * 
     * @param holder the "owner" of the currency
     * @param amount the amount of the handled currency
     */
    public abstract void giveCurrency(Object holder, Object amount);
    
    /**
     * Set the amount of the handled currency to a player
     * If one of the input parameter does not match the requirements, nothing will happen.
     * 
     * @param holder the "owner" of the currency
     * @param amount the amount of the handled currency
     * @throws UnsupportedOperationException when a handler does not support this
     */
    public abstract void setCurrency(Object holder, Object amount) throws UnsupportedOperationException;
    
    /**
     * Get a string, which describes the given object
     * If one of the input parameter does not match the requirements, nothing will happen.
     * 
     * @param value the object that needs to be formated
     * @return the String, after the handler's format
     */
    public abstract String getFormatedString(Object value);
    
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
     * @return true if the object can be used as amount by this handler, false if not
     */
    public abstract boolean checkInputObject(Object obj);
    
    /**
     * Check if the given Object is usable as holder by this handler.
     * It's advised to check this method before interacting with the handler.
     * 
     * @param obj the object that needs to be checked
     * @return true if the object can be used as holder by this handler, false if not
     */
    public abstract boolean checkInputHolder(Object obj);
    
    /**
     * Convert the given Object into a usable format as amount.
     * The return value CAN be null.
     * 
     * @param obj
     * @return an Object, usable by other methods of the Handler, null if the input is invalid.
     */
    public abstract Object convertInputObject(Object obj);
    
    /**
     * Convert the given Object into a usable format as holder.
     * The return value CAN be null.
     * 
     * @param obj
     * @return an Object, usable by other methods of the Handler, null if the input is invalid.
     */
    public abstract Object convertInputHolder(Object obj);
}
