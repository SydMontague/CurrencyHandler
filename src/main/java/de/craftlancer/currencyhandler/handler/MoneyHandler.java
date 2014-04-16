package de.craftlancer.currencyhandler.handler;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.HumanEntity;

import de.craftlancer.currencyhandler.Handler;

public class MoneyHandler implements Handler
{
    private Economy economy;
    private String currency;
    
    public MoneyHandler(Economy economy, String currency)
    {
        this.economy = economy;
        this.currency = currency;
    }
    
    @Override
    public boolean hasCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return false;
        
        if (!checkInputObject(amount))
            return false;
        
        return economy.has(getAccountName(holder), ((Number) amount).doubleValue());
    }
    
    @Override
    public void withdrawCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        economy.withdrawPlayer(getAccountName(holder), ((Number) amount).doubleValue());
    }
    
    @Override
    public void giveCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        economy.depositPlayer(getAccountName(holder), ((Number) amount).doubleValue());
    }
    
    @Override
    public void setCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        String name = getAccountName(holder);
        
        double diff = ((Number) amount).doubleValue() - economy.getBalance(name);
        if (diff < 0)
            economy.withdrawPlayer(name, -diff);
        else
            economy.depositPlayer(name, diff);
    }
    
    @Override
    public String getFormatedString(Object value)
    {
        if (!checkInputObject(value))
            return "INVALID INPUT OBJECT";
        
        return value.toString() + " " + getCurrencyName();
    }
    
    @Override
    public String getCurrencyName()
    {
        return currency;
    }
    
    @Override
    public boolean checkInputObject(Object obj)
    {
        
        return obj instanceof Number;
    }
    
    private static String getAccountName(Object obj)
    {
        String name = obj.toString();
        
        if (obj instanceof HumanEntity)
            name = ((HumanEntity) obj).getName();
        
        return name;
    }
    
    @Override
    public boolean checkInputHolder(Object obj)
    {
        return economy.hasAccount(getAccountName(obj));
    }
}
