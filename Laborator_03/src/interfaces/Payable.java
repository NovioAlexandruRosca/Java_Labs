package src.interfaces;

/**
 * Interface representing a payable attraction.
 */
public interface Payable {

    public void setPriceFee(int entryFee);
    
    public default int getPriceFee(){
        return 0;
    }
    
}
