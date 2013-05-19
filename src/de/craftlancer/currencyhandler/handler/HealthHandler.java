package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class HealthHandler implements Handler<Integer>
{
    String name = "Health";
    
    public HealthHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Player p, Integer amount)
    {
        return p.getHealth() >= amount;
    }
    
    @Override
    public void withdrawCurrency(Player p, Integer amount)
    {
        p.setHealth(p.getHealth() - amount);
    }
    
    @Override
    public void giveCurrency(Player p, Integer amount)
    {
        p.setHealth(p.getHealth() + amount);
    }

    @Override
    public void setCurrency(Player p, Integer amount)
    {
        p.setHealth(amount);
    }
    
    @Override
    public String getFormatedString(Integer value)
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
        return (obj instanceof Integer);
    }
}
