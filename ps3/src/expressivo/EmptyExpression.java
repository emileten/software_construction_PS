package expressivo;

/**
 * Represents the empty Expression. The empty Expression is equivalent to zero. 
 */
public class EmptyExpression implements Expression {

    public EmptyExpression(){
        
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
    
    @Override
    public String toString(){       
        return "";        
    }
    
    @Override 
    public boolean equals(Object thatObject){
        
        if (thatObject instanceof EmptyExpression){
            return true;
        } else {
            return false;
        }
        
    }
    
    @Override
    public int hashCode(){       
        return 1;       
    }

}
