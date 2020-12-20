//Author: God Bennett

public class NeuralNetwork
{
    //features
    double eta = 0.2;
    double alpha = 0.5;
    Architecture architecture = new Architecture ( "2,2,1" );
    Layers layers = new Layers ( );
    
    public NeuralNetwork ( )
    {
        for ( int lsi = 0; lsi < architecture.size ( ); lsi ++ )
        {
            layers.add ( new Layer ( ) );
            
            for (  int lI = 0; lI <= architecture.get ( lsi ); lI ++ )
            {
                int numberOfWeightsFromNextNeuron = lsi + 1 < architecture.size ( ) ? architecture.get ( lsi + 1 ) : 0;
                
                layers.get ( lsi ).add ( new Neuron ( eta, alpha,  numberOfWeightsFromNextNeuron, lI ) );
                
                layers.get ( lsi ).get ( layers.get ( lsi ).size ( ) - 1 ).setOutcome ( 1.0 );
            }
        }
    }
    
    //do forward propagation
    public void doForwardPropagation ( int [ ] input )
    {
        for ( int iI = 0; iI < input.length; iI ++ )
            layers.get ( 0 ).get ( iI ).setOutcome ( input [ iI ] );
            
        //do forward prop wrt prior layers
        for ( int lsi = 1; lsi < architecture.size ( ); lsi ++ )
        {
            Layer priorLayer = layers.get ( lsi - 1 );
            
            for ( int lI = 0; lI < architecture.get ( lsi ); lI ++ )
                layers.get ( lsi ).get ( lI ).doForwardPropagation ( priorLayer );
        }
        
    }
    
    //do backward propagation
    public void doBackwardPropagation ( int target )
    {
        Neuron outcomeNeuron = layers.get ( layers.size ( ) - 1 ).get ( 0 );
        
        outcomeNeuron.setOutcomeGradient ( target );
        
        //set hidden gradient
        for ( int lsi = architecture.size ( ) - 2; lsi > 0; lsi -- )
        {
            Layer currentLayer = layers.get ( lsi );
            Layer nextLayer = layers.get ( lsi + 1 );
            
            for ( int lI = 0; lI < currentLayer.size ( ); lI ++ )
                currentLayer.get ( lI ).setHiddenGradient ( nextLayer );
        }
        
        //do weight update
        for ( int lsi = architecture.size ( ) - 1; lsi > 0; lsi -- )
        {
            Layer currentLayer = layers.get ( lsi );
            Layer priorLayer = layers.get ( lsi - 1 );
            
            for ( int lI = 0; lI <currentLayer.size ( ) - 1; lI ++ )
                currentLayer.get ( lI ).updateWeights ( priorLayer );
        }
    }
    
    //get outcome
    public double getOutcome ( )
    {
        return layers.get ( layers.size ( ) - 1 ).get ( 0 ).getOutcome ( );
    }
}
