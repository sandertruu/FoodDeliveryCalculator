package com.intern.fooddeliverycalculator;

public class VehicleForbiddenException extends IllegalArgumentException{
    public VehicleForbiddenException(){ super();}

    public VehicleForbiddenException(String message){ super(message);}

    public VehicleForbiddenException(String message, Throwable cause){
        super(message, cause);
    }
}
