package br.com.digio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Produto {
    @Id
    private Integer codigo;

    @Column(name = "tipo_vinho")
    private String tipoVinho;

    private BigDecimal preco;
    private String safra;

    @Column(name = "ano_compra")
    private String anoCompra;
}