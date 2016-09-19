package com.example.mariocifuentes.proyectoiteracion1;

import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mario Cifuentes on 18/09/2016.
 */
public class HeartPattern
{

    private ArrayList<Integer> listaHistorial;
    public String respuesta= null;
    public Boolean enfermo = false;



 public HeartPattern()
 {
     listaHistorial = new ArrayList<Integer>();
     respuesta= new String();

 }

    public HeartPattern(ArrayList ritmos){
        listaHistorial = ritmos;
        patternLookup();
    }

    public void patternLookup()

    {
       for(int i=0; i<listaHistorial.size();i++) {
           if (listaHistorial.size() == 0) {
               respuesta = "Aún no has tomado suficientes datos. Mide tu frecuencia cardiaca con TicTac Heart";
           } else if (!listaHistorial.isEmpty()) {

               double varianza = calcularVarianza(listaHistorial);
               double estandar = calcularMedia(listaHistorial);
               double media = calcularMedia(listaHistorial);

               if (media - estandar > 30 || estandar - media > 30)

               {
                    enfermo = true;
                    respuesta="Puede que estés sufriendo de arritmia, visita a un médico";
               }


               if (media < 60)
               {
                   enfermo =true;
                   respuesta = "Según los datos recolectados, puede que estés sufriendo de Bradicardia (Es decir que tu ritmo cardiaco es lento). Visita a un médico";
               }
               if (media >= 60 && media < 90)
               {
                   enfermo=false;
                   respuesta = "Tu ritmo cardiaco parece estar bastante bien!";
               }

               if (media >= 90)
               {
                   enfermo = true;
                   respuesta = "Tu ritmo está bastante acelerado.Puede que estés sufriendo de taquicardia. Visita a un médico.";
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

    public double calcularVarianza(ArrayList<Integer> lista)
    {
        double media = calcularMedia(lista);
        double temp =  0;
        for(double a:lista)
        {
            temp+=(a-media)*(a-media);


        }
        return temp/lista.size();
    }

    public double calcularDesviación(double varianza)
    {

        return Math.sqrt(varianza);

    }




}
