package com.example.mariocifuentes.proyectoiteracion1;

import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mario Cifuentes on 18/09/2016.
 */
public class HeartPattern
{

    private ArrayList<Integer> listaHistorial;
    String respuesta= null;



 public HeartPattern()
 {
     listaHistorial = new ArrayList<Integer>();
     respuesta= new String();

 }
    public void patternLookup()

    {
       for(int i=0; i<listaHistorial.size();i++)
       {
           if(listaHistorial.size()== 0)
           {
               respuesta="Aún no has tomado suficientes datos. Mide tu frecuencia cardiaca con TicTac Heart";
           }

           else if(!listaHistorial.isEmpty())
           {
                double valor = calcularMedia(listaHistorial);
                 if(valor<60)
                 {
                     respuesta= "Según los datos recolectados, puede que estés sufriendo de Bradicardia (Es decir que tu ritmo cardiaco es lento). Visita a un médico";
                 }
                if(valor>=60&&valor<90);
               {
                   respuesta= "Tu ritmo cardiaco parece estar bastante bien!.";
               }

               if(valor>=90);
               {
                   respuesta= "Tu ritmo está bastante acelerado.Puede que estés sufriendo de taquicardia. Visita a un médico.";
               }

           }

           }

       }




    public double calcularMedia(ArrayList<Integer> lista)
    {
       Integer sum =0;
        if(!lista.isEmpty())
        {
            for(Integer list:lista)
            {
                sum+=list;
            }
            return sum.doubleValue()/lista.size();
        }
        return sum;



    }




}
