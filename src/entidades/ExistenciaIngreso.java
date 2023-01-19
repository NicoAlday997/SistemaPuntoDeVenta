/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author alday
 */
public class ExistenciaIngreso {
    private int id;
    private int ingresoId;

    public ExistenciaIngreso() {
    }

    public ExistenciaIngreso(int id, int ingresoId) {
        this.id = id;
        this.ingresoId = ingresoId;
    }
    
    public ExistenciaIngreso(int ingresoId){
        this.ingresoId=ingresoId;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIngresoId() {
        return ingresoId;
    }

    public void setIngresoId(int ingresoId) {
        this.ingresoId = ingresoId;
    }
    
 
}
