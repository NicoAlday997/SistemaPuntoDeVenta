/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author alday
 */

import datos.ComprobacionDAO;
import entidades.Comprobacion;

public class ComprobacionControl
{

	private final ComprobacionDAO DATOS;
	
	public ComprobacionControl()
	{
            this.DATOS=new ComprobacionDAO();
	}
	
        public Comprobacion obtenerComprobacion(){
            Comprobacion comp=DATOS.obtenerComprobacion();
            return comp;
        }
	 
}

