/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.PuestoDAO;
import entidades.Puesto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alday
 */
public class PuestoControl {
    
        private final PuestoDAO DATOS;
	private Puesto obj;
	private DefaultTableModel modeloTabla;
	public int registrosMostrados;
        
        public PuestoControl()
        {
            this.DATOS=new PuestoDAO();
            this.obj=new Puesto();
            this.registrosMostrados=0;
        }
        
        public DefaultTableModel listar()
	{
		List<Puesto> lista=new ArrayList();
		lista.addAll(DATOS.listar());
		
		String[] titulos= {"Id", "Nombre", "Descripcion"};
		
		this.modeloTabla=new DefaultTableModel(null, titulos);
	
		String[] registro=new String[3];
		
		this.registrosMostrados=0;
		
		for(Puesto item:lista)
		{	
			registro[0]=Integer.toString(item.getId());
			registro[1]=item.getNombre();
			registro[2]=item.getDescripcion();
			this.modeloTabla.addRow(registro);
			this.registrosMostrados=this.registrosMostrados+1;
			
		}
		
		return this.modeloTabla;
	}
        
        public int total()
	{
		return DATOS.total();
	}
	
	public int totalMostrados()
	{
		return this.registrosMostrados;
	}

}
