/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author alday
 */
public class Comprobacion {
    private int pzsArticulo;
    private int pzsDetalle;
   

    public Comprobacion() {
    }

    public Comprobacion(int pzsArticulo, int pzsDetalle) {
        this.pzsArticulo = pzsArticulo;
        this.pzsDetalle = pzsDetalle;
    }

    public int getPzsArticulo() {
        return pzsArticulo;
    }

    public void setPzsArticulo(int pzsArticulo) {
        this.pzsArticulo = pzsArticulo;
    }

    public int getPzsDetalle() {
        return pzsDetalle;
    }

    public void setPzsDetalle(int pzsDetalle) {
        this.pzsDetalle = pzsDetalle;
    }

    @Override
    public String toString() {
        return "Comprobacion{" + "pzsArticulo=" + pzsArticulo + ", pzsDetalle=" + pzsDetalle + '}';
    }
       
}
