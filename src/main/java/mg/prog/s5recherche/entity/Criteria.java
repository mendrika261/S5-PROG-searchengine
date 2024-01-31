package mg.prog.s5recherche.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Criteria {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "type_criteria", nullable = false)
    private String typeCriteria; // numeric, boolean, string

    @Column(name = "best_value", nullable = false)
    private String bestValue = "+";

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Criteria name must not be empty");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException("Criteria name must not be longer than 50 characters");
        }
        this.name = name.toLowerCase();
    }

    public void setBestValue(String bestValue) {
        if (bestValue == null || bestValue.isEmpty()) {
            throw new IllegalArgumentException("Criteria best value must not be empty");
        }
        if (!bestValue.equals("+") && !bestValue.equals("-")) {
            throw new IllegalArgumentException("Criteria best value must be + or -");
        }
        this.bestValue = bestValue;
    }

    public void setTypeCriteria(String typeCriteria) {
        if (typeCriteria == null || typeCriteria.isEmpty()) {
            throw new IllegalArgumentException("Criteria type must not be empty");
        }
        if (!typeCriteria.equals("numeric") && !typeCriteria.equals("boolean") && !typeCriteria.equals("string")) {
            throw new IllegalArgumentException("Criteria type must be numeric, boolean or string");
        }
        this.typeCriteria = typeCriteria;
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", typeCriteria='" + typeCriteria + '\'' +
                ", bestValue='" + bestValue + '\'' +
                '}';
    }
}
