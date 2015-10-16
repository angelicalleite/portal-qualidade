//package teste;

/**
 * Created by francisco on 15/11/14.
 */
public class Teste {
    public static void main(String[] args) {
        /*
        String retorno = "Asteraceae Bercht. & J.Presl";
        String avalidado = "ASTERACEAE";
        boolean b = retorno.toUpperCase().contains(avalidado.toUpperCase());
        System.out.println(b);
        */
        // teste com espaços
        String espaco = "algo";
        String sp[] = espaco.split(" ");
        for (String s : sp)
            System.out.println(s);
    }
}
