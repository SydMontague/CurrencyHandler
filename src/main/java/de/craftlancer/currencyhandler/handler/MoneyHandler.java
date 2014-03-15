package de.craftlancer.currencyhandler.handler;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.HumanEntity;

import de.craftlancer.currencyhandler.Handler;

public class MoneyHandler implements Handler<Object, Number>
{
    private Economy economy;
    private String currency;
    
    public MoneyHandler(Economy economy, String currency)
    {
        this.economy = economy;
        this.currency = currency;
    }
    
    @Override
    public boolean hasCurrency(Object holder, Number amount)
    {
        return economy.has(getAccountName(holder), amount.doubleValue());
    }
    
    @Override
    public void withdrawCurrency(Object holder, Number amount)
    {
        economy.withdrawPlayer(getAccountName(holder), amount.doubleValue());
    }
    
    @Override
    public void giveCurrency(Object holder, Number amount)
    {
        economy.depositPlayer(getAccountName(holder), amount.doubleValue());
    }
    
    @Override
    public void setCurrency(Object holder, Number amount)
    {
        String name = getAccountName(holder);
        
        double diff = amount.doubleValue() - economy.getBalance(name);
        if (diff < 0)
            economy.withdrawPlayer(name, amount.doubleValue());
        else
            economy.depositPlayer(name, amount.doubleValue());
    }
    
    @Override
    public String getFormatedString(Number value)
    {
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
    
    private String getAccountName(Object obj)
    {
        String name = obj.toString();
        
        if (obj instanceof HumanEntity)
            name = ((HumanEntity) obj).getName();
        
        return name;
    }
    
    public boolean checkInputHolder(Object obj)
    {
        return economy.hasAccount(getAccountName(obj));
    }
}
