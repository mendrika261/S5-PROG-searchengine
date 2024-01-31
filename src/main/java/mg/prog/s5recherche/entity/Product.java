package mg.prog.s5recherche.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mg.prog.s5recherche.core.Utils;
import mg.prog.s5recherche.entity.Category;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "quality", nullable = false)
    private Double quality; // 0 to 10

    @Transient
    private Double qualityPrice;

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name must not be empty");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Product name must not be longer than 50 characters");
        }
        this.name = name.toLowerCase();
    }

    public void setPrice(Double price) {
        if (price == null) {
            throw new IllegalArgumentException("Product price must not be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Product price must not be negative");
        }
        this.price = price;
    }

    public void setQuality(Double quality) {
        if (quality == null) {
            throw new IllegalArgumentException("Product quality must not be empty");
        }
        if (quality < 0 || quality > 10) {
            throw new IllegalArgumentException("Product quality must be between 0 and 10");
        }
        this.quality = quality;
    }

    public Double getQualityPrice() {
        if (quality == null || price == null || quality == 0 || price == 0)
            return 0.0;
        return Utils.round2(price / quality);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", category=" + getCategory() +
                ", price=" + getPrice() +
                ", quality=" + getQuality() +
                ", qualityPrice=" + getQualityPrice() +
                '}';
    }
}
