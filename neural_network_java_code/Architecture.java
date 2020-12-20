//Author: God Bennett

import java.util.ArrayList;

public class Architecture extends ArrayList <Integer>
{
    //feature
    private String description = "";
    
 
    public Architecture ( String description )
    {
        String [ ] brokenUpDescription = description.split (",");
        
        for ( int dI = 0; dI < brokenUpDescription.length; dI ++ )
            add ( Integer.parseInt ( brokenUpDescription [ dI ] ) );
    }
}
