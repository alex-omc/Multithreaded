package codigo;

import java.math.BigInteger;

public class Hilo extends Thread{
    private int inicio;
    private int ultimo;
    private int incremento;
    private long tiempo;
    BigInteger acum = new BigInteger("1");

    public Hilo(int inicio, int ultimo, int incremento) {
        super();
        this.inicio = inicio;
        this.ultimo = ultimo;
        this.incremento = incremento;
    }

    public BigInteger getAcum() {
        return acum;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void run() {
        calcular();
        String str = imprimirTiempo() + resultadoParcial(0);
        System.out.println(str);
    }

    public String imprimirTiempo() {
        String str = "Hilo " + inicio + " finalizado en " + tiempo + " milisegundos";
        return str;
    }

    public String resultadoParcial(int opc) {
        String str = "";

        if(opc == 0) {
            //No hacer nada
        }else if(opc >= 1) {
            //Grabar en un archivo

            if(opc == 2) {
                str = "\nHilo " +  inicio + " valor " + acum.toString() + "\n";
            }
        }

        return str;
    }

    public void calcular(){
        int i;
        long startTime;
        long endTime;
        String cad;
        //System.out.println("HIlo " + (inicio-1) + " se inicia en " + inSystem.out.println(cant_cores);icio);

        inicio++;
        startTime = System.currentTimeMillis();
        for(i = inicio; i <= ultimo; i += incremento) {
            cad =  i + "";
            acum = acum.multiply(new BigInteger(cad));
        }
        endTime = System.currentTimeMillis();
        tiempo = endTime - startTime;

        // SE regresa la variable a su valor original
        inicio--;
    }

}

