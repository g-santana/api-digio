package br.com.digio.api.repository;

import br.com.digio.api.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    @Query("SELECT c FROM Compra c JOIN FETCH c.cliente JOIN FETCH c.produto ORDER BY c.valorTotal ASC")
    List<Compra> findAllWithClienteProdutoOrderByValor();

    @Query("SELECT c FROM Compra c JOIN FETCH c.cliente JOIN FETCH c.produto WHERE c.produto.anoCompra = :ano ORDER BY c.valorTotal DESC")
    List<Compra> findByAnoCompraOrderByValorDesc(@Param("ano") String ano);

    @Query("SELECT c FROM Compra c JOIN FETCH c.cliente JOIN FETCH c.produto WHERE c.cliente.cpf = :cpf")
    List<Compra> findByClienteCpf(@Param("cpf") String cpf);
}