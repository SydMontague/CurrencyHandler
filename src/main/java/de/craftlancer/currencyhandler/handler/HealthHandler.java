package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.LivingEntity;

import de.craftlancer.currencyhandler.Handler;

public class HealthHandler implements Handler<LivingEntity, Number>
{
    private String name = "Health";
    
    public HealthHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(LivingEntity holder, Number amount)
    {
        return holder.getHealth() >= amount.doubleValue();
    }
    
    @Override
    public void withdrawCurrency(LivingEntity holder, Number amount)
    {
        holder.setHealth(holder.getHealth() - amount.doubleValue());
    }
    
    @Override
    public void giveCurrency(LivingEntity holder, Number amount)
    {
        holder.setHealth(holder.getHealth() + amount.doubleValue());
    }

    @Override
    public void setCurrency(LivingEntity holder, Number amount)
    {
        holder.setHealth(amount.doubleValue());
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
    
    @Override
    public boolean checkInputHolder(Object obj)
    {
        return obj instanceof LivingEntity;
    }
}
