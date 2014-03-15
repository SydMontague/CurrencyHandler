package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.Player;

import de.craftlancer.currencyhandler.Handler;

public class FoodHandler implements Handler<Player, Number>
{
    private String name = "Food";
    
    public FoodHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Player holder, Number amount)
    {
        return holder.getFoodLevel() >= amount.intValue();
    }
    
    @Override
    public void withdrawCurrency(Player holder, Number amount)
    {
        holder.setFoodLevel(holder.getFoodLevel() - amount.intValue());
    }
    
    @Override
    public void giveCurrency(Player holder, Number amount)
    {
        holder.setFoodLevel(holder.getFoodLevel() + amount.intValue());
    }
    
    @Override
    public void setCurrency(Player holder, Number amount)
    {
        holder.setFoodLevel(amount.intValue());
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
        return obj instanceof Number;
    }
    
    public boolean checkInputHolder(Object obj)
    {
        return obj instanceof Player;
    }
}
