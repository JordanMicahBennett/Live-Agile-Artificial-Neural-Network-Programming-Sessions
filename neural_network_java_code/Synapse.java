//Author: God Bennett

public class Synapse
{
     //features
     private double weight;
     private double deltaWeight;
     
     //accessors
     public double getWeight ()
     {
         return weight;
     }
     public double getDeltaWeight ()
     {
         return deltaWeight;
     }
     
     //setters
     public void setWeight ( double value )
     {
         weight = value;
     }
     public void setDeltaWeight ( double value )
     {
         deltaWeight = value;
     }
}