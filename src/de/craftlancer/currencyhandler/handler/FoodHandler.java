package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class FoodHandler implements Handler<Integer>
{
    String name = "Food";
    
    public FoodHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Player p, Integer amount)
    {
        return p.getFoodLevel() >= amount;
    }
    
    @Override
    public void withdrawCurrency(Player p, Integer amount)
    {
        p.setFoodLevel(p.getFoodLevel() - amount);
    }
    
    @Override
    public void giveCurrency(Player p, Integer amount)
    {
        p.setFoodLevel(p.getFoodLevel() + amount);     
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
    
    @Override
    public String getFormatedString(Object value)
    {
        return value.toString() + " " + getCurrencyName();
    }
}
