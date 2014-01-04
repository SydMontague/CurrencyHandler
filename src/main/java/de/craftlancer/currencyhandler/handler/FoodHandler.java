package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class FoodHandler implements Handler<Number>
{
    String name = "Food";
    
    public FoodHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Player p, Number amount)
    {
        return p.getFoodLevel() >= amount.intValue();
    }
    
    @Override
    public void withdrawCurrency(Player p, Number amount)
    {
        p.setFoodLevel(p.getFoodLevel() - amount.intValue());
    }
    
    @Override
    public void giveCurrency(Player p, Number amount)
    {
        p.setFoodLevel(p.getFoodLevel() + amount.intValue());
    }
    
    @Override
    public void setCurrency(Player p, Number amount)
    {
        p.setFoodLevel(amount.intValue());
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
