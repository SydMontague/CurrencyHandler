package de.craftlancer.currencyhandler.handler;

import org.bukkit.entity.LivingEntity;

import de.craftlancer.currencyhandler.Handler;

public class HealthHandler implements Handler
{
    private String name = "Health";
    
    public HealthHandler(String name)
    {
        this.name = name;
    }
    
    @Override
    public boolean hasCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return false;
        
        if (!checkInputObject(amount))
            return false;
        
        return ((LivingEntity) holder).getHealth() >= ((Number) amount).doubleValue();
    }
    
    @Override
    public void withdrawCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        LivingEntity entity = (LivingEntity) holder;
        entity.setHealth(entity.getHealth() - ((Number) amount).doubleValue());
    }
    
    @Override
    public void giveCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        LivingEntity entity = (LivingEntity) holder;
        entity.setHealth(entity.getHealth() + ((Number) amount).doubleValue());
    }
    
    @Override
    public void setCurrency(Object holder, Object amount)
    {
        if (!checkInputHolder(holder))
            return;
        
        if (!checkInputObject(amount))
            return;
        
        ((LivingEntity) holder).setHealth(((Number) amount).doubleValue());
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
