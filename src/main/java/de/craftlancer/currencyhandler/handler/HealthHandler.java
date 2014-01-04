package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class HealthHandler implements Handler<Number>
{
    String name = "Health";
    
    public HealthHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Player p, Number amount)
    {
        return p.getHealth() >= amount.doubleValue();
    }
    
    @Override
    public void withdrawCurrency(Player p, Number amount)
    {
        p.setHealth(p.getHealth() - amount.doubleValue());
    }
    
    @Override
    public void giveCurrency(Player p, Number amount)
    {
        p.setHealth(p.getHealth() + amount.doubleValue());
    }

    @Override
    public void setCurrency(Player p, Number amount)
    {
        p.setHealth(amount.doubleValue());
    }
    
    @Override
    public String getFormatedString(Number value)
    {
        return value.toString() + " " + getCurrencyName();
    }
    
    @Override
    public String getCurrencyName()
    {
        return name;
    }
    
    @Override
    public boolean checkInputObject(Object obj)
    {
        return (obj instanceof Number);
    }
}
