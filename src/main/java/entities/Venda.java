package entities;

public interface Venda {
    /*A interface foi criada para suprir um dos objetivos passados para a nota da atividade,
     * porém, não será usada, pois demos preferência a usar tudo diretamente no banco de dados, dessa forma,
     *  o uso de interface não foi visto como fácil e/ou viável de ser implementado nesse projeto. */
    String getNomeComprador();

    String getNomeProduto();

    String getNomeVendedor();

    float getValorTotal();
}
