//Author: God Bennett

import java.util.ArrayList;
import java.util.Random;


public class Neuron
{
    //features
    private double eta;
    private double alpha;
    private double gradient;
    private double outcome;
    private int numberOfWeightsFromNextNeuron;
    private int neuronId;
    private ArrayList <Synapse> weights = new ArrayList <Synapse> ( );
    
    //constructor
    public Neuron ( double eta, double alpha, int numberOfWeightsFromNextNeuron, int neuronId )
    {
        this.eta = eta;
        this.alpha = alpha;
        this.numberOfWeightsFromNextNeuron = numberOfWeightsFromNextNeuron;
        this.neuronId = neuronId;
        this.gradient = 0;
        
        //define weights per neuron
        for ( int wI = 0; wI < this.numberOfWeightsFromNextNeuron; wI ++ )
        {
            weights.add ( new Synapse ( ) );
            
            weights.get ( wI ).setWeight ( new Random ( ).nextDouble ( ) );
        }
    }
    
    //getters
    public ArrayList <Synapse> getWeights ( )
    {
        return weights;
    }
    public double getOutcome ( )
    {
        return outcome;
    }
    public double getGradient ( )
    {
        return gradient;
    }
    public double getDistributedWeightSigma ( Layer nextLayer )
    {
        double sigma = 0;
        
        for ( int nLI = 0; nLI < nextLayer.size ( ) - 1; nLI ++ )
            sigma += getWeights ( ).get ( nLI ).getWeight ( ) * nextLayer.get ( nLI ).getGradient ( );
        
        return sigma;
    }
    public double getActivation ( double value )
    {
        return Math.tanh ( value );
    }
    public double getPrimeActivation ( double value )
    {
        return 1 - Math.pow ( Math.tanh ( value ), 2 );
    }
    
    
    //setters
    public void setGradient ( double value )
    {
        gradient = value;
    }
    public void setOutcome ( double value )
    {
        outcome = value;
    }
    public void setHiddenGradient ( Layer nextLayer )
    {
        double delta = getDistributedWeightSigma ( nextLayer );
        
        setGradient ( getPrimeActivation ( getOutcome ( ) ) * delta );
    }
    public void setOutcomeGradient ( int target )
    {
        double delta = target - outcome;
        
        setGradient ( getPrimeActivation ( getOutcome ( ) )  * delta );
    }
    public void doForwardPropagation ( Layer priorLayer )
    {
        double sigma = 0;
        
        //weights * outcomes
        for ( int pLI = 0; pLI < priorLayer.size ( ); pLI ++ )
            sigma += priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).getWeight ( ) * priorLayer.get ( pLI ).getOutcome ( );
            
        setOutcome ( getActivation ( sigma ) );
    }
    
    
    public void updateWeights ( Layer priorLayer )
    {
        for ( int pLI = 0; pLI < priorLayer.size ( ); pLI ++ )
        {
            double priorDeltaWeight = priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).getDeltaWeight ( );
            
            //(gradient * outcome * eta ) + ( alpha * priorDeltaWeight )
            double newDeltaWeight = ( gradient * priorLayer.get ( pLI ).getOutcome ( ) * eta ) + ( alpha * priorDeltaWeight );
            
      
            priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).setWeight ( priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).getWeight ( ) + newDeltaWeight );
            priorLayer.get ( pLI ).getWeights ( ).get ( neuronId ).setDeltaWeight ( newDeltaWeight );
        }
    }
}