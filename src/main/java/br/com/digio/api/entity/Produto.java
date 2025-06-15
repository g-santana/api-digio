package br.com.digio.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    private Integer codigo;

    @Column(name = "tipo_vinho")
    private String tipoVinho;

    private BigDecimal preco;
    private String safra;

    @Column(name = "ano_compra")
    private Integer anoCompra;

    protected Produto() {}

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTipoVinho() {
        return tipoVinho;
    }

    public void setTipoVinho(String tipoVinho) {
        this.tipoVinho = tipoVinho;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getSafra() {
        return safra;
    }

    public void setSafra(String safra) {
        this.safra = safra;
    }

    public Integer getAnoCompra() {
        return anoCompra;
    }

    public void setAnoCompra(Integer anoCompra) {
        this.anoCompra = anoCompra;
    }

    @Override
    public String toString() {
        return "Produto(" +
                "codigo=" + codigo +
                ", tipoVinho='" + tipoVinho + '\'' +
                ", preco=" + preco +
                ", safra='" + safra + '\'' +
                ", anoCompra=" + anoCompra +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(codigo, produto.codigo) && Objects.equals(tipoVinho, produto.tipoVinho) && Objects.equals(preco, produto.preco) && Objects.equals(safra, produto.safra) && Objects.equals(anoCompra, produto.anoCompra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, tipoVinho, preco, safra, anoCompra);
    }
}